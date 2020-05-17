/**
 * Server class opens connection -- makes connection available to any clients. 
 * 
 * By Carina Caraballo
 */
import java.net.*;
import java.io.*;

public class Server
{
	
	public static void main(String[] args) throws IOException 
    {
        boolean listening = true;
                
        try (ServerSocket sock = new ServerSocket(5555))
        {
            System.out.println("Sarah & Carina Bank Incorporated Connected...");
            
            while(listening)
            {
                new ServerThread(sock.accept()).start();
            
            }
            
        } catch (IOException e) 
        {
            System.out.println("Port could not be accessed");
            System.exit(-1);
        }
    }
    
}
