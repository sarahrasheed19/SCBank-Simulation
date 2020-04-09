/**
 * ServerThread - receives input from the client and sends to protocol to
 * fetch bank response 
 */
import java.net.*;
import java.io.*;

public class ServerThread extends Thread 
{
    private Socket sock = null;
    
    //constructor
    public ServerThread(Socket socket)
    {
        //super("ServerThread");
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
            
            //output = exchange.processInput(null);
            
        
        }catch (IOException e) {
        }
    } 
}
