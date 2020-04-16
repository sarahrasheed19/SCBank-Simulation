/* SCProtocol.java
* 
* This class is dedicating to calculating, based on client input, 
* what the bank's response will be.
* 
* Author: Carina Caraballo
* Partner: Sarah Rasheed
* Class: CSC450
*/

import java.net.*;
import java.io.*;

public class SCProtocol {

    private static final int DISCONNECTED = 0; //client and server are disconnected, no interaction yet 
    private static final int CONNECTED = 1; //client is connected 
    private static final int PROCESSING = 2; //machine checking username
    private static final int CHECKINGPW = 3; //machine checking password
    private static final int WITHDRAW = 8; //withdraw mode
    private static final int DEPOSIT = 9; //for deposits 
    private static final int SIGNOFF = -1; //ending this
    private String[] username = {"admin", "client2"};
    private String[] password = {"password", "pwMoReStr"};
    private Account acct = new Account("admin", "123abc"); //admin account    

    private int STATE = 0; //we start at DISCONNECTED


    //first input processing if it is a username or password
    //parameter is a String
    public String processInput(String signon) {
        String output = null;
        
        if(STATE == CHECKINGPW){
            System.out.println(signon);
            if(signon.compareTo(acct.getPassword()) == 0){
                output = "Welcome. What can we help you with today? \n(0) See Acct Number"+
                         "(1) See Acct Balance (2) Withdraw Money (3) Deposit Money (4) Sign Out";
                STATE = CONNECTED;               
            } else { if(signon.compareTo(acct.getPassword()) != 0) { 
                output = "Wrong Password! Please enter valid password or (4) to exit.";
                STATE = CHECKINGPW;
            }}
        }
        if(STATE == PROCESSING){
            if(signon.compareTo(acct.getUserName()) == 0){
                output = "Welcome back " + signon + ". Please enter your password.";
                STATE = CHECKINGPW;
            } else { if(signon.compareTo(acct.getUserName()) != 0){
                output = "Invalid username! Try again or (4) to exit.";
                STATE = PROCESSING;
            }}
        }
        if(STATE == DISCONNECTED){ //prompt user input username
            output = "Welcome to Sarah & Carina Bank Incorporated! Please enter your username.";
            STATE = PROCESSING;
        }
        return output;
    }


 //now that we have the client connected, inputs will all be numerical
    public String processInput(float option){
        String output = null;
        if(STATE == WITHDRAW){
                //check that the withdraw amount is not more than the account balance
                if(option <= acct.getBalance()){
                    System.out.println("withdrawing");
                    acct.withdraw(option);
                    output = "Your remaining balance now is: " + acct.getBalance() +
                             "\n Would you like to (2) Withdraw More Money? (3) Deposit Money? (4) Sign Out?";
                    STATE = CONNECTED;
                } else { if(option> acct.getBalance()){
                    output = "ERROR: You don't have this much money" + acct.getUserName() + 
                             "\nWould you like to: \n(1) See Acct Balance? (2) Withdraw Money? "+
                             "(3) Deposit Money? (4) Sign Out?";
                    STATE = CONNECTED;
                }}
                //should we just have the account be in protocol? 
                if(option == 4){
                    output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                    STATE = SIGNOFF;
                    //break;
                } 
             } else {
                 if(STATE == DEPOSIT){
                     //check that the withdraw amount is not more than the account balance
                     if(option > 0){
                        System.out.println("depositing");
                        acct.deposit(option);// send negative value to method that amends balance
                        output = "Your balance is now: " + acct.getBalance() +
                        "\nWould you like to (2) Withdraw Money? (3) Deposit More Money? (4) Sign Out?";
                        STATE = CONNECTED;
                    } else { if(option <= 0){
                        output = "ERROR: INVALID DEPOSIT!" + 
                                 "\nWould you like to: \n(1) See Acct Balance (2) Withdraw Money? "+
                                 "(3) Deposit Money? (4) Sign Out?";
                        STATE = CONNECTED;
                    }}
                    //should we just have the account be in protocol? 
                    if(option == 4){
                        output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                        STATE = SIGNOFF;
                        //break;
                    }
            
                }
        }
        
        //this minimalist version is still using an account object
        //account object has acct number and balance 
        //so if user wants to see account number
        if(STATE == CONNECTED){
            if(option == 0) {
                //readAcctNo and set it equal to output
                output = "Your account number is: " + acct.getAccountNumber() +
                         "\nWould you like to: \n(1) See Acct Balance? (2) Withdraw Money? "+
                         "(3) Deposit Money? (4) Sign Out?";
                STATE = CONNECTED;
            }
            if(option == 1) {
                output = "Your account balance is: " + acct.getBalance() +
                         "\nWould you like to: \n(2) Withdraw Money? (3) Deposit Money? (4) Sign Out?";
                STATE = CONNECTED;
            }
            if(option == 2){
                output = "How much would you like to withdraw? Enter amount or (4) if you'd like to exit.";
                STATE = WITHDRAW;
            }
            if(option == 3){
                output = "How much would you like to deposit? Enter amount or (4) if you'd like to exit.";
                STATE = DEPOSIT;
            }
            if(option == 4){
                output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                STATE = SIGNOFF;
                //break;
            }
            
           
        } 
        
        return output;
    }
}
