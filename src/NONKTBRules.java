import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class NONKTBRules {
	
	ResultSet masterRs,ticketRs,rulesRs;
	Statement stmt;
	SalesNoteGenerator sng;
	int ind,last,i=0,originCheck=0;
	String sub;
	String[] ruleArray;
	String[][] ruleComponent;
	double calc,price;
	double originDisPrice;
	int id = 0,sno=1;
	CreationHelper createHelper;
	Sheet sheet;
	double yoursSGD=0,theirsSGD=0,yoursMYR=0,theirsMYR=0,thirdPartySGD = 0,thirdPartyMYR = 0;
	static int line = 14;
	FileOutputStream fileOut ;
	boolean satisfy = true;
	double operatorAmount;
	String newStarting,newDestination;
	int num = 0;
	String balancePayableTo;
	String EBCommission;
	String partyPayabaleType;
	double ownSite;
	double operatorSite,op_site,eb_site;
	double partyAmount;


	public void start(String startDate, String endDate, String dbName,String table) throws SQLException
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		 CellStyle cellStyle;
		 Cell d1,d2;
		 //cellStyle = wb.createCellStyle();
		 sng = new SalesNoteGenerator();
		 createHelper = wb.getCreationHelper();
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");
	    	String masterSql = "SELECT * FROM newmaster where CID in (Select Company_ID from extern)";
	    	stmt = conn.createStatement();
	    	masterRs = stmt.executeQuery(masterSql);
	    	masterRs.beforeFirst();
	    	RuleSplit splitter = new RuleSplit();
	    	

	    	
	    	
	    	while (masterRs.next())
	    	{
	    		
	    		String compId= "C:\\files\\outputrules\\"+masterRs.getInt("CID")+".xls";
				 sheet = wb.createSheet("Sheet1");
				 op_site = masterRs.getDouble("op_site");
				 eb_site = masterRs.getDouble("eb_site");
				 
				 
				 try {
					 
				     fileOut = new FileOutputStream(compId);
				   
					} catch (IOException e) {
						e.printStackTrace();
					}
				 
				 
				 
				 Row row = sheet.createRow(14);
			 		row.createCell(0).setCellValue("S.No");
			 		row.createCell(1).setCellValue("Invoice Number");
			 		row.createCell(2).setCellValue("Purchase Date");
			 		row.createCell(3).setCellValue("Ticket Price");
			 		row.createCell(4).setCellValue("Ticket Currency");
			 		row.createCell(5).setCellValue("External Transaction ID");
			 		row.createCell(6).setCellValue("Departure Date");
			 		row.createCell(7).setCellValue("Route ID");
			 		row.createCell(8).setCellValue("Discount");
			 		row.createCell(9).setCellValue("Route");
			 		row.createCell(10).setCellValue("EB.COM SGD");
			 		row.createCell(11).setCellValue("operator SGD");
			 		row.createCell(13).setCellValue("EB.COM MYR");
			 		row.createCell(14).setCellValue("operator MYR");
			 		row.createCell(15).setCellValue("Third Party SGD");
			 		row.createCell(16).setCellValue("Third Party MYR");
			 		
			 		Row sn = sheet.createRow(0);
	    			sn.createCell(0).setCellValue("Sales Note Number: SN"+sng.generate(endDate)+ " "+masterRs.getString("operator_name"));
			 		Row row3 = sheet.createRow(1);
		    		row3.createCell(0).setCellValue("Merchant:"+masterRs.getString("operator_name"));
		    		Row roww = sheet.createRow(2);
		    		roww.createCell(0).setCellValue("Description: Online Sales of Merchant's Coach Tickets Purchased from "+startDate+" to "+endDate);
				 
				 
	    		System.out.println(masterRs.getString("CID"));
	    		String ticketSql = "SELECT * FROM extern where Company_ID = "+masterRs.getDouble("CID")+" and Purchase_Date_and_Time between '"+startDate+"' and '"+endDate+"';";
	    		Statement  stmt1 = conn.createStatement();
 	    		ticketRs = stmt1.executeQuery(ticketSql);
 	    		
	    		String rulesSql = "Select  * from rulesmaster where Operator_ID = "+ masterRs.getDouble("CID")+" ;";
	    		String rulescount = "Select count(*) from rulesmaster where Operator_ID = "+ masterRs.getDouble("CID")+" ;";
	    		Statement  stmt2 = conn.createStatement();
	    		Statement  stmt3 = conn.createStatement();
	    		rulesRs = stmt2.executeQuery(rulesSql);
	    		
	    		
	    		ResultSet countRs = stmt3.executeQuery(rulescount);
	    		countRs.next();
	    		int size = countRs.getInt(1);
	    		rulesRs.beforeFirst();
	    		
	       		ruleArray = new String[size];
	       		
	       		
	       		String startDate1 = startDate.trim();
				startDate1 = startDate1.substring(0, 8)+"01";
				String ticketCount = "SELECT COUNT(*) FROM extern where Company_ID = "+masterRs.getDouble("CID")+" and Purchase_Date_And_Time between '"+startDate1+"' and '"+startDate+"';";
				Statement stmtCount = conn.createStatement();
				ResultSet count = stmtCount.executeQuery(ticketCount);
				count.next();
				int ticketCounter = count.getInt(1);
	       		
	       		if(!rulesRs.next())
	       		{
	       			satisfy = false;
	       			//System.out.println("no rules found for "+masterRs.getString("CID"));
	       			
	       		}
	       		else
	       		{
	       			rulesRs.beforeFirst();
	       			while(rulesRs.next())
	       			{
	    			
	       				ruleArray[i] = rulesRs.getString("rules");
	       				i++;
	    		
	       			}
	       		}
	    		i=0;
	    		rulesRs.beforeFirst();
	    		
	    		ticketRs.beforeFirst();
	    		
	    		while(ticketRs.next())
	    		{	
	    			if(satisfy)
	    			{
	    			for(int i=0;i<size;i++)
	    			{
	    				ruleComponent = splitter.split(ruleArray[i]);
	    				rulesRs.next();
	    				balancePayableTo = rulesRs.getString("Balance_Payable_To");
						EBCommission = rulesRs.getString("EB_Commission");
						partyPayabaleType = rulesRs.getString("Party_Payable_Type");
						ownSite = rulesRs.getDouble("OwnSite");
						operatorSite = rulesRs.getFloat("OperatorSite");
						partyAmount = rulesRs.getDouble("Party_Amount");
	    				satisfy = true;
	    				for(int j=0;j<7;j++)
	    				{
	    					//System.out.println(ruleComponent[j][0]);
	    					String ru = ruleComponent[j][0]; 
	    					
	    					if(ru == null){
	    						//System.out.println("all rules satisfied");
	    						break;
	    					}
	    					
	    					switch (ru)
	    					{
	    					case "route":
	    						{
	    							String starting = ruleComponent[j][1];
	    							String destination = ruleComponent[j][2];
	    							if(ruleComponent[j][1].trim().equalsIgnoreCase("kl"))
	    								starting = "Kuala Lumpur";
	    							if(ruleComponent[j][2].trim().equalsIgnoreCase("kl"))
	    							{
	    								destination = "Kuala Lumpur";
	    							}
	    							if(!starting.equalsIgnoreCase(ticketRs.getString("from_place")))
	    							{
	    								String startsql = "Select * from locationmaster where location = '"+ticketRs.getString("from_place")+"';";
	    								Statement startstmt = conn.createStatement();
	    								ResultSet startRs = startstmt.executeQuery(startsql);
	    								if(startRs.next())
	    								{
	    									 newStarting = startRs.getString("city");
	    								}
	    								else
	    								{
	    									newStarting = starting;
	    								}	
	    								
	    							}
	    							if(!destination.equalsIgnoreCase(ticketRs.getString("to_place")))
	    							{
	    								String destsql = "Select * from locationmaster where location = '"+ticketRs.getString("to_place")+"';";
	    								Statement deststmt = conn.createStatement();
	    								ResultSet destRs = deststmt.executeQuery(destsql);
	    								if(destRs.next())
	    								{
	    									newDestination = destRs.getString("city");
	    								}
	    								else
	    								{
	    									if(ticketRs.getString("to_place").equalsIgnoreCase("unknown"))
	    										newDestination = "unknown";
	    									else
	    										newDestination = destination;
	    								}	
	    								
	    							}
	    							
	    							if(newStarting.trim().equalsIgnoreCase(starting.trim())&&newDestination.trim().equalsIgnoreCase(destination.trim()))
	    							{
	    								satisfy = true;
	    								
	    							}
	    							else
	    							{
	    								satisfy = false;
	    							}
	    							
	    						}
	    	    				break;
	    	    			case "ticketrange":    	    			
	    	    				double ticketPrice = ticketRs.getDouble("New_Ticket_Price");
	    	    				double low = Double.parseDouble(ruleComponent[j][1]);
	    	    				double high = Double.parseDouble(ruleComponent[j][2]);

	    	    				
	    	    				if(ticketPrice>=low && ticketPrice<=high)
	    	    				{
	    	    					satisfy = true;

	    	    				}
	    	    				else
	    	    				{
	    	    					satisfy =false;

	    	    				}
	    	    	    		break;
	    	    			case "ticketcount":
	    	    			{
	    	    				ticketCounter = ticketCounter+1;
	    	    				if(ticketCounter>Integer.parseInt(ruleComponent[j][1])&&ticketCounter<=Integer.parseInt(ruleComponent[j][2]))
	    	    				{
	    	    					satisfy = true;
	    	    				}
	    	    				else
	    	    				{
	    	    					satisfy = false;
	    	    				}
	    	    			}	   	    				
	    	    				break;
	    	    			case "standard":
	    	    				satisfy = false;
	    	    				break;
	    	    			case "ticketprice":
	    	    				{
	    	    					double price = Double.parseDouble(ruleComponent[j][1]);
	    	    					if(ticketRs.getDouble("New_Ticket_Price")==price)
	    	    					{
	    	    						satisfy = true;
	    	    					}
	    	    					else
	    	    					{
	    	    						satisfy = false;
	    	    					}
	    	    				}
	    	    				break;
	    	    			case "time":
	    	    			{
	    	    				String startTime = ruleComponent[j][1];
	    	    				String endTime = ruleComponent[j][2];
	    	    				String dept = ticketRs.getTimestamp("Departure_Date_and_Time").toString();
	    	    				DateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
	    	    				Date deptDate = sdf.parse(dept);
	    	    				DateFormat sdftime = new SimpleDateFormat("hh:mm"); 
	    	    				Date start = sdftime.parse(startTime);
	    	    				Date end = sdftime.parse(endTime);
	    	    				Calendar cal = Calendar.getInstance();
	    	    				cal.setTime(deptDate);
	    	    				cal.set(Calendar.MILLISECOND,0);
	    	    				Calendar cal1 = Calendar.getInstance();
	    	    				Calendar cal2 = Calendar.getInstance();
	    	    				
	    	    				cal1.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),start.getHours() ,start.getMinutes(),0);
	    	    				cal1.set(Calendar.MILLISECOND,0);
	    	    				Date startTime1 = cal1.getTime();
	    	    				cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),end.getHours() ,end.getMinutes(),0);
	    	    				cal2.set(Calendar.MILLISECOND,0);
	    	    				Date endTime1 = cal2.getTime();
	    	    				int a = cal.compareTo(cal1);
	    	    				int b = cal.compareTo(cal2);
	    	    				if(a>=0 && b<=0)
	    	    				{
	    	    					satisfy = true;
	    	    					
	    	    				}
	    	    				else
	    	    				{
	    	    					satisfy = false;
	    	    					
	    	    				}
	    	    				
	    	    			}
	    	    				break;
	    	    			case "origindiscount":
	    	    			{
	    	    				String starting = ruleComponent[j][1];
	    	    				if(!starting.equalsIgnoreCase(ticketRs.getString("from_place")))
	    	    				{
	    	    					String startsql = "Select * from locationmaster where location = '"+ticketRs.getString("from_place")+"';";
    								Statement startstmt = conn.createStatement();
    								ResultSet startRs = startstmt.executeQuery(startsql);
    								if(startRs.next())
    								{
    									 newStarting = startRs.getString("city");
    								}
    								else
    								{
    									newStarting = starting;
    								}	
	    	    				}
	    	    				if(starting.equalsIgnoreCase(ruleComponent[j][1]))
	    	    				{
	    	    					satisfy = true;
	    	    					originCheck = 1;
	    	    					originDisPrice = ticketRs.getDouble("New_Ticket_Price");
	    	    					if(ruleComponent[j][2].trim().equalsIgnoreCase("percentage"))
	    	    					{
	    	    						originDisPrice = (originDisPrice - ((originDisPrice*Double.parseDouble(ruleComponent[j][3])/100)));
	    	    					}else
	    	    					{
	    	    						originDisPrice = originDisPrice - Double.parseDouble(ruleComponent[j][3]);
	    	    					}
	    	    				}
	    	    				else
	    	    				{
	    	    					satisfy = false;
	    	    				}
	    	    			}
	    	    				
	    	    				break;
	    	    			case "all":
	    	    				satisfy = true;
	    	    				break;
	    	    		 default:
	    	    			
	    					}
	    					if(satisfy == false){
	    						break;
	    					}

	    				}
	    				if(satisfy)
	    				{
	    					line = line+1;
	    				/*	if(masterRs.getString("CID").equalsIgnoreCase("54"))
	    					{
	    					System.out.println("a rule satisfied for "+masterRs.getInt("CID")+" for rule "+ruleArray[i]);
	    					System.out.println("balancePayableTo= "+balancePayableTo+" EBCommission = "+EBCommission+" partyPayabaleType = "+partyPayabaleType+" ownSite = "+ownSite+" operatorSite = "+ operatorSite+" partyAmount = "+partyAmount);
	    					System.out.println();
	    					} */
	    					
	    					
	    					
	    				Row row1 = sheet.createRow(line);
	    				row1.createCell(0).setCellValue(sno++);
		    	    		row1.createCell(1).setCellValue(ticketRs.getString("Transaction_ID"));
		    	    		row1.createCell(2).setCellValue(ticketRs.getString("Purchase_Date_and_Time"));
		    	    		row1.createCell(3).setCellValue(ticketRs.getString("New_Ticket_Price"));
		    	    		row1.createCell(4).setCellValue(ticketRs.getString("Ticket_Currency"));
		    	    		row1.createCell(5).setCellValue(ticketRs.getString("External_Transaction_ID"));
		    	    		row1.createCell(6).setCellValue(ticketRs.getString("Departure_Date_and_Time"));
		    	    		row1.createCell(7).setCellValue(ticketRs.getString("Route_ID"));
		    	    		row1.createCell(8).setCellValue(ticketRs.getString("Trip_No"));
	    					
		    	    		if(originCheck==1)
		    	    		{
		    	    			 price = originDisPrice;
		    	    			 originCheck = 0;
		    	    			// System.out.println("original price = "+ticketRs.getDouble("New_Ticket_Price")+" price = "+price);
		    	    			// System.out.println();
		    	    		}
		    	    		else
		    	    		{
		    	    			 price = ticketRs.getDouble("New_Ticket_Price");
		    	    		}
	    					if(balancePayableTo.equalsIgnoreCase("operator"))
	    					{
	    						row1.createCell(15).setCellValue(0);
	    						if(EBCommission.equalsIgnoreCase("fixed"))
	    						{
	    							 operatorAmount = price - ownSite;
	    							if(ticketRs.getString("Ticket_Currency").equalsIgnoreCase("sgd"))
	    							{
	    							row1.createCell(10).setCellValue(ownSite);
	    							row1.createCell(11).setCellValue(operatorAmount);
	    							row1.createCell(13).setCellValue(0);
	    							row1.createCell(14).setCellValue(0);
	    							row1.createCell(15).setCellValue(0);
	    							row1.createCell(16).setCellValue(0);
	    							yoursSGD = yoursSGD+ownSite;
	    							theirsSGD = theirsSGD+operatorAmount;
	    							}
	    							else
	    							{
	    								row1.createCell(10).setCellValue(0);
		    							row1.createCell(11).setCellValue(0);
		    							row1.createCell(13).setCellValue(ownSite);
		    							row1.createCell(14).setCellValue(operatorAmount);
		    							row1.createCell(15).setCellValue(0);
		    							row1.createCell(16).setCellValue(0);
		    							yoursMYR = yoursMYR+ownSite;
		    							theirsMYR = theirsMYR+operatorAmount;
	    							}
	    							
	    						}else
	    						{
	    							double operatorAmount = (price - ((price*ownSite)/100));
	    							if(ticketRs.getString("Ticket_Currency").equalsIgnoreCase("sgd"))
	    							{
	    								row1.createCell(10).setCellValue((price*ownSite)/100);
		    							row1.createCell(11).setCellValue(operatorAmount);
		    							row1.createCell(13).setCellValue(0);
		    							row1.createCell(14).setCellValue(0);
		    							row1.createCell(15).setCellValue(0);
		    							row1.createCell(16).setCellValue(0);
		    							yoursSGD = yoursSGD+(price*ownSite)/100;
		    							theirsSGD = theirsSGD+operatorAmount;
	    							}
	    							else
	    							{
	    								row1.createCell(10).setCellValue(0);
		    							row1.createCell(11).setCellValue(0);
		    							row1.createCell(13).setCellValue((price*ownSite)/100);
		    							row1.createCell(14).setCellValue(operatorAmount);
		    							row1.createCell(15).setCellValue(0);
		    							row1.createCell(16).setCellValue(0);
		    							yoursMYR = yoursMYR+(price*ownSite)/100;
		    							theirsMYR = theirsMYR+operatorAmount;
	    							}
	    						}
	    					}else
	    					{
	    						if(EBCommission.equalsIgnoreCase("fixed"))
	    						{
	    							double amount = price - ownSite;
	    							if(partyPayabaleType.equalsIgnoreCase("fixed"))
	    							{
	    							 operatorAmount = amount - partyAmount;
	    							}
	    							else
	    							{
	    								operatorAmount = (amount -((amount * partyAmount)/100));
	    							}
	    							
	    							if(ticketRs.getString("Ticket_Currency").equalsIgnoreCase("sgd"))
	    							{
	    								row1.createCell(10).setCellValue(ownSite);
		    							row1.createCell(11).setCellValue(operatorAmount);
		    							row1.createCell(13).setCellValue(0);
		    							row1.createCell(14).setCellValue(0);
		    							row1.createCell(15).setCellValue(partyAmount);
		    							row1.createCell(16).setCellValue(0);
		    							yoursSGD = yoursSGD+ownSite;
		    							theirsSGD = theirsSGD+operatorAmount;
		    							thirdPartySGD = thirdPartySGD + partyAmount;
	    							}
	    							else
	    							{
	    								row1.createCell(10).setCellValue(0);
		    							row1.createCell(11).setCellValue(0);
		    							row1.createCell(13).setCellValue(ownSite);
		    							row1.createCell(14).setCellValue(operatorAmount);
		    							row1.createCell(15).setCellValue(0);
		    							row1.createCell(16).setCellValue(partyAmount);
		    							yoursMYR = yoursMYR+ownSite;
		    							theirsMYR = theirsMYR+operatorAmount;
		    							thirdPartyMYR = thirdPartyMYR + partyAmount;
	    							}
	    							
	    						}else
	    						{
	    							double amount = (price -((price* ownSite)/100));
	    							if(partyPayabaleType.equalsIgnoreCase("fixed"))
	    							{
	    							 operatorAmount = amount - partyAmount;
	    							}
	    							else
	    							{
	    								operatorAmount = (amount -((amount * partyAmount)/100));
	    							}
	    							
	    							if(ticketRs.getString("Ticket_Currency").equalsIgnoreCase("sgd"))
	    							{
	    								row1.createCell(10).setCellValue((amount * partyAmount)/100);
		    							row1.createCell(11).setCellValue(operatorAmount);
		    							row1.createCell(13).setCellValue(0);
		    							row1.createCell(14).setCellValue(0);
		    							row1.createCell(15).setCellValue(partyAmount);
		    							row1.createCell(16).setCellValue(0);
		    							yoursSGD = yoursSGD+(amount * partyAmount)/100;
		    							theirsSGD = theirsSGD+operatorAmount;
		    							thirdPartySGD = thirdPartySGD + partyAmount;
	    							}
	    							else
	    							{
	    								row1.createCell(10).setCellValue(0);
		    							row1.createCell(11).setCellValue(0);
		    							row1.createCell(13).setCellValue((amount * partyAmount)/100);
		    							row1.createCell(14).setCellValue(operatorAmount);
		    							row1.createCell(15).setCellValue(0);
		    							row1.createCell(16).setCellValue(partyAmount);
		    							yoursMYR = yoursMYR+(amount * partyAmount)/100;
		    							theirsMYR = theirsMYR+operatorAmount;
		    							thirdPartyMYR = thirdPartyMYR + partyAmount;
	    							}
	    						}
	    					} 

	    					break;
	    				}
	    			}
	    			
	    		}
	    		
	    			rulesRs.beforeFirst();
	    			if(satisfy == false)
	    			{
	    				
	    				
	    				line = line+1;
	    			
	    				Row row1 = sheet.createRow(line);
    				
	    				row1.createCell(0).setCellValue(sno++);
	    				row1.createCell(1).setCellValue(ticketRs.getString("Transaction_ID"));
	    	    		row1.createCell(2).setCellValue(ticketRs.getString("Purchase_Date_and_Time"));
	    	    		row1.createCell(3).setCellValue(ticketRs.getString("New_Ticket_Price"));
	    	    		row1.createCell(4).setCellValue(ticketRs.getString("Ticket_Currency"));
	    	    		row1.createCell(5).setCellValue(ticketRs.getString("External_Transaction_ID"));
	    	    		row1.createCell(6).setCellValue(ticketRs.getString("Departure_Date_and_Time"));
	    	    		row1.createCell(7).setCellValue(ticketRs.getString("Route_ID"));
	    	    		row1.createCell(8).setCellValue(ticketRs.getString("Trip_No"));
	
	    	    			calc = (eb_site*Double.parseDouble(ticketRs.getString("New_Ticket_Price")));
	    	    			if(ticketRs.getString("Ticket_Currency").equalsIgnoreCase("SGD"))
	    	    			{
	    	    				
	    	    				row1.createCell(10).setCellValue(calc);
	    	    				double calc1=Double.parseDouble(ticketRs.getString("New_Ticket_Price"))-calc;
	    	    				row1.createCell(11).setCellValue(calc1);
	    	    				row1.createCell(13).setCellValue(0);
	    	    				row1.createCell(14).setCellValue(0);
	    	    				row1.createCell(15).setCellValue(0);
	    	    				row1.createCell(16).setCellValue(0);
	    	    				yoursSGD = yoursSGD+calc;
	    	    				theirsSGD = theirsSGD+calc1;
	    	    			}
	    	    			else
	    	    			{
	    	    				row1.createCell(10).setCellValue(0);
	    	    				row1.createCell(11).setCellValue(0);
	    	    				row1.createCell(13).setCellValue(calc);
	    	    				row1.createCell(15).setCellValue(0);
	    	    				double calc1=Double.parseDouble(ticketRs.getString("New_Ticket_Price"))-calc;
	    	    				row1.createCell(14).setCellValue(calc1);
	    	    				row1.createCell(16).setCellValue(0);
	    	    				yoursMYR = yoursMYR+calc;
	    	    				theirsMYR = theirsMYR+calc1;
	    	    				
	    	    			}
	    	    		
	    
	    	    		
	    	    		
	    				//new change 11 dec 2015
	    	    			if(Integer.parseInt(ticketRs.getString("Company_ID"))==29)
	    				satisfy = true;
	    	    			else
	    	    			satisfy = false;
	    				
	    			}
	    		}
	    		try {
	    			Row lastRow = sheet.createRow(sno++ + 14);
    	    		lastRow.createCell(9).setCellValue("Total");
    		    	lastRow.createCell(10).setCellValue(yoursSGD);
    		    	lastRow.createCell(11).setCellValue(theirsSGD);
    		    	lastRow.createCell(13).setCellValue(yoursMYR);
    		    	lastRow.createCell(14).setCellValue(theirsMYR);
    		    	lastRow.createCell(15).setCellValue(thirdPartySGD);
    		    	lastRow.createCell(16).setCellValue(thirdPartyMYR);
    		    	
    		    	
    		    	Row firstline = sheet.createRow(4);
    		    	firstline.createCell(2).setCellValue("________________________________________________________________________________________________________________________________________");
    		    	
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
    		    	
    		    	Row e = sheet.createRow(8);
    		    	e.createCell(3).setCellValue("d) Amount payable for Sales on ThirdParty");
    		    	e.createCell(12).setCellValue(thirdPartySGD);
    		    	e.createCell(13).setCellValue(thirdPartyMYR);
    		    	
    		    	Row c = sheet.createRow(9);
    		    	c.createCell(3).setCellValue("d) Adjustments");
    		    	c.createCell(12).setCellValue("-");
    		    	c.createCell(13).setCellValue("-");
    		    	
    		    	Row midline = sheet.createRow(10);
    		    	midline.createCell(2).setCellValue("________________________________________________________________________________________________________________________________________");
    		    	
    		    	Row d = sheet.createRow(11);
    		    	d.createCell(3).setCellValue("e) Net Payable:");
    		    	d.createCell(12).setCellValue(yoursSGD+theirsSGD+thirdPartySGD);
    		    	d.createCell(13).setCellValue(yoursMYR+theirsMYR+thirdPartyMYR);
    		    	
    		    	Row endline = sheet.createRow(12);
    		    	endline.createCell(2).setCellValue("________________________________________________________________________________________________________________________________________");
    		    	
    		    	
		 			wb.write(fileOut);
		 			fileOut.close();
		 			line = 14;
		 			sno=1;
		 			yoursSGD=0;theirsSGD=0;yoursMYR=0;theirsMYR=0;thirdPartySGD = 0;thirdPartyMYR = 0;
		 			wb.removeSheetAt(0);
		 			satisfy = true;
		 		} catch (Exception e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
	    	}
	    	 
	    	
		}catch (Exception e){
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "output generated");
	}
}
