package pro.tools.mail.pojo;

import java.util.List;

/**
 * Created on 17/7/27 21:36 星期四.
 *
 * @author sd
 */
public class MailSend implements java.io.Serializable {

    //接受方邮箱地址列表
    private List<String> toList;
    //标题
    private String subject;
    //内容
    private String content;
    //附件文件列表
    private List<String> attachFiles;


    public List<String> getToList() {
        return toList;
    }

    public MailSend setToList(List<String> toList) {
        this.toList = toList;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailSend setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailSend setContent(String content) {
        this.content = content;
        return this;
    }

    public List<String> getAttachFiles() {
        return attachFiles;
    }

    public MailSend setAttachFiles(List<String> attachFiles) {
        this.attachFiles = attachFiles;
        return this;
    }
}
