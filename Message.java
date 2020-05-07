
/**
 * Message.java will be used to send messages between server and client.
 *
 * Sarah Rasheed
 * CSC 450 - Assignment 6
*/

import java.io.*;

public class Message implements Serializable
{
    
	private static final long serialVersionUID = 3844699835374897747L;
	
	
	// instance variables - replace the example below with your own
    private String sender;
    private String message;

    //constructor method for objects of class Message
    public Message(String sender, String message){
        this.sender = sender;
        this.message = message;
    }

    //assigns the message to the message variable
    public Message(String message){
        this.message = message;
    }

    //getter for sender
    public String getSender() {
        return sender;
    }

    //getter for message
    public String getMessage(){
        return message;
    }

    //setter for message
    public void setMessage(String message){
        this.message = message;
    }

    //setter for sender
    public void setSender(String sender){
        this.sender = sender;
    }
}
