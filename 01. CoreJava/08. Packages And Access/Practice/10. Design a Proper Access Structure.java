class BankAccount {
    private double balance;
    private String accountNumber;
    protected String ownerName;
    public String bankName;

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance;
        }
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    protected void generateStatement() {
        System.out.println(balance);
        System.out.println(accountNumber);
        System.out.println(ownerName);
        System.out.println(bankName);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public void setInterestRate(double interestRate) {
        if (interestRate > 0) {
            this.interestRate = interestRate;
        }
    }

    protected void generateStatement() {
        super.generateStatement();
        System.out.println(interestRate);
    }

    
}

class Test {
    public static void main (String A[]) {
        SavingsAccount a = new SavingsAccount();
        a.setBalance(50000.00);
        a.setAccountNumber("12ASN");
        a.ownerName = "Navin";
        a.bankName = "APSHIT";

        a.setInterestRate(9.0);

        a.generateStatement();

    }
}