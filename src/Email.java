import java.time.LocalDateTime;
import java.util.Locale;

public class Email {
    private String date;
    private String sender;
    private String receiver;
    private String theme;

    public Email(String date, String sender, String receiver, String theme) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.theme = theme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Email{" +
                "date='" + date + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }
}
