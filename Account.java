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
    //variables for account
    public String accountNumber;
    public String username;
    private String password;
    public double balance;
    public int checkout = 0; //checkout determines if the client is signed in

    //variables to be used to genAccountNumber()
    private int tempNum = 1000;
    private ArrayList<Integer> accountNumbers = new ArrayList<Integer>();

    //creates Account object with username and password as parameters. Default
    //balance is 1000
    public Account(String username, String password){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.password = password;
        this.balance = 1000;
    }

    //creates Account object with username, password, and balance as parameters
    public Account(String username, String password, double balance){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    //returns accountNumber
    public String getAccountNumber(){
        return accountNumber;
    }

    //returns username
    public String getUserName(){
        return username;
    }

    //returns password, password is a private variable
    public String getPassword(){
        return password;
    }

    //returns balance
    public double getBalance(){
        return balance;
    }

    //getCheckout() returns 1 or 0 for signedin status
    public synchronized int getCheckOut(){
        return checkout;
    }

    //sets the balance to a new balance given from the parameter
    public void setBalance(double balance){
        this.balance = balance;
    }

    //sets password from parameter
    public void setPassword(String pw){
        this.password = pw;
    }

    //adds value in parameter to current balance, hence a deposit
    public void deposit(double deposit){
        this.balance = balance + deposit;
    }

    //subtracts value in parameter from current balance, hence a withdrawal
    public void withdraw(double withdrawal){
        this.balance = balance - withdrawal;
    }

    //random account number generator
    public String genAccountNumber(){
        Random rand = new Random();

        //adds random int to temp to create random account numbers
        int randNum = tempNum + rand.nextInt(1000);

        //checks to see if the generated account number already exists in private
        //arraylist that keeps track of all existing account numbers
        for (int i = 0; i < accountNumbers.size(); i++){
            if (randNum == accountNumbers.get(i)){
                randNum = Integer.parseInt(genAccountNumber()); //if the num exists, generate a new account number
                accountNumbers.add(randNum); //add the new number to the arraylist
            }
        }

        accountNumbers.add(randNum); //add the new number to arraylist for tracking
        return Integer.toString(randNum); //return this to the Account constructors for new objects
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
