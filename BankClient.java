/**
 * BankClient.java is the client file for the BankSimulation. Client would be able to interact with the server.
 *
 * Author: Sarah Rasheed
 * Partner: Carina Caraballo
 * CSC 450: BankSimulation
 */

import java.io.*;
import java.net.*;


public class BankClient implements Serializable
{

	private static final long serialVersionUID = -6475723174937998367L;

	public static void main(String[] args) throws IOException{

        //hostname and portnumber to create the Socket
        String hostname = "localhost";
        int portnumber = 5555;

        //basic username for Bank Client
        String username = "Bank Client";

        try(
        //creates socket and input/output streams
            Socket socket = new Socket(hostname, portnumber);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        )

          {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromUser;
            Message outMessage;
            System.out.println("Client");

            while((outMessage = (Message)in.readObject()) != null){

                System.out.println("Server: " + outMessage.getMessage()); //prints the message from server
                fromUser = stdIn.readLine(); //reads input from user

                //if the input is anything but "4" and fromUser is not null, print and send the message to server
                if(fromUser != null && (fromUser != "4" || outMessage.getMessage().equals("Welcome to Sarah & Carina Bank Incorporated! Please enter your username.")
                || outMessage.getMessage().equals("Welcome back " + username + ". Please enter your password."))){
                  System.out.println(username + ": " + fromUser);
                  outMessage = new Message(username, fromUser);

                  out.writeObject(outMessage);
                  out.reset();
                } //if input equals 4, break the loop
                else if(fromUser.equals("4")){
                  break;
                }
              }
            }

        //exceptions
        catch(UnknownHostException e){
            System.err.println("Don't know about host ");
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Couldn't get I/O for the connection to " + hostname);
            System.exit(1);
        }
        catch(ClassNotFoundException f){
            f.printStackTrace();
        }
    }
}
