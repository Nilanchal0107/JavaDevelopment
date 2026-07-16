
class BankAccount {

    double balance;

    void deposit (double amount) {
        balance += amount;
        System.out.println("Current Balance: " + balance);
    }

    void withdraw (double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds");
        }
        else {
            balance -= amount;
            System.out.println("Current Balance: " + balance);
        }
    }

    double getBalance() {
        return balance;
    }
}

class BankAccountClass {
    public static void main (String A[]) {
        BankAccount holder1 = new BankAccount();
        holder1.deposit(10000.00);
        holder1.withdraw(8999.99);

        BankAccount holder2 = new BankAccount();
        holder2.deposit(8000.00);
        holder2.withdraw(8999.99);

        System.out.println("Bank Account of Holder 1: " + holder1.getBalance());
        System.out.println("Bank Account of Holder 2: " + holder2.getBalance());
    }
}