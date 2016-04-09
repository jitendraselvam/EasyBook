import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class CreateDatabase {
	
	public void create(String name){
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/trans";
	    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=123123");
	    	String create = "CREATE DATABASE "+name+";";
	    	Statement stmt = conn.createStatement();
	    	stmt.execute(create);
	    	JOptionPane.showMessageDialog(null, "database "+name+" created successfully.");
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "database not created ");
		}
	}
}
