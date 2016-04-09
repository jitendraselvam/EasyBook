import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class RuleChecker {
	int ind,last;
	String sub,sql;
	Connection conn;
	static String startDate,endDate;
	
	public void check(String rule,int companyId,String dbName,String table,String date1,String date2)
	{
		startDate = date1;
		endDate = date2;
		try{
			Class.forName("com.mysql.jdbc.Driver");
	    	String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
	    	conn = DriverManager.getConnection(connectionUrl, "root", "123123");
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		switch (rule.substring(0,rule.indexOf("(")).trim())
		{
		case "route":
			 ind = rule.indexOf("(");
			 last = rule.length()-1;
			 sub = rule.substring(ind+1,last);
			String locations[] = sub.split(",");
			//System.out.println("sub "+locations[0]+" "+locations[1]);
			route(locations[0],locations[1],table,companyId);
			break;
		
		case "ticketRange":
    			ind = rule.indexOf("(");
    			last = rule.length()-1;
    			sub = rule.substring(ind+1,last);
    			String numbers[] = sub.split(",");
    			//System.out.println("sub "+locations[0]+" "+locations[1]);
    			ticketRange(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]),table,companyId);
    			break;
		case "standard":
			ind = rule.indexOf("(");
			last = rule.length()-1;
			standard();
			break;
		case "ticketPrice":
			ind = rule.indexOf("(");
			last = rule.length()-1;
			sub = rule.substring(ind+1,last);
			//System.out.println("sub "+locations[0]+" "+locations[1]);
			ticketPrice(Integer.parseInt(sub),table,companyId);
			break;
		case "time":
			ind = rule.indexOf("(");
			last = rule.length()-1;
			sub = rule.substring(ind+1,last);
			String time[] = sub.split(",");
			//System.out.println("sub "+locations[0]+" "+locations[1]);
			time(Integer.parseInt(time[0]),Integer.parseInt(time[1]),table,companyId);
			break;
		case "originDiscount":
			ind = rule.indexOf("(");
			last = rule.length()-1;
			sub = rule.substring(ind+1,last);
			String dis[] = sub.split(",");
			//System.out.println("sub "+locations[0]+" "+locations[1]);
			originDiscount(dis[0],dis[1],Integer.parseInt(dis[2].trim()),table,companyId);
			break;
		}
	}
	
	
	/************************************************** ROUTE FUNCTION *****************************************************************/
	
	public void route(String a, String b,String table,int companyId){
		//System.out.println("In route function "+a+" "+b);
	}
	
	/************************************************** END FUNCTION *******************************************************************/
	
	
	/************************************************** TICKET RANGE FUNCTION **********************************************************/
	
	
	public void ticketRange(int low,int high,String table,int companyId){
		//System.out.println("In ticketRange function "+low+" "+high);
		try{
			sql = "Select New_Ticket_Price from "+table+" where Company_ID = "+companyId+" and Purchase_Date_and_Time between '"+startDate+"' and '"+endDate+"';";
			Statement Stmt = conn.createStatement();
	    	ResultSet Rs = Stmt.executeQuery(sql);
	    	while (Rs.next())
	    	{
	    		if(Rs.getDouble("New_Ticket_Price")>low && Rs.getDouble("New_Ticket_Price")<=high)
	    		{
	    			System.out.println("between "+low +"and"+high);
	    		}
	    	}
			conn.close();
			sql=null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/************************************************** END FUNCTION *******************************************************************/
	
	
	/************************************************** STANDARD FUNCTION **************************************************************/
	public void standard(){
		//System.out.println("In standard function ");
	}
	
	/************************************************** END FUNCTION *******************************************************************/
	
	/************************************************** TICKET PRICE FUNCTION **********************************************************/
	public void ticketPrice(int value,String table,int companyId){
		//System.out.println("In ticketPrice function "+value);
		try{
			sql = "select New_Ticket_Price from "+table+" where Company_ID = "+companyId+" and Purchase_Date_and_Time between '"+startDate+"' and '"+endDate+"';";
			Statement Stmt = conn.createStatement();
	    	ResultSet Rs = Stmt.executeQuery(sql);
	    	while (Rs.next())
	    	{
	    		if(value == Rs.getDouble("New_Ticket_Price from"))
	    		{
	    			System.out.println("price matched");
	    		}
	    	}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/************************************************** END FUNCTION *******************************************************************/
	
	/************************************************** ORIGIN DISCOUNT FUNCTION ********************************************************/
	public void originDiscount(String place,String type ,int value,String table,int companyId){
		//System.out.println("In originDiscount function "+place+ " "+type+ " "+value);
	}
	/************************************************** END FUNCTION *******************************************************************/
	
	/************************************************** TIME FUNCTION *******************************************************************/
	public void time(int startTime, int endTime,String table,int companyId){
		//System.out.println("In time function "+startTime+ " "+endTime);
	}
}
	/************************************************** END FUNCTION *******************************************************************/
  