import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





public class OutputFileGen {
	
	ResultSet masterRs;
	ResultSet rulesRs;
	ResultSet stdRs;
	
	public void generate(String startDate,String endDate, String dbName,String table) throws SQLException
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	String masterSql = "Select CID from newmaster;";
	    	String rulesSql = "select DISTINCT Operator_ID from rulesmaster;";
	    	String stdSql = "SELECT * FROM newmaster where CID not in (Select Operator_ID from rulesmaster);";
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
			Statement stmt = conn.createStatement();
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			masterRs = stmt.executeQuery(masterSql);
			masterRs.beforeFirst();
			rulesRs = stmt1.executeQuery(rulesSql);
			rulesRs.beforeFirst();
			stdRs = stmt2.executeQuery(stdSql);
			stdRs.beforeFirst();
			
			}
		catch(Exception e){
			e.printStackTrace();
			}

		StandardAlgo sa = new StandardAlgo();
		stdRs.beforeFirst();
		sa.start(startDate,endDate,dbName,table);
		RulesAlgo ra = new RulesAlgo();
		ra.start(startDate,endDate,dbName,table);
		NONKTBRules nonKtb = new NONKTBRules();
		nonKtb.start(startDate,endDate,dbName,"extern");
		}
	}	

