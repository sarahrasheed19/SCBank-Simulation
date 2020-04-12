/**
 * ServerThread - receives input from the client and sends to protocol to
 * fetch bank response 
 * 
 * Author: Carina Caraballo
 * Partner: Sarah Rasheed
 */

import java.net.*;
import java.io.*;

public class ServerThread extends Thread 
{
    private Socket sock = null;
    
    //constructor
    public ServerThread(Socket socket)
    {
        super("ServerThread");
        this.sock = socket;
    }
    
    public void run() {
        try (
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            ObjectOutputStream acctChannel = new ObjectOutputStream(sock.getOutputStream());
        ){
            String input, output;
            SCProtocol exchange = new SCProtocol();
            
            output = exchange.processInput("new");
            out.println(output);
            
            while((input = in.readLine()) != null){
                if (input.equals("4")){
                    System.out.println("Thank you for banking with Sarah and Carina Bank Incorporated!");
                    break;
                }
                for (int i= 0; i<10; i++){
                    if (input.contains(String.valueOf(i))){
                        output = exchange.processInput(Float.parseFloat(input));
                        out.println(output);
                    }
                    else {
                        output = exchange.processInput(input);
                        out.println(output);
                    }
                }
                
            }
            sock.close();

        }catch (IOException e) {
        }
    } 
}
