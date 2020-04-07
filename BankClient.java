
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
        int portnumber = 4444;
        String name = "Bank Client";
        
        try{
            Socket socket = new Socket(hostname, portnumber);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        }{
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            Message outMessage;
            String fromUser;
            Scanner scan = new Scanner(System.in);
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
