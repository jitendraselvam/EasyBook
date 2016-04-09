import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class LocationMaster {
	int check =1;
	int j;
	
	public void update(String path,String dbName)
	{

		try{
			FileInputStream file = new FileInputStream(new File(path));		    
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String sql = "INSERT INTO locationmaster (location,city) VALUES (?,?);";
	    	
	    	
	    	DatabaseMetaData md = conn.getMetaData();
	    	ResultSet rs = md.getTables(null, null, "%", null);
	    	while (rs.next()) {
	    	  System.out.println(rs.getString(3));
	    	  if(rs.getString(3).equals("locationmaster"))
	    		  check = 0;
	    	}
	    	if(check ==1)
	    	{
	    		String create = "CREATE TABLE locationmaster (location VARCHAR(128),city VARCHAR(128));";
	    		Statement stmt = conn.createStatement();
		    	stmt.executeUpdate(create);
	    	}
	    	
	    	PreparedStatement stmt = conn.prepareStatement(sql);
	    	HSSFWorkbook workbook = new HSSFWorkbook(file);
	    	
	    	Sheet sheet1 = workbook.getSheetAt(0);
	    	Iterator<Row> iterator = sheet1.iterator();
	         
	        while (iterator.hasNext()) {
	        	Row nextRow = iterator.next();
    			if(nextRow.getRowNum()<1){
	    			continue;
	        	  	}    
	        	else{
	            
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            j=1;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                        System.out.print(cell.getStringCellValue());
	                        stmt.setString(j, cell.getStringCellValue());
	                        j++;
	            }
	            System.out.println();
	            stmt.executeUpdate();
	            j=1;
	        	}
	        }
	         
	        workbook.close();	
		
	}catch(Exception e){
		e.printStackTrace();
		 JOptionPane.showMessageDialog(null, "location upload unsuccessful" );
	}
	
		 JOptionPane.showMessageDialog(null, "location upload successful" );
	}

}
