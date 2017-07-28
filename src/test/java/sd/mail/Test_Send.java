package sd.mail;

import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.mail.clientpool.MailClientPool;
import pro.tools.mail.exception.MailException;
import pro.tools.mail.pojo.MailSend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17/4/8 13:09 星期六.
 *
 * @author SeanDragon
 */
public class Test_Send {

    private static final Logger logger = LoggerFactory.getLogger(Test_Send.class);

    @Test
    public void test1() {

        String host = "smtp.qq.com";        // 发送邮件的服务器的IP
        String port = "465";    // 发送邮件的服务器的端口

        String from = "451482067@qq.com";        // 邮件发送者的地址
        String userName = "451482067";    // 登陆邮件发送服务器的用户名
        String password = "sawobshrstasbjfg";    // 登陆邮件发送服务器的密码

        List<String> to = new ArrayList<String>();            // 邮件接收者的地址
        to.add("syl8023who@gmail.com");

        boolean validate = true;    // 是否需要身份验证

        String subject = "标题test111";        // 邮件标题
        String content = "内容test111";        // 邮件的文本内容
        String[] attachFileNames = new String[0];    // 邮件附件的文件名

        //ToolMail.sendTextMail(host, port, validate, userName, password, from, to, subject, content, null);
        while (true) ;
    }

    private static MailClientPool mailClientPool;

    @BeforeClass
    public static void bc() {
        try {
            mailClientPool = new MailClientPool(false, "smtp.qq.com", "smtp", true, true);
            mailClientPool.setUser("451482067")
                    .setPassword("bprfftgtnsxfbife")
                    .setUserMail("451482067@qq.com");
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws MailException {
        MailSend mailSend = new MailSend();
        mailSend.setToList(Lists.newArrayList("995956062@qq.com"))
                .setSubject("标题")
                .setContent("内容")
        //.setAttachFiles(Lists.newArrayList("/Users/sd/Pictures/archive/background/starry-sky-1654074.jpg"))
        ;
        mailClientPool.start();
        mailClientPool.send(mailSend);
        while (true) ;
    }

    //@Test
//    public void test2() throws Exception {
//        Properties prop = new Properties();
//        prop.setProperty("mail.transport.protocol", "smtp");
//        prop.setProperty("mail.smtp.host", "smtp.qq.com");
//        prop.setProperty("mail.smtp.auth", "true");
//        prop.put("mail.smtp.port", "25");
//        prop.setProperty("mail.debug", "true");
//        PopAuthenticator popAuthenticator = new PopAuthenticator("1274444444@qq.com", "4444444");
//        //Authenticator authenticator = (Authenticator) popAuthenticator;
//        //创建会话
//        Session session = Session.getInstance(prop, popAuthenticator);
//        //填写信封写信
//        Message msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress("1271099894@qq.com"));
//        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getEmail()));
//        msg.setSubject(user.getUsername() + "激活邮箱!");
//        msg.setText(user.getUsername() + ",你好请到这个地址激活你的账号：http://www.estore.com/ActiveServlet?activecode=" + user.getActivecode());
//        //验证用户名密码发送邮件
//        Transport transport = session.getTransport();
////transport.connect("1274444444@qq.com","4444444");
//        transport.send(msg);
//    }
}
