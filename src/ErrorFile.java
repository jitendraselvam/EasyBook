import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ErrorFile {
	
	Sheet sheet;
	int i=1,count=0;
	FileOutputStream fileOut;
	public void createFile(String dbName,String table){
		Workbook wb = new HSSFWorkbook();
		 sheet = wb.createSheet("Sheet1");
		 
		
		
		try {
		 
		     fileOut = new FileOutputStream("C:\\files\\Error\\Error.xls");
		   
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String sql = "select * from "+table+" where Company_Id not in (Select CID from newmaster);";
	    	Statement stmt = conn.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	Row row = sheet.createRow(0);
    		row.createCell(0).setCellValue("Company_Id");
    		row.createCell(1).setCellValue("Sub_Company");
    		row.createCell(2).setCellValue("Cart_GUID");
    		row.createCell(3).setCellValue("Payment_Gateway");
    		row.createCell(4).setCellValue("Purchase_Date_and_Time");
    		row.createCell(5).setCellValue("Invoice_Number");
    		row.createCell(6).setCellValue("Create_User");
    		row.createCell(7).setCellValue("Payment_Type");
    		row.createCell(8).setCellValue("Settlement_Currency");
    		row.createCell(9).setCellValue("Total_Cost");
    		row.createCell(10).setCellValue("Ticket_Currency");
    		row.createCell(11).setCellValue("Ticket_Price");
    		row.createCell(12).setCellValue("New_Ticket_Price");
    		row.createCell(13).setCellValue("Discount_code_Discount");
    		row.createCell(14).setCellValue("Round_Trip_Discount");
    		row.createCell(15).setCellValue("Passenger_Name");
    		row.createCell(16).setCellValue("From_Sub_Place");
    		row.createCell(17).setCellValue("To_Sub_Place");
    		row.createCell(18).setCellValue("Departure_Date_and_Time");
    		row.createCell(19).setCellValue("Member_From_Company_ID");
    		
    		
	    	while(rs.next()){
	    		//System.out.println(rs.getString("Company_Id"));
	    			Row row1 = sheet.createRow(i++);
	    		row1.createCell(0).setCellValue(rs.getString(1));
	    		row1.createCell(1).setCellValue(rs.getString(2));
	    		row1.createCell(2).setCellValue(rs.getString(3));
	    		row1.createCell(3).setCellValue(rs.getString(4));
	    		row1.createCell(4).setCellValue(rs.getDate(5));
	    		row1.createCell(5).setCellValue(rs.getString(6));
	    		row1.createCell(6).setCellValue(rs.getString(7));
	    		row1.createCell(7).setCellValue(rs.getString(8));
	    		row1.createCell(8).setCellValue(rs.getString(9));
	    		row1.createCell(9).setCellValue(rs.getString(10));
	    		row1.createCell(10).setCellValue(rs.getString(11));
	    		row1.createCell(11).setCellValue(rs.getDouble(12));
	    		row1.createCell(12).setCellValue(rs.getDouble(13));
	    		row1.createCell(13).setCellValue(rs.getDouble(14));
	    		row1.createCell(14).setCellValue(rs.getInt(15));
	    		row1.createCell(15).setCellValue(rs.getString(16));
	    		row1.createCell(16).setCellValue(rs.getString(17));
	    		row1.createCell(17).setCellValue(rs.getString(18));
	    		row1.createCell(18).setCellValue(rs.getDate(19));
	    		row1.createCell(19).setCellValue(rs.getInt(20));
	    		
	    		count++;
	    	}
	    	int a[] = new int[count+1];
	    	i=0;
	    	rs.beforeFirst();
	    	while(rs.next())
	    	{
	    	a[i]=Integer.parseInt(rs.getString("Company_Id"));
	    	i++;
	    	}
	    	
	    	
	    	
	    	 wb.write(fileOut);
				fileOut.close();
				System.out.println("Error file created");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
