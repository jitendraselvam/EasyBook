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


public class RulesUpdater {

	public void update(String path,String dbName) {
		int j,check = 1,opCheck=0;
		StringBuilder sb = new StringBuilder();
		
		try{
			FileInputStream file = new FileInputStream(new File(path));		    
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String sql = "INSERT INTO rulesmaster (Operator_ID,rules,Balance_Payable_To,EB_Commission,OwnSite,OperatorSite,Party_Payable_Type,Party_Amount) VALUES (?,?,?,?,?,?,?,?);";
	    	
	    	
	    	DatabaseMetaData md = conn.getMetaData();
	    	ResultSet rs = md.getTables(null, null, "%", null);
	    	while (rs.next()) {
	    	  System.out.println(rs.getString(3));
	    	  if(rs.getString(3).equals("rulesmaster"))
	    		  check = 0;
	    	}
	    	if(check ==1)
	    	{
	    		String create = "CREATE TABLE rulesmaster (Operator_ID INT ,rules VARCHAR(768),Balance_Payable_To varchar(64),EB_Commission varchar(32),OwnSite Double,OperatorSite Double,Party_Payable_Type varchar(32),Party_Amount double);";
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
	                
	                switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                        System.out.print("string: "+cell.getStringCellValue()+"j = "+j+"--");
	                        //stmt.setString(j, cell.getStringCellValue());
	                        stmt.setString(j,cell.getStringCellValue());
	                        j++;
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                        if(opCheck==0)
	                        {
	                        	stmt.setDouble(1, cell.getNumericCellValue());
	                        System.out.print("numeric: "+cell.getNumericCellValue()+" j = "+j+"--");
	                        opCheck=1;    
	                        j++;
	                        }
	                        else
	                        {
	                        	System.out.print("numeric: "+cell.getNumericCellValue()+"j = "+j+"--");
	                        	stmt.setDouble(j, cell.getNumericCellValue());
	                        	j++;
	                        }
	                        
	                        break;
	                    case Cell.CELL_TYPE_BLANK:
	                    	//System.out.println("blank");
	                    	System.out.print("Blank "+"j = "+j+"--");
	                    	stmt.setDouble(j,00);
	                    	j++;
	                    	break;
	                }
	                       
	                       
	                }
	            
	           
	            if(j==6)
	            {
	            	stmt.setDouble(6, 00);
	            	stmt.setString(7, "NA");
	            	stmt.setDouble(8, 00);
	            } 
	            if(j==7)
	            {
	            	stmt.setString(7, "NA");
	            	stmt.setDouble(8, 00);
	            }
	            
	            opCheck =0;
	            sb.setLength(0);
	            j=1;
	            stmt.executeUpdate();
	        	}
	        
	        }
	        
	         
	        workbook.close();	
		
	}catch(Exception e){
		e.printStackTrace();
		 JOptionPane.showMessageDialog(null, "rules upload unsuccessful" );
		}
	
		 JOptionPane.showMessageDialog(null, "rules upload successful" );
	}
		
}


