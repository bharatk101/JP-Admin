package bharat.example.com.jp.models;

/**
 * Created by bharat on 3/1/18.
 */

public class Sender {

    public String to;
    public Notification notification;

    public Sender(String to,Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public Sender() {
    }
}
