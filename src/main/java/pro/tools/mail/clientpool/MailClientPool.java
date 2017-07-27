package pro.tools.mail.clientpool;

import com.google.common.collect.Queues;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolStr;
import pro.tools.mail.exception.MailException;
import pro.tools.mail.pojo.MailSend;
import pro.tools.time.DatePlus;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

/**
 * Created on 17/7/27 21:43 星期四.
 *
 * @author sd
 */
public class MailClientPool {
    private static final Logger log = LoggerFactory.getLogger(MailClientPool.class);

    private Properties prop;
    private String user;
    private String password;
    private String userMail;
    private Session session;
    private Queue<MailSend> queue = Queues.newConcurrentLinkedQueue();


    public MailClientPool(Boolean mailDebug, String mailHost, String mailTransportProtocol) throws MailException {
        try {
            init(mailDebug, mailHost, mailTransportProtocol, false, false);
        } catch (Exception e) {
            throw new MailException(prop, e.getMessage(), e);
        }
    }

    public MailClientPool(Boolean mailDebug, String mailHost, String mailTransportProtocol, Boolean mailAuth, Boolean mailSsl) throws MailException {
        try {
            init(mailDebug, mailHost, mailTransportProtocol, mailAuth, mailSsl);
        } catch (Exception e) {
            throw new MailException(prop, e.getMessage(), e);
        }
    }

    public String getUser() {
        return user;
    }

    public MailClientPool setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MailClientPool setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserMail() {
        return userMail;
    }

    public MailClientPool setUserMail(String userMail) {
        this.userMail = userMail;
        return this;
    }

    /**
     * @param mailDebug             开启debug调试，以便在控制台查看
     * @param mailHost              设置邮件服务器主机名
     * @param mailTransportProtocol 发送邮件协议名称
     * @param mailAuth              发送服务器需要身份验证
     * @param mailSsl               开启SSL加密，否则会失败
     */
    private void init(Boolean mailDebug, String mailHost, String mailTransportProtocol, Boolean mailAuth, Boolean mailSsl) throws Exception {
        prop = new Properties();
        prop.setProperty("mail.debug", mailDebug.toString());
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.smtp.auth", mailAuth.toString());
        prop.setProperty("mail.transport.protocol", mailTransportProtocol);


        prop.put("mail.smtp.ssl.enable", mailSsl);
        if (mailSsl) {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.socketFactory", sf);
        }

        // 创建session
        session = Session.getInstance(prop);
    }

    public void send(MailSend mailSend) {
        queue.add(mailSend);
    }

    public void start() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    sendMail();
                }
            });
            thread.setName("sendMail-" + (i + 1));
            thread.start();
        }
    }

    private void sendMail() {
        MailSend mailSend = queue.poll();
        if (mailSend != null) {

            if (ToolStr.isBlank(user)
                    || ToolStr.isBlank(password)
                    || ToolStr.isBlank(userMail)) {
                log.warn("USER:" + user + "\tPASSWORD:" + password + "\tUSER_MAIL:" + userMail);
            }

            // 发送邮件
            try (
                    // 通过session得到transport对象
                    Transport ts = session.getTransport()) {
                // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
                ts.connect(prop.getProperty("mail.host"), user, password);//后面的字符是授权码
                // 创建邮件
                Message message = createSimpleMail(session, mailSend);
                // 指明邮件的发件人
                message.setFrom(new InternetAddress(userMail));
                ts.sendMessage(message, message.getAllRecipients());
                ts.close();
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.warn("发送失败", e);
                send(mailSend);
            }
        }
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    private static MimeMessage createSimpleMail(Session session, final MailSend mailSend) throws MessagingException, UnsupportedEncodingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        List<String> toList = mailSend.getToList();
        if (toList != null && toList.size() > 0) {
            Address[] addresses = new Address[toList.size()];
            for (int i = 0; i < toList.size(); i++) {
                addresses[i] = new InternetAddress(toList.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, addresses);
        }
        // 邮件的标题
        message.setSubject(mailSend.getSubject());
        // 发送时间
        message.setSentDate(new DatePlus().toDate());
        Multipart multipart = new MimeMultipart();

        // 正文
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(mailSend.getContent(), StrConst.DEFAULT_CHARSET_NAME);
        multipart.addBodyPart(mbp, 0);

        List<String> attachFiles = mailSend.getAttachFiles();

        // 附件
        if (attachFiles != null && attachFiles.size() > 0) {
            for (String attachFile : attachFiles) {
                mbp = new MimeBodyPart();
                //得到数据源
                FileDataSource fds = new FileDataSource(attachFile);
                //得到附件本身并至入BodyPart
                mbp.setDataHandler(new DataHandler(fds));
                //解决附件乱码
                String filename = MimeUtility.encodeText(fds.getName());
                mbp.setFileName(filename);  //得到文件名同样至入BodyPart

                multipart.addBodyPart(mbp);
            }
        }

        // 设置邮件消息的主要内容
        message.setContent(multipart);


        // 返回创建好的邮件对象
        return message;
    }
}
