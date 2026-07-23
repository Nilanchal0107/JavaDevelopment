abstract class Notification {
    void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
    abstract void send(String msg);
}

class EmailNotification extends Notification {
    void send(String msg) {
        System.out.println("Email: " + msg);
    }
}

interface Notifiable {
    void send(String msg);
}

class SMSNotification implements Notifiable {
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}

class Demo {
    public static void main (String A[]) {
        Notification a = new EmailNotification();
        a.send("Hello world from Email");

        Notifiable b = new SMSNotification();
        b.send("Hello world from SMS");
    }
}

/*

which approach would you use if you also need a shared `log()` method that all notifications should reuse?
I will use abstract beacuse it gives us options for storing concrete methods and instance variables also.
Interface restricts use of concrete method as well as you can only store final/static variables only.

*/