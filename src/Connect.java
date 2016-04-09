import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;


public class Connect {

	Statement stmt;

	static int sheets;
	static int count;
	static 	int index=0;
	static Boolean doprint = true;
	static int j=1,k=1;
	static int result;
	static int col = 1;
	static int externCheck;
	static boolean lol;
	static String checkRes;
	PreparedStatement externStmt;
	 static FileOutputStream fileOut1;
	 static Workbook wb1;
	 static String[] places;
	 File file1;


	public void connect(String fileLoc,String dbName,String table,String fName)  throws IOException,Exception 
	{
		SimpleDateFormat  formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
		UpdateChecker up = new UpdateChecker();
		//checkRes = up.check(fileLoc,table, dbName);

	
		try {
		    	FileInputStream file = new FileInputStream(new File(fileLoc));	
		    	if(fileLoc.contains("External"))
		    	{
		    		System.out.println("external file");
		    		checkRes = up.externalCheck(fileLoc, "extern", dbName);
		    		externCheck = 0;
		    	}
		    	else
		    	{
		    		System.out.println("internal file");
		    		checkRes = up.check(fileLoc,table, dbName);
		    		externCheck = 1;
		    	}
		    	Class.forName("com.mysql.jdbc.Driver");
		    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
		    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
		    	String masterIgnore ="Select CID , external from newmaster where round_trip_discount = 'false';";
		    	Statement masterStmt = conn.createStatement();
		    	ResultSet masterRs = masterStmt.executeQuery(masterIgnore);
		    	masterRs.beforeFirst();
		 if(!checkRes.equalsIgnoreCase("empty"))
		    	{
		    	System.out.println("delete row with:"+ checkRes);
		    	if(externCheck ==1)
		    	{
		    	String delSQL = "delete from "+table+" where Purchase_Date_And_Time between '"+checkRes+"' and DATE_ADD('"+checkRes+"', INTERVAL 1 DAY);";
		    	String delSQL1 = "delete from "+table+" where Departure_Date_And_Time between '"+checkRes+"' and DATE_ADD('"+checkRes+"', INTERVAL 1 DAY);";
		    	PreparedStatement delStmt = conn.prepareStatement(delSQL);
	    		PreparedStatement delStmt1 = conn.prepareStatement(delSQL1);
	    		delStmt.executeUpdate(delSQL);
	    		delStmt.executeUpdate(delSQL1);
		    	}
		    	else if (externCheck ==0)
		    	{
		    	String delExternSQL = "delete from extern where Purchase_Date_And_Time between '"+checkRes+"' and DATE_ADD('"+checkRes+"', INTERVAL 1 DAY);";
		    	String delExternSQL1 = "delete from extern where Departure_Date_And_Time between '"+checkRes+"' and DATE_ADD('"+checkRes+"', INTERVAL 1 DAY);";
		    	PreparedStatement delStmt = conn.prepareStatement(delExternSQL);
	    		PreparedStatement delStmt1 = conn.prepareStatement(delExternSQL1);
		    	delStmt.executeUpdate(delExternSQL);
	    		delStmt.executeUpdate(delExternSQL1);
		    	}
		    		/*Date date = formatter.parse(checkRes);
		    		Calendar c = Calendar.getInstance();
		    		c.setTime(formatter.parse(checkRes));
		    		c.add(Calendar.DATE, 1); 
		    		String nextDate = formatter.format(c.getTime()).toString();
		    		System.out.println("date = "+ checkRes +"next date = "+nextDate);
		    		String delSQL = "DELETE FROM "+table+" where Purchase_Date_and_Time between '"+checkRes+" and "+nextDate+"';";
		    		String delSQL1 = "DELETE FROM "+table+" where Departure_Date_and_Time = '"+checkRes+"';"; */
		    		//PreparedStatement delStmt = conn.prepareStatement(delSQL);
		    		//PreparedStatement delStmt1 = conn.prepareStatement(delSQL1);
		    		//delStmt.executeUpdate(delSQL);
		    	//	delStmt.executeUpdate(delSQL1);
		    		//delStmt.executeUpdate(delExternSQL);
		    		//delStmt.executeUpdate(delExternSQL1);
		    	} 

		    	
		    	String sql = "INSERT INTO "+table+" (Company_ID, Sub_Company, Cart_GUID, Payment_Gateway, Purchase_Date_and_Time, Invoice_Number, Create_User, Payment_Type, Settlement_Currency, Total_Cost, Ticket_Currency, Ticket_Price, New_Ticket_Price, Discount_Code_Discount, Round_Trip_Discount, Passenger_Name, From_Sub_Place, To_Sub_Place, Departure_Date_and_Time, Member_From_Company_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    	String externalSql = "INSERT INTO extern (Company_ID, Transaction_ID, Purchase_Date_and_Time, New_Ticket_Price, Ticket_Currency, Passenger_Name, External_Ticket_ID, External_Transaction_ID, Departure_Date_and_Time, Route_ID, Trip_No, from_place, to_place) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    	PreparedStatement stmt = conn.prepareStatement(sql);
		    	externStmt = conn.prepareStatement(externalSql);
		    	HSSFWorkbook workbook = new HSSFWorkbook(file);
		    	sheets = workbook.getNumberOfSheets();
		    	
	
		    	
		    	for(int i=0;i<workbook.getNumberOfSheets();i++)
		    	{
		    		HSSFSheet sheet = workbook.getSheetAt(i);
		    		Iterator<Row> rowIterator = sheet.iterator();
		    		Row  row1 = rowIterator.next();
		    		Iterator<Cell> cellIterator1 = row1.cellIterator();
		    		index=0;j=1;
		    		while (cellIterator1.hasNext())
		    		{
		    			
		    			Cell cell = cellIterator1.next();
						if(cell.getStringCellValue().equalsIgnoreCase("Company ID"))
						{
							break;
						}
						index++;
		    		}
		    		
		    		while(rowIterator.hasNext())
		    		{	
		    			
		    			Row row = rowIterator.next();
		    			Iterator<Cell> cellIterator = row.cellIterator();
		    			count = 0;
		    			doprint = true;
		    			while(cellIterator.hasNext())
		    			{
		    				
		    				Cell cell = cellIterator.next();	
		    				if (count < index)
		    				{
		    					count++;
		    					col++;
		    					
		    					continue;
		    				}
		    				if ( count == index)
		    				{
		    					if(cell.getCellType()==Cell.CELL_TYPE_BLANK || cell.getStringCellValue().trim().isEmpty())
		    					{
		    						col++;
		    						
		    						doprint = false;
		    						break;
		    					}
		    				}
		    				
		    				switch(cell.getCellType()) 
				            {
		    						case Cell.CELL_TYPE_BLANK:
		    							col++;
		    							System.out.print("blank");
		    							break;
				                	case Cell.CELL_TYPE_BOOLEAN:    	 
				                		break;
				                	case Cell.CELL_TYPE_NUMERIC:
				                		if(externCheck ==1)
				                		{
				                			stmt.setDouble(j, cell.getNumericCellValue());
				                			j++;
				                		}
				                		else
				                		{
				                			externStmt.setDouble(k, cell.getNumericCellValue());
				                			k++;
				                		}
				                		col++;
				                		break;
				                	case Cell.CELL_TYPE_STRING:
				                		
					                		if((j==5||j==19)&& externCheck ==1)
					                		{
					                		
					                			col++;
					                			String tempDate = cell.getStringCellValue();
					                			Date date = formatter.parse(tempDate);
					                			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
					                			stmt.setTimestamp(j, timestamp);
					                			j++;
					                		}
					                		else if((k==3 || k ==9) && externCheck ==0)
					                		{
					                			col++;
					                			String tempDate = cell.getStringCellValue();
					                			Date date = formatter.parse(tempDate);
					                			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
					                			externStmt.setTimestamp(k, timestamp);
					                			k++;
					                		}
					                		else if((k==10) && externCheck ==0)
					                		{
					                			
					                			String routeId = cell.getStringCellValue();
					                			externStmt.setString(k, routeId);
					                		/*	if(routeId.contains("-") && routeId.contains("/"))
					                			{
					                				String route = routeId.substring(routeId.indexOf("_")+1,routeId.indexOf("/"));
					                				places = route.split("-");
					                				if(places.length==2)
					                				{
					                					externStmt.setString(12, places[0]);
							    						externStmt.setString(13, places[1]);
					                					
					                				}
					                				else
					                				{
					                					externStmt.setString(12, places[0]);
							    						externStmt.setString(13, "unknown");
					                				} 
					                				
					                			} */
					                			if(routeId.contains("-") && (routeId.contains("/") || routeId.contains("(")) )
					                			splitter(routeId);
					                			else
					                			{
					                				externStmt.setString(12, "unknown");
						    						externStmt.setString(13, "unknown");	
					                			}
					                			
					                			
					                			
					                			k++;
					                		}
					                		else
					                		{
					                			if(j==1 && k ==1)
					                				while(masterRs.next())
					                				{
					                					//System.out.println(cell.getStringCellValue());
					                					if(masterRs.getDouble("CID") == Double.parseDouble(cell.getStringCellValue()))
					                					{	          
					                						doprint = true;
					                						break; //added new
					                					}
					                				}
					                			masterRs.beforeFirst();
					                			if(externCheck ==1)
					                			{
					                				stmt.setString(j, cell.getStringCellValue());
					                				j++;
					                			}
					                			else
					                			{
					                				externStmt.setString(k, cell.getStringCellValue());
					                				//System.out.println(cell.getStringCellValue());
					                				k++;
					                			}
				                		
				                		}
				                		break;
				            }
		    				if(externCheck ==1)
		    				{
		    					stmt.setDouble(14, 0);
		    				}
		    				count++;
		    				
		    			} if (doprint)
		    			{	
		    				if(externCheck ==1)
		    				{
		    					result = stmt.executeUpdate();
		    					
		    				}
		    				else
		    				{
		    				result = externStmt.executeUpdate();
		    				}

		    			}
		    			
		    			j=1;
		    			k=1;
		    			col=1;
		    		}	
		    		//System.out.println("Updated "+sheet.getSheetName());
		    		//lblProgress.setText(sheet.getSheetName());
		    		
		    	}
		    	file.close();
				//System.out.println("Database updated successfully");
				ErrorFile ef = new ErrorFile();
				ef.createFile(dbName,table);
		    	}catch (FileNotFoundException e) {
					e.printStackTrace();
					StringBuilder sb = new StringBuilder(e.toString());
				    for (StackTraceElement ste : e.getStackTrace()) {
				        sb.append("\n\tat ");
				        sb.append(ste);
				    }
				    String trace = sb.toString();
				    JOptionPane.showMessageDialog(null, trace);
				}
				catch (IOException e) {
				    e.printStackTrace();
				    StringBuilder sb = new StringBuilder(e.toString());
				    for (StackTraceElement ste : e.getStackTrace()) {
				        sb.append("\n\tat ");
				        sb.append(ste);
				    }
				    String trace = sb.toString();
				    JOptionPane.showMessageDialog(null, trace);
				}
			}


