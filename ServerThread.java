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
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            ObjectInputStream channelIn = new ObjectInputStream(sock.getInputStream());
            ObjectOutputStream channelOut = new ObjectOutputStream(sock.getOutputStream());
        ){
            String input, output;
            Message inBank, outBank;
            String name = "S&C Bank Inc.";
            
            SCProtocol exchange = new SCProtocol();
            //good up to here

            output = exchange.processInput(null);
            outBank = new Message(name,output);
            channelOut.writeObject(outBank);
        
            String check = "0123456789";    
                    
            while((inBank = (Message)channelIn.readObject()) != null){
                input = inBank.getMessage();
                System.out.println(input);
              
                if (check.contains(input)){
                    output = exchange.processInput(Float.parseFloat(input));
                    outBank = new Message(name, output);
                    channelOut.writeObject(outBank);
                }
                else {
                    output = exchange.processInput(input);
                    outBank = new Message(name, output);
                    channelOut.writeObject(outBank);
                }

                if (input.equals("4")){
                    System.out.println("Thank you for banking with Sarah and Carina Bank Incorporated!");
                    break;
                }
                
            }
            channelIn.close();
            channelOut.close();
            sock.close();
        
        }catch (IOException e) {
        } catch (ClassNotFoundException f){
        }
    } 
}
