import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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


public class StandardRule {
	
	Sheet sheet;
	FileOutputStream fileOut ;
	int i=14;
	int sno=0;
	double calc;
	int cid=0;
	CreationHelper createHelper;
	int lastline=0;
	double yoursSGD=0,theirsSGD=0,yoursMYR=0,theirsMYR=0;
	
	
	public void apply(String tickets[],int line,int first)
	{ 
		Workbook wb = new HSSFWorkbook();
		 CellStyle cellStyle;
		 Cell d1,d2;
		 Date date = new Date();
		 cellStyle = wb.createCellStyle();
		 createHelper = wb.getCreationHelper();
		 if(first==1)
		 {
			
			 String compId= "C:\\files\\outputrules\\"+tickets[0]+".xls";
			 sheet = wb.createSheet("Sheet1");
			 try {
				 
			     fileOut = new FileOutputStream(compId);
			   
				} catch (IOException e) {
					e.printStackTrace();
				}
			 
			 
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
 		row.createCell(10).setCellValue("EB.com SGD");
 		row.createCell(11).setCellValue("Operator SGD");
 		row.createCell(13).setCellValue("EB.com MYR");
 		row.createCell(14).setCellValue("Operator MYR");
 		
 		Row row1 = sheet.createRow(line);
 		row1.createCell(0).setCellValue(tickets[0]);
 		
		 }
		 else
		 {

		 }
		 
		 try {
			wb.write(fileOut);
			fileOut.close();
			wb.removeSheetAt(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}

