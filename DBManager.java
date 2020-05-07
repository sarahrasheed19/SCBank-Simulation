//This class will serve to connect and interact with the database SCBank

import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBManager 
{
	private Connection db;
	private Statement stmt;
	private String result = null;
	private String sql = null;
	private int checked = 0;
	private int acctID = 0;
	private float acctBalance = 0;
	
	public DBManager (String username, String password)
	{
		String url = "jdbc:postgresql://localhost:5432/"+username;

		try{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver is set; ready to go!");
			db = DriverManager.getConnection(url, username, password);
			System.out.println("DONE");
			stmt = db.createStatement();
			} 
		
		catch (ClassNotFoundException e){
			System.out.println("Connection to driver failed!");
			System.exit(-1);
			} 
		catch (SQLException e){
			System.out.println("Message: " + e.getMessage());
			
          }
	}
	//checks if the user name is valid
	public boolean usernameExists(String user) {
		//here I'll make an SQL query that checks for user existing
		try {
			PreparedStatement st = db.prepareStatement("SELECT * FROM clients WHERE username = ?");
			st.setString(1, user);
			ResultSet rs = st.executeQuery(); //good up to here
			while (rs.next())
			{
			    System.out.print("Column 4 returned ");
			    rs.getString(4);
			}
			return (result.compareTo(user) == 0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Invalid Username.");
			return false;
		}
	}
	//checks if the password is correct
	public boolean passwordCorrect(String pw) {
		//checking if the password is correct for the user name saved up there
		sql = "SELECT password FROM clients WHERE username =\'" + result + "\';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			result = rs.getString("password");
			return (result.compareTo(md5hash(pw)) == 0);
		} catch (SQLException e) {
			System.out.println("ERROR");
			return false;
		}
	}
	
	//creating md5 hash of password entered to compare to hash of user password
	//that is saved in the database
	public String md5hash(String pw) {
		String md5PW = null;
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5");
			byte[] hash = alg.digest(pw.getBytes("UTF-8"));
			
			StringBuilder encrypted = new StringBuilder(2 * hash.length);
			
			for(byte b : hash)
				encrypted.append(String.format("%02x", b&0xff));
			
			md5PW = encrypted.toString();
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5 error.");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Utf-8 error.");
			e.printStackTrace();
		}
		return md5PW;
	}
	
	//message to check on checkout value of account by user name
	public int isSignedIn(String username) {
		sql = "SELECT * FROM accounts WHERE client_id = (SELECT id FROM clients WHERE username = \'"+username+"\';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			checked = rs.getInt("checkout");
			return (checked);
		} catch (SQLException e) {
			System.out.println("ERROR");
			return 9;
		}
	}
	
	public void changeCO(String username) {
		if (isSignedIn(username)==0) {
			sql = "UPDATE main SET checkout = 1 WHERE username = \'"+username+"\';";
			
		} else {
			sql = "UPDATE main SET checkout = 0 WHERE username = \'"+username+"\';";
			}
		try {
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			System.out.println("UPDATE CHECKOUT FAILED");
			e.printStackTrace();
		}
	}
	
	public float getBalance(String username) {
		sql = "SELECT acctBalance FROM accounts WHERE client_id = (SELECT id FROM clients WHERE username = \'"+username+"\';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			acctBalance = rs.getFloat("acctBalance");
			return acctBalance;
		} catch (SQLException e) {
			System.out.println("ERROR");
			return -1;
		}
	}
	
	public float getAccountNumber(String username) {
		sql = "SELECT acctNumber FROM accounts WHERE client_id = (SELECT id FROM clients WHERE username = \'"+username+"\';";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			acctID = rs.getInt("acctNumber");
			return acctID;
		} catch (SQLException e) {
			System.out.println("ERROR");
			return -1;
		}
	}
	
	public void deposit(String username, float amt) {
		sql = "UPDATE main SET acctBalance = acctBalance + amt WHERE username = \'"+username+"\';";
		try {
			stmt.executeUpdate(sql);
			db.commit();
		} catch (SQLException e1) {
			System.out.println("DEPOSIT FAILED");
			e1.printStackTrace();
		}
		
	}
	
	public void withdraw(String username, float amt) {
		sql = "UPDATE main SET acctBalance = acctBalance - amt WHERE username = \'"+username+"\';";
		try {
			stmt.executeUpdate(sql);
			db.commit();
		} catch (SQLException e1) {
			System.out.println("WITHDRAW FAILED");
			e1.printStackTrace();
		}
		
	}
	
	//basic transfer method from one account holder to another
	public void transfer(String sender, String receiver, float amt) {
		//first remove amount from the sender's account
		withdraw(sender, amt);
		//then add it to the receiver's account
		deposit(receiver, amt);
	}
	
	public void createAcct(String un, String pw, String first, String last, float bal) {
		sql = "INSERT INTO main(username, password, fname, lname, acctBalance) "
				+ "VALUES (un, pw, first, last, bal);";
		try {
			stmt.executeUpdate(sql);
			db.commit();
		} catch (SQLException e) {
			System.out.println("Account Creation Failed.");
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			db.close();
		} catch (SQLException e) {
			System.out.println("Database Connection Closure Failure");
			e.printStackTrace();
		}
	}
}

