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
import javax.swing.JProgressBar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class MasterFile {
	
	Statement stmt;
	static int sheets;
	static int j =1;
	static int col=0;
	int check = 1;


	public void runMaster(String dbName, String fileLoc){
		try{
			FileInputStream file = new FileInputStream(new File(fileLoc));		    
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String sql = "INSERT INTO newmaster (CID,operator_name,eb_site,op_site,round_trip_discount,third_party,external,payment_Gateway) VALUES (?,?,?,?,?,?,?,?);";
	    	
	    	
	    	DatabaseMetaData md = conn.getMetaData();
	    	ResultSet rs = md.getTables(null, null, "%", null);
	    	while (rs.next()) {
	    	  System.out.println(rs.getString(3));
	    	  if(rs.getString(3).equals("newmaster"))
	    		  check = 0;
	    	}
	    	if(check ==1)
	    	{
	    		String create = "CREATE TABLE newmaster (CID INT,operator_name VARCHAR(128),eb_site VARCHAR(50),op_site VARCHAR(50),round_trip_discount VARCHAR(50),third_party VARCHAR(50),external VARCHAR(50),payment_Gateway VARCHAR(50));";
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
	             col=1;  j=1;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                
	                switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                        System.out.print("string: "+cell.getStringCellValue());
	                        stmt.setString(j, cell.getStringCellValue());
	                        j++;
	                        break;
	                    case Cell.CELL_TYPE_BOOLEAN:
	                        System.out.print("boolean :"+cell.getBooleanCellValue());
	                        if(cell.getBooleanCellValue()==true)
	                        	stmt.setString(j, "true");
	                        else
	                        	stmt.setString(j, "false");
	                        j++;
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                        System.out.print("numeric: "+cell.getNumericCellValue());
	                        stmt.setDouble(j, cell.getNumericCellValue());
	                        j++;
	                        break;
	                    case Cell.CELL_TYPE_BLANK:
	                    	System.out.println("blank");
	                    	stmt.setString(j,"");
	                    	j++;
	                    	break;
	                       
	                       
	                }
	                col++;

	            }
	            System.out.println();
	            stmt.executeUpdate();
	        	}
	        }
	         
	        workbook.close();	
		}catch(Exception e){
			e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "master upload unsuccessful check file syntax" );
		}
		
		 JOptionPane.showMessageDialog(null, "master uplaod successful" );
		
	}
}

