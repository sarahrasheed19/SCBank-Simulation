/* SCProtocol.java
*
* This class is dedicating to calculating, based on client input,
* what the bank's response will be.
*
* Author: Carina Caraballo
* Partner: Sarah Rasheed
* Class: CSC450
*/

//import java.net.*;
//import java.io.*;

public class SCProtocol {

    private static final int DISCONNECTED = 0; //client and server are disconnected, no interaction yet
    private static final int CONNECTED = 1; //client is connected
    private static final int PROCESSING = 2; //machine checking username
    private static final int CHECKINGPW = 3; //machine checking password
    private static final int CREATEACCT = 4; //
    private static final int WITHDRAW = 8; //withdraw mode
    private static final int DEPOSIT = 9; //for deposits 
    private static final int TRANSFER = 6;
    private static final int SIGNOFF = -1; //ending this
    public DBManager dbMan = new DBManager("SCBank", "123pass"); //db with our account information
    public static int count = 3;
    private String unsql, pwsql, fname, lname = null;
    private float initdep = 0;
    private String username = null;
    private float trans = 0;

    private int STATE = 0; //we start at DISCONNECTED


    //first input processing if it is a username or password
    //parameter is a String
    public String processInput(String signon) {
        String output = null;
        //Once the user name is validated, password has to be validated
        if(STATE == TRANSFER) {
        	if(dbMan.isSignedIn(signon)==0){
            	dbMan.transfer(username, signon, trans);
            	output = "Transfer complete! What would you like to do next?" + 
            			"(1) See Acct Balance (2) Withdraw Money (3) Deposit Money (4) Transfer More Money (5) Sign Out";
        	} else {
        		output = "Account busy. Transfer not possible. What would you like to do next? "+ 
        				"(1) See Acct Balance (2) Withdraw Money (3) Deposit Money (4) Transfer More Money (5) Sign Out";
        	}
        	STATE = CONNECTED;
        }
        
        if(STATE == CREATEACCT) {
       
        	if(count == 0) {
        		lname = signon;
        		output = "A deposit is mandatory for account creation. How much would you like to deposit today?";
        		STATE = CREATEACCT;
        		count--;
        	}
        	if(count == 1) {
        		fname = signon;
        		output = "Please enter the first 15 letters of your last name: ";
        		STATE = CREATEACCT;
        		count--;
        	}
        	if(count == 2) {
        		pwsql = signon;
        		output = "Please enter the first 15 letters of your first name: ";
        		STATE = CREATEACCT;
        		count--;
        	}	
        	if(count == 3) {
        		unsql = signon;
        		output = "Please enter your desired password (Requirements: Include letters). ";
        		STATE = CREATEACCT;
        		count --;
        	}	
        	
        	
        }
        if(STATE == CHECKINGPW){
            System.out.println(signon);
            //if password is valid, state changes to connected and we move to other processInput method
            if(dbMan.passwordCorrect(signon, username)){
            	//update the state of the account in database
            	dbMan.changeCO(username);
                output = "Welcome. What can we help you with today? \n(0) See Acct Number"+
                         "(1) See Acct Balance (2) Withdraw Money (3) Deposit Money (4) Transfer Money (5) Sign Out";
                STATE = CONNECTED;               
            } else { 
                output = "Wrong Password! Please enter valid password or (4) to exit.";
                STATE = CHECKINGPW;
            }
        }
       
        //we are waiting for user name here. Only options are to enter correct user name or to exit.
        if(STATE == PROCESSING){
            if(dbMan.usernameExists(signon)){
            	username = signon;
                if(dbMan.isSignedIn(username)==0){
                    output = "Welcome back " + username + ". Please enter your password.";
                    STATE = CHECKINGPW;
                } else{ 
                	output = "Error: Account in use! Please try again later. " ;
                	STATE = DISCONNECTED;
                }
            } else { 
                output = "Invalid username! Try again or (5) to exit.";
                STATE = PROCESSING;
            }
        }
        //this is where we start where we prompt for username
        if(STATE == DISCONNECTED){ //prompt user input username
            output = "Welcome to Sarah & Carina Bank Incorporated! Please enter your username. "+
                     "\nIf you don't have an account with us, and would like to create one, please "+
            		 "enter 7.";
            STATE = PROCESSING;
        }
        System.out.println("walking my way downtown");
        
        
        return output;
    }


