class InsufficientFundsException extends Exception {
    InsufficientFundsException(String msg) {
        super(msg);
    }
}

class InvalidAmountException extends RuntimeException {
    InvalidAmountException() {
        super("Amount must be positive");
    }
}

class BankAccount {
    private double balance;

    BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new InvalidAmountException();
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException();
        if (amount > balance) throw new InsufficientFundsException("Insufficient funds: balance is " + balance);
        balance -= amount;
        System.out.println("Withdrawn: " + amount);
    }

    double getBalance() {
        return balance;
    }
}

class Demo {
    public static void main(String[] args) {
        BankAccount obj = new BankAccount(500.00);

        try {
            obj.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Operation complete");
        }

        try {
            obj.withdraw(200);
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Operation complete");
        }

        try {
            obj.withdraw(400);
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Operation complete");
        }

        System.out.println("Final balance: " + obj.getBalance());
    }
}