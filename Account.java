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
    private String password;
    public double balance;
    public int checkout = 0;

    private int tempNum = 1000;
    private ArrayList<Integer> accountNumbers = new ArrayList<Integer>();

    public Account(String username, String password){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.password = password;
        this.balance = 1000;
    }

    public Account(String username, String password, double balance){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getUserName(){
        return username;
    }
 
    public String getPassword(){
        return password;
    }

    public double getBalance(){
        return balance;
    }

    public synchronized int getCheckOut(){
        return checkout;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void setPassword(String pw){
        this.password = pw;
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

    public synchronized void signIn(Account account){
        this.checkout = 1;
    }

    public synchronized void signOut(Account account){
        this.checkout = 0;
    }

    public synchronized boolean ifSignedIn(Account account){
        boolean ifSignedIn = true;
       
        if(account.getCheckOut() == 1){
            ifSignedIn = true;
        }
        else if(account.getCheckOut() == 0){
            ifSignedIn = false;
        }
        
        return ifSignedIn;
    }
}
