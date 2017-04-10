package sd.mail;

import org.junit.Test;
import pro.tools.http.ToolMail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17/4/8 13:09 星期六.
 *
 * @author SeanDragon
 */
public class Test_Send {

    @Test
    public void test1() {
        String host = "smtp.163.com";        // 发送邮件的服务器的IP
        String port = "25";    // 发送邮件的服务器的端口

        String from = "dongcb678@163.com";        // 邮件发送者的地址
        String userName = "dongcb678@163.com";    // 登陆邮件发送服务器的用户名
        String password = "xxx";    // 登陆邮件发送服务器的密码

        List<String> to = new ArrayList<String>();            // 邮件接收者的地址
        to.add("150584428@qq.com");

        boolean validate = true;    // 是否需要身份验证

        String subject = "标题test111";        // 邮件标题
        String content = "内容test111";        // 邮件的文本内容
        String[] attachFileNames = new String[]{"D:/code.jpg"};    // 邮件附件的文件名

        ToolMail.sendTextMail(host, port, validate, userName, password, from, to, subject, content, attachFileNames);
    }
}
