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
            ObjectInputStream channelIn = new ObjectInputStream(sock.getInputStream());
            ObjectOutputStream channelOut = new ObjectOutputStream(sock.getOutputStream());
            
        ){
            String input, output;
            Message inBank, outBank;
            String name = "S&C Bank Inc.";
            
            SCProtocol exchange = new SCProtocol();
            
            output = exchange.processInput("new");
            outBank = new Message(name,output);
            
            while((inBank = (Message)channelIn.readObject()) != null){
                input = inBank.getMessage();
                if (input.equals("4")){
                    System.out.println("Thank you for banking with Sarah and Carina Bank Incorporated!");
                    break;
                }
                for (int i= 0; i<10; i++){
                    if (input.contains(String.valueOf(i))){
                        output = exchange.processInput(Float.parseFloat(input));
                        outBank.setMessage(output);
                        channelOut.writeObject(outBank);
                        //out.println(output);
                    }
                    else {
                        output = exchange.processInput(input);
                        outBank.setMessage(output);
                        channelOut.writeObject(outBank);
                        //out.println(output);
                    }
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
