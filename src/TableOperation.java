import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class TableOperation {
	
	public void createTable(String name, String dbName){
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String create = "CREATE TABLE "+name+" (Company_ID VARCHAR(16),Sub_Company VARCHAR(64),Cart_GUID VARCHAR(64),Payment_Gateway VARCHAR(64),Purchase_Date_and_Time DATETIME,Invoice_Number VARCHAR(64),Create_User VARCHAR(64),Payment_Type VARCHAR(64),Settlement_Currency VARCHAR(64),Total_Cost DOUBLE,Ticket_Currency VARCHAR(64),Ticket_Price DOUBLE,New_Ticket_Price DOUBLE,Discount_code_Discount DOUBLE,Round_Trip_Discount INT,Passenger_Name VARCHAR(64),From_Sub_Place VARCHAR(64),To_Sub_Place VARCHAR(64),Departure_Date_and_Time DATETIME,Member_From_Company_ID INT);";
	    	String externCreate = "CREATE TABLE extern (Company_ID VARCHAR(16),Transaction_ID VARCHAR(64),Purchase_Date_and_Time DATETIME,New_Ticket_Price DOUBLE,Ticket_Currency VARCHAR(64),Passenger_Name VARCHAR(64),External_Ticket_ID varchar(64),External_Transaction_ID varchar(64),Departure_Date_and_Time DATETIME,Route_ID varchar(128),Trip_No varchar(32),from_place VARCHAR(256),to_place VARCHAR(32));";
	    	Statement stmt = conn.createStatement();
	    	Statement externStmt = conn.createStatement();
	    	stmt.executeUpdate(create);
	    	externStmt.executeUpdate(externCreate);
	    	conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void deleteTable(String name, String dbName){
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String drop ="DROP TABLE "+ name+";"; 
	    	String dropExtern ="DROP TABLE extern;"; 
	    	Statement stmt = conn.createStatement();
	    	Statement externStmt = conn.createStatement();
	    	stmt.executeUpdate(drop);
	    	externStmt.executeUpdate(dropExtern);
	    	conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void truncateTable(String name, String dbName){
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String truncate ="TRUNCATE "+ name+";"; 
	    	String ExternTruncate ="TRUNCATE extern;"; 
	    	Statement stmt = conn.createStatement();
	    	Statement externStmt = conn.createStatement();
	    	stmt.executeUpdate(truncate);
	    	externStmt.executeUpdate(ExternTruncate);
	    	conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

}
