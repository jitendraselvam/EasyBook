

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JTextField;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Toucher
{
	
	static Label label;
	static String[][] string_array;
	static WritableWorkbook workbook1;
	
	public void touch(String fileName,String fileName1) throws BiffException, IOException {

		

		
		 Workbook workbook = Workbook.getWorkbook(new File(fileName));
		 int noOfSheets = workbook.getNumberOfSheets();
		 //System.out.println("no of sheets: "+ noOfSheets);
		 
		 try{
			 String copyFile = fileName;
	    	    workbook1 = Workbook.createWorkbook(new File("C:\\touched\\"+fileName1));
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 for(int count = 0;count<noOfSheets;count++)
		 {
			 
	      Sheet sheet = workbook.getSheet(count);
	      String name = sheet.getName();
	      int totalNoOfRows = sheet.getRows();
	      int totalNoOfCols = sheet.getColumns();
	      
  	    
	      
	      try {
	    	    
	    	    WritableSheet writableSheet = workbook1.createSheet(name, count);
		      string_array = new String[totalNoOfCols][totalNoOfRows] ;
		      for(int i =0;i<totalNoOfRows;i++)
		      {
		    	  for(int j =0;j<totalNoOfCols;j++)
		    	  {
		    		  Cell cell1 = sheet.getCell(j,i);
		    		  String write = cell1.getContents();
		    		  string_array[j][i] = write;
		    	  }
		     
		      }
	    	    for(int i = 0;i<totalNoOfRows;i++)
	    	    {
	    	    	for(int j =0;j<totalNoOfCols;j++)
	    	    	{
	    	    		//System.out.print(string_array[j][i]+" \t");
	    	    		writableSheet.addCell(new Label(j,i,string_array[j][i]));
	    	    	}
	    	    	//System.out.println();
	    	    }
	    	   
	    	    
	    	    
	    	} catch (WriteException e) {

	    	}
	      

	     // Cell cell2 = sheet.getCell(3, 4);
	      //System.out.println(cell2.getContents());
	      
	      
	      
		//workbook1.close();
	}
		 workbook1.write();
		 try {
			workbook1.close();
			workbook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clean(String file)
	{
try{
    		
    		File deleteFile = new File(file);
        	
    		if(deleteFile.delete()){
    		//	System.out.println(deleteFile.getName() + " is deleted!");
    		}else{
    			//System.out.println("Delete operation is failed.");
    		}
    	   
    	}catch(Exception e){
    		
    		e.printStackTrace();
    		
    	}
	}
	
}