class BankAccount {
    private double balance;
    private String owner;

    void setBalance (double amount) {
        if (amount < 0) {
            System.out.println("Invalid amount");
        }
        else {
            balance += amount;
        }
    }

    void setOwner (String handler) {
        owner = handler;
    }

    double getBalance() {
        return balance;
    }

    String getOwner() {
        return owner;
    }
}

class Test {
    public static void main (String A[]) {
        BankAccount b1 = new BankAccount();
        b1.setBalance(5000.0);
        b1.setBalance(-5000.0);

        System.out.println("Balance: " + b1.getBalance());
    }
}