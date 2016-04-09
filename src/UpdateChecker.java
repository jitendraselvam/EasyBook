import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class UpdateChecker {
	Cell cell;
	int i =0,j=0;
	String strsql;
	ResultSet rs;
	String val;
	public String check( String fileLoc, String table, String dbName){
		SimpleDateFormat  formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
		try {
	    	FileInputStream file = new FileInputStream(new File(fileLoc));		    
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	HSSFWorkbook workbook = new HSSFWorkbook(file);
	    	HSSFSheet sheet = workbook.getSheetAt(0);
	    	Iterator<Row> rowIterator = sheet.iterator();
    		Row  row1 = rowIterator.next();
    		row1 = rowIterator.next();
    		Iterator<Cell> cellIterator1 = row1.cellIterator();
    			Cell cell = cellIterator1.next();	
    			cell = cellIterator1.next();	
    			cell = cellIterator1.next();	
    			cell = cellIterator1.next();
    			cell = cellIterator1.next();
    			cell = cellIterator1.next();
    			//cell = cellIterator1.next();
    		//String date = cell.getStringCellValue().substring(0,11);
    			String date = cell.getStringCellValue();
    			System.out.println("DATE CHECKER: "+date);
    	Date date1 = formatter.parse(date);
    	System.out.println("DATE: "+date1);
    		Date datesql = new java.sql.Date(date1.getTime());
    		strsql = datesql.toString();
    		//System.out.println("DATE: "+strsql); 
    		String sql = "SELECT * from "+table+" where Purchase_Date_and_Time BETWEEN '"+strsql+"' AND DATE_ADD('"+strsql+"', INTERVAL 1 DAY) LIMIT 1;";
    		//System.out.println(sql);
    		Statement stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		rs.beforeFirst();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			if(rs.next())
				val = strsql;
			else
				val = "empty";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("val: "+val);
		return val;
	}
	
	public String externalCheck(String fileLoc, String table, String dbName)
	{
		SimpleDateFormat  formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
		try {
	    	FileInputStream file = new FileInputStream(new File(fileLoc));		    
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	HSSFWorkbook workbook = new HSSFWorkbook(file);
	    	HSSFSheet sheet = workbook.getSheetAt(0);
	    	Iterator<Row> rowIterator = sheet.iterator();
    		Row  row1 = rowIterator.next();
    		row1 = rowIterator.next();
    		Iterator<Cell> cellIterator1 = row1.cellIterator();
    			Cell cell = cellIterator1.next();	
    			cell = cellIterator1.next();	
    			cell = cellIterator1.next();	
    			cell = cellIterator1.next();
    			//cell = cellIterator1.next();
    		//String date = cell.getStringCellValue().substring(0,11);
    			String date = cell.getStringCellValue();
    			System.out.println("DATE CHECKER: "+date);
    	Date date1 = formatter.parse(date);
    	System.out.println("DATE: "+date1);
    		Date datesql = new java.sql.Date(date1.getTime());
    		strsql = datesql.toString();
    		//System.out.println("DATE: "+strsql); 
    		String sql = "SELECT * from "+table+" where Purchase_Date_and_Time BETWEEN '"+strsql+"' AND DATE_ADD('"+strsql+"', INTERVAL 1 DAY) LIMIT 1;";
    		//System.out.println(sql);
    		Statement stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		rs.beforeFirst();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			if(rs.next())
				val = strsql;
			else
				val = "empty";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("val: "+val);
		return val;
	}
}
