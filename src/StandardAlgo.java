import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class StandardAlgo {
	
	Sheet sheet;
	FileOutputStream fileOut ;
	int i=14;
	int sno=0;
	double calc;
	CreationHelper createHelper;
	int lastline=0;
	String name = null;
	String check = null;
	double yoursSGD=0,theirsSGD=0,yoursMYR=0,theirsMYR=0;
	ResultSet masterRs;
	SalesNoteGenerator sng;
	
public void start(String startDate,String endDate, String dbName,String table) throws SQLException{
	 //startDate ="2014-12-01";
	 //endDate ="2014-12-08";
	Workbook wb = new HSSFWorkbook();
	 CellStyle cellStyle;
	 Cell d1,d2;
	 Date date = new Date();
	 cellStyle = wb.createCellStyle();
	 createHelper = wb.getCreationHelper();
	 sng = new SalesNoteGenerator();
	 try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String sql = "SELECT * FROM newmaster where CID not in (Select Operator_ID from rulesmaster) AND round_trip_discount ='false' ";
	    	Statement stmt = conn.createStatement();
	    	masterRs = stmt.executeQuery(sql);
	    	masterRs.beforeFirst();
		}catch (Exception e){
			e.printStackTrace();
		}
	 while(masterRs.next())
	 {
		 	int companyId = (int) masterRs.getDouble("CID");
		 	name = masterRs.getString("operator_name");
			 String compId= "C:\\files\\output\\"+companyId+".xls";
			 name = masterRs.getString("operator_name");
			 sheet = wb.createSheet("Sheet1");
			 System.out.println(companyId);
			 double eb_site = masterRs.getDouble("eb_site");
			 double op_site = masterRs.getDouble("op_site");
	 
	 try {
		 
	     fileOut = new FileOutputStream(compId);
	   
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	 
	 try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	
	    	String masterIgnore ="Select * from newmaster where round_trip_discount = 'false';";
	    	Statement masterStmt = conn.createStatement();
	    	ResultSet masterRs = masterStmt.executeQuery(masterIgnore);
	    	masterRs.beforeFirst();
	    	
	    	String sql = "select * from "+table+" where Purchase_Date_and_Time between '"+startDate+"' and '"+endDate+"' and Company_Id = "+companyId+";";
	    	Statement stmt = conn.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	Row row = sheet.createRow(14);
    		row.createCell(0).setCellValue("S.No");
    		row.createCell(1).setCellValue("Purchase Date");
    		row.createCell(2).setCellValue("Invoice Number");
    		row.createCell(3).setCellValue("Ticket Currency");
    		row.createCell(4).setCellValue("Ticket Price");
    		row.createCell(5).setCellValue("Origin");
    		row.createCell(6).setCellValue("Destination");
    		row.createCell(7).setCellValue("Departure Date");
    		row.createCell(8).setCellValue("Website code");
    		row.createCell(10).setCellValue("Easibook.com SGD");
    		row.createCell(11).setCellValue("Operator SGD");
    		row.createCell(13).setCellValue("Easibook.com MYR");
    		row.createCell(14).setCellValue("Operator MYR");
	    	while(rs.next())
	    	{	masterRs.beforeFirst();
	    		while(masterRs.next()){
	    			
	    		check = String.valueOf(masterRs.getDouble("CID"));
	    		 if(rs.getString("Company_Id").equalsIgnoreCase(check)){
	    			continue;
	    		}
	    		}
	    		if(rs.getString("Create_User").equalsIgnoreCase("Bot") ){
	    			continue;
	    		}
	    		else{
	    			lastline++;
	    			Row sn = sheet.createRow(0);
	    			sn.createCell(0).setCellValue("Sales Note Number: SN"+sng.generate(endDate)+ " "+name);
	    			Row row3 = sheet.createRow(1);
		    		row3.createCell(0).setCellValue("Merchant: "+name);
		    		Row roww = sheet.createRow(2);
		    		roww.createCell(0).setCellValue("Description: Online Sales of Merchant's Coach Tickets Purchased from "+startDate+" to "+endDate);
	    		Row row1 = sheet.createRow(++i);
	    		row1.createCell(0).setCellValue(++sno);
	    		row1.createCell(1).setCellValue(rs.getString("Purchase_Date_and_Time"));
	    		row1.createCell(2).setCellValue(rs.getString("Invoice_Number"));
	    		row1.createCell(3).setCellValue(rs.getString("Ticket_Currency"));
	    		row1.createCell(4).setCellValue(rs.getString("New_Ticket_Price"));
	    		row1.createCell(5).setCellValue(rs.getString("From_Sub_Place"));
	    		row1.createCell(6).setCellValue(rs.getString("To_Sub_Place"));
	    		row1.createCell(7).setCellValue(rs.getString("Departure_Date_and_Time"));
	    		row1.createCell(8).setCellValue(rs.getString("Member_From_Company_ID"));
	    		if(rs.getString("Member_From_Company_ID").equals("0"))
	    		{
	    			calc = (eb_site*Double.parseDouble(rs.getString("New_Ticket_Price")));
	    			if(rs.getString("Ticket_Currency").equalsIgnoreCase("SGD"))
	    			{
	    				
	    				row1.createCell(10).setCellValue(calc);
	    				double calc1=Double.parseDouble(rs.getString("New_Ticket_Price"))-calc;
	    				row1.createCell(11).setCellValue(calc1);
	    				row1.createCell(13).setCellValue("0");
	    				row1.createCell(14).setCellValue("0");
	    				yoursSGD = yoursSGD+calc;
	    				theirsSGD = theirsSGD+calc1;
	    			}
	    			else
	    			{
	    				row1.createCell(10).setCellValue("0");
	    				row1.createCell(11).setCellValue("0");
	    				row1.createCell(13).setCellValue(calc);
	    				double calc1=Double.parseDouble(rs.getString("New_Ticket_Price"))-calc;
	    				row1.createCell(14).setCellValue(calc1);
	    				yoursMYR = yoursMYR+calc;
	    				theirsMYR = theirsMYR+calc1;
	    			}
	    		}
	    		else
	    		{
	    			calc = (op_site*Double.parseDouble(rs.getString("New_Ticket_Price")));
	    			if(rs.getString("Ticket_Currency").equalsIgnoreCase("SGD"))
	    			{
	    				
	    				row1.createCell(10).setCellValue(calc);
	    				double calc1 = Double.parseDouble(rs.getString("New_Ticket_Price"))-calc;
	    			row1.createCell(11).setCellValue(calc1);
	    			row1.createCell(13).setCellValue("0");
    				row1.createCell(14).setCellValue("0");
    				yoursSGD = yoursSGD+calc;
    				theirsSGD = theirsSGD+calc1;
	    			}
	    			else{
	    				row1.createCell(10).setCellValue("0");
	    				row1.createCell(11).setCellValue("0");
	    				row1.createCell(13).setCellValue(calc);
	    				double calc1 = Double.parseDouble(rs.getString("New_Ticket_Price"))-calc;
		    			row1.createCell(14).setCellValue(calc1);
		    			yoursMYR = yoursMYR+calc;
	    				theirsMYR = theirsMYR+calc1;
	    			}
	    		}
	    		}
	    	}
	    	Row lastRow = sheet.createRow(lastline+15);
	    	lastRow.createCell(9).setCellValue("Total");
	    	lastRow.createCell(10).setCellValue(yoursSGD);
	    	lastRow.createCell(11).setCellValue(theirsSGD);
	    	lastRow.createCell(13).setCellValue(yoursMYR);
	    	lastRow.createCell(14).setCellValue(theirsMYR);
	    	
	    	Row line = sheet.createRow(4);
	    	line.createCell(2).setCellValue("___________________________________________________________________________________________________________________________________________");
	    	
	    	Row preA = sheet.createRow(5);
	    	preA.createCell(12).setCellValue("SGD");
	    	preA.createCell(13).setCellValue("MYR");
	    	
	    	Row a = sheet.createRow(6);
	    	a.createCell(3).setCellValue("a) Amount paybale for Sales on Easibook.com");
	    	a.createCell(12).setCellValue(yoursSGD);
	    	a.createCell(13).setCellValue(yoursMYR);
	    	
	    	Row b = sheet.createRow(7);
	    	b.createCell(3).setCellValue("b) Amount payable for Sales on The One Travel & Tours website");
	    	b.createCell(12).setCellValue(theirsSGD);
	    	b.createCell(13).setCellValue(theirsMYR);
	    	
	    	Row c = sheet.createRow(8);
	    	c.createCell(3).setCellValue("c) Adjustments");
	    	c.createCell(12).setCellValue("-");
	    	c.createCell(13).setCellValue("-");
	    	
	    	Row midline = sheet.createRow(9);
	    	midline.createCell(2).setCellValue("________________________________________________________________________________________________________________________________________");
	    	
	    	Row d = sheet.createRow(10);
	    	d.createCell(3).setCellValue("d) Net Payable:");
	    	d.createCell(12).setCellValue(yoursSGD+theirsSGD);
	    	d.createCell(13).setCellValue(yoursMYR+theirsMYR);
	    	
	    	Row endline = sheet.createRow(11);
	    	endline.createCell(2).setCellValue("________________________________________________________________________________________________________________________________________");
	    	
	    	wb.write(fileOut);
			fileOut.close();
			lastline=0;
			i=14;
			sno=0;
			yoursSGD=0;theirsSGD=0;yoursMYR=0;theirsMYR=0;
			wb.removeSheetAt(0);
			
	 	}
	 
	 
	 catch(Exception e)
	 	{
		 e.printStackTrace();
		 JOptionPane.showMessageDialog(null, "output generation unsuccessful" );
	 	} 
	 }
	 RoundTripDiscount rtd = new RoundTripDiscount();
	rtd.start(startDate, endDate, dbName, table);
	 System.out.println("outputs generated");
	 JOptionPane.showMessageDialog(null, "Output generated successfully.");
	}
}
