
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
    public float balance;

    private int tempNum = 1000;
    private ArrayList<Integer> accountNumbers = new ArrayList<>();

    public Account(String username){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.balance = 1000.0;
    }

    public Account(String username, float balance){
        this.accountNumber = genAccountNumber();
        this.username = username;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public String getUserName(){
        return username;
    }

    public float getBalance(){
        return balance;
    }

    public void setBalance(float balance){
        this.balance = balance;
    }
    public void deposit(float deposit){
        this.balance = balance + deposit;
    }

    public void withdraw(float withdrawal){
        this.balance = balance - withdrawal;
    }
    
    public String genAccountNumber(){
        Random rand = new Random();

        int randNum = tempNum + rand.nextInt(1000);
        accountNumbers.add(randNum);

        for (int i = 0; i < accountsNumbers.size(); i++){
            if (randNum == accountNumbers.get(i)){
                randNum = Integer.parseInt(genAccountNumber());
                accountNumbers.add(randNum);
            }
        }
        return Integer.toString(randNum);
    }
}
