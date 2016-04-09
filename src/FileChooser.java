import javax.swing.*;

import jxl.read.biff.BiffException;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class FileChooser extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

JButton go;
   
   JFileChooser chooser;
   String choosertitle;
   int count =0;
   int i=0;
   static String fileName[];
   File[] listOfFiles;
   JProgressBar pbar;
   static int min,max;
   JLabel label,label1;
   
  public void FileChooser1(String dbName, String table) throws IOException, Exception {   
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    min = 0;
    Connect cn = new Connect();
    Toucher t = new Toucher();

    
    
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      String fileLoc = chooser.getSelectedFile().toString();
      String filename=chooser.getSelectedFile().getName();
      System.out.println(fileLoc);
      
      File folder = new File(fileLoc);
      max = folder.listFiles().length;
      pbar = new JProgressBar();                //new changes
      pbar.setMinimum(min);
      pbar.setMaximum(max);
      label = new JLabel("progress");
      label1 = new JLabel();
      add(label);
      add(label1);
      add(pbar);
      
      
      JFrame frame = new JFrame("Progress Bar Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(this);
      frame.pack();
      frame.setVisible(true); 

      listOfFiles = folder.listFiles();

          for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
              System.out.println("File " + listOfFiles[i].getName());
              count++;
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory " + listOfFiles[i].getName());
            }
          }
          
          String fileName[] = new String[count];
          String fileName1[] = new String[count];
          
          for (int i = 0; i < listOfFiles.length; i++) {
              if (listOfFiles[i].isFile()) {
                //System.out.println("File " + listOfFiles[i].getName());
            	  fileName1[i]=listOfFiles[i].getName();
              } else if (listOfFiles[i].isDirectory()) {
               // System.out.println("Directory " + listOfFiles[i].getName());
              }
            }
          
          
          JPanel panel = new JPanel();
          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
          JTextField textField = new JTextField("PROGRESS");
          textField.setMaximumSize( textField.getPreferredSize() );
          panel.add(textField);
          panel.setVisible(true);
          
          
          
    
         
          
          
          
      /*    File theDir = new File("C:\\touched");

      	// if the directory does not exist, create it
      	if (!theDir.exists()) {
      	    System.out.println("creating directory: " + "touched");
      	    boolean result = false;

      	    try{
      	        theDir.mkdir();
      	        result = true;
      	    } 
      	    catch(SecurityException se){
      	        //handle it
      	    }        
      	    if(result) {    
      	        System.out.println("DIR created");  
      	    }
      	} */
          
          
          
          
          
          
          new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(min<=max)
	        	  {
	        	  
	        		  for(i=0;i<fileName.length;i++)
	        		  {
	        			  fileName[i]= listOfFiles[i].getAbsolutePath().toString();
	        			  textField.setText(fileName[i]);
	        			  min = min+1;
  	  
	        			  //updateBar(min);
	        			  try {
	        				  t.touch(fileName[i],fileName1[i]);
	        				  cn.connect("C:\\touched\\"+fileName1[i],dbName,table,fileName1[i]);
	        				 t.clean("C:\\touched\\"+fileName1[i]);
	        				  SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									updateBar(min);
								}
	        					  
	        				  });
	        			  }
	        			  catch (BiffException | IOException e) 
	        			  {
	        				  e.printStackTrace();
	        			  } 
	        			  catch (Exception e) 
	        			  {
	        				  e.printStackTrace();
	        			  } 
  	  
  	 
	        		  }
				
	        	  }
			}
          
          }).start();
          

    }
    else {
    	System.out.println("No Selection ");
    } 
    
    
     
  }
 

	
public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
  
public void FileChooser2(String dbName) {
	// TODO Auto-generated method stub
	chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    RulesUpdater rul = new RulesUpdater();
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
    	File selectedPfile = chooser.getSelectedFile();
    	System.out.println(selectedPfile.getAbsolutePath().toString());
    	System.out.println(selectedPfile.getPath());
      
        rul.update(selectedPfile.getAbsolutePath().toString(),dbName);
      
      
      }
    else {
      System.out.println("No Selection ");
      }
     
  }
  public Dimension getPreferredSize1(){
    return new Dimension(200, 200);
    }
public void FileChooser3(String dbName) {
	// TODO Auto-generated method stub
	chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    LocationMaster lm = new LocationMaster();
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
    	File selectedPfile = chooser.getSelectedFile();
    	System.out.println(selectedPfile.getAbsolutePath().toString());
    	System.out.println(selectedPfile.getPath());
      
    	lm.update(selectedPfile.getAbsolutePath().toString(),dbName);
      
      
      }
    else {
      System.out.println("No Selection ");
      }
     
  
	
}

public void updateBar(int newValue) {
	
    pbar.setValue(newValue);
    //pbar.setString();
    label.setText(newValue+"/"+max);
  }

}

    