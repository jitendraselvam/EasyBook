import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class listUpdater {
	
	int count =0,i=0;
	String names[];
	String tableNames[];
	
	public String[] display(){
		
		

    try {
    	Class.forName("com.mysql.jdbc.Driver");
     Connection con = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=123123");

      DatabaseMetaData meta = con.getMetaData();
      ResultSet res = meta.getCatalogs(); 
      
      while (res.next()) {
    	  if(!(res.getString(1).equals("information_schema")||res.getString(1).equals("performance_schema")||res.getString(1).equals("sys")))
         {
    		 // System.out.println("   "+res.getString(1));
    	  count ++;
         }
      }
      res.beforeFirst();
       names = new String[count];
      while(res.next())
      {
    	  if(!(res.getString(1).equals("information_schema")||res.getString(1).equals("performance_schema")||res.getString(1).equals("sys")))
    	  {
    		 // System.out.println("  ACTIVITY "+res.getString(1));
    		  names[i]=res.getString(1);
    		  i++;
    	  }
      }

      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return names;
  }
	
	
	
	
	
	public String[] displayTables(String dbName){
		int count1 = 0; i = 0;
		//tableNames = new String[2];
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://localhost:3306/"+dbName;
		     Connection conn = DriverManager.getConnection(connectionUrl, "root", "123123");	
		String query = "SHOW TABLES;";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
		  //System.out.println(rs.getString(1));
			//tableNames[i]=rs.getString(1);
			//i++;
		  count1++;
		}
		tableNames = new String[count1];
		System.out.println(count1);
		 
		 rs.beforeFirst();
		 while (rs.next()) {
			  //System.out.println("ACTIVITY: "+rs.getString(1));
				tableNames[i]=rs.getString(1);
				i++;
			  
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return tableNames;
		
	}
	
	
}


