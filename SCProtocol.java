/* SCProtocol.java
* 
* This class is dedicating to calculating, based on client input, 
* what the bank's response will be.
* 
* By Carina C
*/

import java.net.*;
import java.io.*;

public class SCProtocol {

    private static final int DISCONNECTED = 0; //client and server are disconnected, no interaction yet 
    private static final int CONNECTED = 1; //client is connected 
    private static final int PROCESSING = 2; //machine checking username
    private static final int CHECKINGPW = 3; //machine checking password

    private int STATE = 0; //we start at DISCONNECTED


    //first input processing if it is a username or password
    //parameter is a String
    public String processInput(String signon) {
        String[] username = {"client1","client2","client3"}; //accepted clients
        String password = "1z2y3x4w";
        String output = null;

        if(STATE == DISCONNECTED){ //prompt user input username
            output = "Welcome to Sarah & Carina Bank Incorporated! Please enter your username.";
            STATE = PROCESSING;
        }
        if(STATE == PROCESSING){
            for(int i = 0; i<username.length ; i++){
                if(signon.compareTo(username[i])){
                    output = "Welcome back " + signon + ". Please enter your password.";
                    STATE = CHECKINGPW;
                } else {
                    output = "Invalid username! Try again.";
                    STATE = DISCONNECTED;
                }
            }
        }
        if(STATE == CHECKINGPW){
            if(signon.compareTo(password)){
                output = "Welcome. What can we help you with today? (1) See Acct Balance"+ 
                        " (2) Withdraw Money (3) Deposit Money (4) Sign Out";
                STATE = CONNECTED;
            }
        }
 
        return output;
    }

    //now that we have the client connected, inputs will all be numerical
    public String processInput(int option){
        String output = null;

        //this minimalist version is still using an account object
        //account object has acct number and balance 
        //so if user wants to see account number
        if(STATE == CONNECTED){
            if(option == 1) {
                //readAcctNo and set it equal to output
                output = "Your account number is: " + //+ServerThread.Acct.getAcctNumber();
                         "Would you like to (2) Withdraw Money? (3) Deposit Money? (4) Sign Out?";
                STATE = CONNECTED;
            }
            if(option == 2) {
                output = "How much would you like to withdraw 
              

    }