 //now that we have the client connected, inputs will all be numerical
    public String processInput(float option){
        String output = null;
        System.out.println(option);
        System.out.println("state: "+ STATE);
        
    
        
        if(STATE == WITHDRAW){
                //check that the withdraw amount is not more than the account balance
                //if the amount is greater than what they have, they'll be sent to main menu
                if(option <= dbMan.getBalance(username)){
                    System.out.println("withdrawing");
                    dbMan.withdraw(username, option);
                    output = "Your remaining balance now is: " + dbMan.getBalance(username) +
                             "\n Would you like to (2) Withdraw More Money? (3) Deposit Money? (4) Transfer Money (5) Sign Out?";
                    STATE = CONNECTED;
                } else { if(option> dbMan.getBalance(username)){
                    output = "ERROR: You don't have this much money " + username + 
                             "\nWould you like to: \n(1) See Acct Balance? (2) Withdraw Money? "+
                             "(3) Deposit Money? (4) Transfer Money (5) Sign Out?";
                    STATE = CONNECTED;
                }}
                //if 5 is inputted anywhere, connection ends 
                if(option == 5){
                	dbMan.changeCO(username);
                    output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                    STATE = SIGNOFF;
                    //break;
                } 
        } else {
        	if(STATE == DEPOSIT){
                     //check that deposit is positive and if it isn't it's invalid and back to main menu
                     if(option > 0){
                        System.out.println("depositing");
                        dbMan.deposit(username, option);// send negative value to method that amends balance
                        output = "Your balance is now: " + dbMan.getBalance(username) +
                        "\nWould you like to (2) Withdraw Money? (3) Deposit More Money? (4) Transfer Money (5) Sign Out?";
                        STATE = CONNECTED;
                    } else { if(option <= 0){
                        output = "ERROR: INVALID DEPOSIT!" +
                                 "\nWould you like to: \n(1) See Acct Balance (2) Withdraw Money? "+
                                 "(3) Deposit Money? (4) Transfer Money (5) Sign Out?";
                        STATE = CONNECTED;
                    }}
                    //if 5 is inputed anywhere, connection ends
                    if(option == 5){
                    	dbMan.changeCO(username);
                        output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                        STATE = SIGNOFF;
                        //break;
                    }
                }else {
                	if(STATE == TRANSFER) {
                   		if (option > 0 && option < dbMan.getBalance(username)) {
                   			trans = option;
                   			System.out.println("TRANS = " + trans);
                  			output = "Please enter username of user you'd like to send money to.";
                   			STATE = TRANSFER;
                   		}
                   	}
                }
                
        } 
        //this overwritten processInput method starts here at CONNECTED
        //Options given when password is correct are processed here
        if(STATE == CONNECTED){
            if(option == 0) {
                output = "Your account number is: " + dbMan.getAccountNumber(username) +
                         "\nWould you like to: \n(1) See Acct Balance? (2) Withdraw Money? "+
                         "(3) Deposit Money? (4) Transfer Money (5) Sign Out?";
                STATE = CONNECTED;
            }
            if(option == 1) {
                output = "Your account balance is: " + dbMan.getBalance(username) +
                         "\nWould you like to: \n(2) Withdraw Money? (3) Deposit Money? (4) Transfer Money (5) Sign Out?";
                STATE = CONNECTED;
            }
            if(option == 2){
                output = "How much would you like to withdraw? Enter amount or (5) if you'd like to exit.";
                STATE = WITHDRAW;
            }
            if(option == 3){
                output = "How much would you like to deposit? Enter amount or (5) if you'd like to exit.";
                STATE = DEPOSIT;
            }
            if(option == 4) {
            	output = "How much would you like to transfer?";
            	STATE = TRANSFER;
            }
            if(option == 5){
            	dbMan.changeCO(username);
                output = "Thank you for banking with Sarah and Carina Bank Incorporated!";
                STATE = SIGNOFF;
            }  
           
        } 
        
        
        if(STATE == CREATEACCT) {
        	initdep = option;
        	dbMan.createAcct(unsql, pwsql, fname, lname, initdep);
        	output = fname + " Welcome to SCBank, Inc! We can now sign in using your credentials.";
        	STATE = DISCONNECTED;
        }
        
        if(STATE == PROCESSING) {
        	if(option == 7) {
        		output = "Thank you for your interest in SCBank, Inc! Please enter your desired username(15 char MAX).";
        		STATE = CREATEACCT;
        	}
        }

        return output;
    }
}
