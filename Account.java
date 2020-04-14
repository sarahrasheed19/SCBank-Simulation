
/**
 * Account.java contains the object used for messaging between client and server.
 *
 * Author: Sarah Rasheed
 * Partner: Carina Caraballo
 * CSC 450: Bank Simulation
 */

import java.util.Random;
import java.util.ArrayList;

public class Account
{
    public String accountNumber;
    public String username;
    public double balance;
    public int checkout = 0;

    private int tempNum = 1000;
    private ArrayList<Integer> accountNumbers = new ArrayList<Integer>();

    public Account(String username){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.balance = 1000;
        this.checkout = checkout;
    }

    public Account(String username, double balance){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.balance = balance;
        this.checkout = checkout;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getUserName(){
        return username;
    }

    public double getBalance(){
        return balance;
    }

    public int getCheckOut(){
        return checkout;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }
    public void deposit(double deposit){
        this.balance = balance + deposit;
    }

    public void withdraw(double withdrawal){
        this.balance = balance - withdrawal;
    }

    public String genAccountNumber(){
        Random rand = new Random();

        int randNum = tempNum + rand.nextInt(1000);
        
        for (int i = 0; i < accountNumbers.size(); i++){
            if (randNum == accountNumbers.get(i)){
                randNum = Integer.parseInt(genAccountNumber());
                accountNumbers.add(randNum);
            }
        }
        
        accountNumbers.add(randNum);
        return Integer.toString(randNum);
    }

    public void signIn(Account account){
        this.checkout = 1;
    }

    public void signOut(Account account){
        this.checkout = 0;
    }

    public boolean ifSignedIn(Account account){
        if(account.getCheckOut() == 1){
            return true;
        }
        else if(account.getCheckOut() == 0){
            return false;
        }
        
        return false;
    }
}
