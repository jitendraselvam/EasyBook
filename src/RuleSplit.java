import javax.swing.JOptionPane;


public class RuleSplit {
	
	int ind,last;
	String sub;
	String result[][];
	String[] all;
	int funcCount = 0;
	
	public String[][] split(String str){
		str = str.toLowerCase();
    	all = str.split("&");
    	funcCount =0;
    	result = new String[7][5];
    	for(String str1: all)
    	{
    		if(str1.contains("("))
    		{
    			
    			
    			//System.out.println(str1.substring(0,str1.indexOf("(")).trim());
    			switch (str1.substring(0,str1.indexOf("(")).trim())
    			{
    			case "route":
    				str1 = str1.trim();
    				 ind = str1.indexOf("(");
    				 last = str1.length()-1;
    				 sub = str1.substring(ind+1,last);
    				String locations[] = sub.split(",");
    				//System.out.println("sub "+locations[0]+" "+locations[1]);
    				result[funcCount][0] ="route";
    				result[funcCount][1] =locations[0];
    				result[funcCount][2] =locations[1];
    				break;
    			
    			case "ticketrange":
    				str1 = str1.trim();
    	    			ind = str1.indexOf("(");
    	    			last = str1.length()-1;
    	    			sub = str1.substring(ind+1,last);
    	    			String numbers[] = sub.split(",");
    	    			//System.out.println("sub "+Integer.parseInt(numbers[0])+" "+Integer.parseInt(numbers[1]));
    	    			result[funcCount][0] ="ticketrange";
        				result[funcCount][1] =numbers[0];
        				result[funcCount][2] =numbers[1];
    	    			break;
    			case "ticketcount":
    				str1 = str1.trim();
    				ind = str1.indexOf("(");
    				last = str1.length()-1;
    				sub = str1.substring(ind+1,last);
    				String range1[]=sub.split(",");
    				//System.out.println("sub "+Integer.parseInt(range1[0])+" "+Integer.parseInt(range1[1]));
    				result[funcCount][0] ="ticketcount";
    				result[funcCount][1] =range1[0];
    				result[funcCount][2] =range1[1];
    				break;
    			case "standard":
    				str1 = str1.trim();
    				ind = str1.indexOf("(");
    				last = str1.length()-1;
    				break;
    			case "ticketprice":
    				str1 = str1.trim();
    				ind = str1.indexOf("(");
    				last = str1.length()-1;
    				sub = str1.substring(ind+1,last).trim();
    				//System.out.println("sub "+sub);
    				result[funcCount][0] ="ticketprice";
    				result[funcCount][1] =sub;
    				break;
    			case "time":
    				str1 = str1.trim();
    				ind = str1.indexOf("(");
    				last = str1.length()-1;
    				sub = str1.substring(ind+1,last);
    				String time[] = sub.split(",");
    				//System.out.println("sub "+Integer.parseInt(time[0])+" "+Integer.parseInt(time[1]));
    				result[funcCount][0] ="time";
    				result[funcCount][1] =time[0];
    				result[funcCount][2] =time[1];
    				break;
    			case "origindiscount":
    				str1 = str1.trim();
    				ind = str1.indexOf("(");
    				last = str1.length()-1;
    				sub = str1.substring(ind+1,last);
    				String dis[] = sub.split(",");
    				//System.out.println("sub "+dis[0]+" "+dis[1]+" "+Integer.parseInt(dis[2].trim()));
    				result[funcCount][0] ="origindiscount";
    				result[funcCount][1] =dis[0];
    				result[funcCount][2] =dis[1];
    				result[funcCount][3] = dis[2].trim();
    				break;
    			case "all":
    				result[funcCount][0] = "all";
    		 default:
    			// JOptionPane.showMessageDialog(null, "Unidentified rule: "+str1.trim());
    			
    			}
    			funcCount++;
    			
    		}

    	}
		
		
	/*	for(int i = 0;i<7;i++)
		{
			System.out.printf("\n");
			for(int j = 0;j<5;j++)
				System.out.printf("%s \t",result[i][j]);
			
		} */
		return result;
		
	} 
	


}
