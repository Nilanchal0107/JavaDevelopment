class BankAccount {
    private int balance;

    BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        if (amount <= balance) 
            balance -= amount;
        else
            System.out.println("Insufficient funds");
    }

    public int getBalance() {
        return balance;
    }
}

class Demo {
    public static void main (String A[]) throws InterruptedException {
        BankAccount obj = new BankAccount(1000);

        Runnable a = () -> {
            for (int i = 0; i < 10; i++)
                obj.deposit(500);
        };

        Runnable b = () -> {
            for (int i = 0; i < 10; i++)
                obj.deposit(500);
        };

        Runnable c = () -> {
            for (int i = 0; i < 20; i++)
                obj.withdraw(200);
        };

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);
        Thread t3 = new Thread(c);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(obj.getBalance());
    }
}