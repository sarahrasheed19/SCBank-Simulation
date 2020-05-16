//This class will serve to connect and interact with the database SCBank

import java.sql.*;


public class DBManager 
{
	private Connection db;
	//private Statement stmt;
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
			//stmt = db.createStatement();
			} 
		
		catch (ClassNotFoundException e){
			System.out.println("Connection to driver failed!");
			System.exit(-1);
			} 
		catch (SQLException e){
			System.out.println("Message: " + e.getMessage());
			
          }
	}
	//function correct -- debugged
	//checks if the user name is valid
	public boolean usernameExists(String user) {
		//here I'll make an SQL query that checks for user existing
		Boolean bool = false;
		sql = "SELECT username FROM main WHERE username = ? ";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery(); //good up to here
			while (rs.next()) {
	            result = rs.getString("username");  
	            if((result.trim()).compareTo(user) == 0) {
	            	bool = true;
	            }
	            System.out.println(result);
	        }
			return bool;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Invalid Username.");
			return false;
		}
	}
	//debugged so far
	//checks if the password is correct
	public boolean passwordCorrect(String pw, String username) {
		//checking if the password is correct for the user name saved up there
		sql = "SELECT password FROM main WHERE password = crypt(?, password)";
		Boolean lol = false;
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, pw);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("password");
				System.out.println(result);
				lol = true;
				
			}
			return lol;
		} catch (SQLException e) {
			System.out.println("ERROR");
			return false;
		}
	}
	/*
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
	}*/
	
	
	
	//method debugged -- connects to db successfully
	//message to check on checkout value of account by user name
	public int isSignedIn(String username) {
		sql = "SELECT checkout FROM main WHERE username = ?";
		try {
			PreparedStatement pstmt2 = db.prepareStatement(sql);
			pstmt2.setString(1, username);
			ResultSet rs = pstmt2.executeQuery();

			while (rs.next()) {
				checked = rs.getInt("checkout");
	        }
			
			return (checked);
			
		} catch (SQLException e) {
			System.out.println("ERROR");
			return 9;
		}
	}
	
	
	public void changeCO(String username) {
		if (isSignedIn(username)==0) {
			sql = "UPDATE main SET checkout = 1 WHERE username = ?";
			
		} else {
			sql = "UPDATE main SET checkout = 0 WHERE username = ?";
		}
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("UPDATE CHECKOUT FAILED");
			e.printStackTrace();
		}
	}
	
	public float getBalance(String username) {
		sql = "SELECT acctBalance FROM main WHERE username = ?";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				acctBalance = rs.getFloat("acctBalance");
			}
			return acctBalance;
		} catch (SQLException e) {
			System.out.println("ERROR");
			return -1;
		}
	}
	
	public int getAccountNumber(String username) {
		sql = "SELECT acctno FROM main WHERE username = ?";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("Acct NUMBER");
			while(rs.next()) {
				acctID = rs.getInt("acctno");
				
			}
			return acctID;
		} catch (SQLException e) {
			System.out.println("ERROR");
			return -1;
		}
	}
	//works up to here
	
	
	public void deposit(String username, float amt) {
		sql = "UPDATE main SET acctBalance = acctBalance + ? WHERE username = ?";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setFloat(1, amt);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("DEPOSIT FAILED");
			e1.printStackTrace();
		}
		
	}
	
	public void withdraw(String username, float amt) {
		sql = "UPDATE main SET acctBalance = acctBalance - ? WHERE username = ?";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setFloat(1, amt);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("WITHDRAW FAILED");
			e1.printStackTrace();
		}
		
	}
	
	//basic transfer method from one account holder to another
	public void transfer(String sender, String receiver, float amt) {
		sql = "UPDATE main SET acctBalance = acctBalance + ? WHERE username = ?";
		String sql2 = "UPDATE main SET acctBalance = acctBalance - ? WHERE username = ?";

		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			PreparedStatement send = db.prepareStatement(sql2);
			pstmt.setFloat(1, amt);
			pstmt.setString(2, receiver);
			send.setFloat(1,amt);
			send.setString(2, sender);
			send.executeUpdate();
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			System.out.println("TRANSFER FAILED");
			e1.printStackTrace();
		}
	}
	
	public void createAcct(String un, String pw, String first, String last, float bal) {
		sql = "INSERT INTO main(username, password, fname, lname, acctBalance) VALUES (?, crypt(?, gen_salt('md5')), ?, ?, ?);";
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, un);
			pstmt.setString(2, pw);
			pstmt.setString(3, first);
			pstmt.setString(4, last);
			pstmt.setFloat(5, bal);
			pstmt.executeUpdate();
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

