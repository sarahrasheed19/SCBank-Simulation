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

            while((inBank = (Message)channelIn.readObject()) != null){
                //input is always a string that has to be judged either as that or a float
                input = inBank.getMessage();
                System.out.println(input);
                //input is sent to isNumber method to check if it's a float
                if (isNumber(input)){
                    //if it's a float it is sent to second processInput method
                    output = exchange.processInput(Float.parseFloat(input));
                    outBank = new Message(name, output);
                    //send output to bank client
                    channelOut.writeObject(outBank);
                }
                else {
                    //if not a float, it stays an input and is sent to first processInput method
                    output = exchange.processInput(input);
                    outBank = new Message(name, output);
                    channelOut.writeObject(outBank);
                }
                //if at any time the input is 4, connection is ended
                if (input.equals("4")){
                    System.out.println("Thank you for banking with Sarah and Carina Bank Incorporated!");
                    break;
                }

            }
            channelIn.close();
            channelOut.close();
            sock.close();

        }catch (IOException e) {
            System.out.println("Not able to find I/O Connection");
        } catch (ClassNotFoundException f){
        }
    }
    public static boolean isNumber(String in){
        try{ //input can only be parseFloat if it gives off a valid float number
            Float.parseFloat(in);
        } catch (NumberFormatException e){
        //if it doesn't it'll throw exception and it is not a number
            return false;
        }
        return true;
    }
}
