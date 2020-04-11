
/**
 * BankClient.java is the client file for the BankSimulation. Client would be able to interact with the server.
 *
 * Author: Sarah Rasheed
 * Partner: Carina Caraballo
 * CSC 450: BankSimulation
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BankClient implements Serializable
{
    public static void main(String[] args) throws IOException{

        String hostname = "localhost";
        int portnumber = 5555;
        String username = "Bank Client";

        try(
            Socket socket = new Socket(hostname, portnumber);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        )
          {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromUser;
            Message outMessage;
            Scanner scan = new Scanner(System.in);

            while((outMessage = (Message)in.readObject()) != null){
                System.out.println("Server: " + outMessage.getMessage());
                if(outMessage.getMessage().equals("Welcome to Sarah & Carina Bank Incorporated! Please enter your username.")){
                  username = scan.nextLine();
                }
                else if(outMessage.getMessage.().equals("Welcome back " + username + ". Please enter your password.")){
                  String password = scan.nextLine();
                }

                fromUser = stdIn.readLine();
                if(fromUser != null && fromUser.equals("4")){
                  System.out.println(username + ": " + fromUser);
                  outMessage = new Message(username, fromUser);

                  out.writeObject(outMessage);
                  out.reset();
                }
                else if(fromUser.equals("4")){
                  break;
                }
              }
            }

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