package unibl.etf.ip.webshop_ip2023.model.dto;

public class EmailContent {
    public static final String ACTIVATION_EMAIL_SUBJECT="WebShop aktivacija naloga!";
    public static final String ACTIVATION_EMAIL_CONTENT="Zdravo USER, Vaš kod za aktivaciju naloga je CODE!"+System.lineSeparator()+System.lineSeparator()+System.lineSeparator()+"WebShop 2023 ";
    private String recipient;
    private String subject;
    private String content;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
public EmailContent(){}
    public EmailContent(String recipient, String user,int code) {
        this.recipient = recipient;
        this.subject = ACTIVATION_EMAIL_SUBJECT;
        this.content = ACTIVATION_EMAIL_CONTENT.replace("USER",user).replace("CODE",Integer.toString(code));
    }
    public EmailContent(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailContent{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