	private void splitter(String routeId1) {

		
		try
		{
			String string2 = null;
			String string1 = null;
			int dash=0;
			for(int count = 0;count<routeId1.length();count++)
			{
				Character check = routeId1.charAt(count);
				if(check == '-')
				{
				dash++;
				}
			}
			if(dash==2)
			{
				String[] split = routeId1.split("-");
				String deepSplitLeft[] = split[1].split(" "); // we only need deepSplitLeft[1]
				String deepSplitRight[] = split[2].trim().split(" "); // we only need deepSplitRight[1]
				string1 = deepSplitLeft[1];
				string2 =deepSplitRight[0];
			}
			else
			{
			String s = routeId1;
			String[] splits = s.split("-"); 

			String[] lefts = splits[0].split("[^a-zA-Z]"); 
			String[] rights = splits[1].split("[^a-zA-Z]");
			string1 = lefts[lefts.length - 1];
			if(dash==2)
			{
				if(string2.isEmpty())
				{
					string2 = rights[0];
				}
				else
				{
					string2 = rights[1];
				}
			}
			else
			{
				 string2 = rights[0];
			}
			
			}
		
		
		externStmt.setString(12, string1);
		externStmt.setString(13, string2);
		
		}catch(Exception e)
		{
			System.out.println("*****ERROR**** :  "+routeId1);
		}
}
		
	
		}
