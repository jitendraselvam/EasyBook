import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Choice;
import java.awt.Color;

import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Panel;

import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;


public class excelGui {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	static FileChooser panel;
	Choice choice_4,choice,choice_3,choice_2,choice_1;
	listUpdater ul = new listUpdater();

	/**
	 * Launch the application.
	 */
	
	//get name for database
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					panel = new FileChooser();
					excelGui window = new excelGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public excelGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 519, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		try {
		    Image img = ImageIO.read(getClass().getResource("refresh.png"));
		  } catch (IOException ex) {
		  }
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 510, 350);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Database", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnCreateDatabase = new JButton("Create Database");
		btnCreateDatabase.setBounds(10, 21, 147, 23);
		panel_1.add(btnCreateDatabase);
		
		//enter name and delete
		
		JButton btnDeleteDatabase = new JButton("Delete Database");
		btnDeleteDatabase.setBounds(10, 68, 147, 23);
		panel_1.add(btnDeleteDatabase);
		
		JButton btnCreateTable = new JButton("Create Table");
		btnCreateTable.setBounds(343, 11, 119, 23);
		panel_1.add(btnCreateTable);
		
		JButton btnEmptyTable = new JButton("Empty Table");
		btnEmptyTable.setBounds(343, 56, 119, 23);
		panel_1.add(btnEmptyTable);
		
		JButton btnDeleteTable = new JButton("Delete Table");
		btnDeleteTable.setBounds(343, 104, 119, 23);
		panel_1.add(btnDeleteTable);
		
		JLabel lblSelectDatabase_2 = new JLabel("Select Database");
		lblSelectDatabase_2.setBounds(23, 186, 88, 14);
		panel_1.add(lblSelectDatabase_2);
		
		choice_4 = new Choice();
		choice_4.setBounds(142, 180, 157, 20);
		panel_1.add(choice_4);
		
		JButton btnUploadMaster = new JButton("upload Master");
		btnUploadMaster.setBounds(343, 174, 141, 26);
		panel_1.add(btnUploadMaster);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setBounds(174, 245, 131, 42);
		panel_1.add(btnNewButton_1);
		//btnNewButton_1.setIcon(new ImageIcon("refresh"));
		
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateDatabaseList();
					}
				});
		btnUploadMaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				  
				String fileLoc = showOpenFileDialog();
				if(!fileLoc.isEmpty())
				{
				MasterFile mf = new MasterFile();
				mf.runMaster(choice_4.getItem(choice_4.getSelectedIndex()),fileLoc);
				}
			}
		});
		btnDeleteTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableName = JOptionPane.showInputDialog(null,
						 "Enter name of table:",
						 "Delete table",
						 JOptionPane.QUESTION_MESSAGE);
				if(!(tableName.isEmpty()))
						{
				TableOperation to = new TableOperation();
				to.deleteTable(tableName,choice_4.getItem(choice_4.getSelectedIndex()));
						}
			}
		});
		btnEmptyTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tableName = JOptionPane.showInputDialog(null,
						 "Enter name of table:",
						 "Truncate table",
						 JOptionPane.QUESTION_MESSAGE);
				if(!(tableName.isEmpty()))
						{
				TableOperation to = new TableOperation();
				to.truncateTable(tableName,choice_4.getItem(choice_4.getSelectedIndex()));
						}
			}
		});
		btnCreateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tableName = JOptionPane.showInputDialog(null,
						 "Enter name of table:",
						 "Create table",
						 JOptionPane.QUESTION_MESSAGE);
				if(!(tableName.isEmpty()))
						{
				TableOperation to = new TableOperation();
				to.createTable(tableName,choice_4.getItem(choice_4.getSelectedIndex()));
						}
			}
		});
		btnDeleteDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dbName = JOptionPane.showInputDialog(null,
						 "Enter name of Database:",
						 "Delete Database",
						 JOptionPane.QUESTION_MESSAGE);
				DeleteDatabase deleteDb = new DeleteDatabase();
				boolean res = true;
				res = deleteDb.deleteDb(dbName);
				if(res == false)
				JOptionPane.showMessageDialog(frame, "Database "+dbName+" Deleted successfully.");
				else
					JOptionPane.showMessageDialog(frame, "Database "+dbName+" not Deleted.");
			}
		});
		btnCreateDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String dbName = JOptionPane.showInputDialog(null,
						 "Enter name of Database:",
						 "Create Database",
						 JOptionPane.QUESTION_MESSAGE);
				CreateDatabase createDB = new CreateDatabase();
				createDB.create(dbName);
				//JOptionPane.showMessageDialog(frame, "Database "+dbName+" created successfully.");
			}
		});
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Transactions", null, panel_2, null);
		panel_2.setLayout(null);
		JLabel lblSelectDatabase = new JLabel("Select Database");
		lblSelectDatabase.setBounds(10, 29, 91, 14);
		panel_2.add(lblSelectDatabase);
		
		
		choice = new Choice();
		choice.setBounds(107, 23, 119, 20);
		panel_2.add(choice);
		
		JLabel lblSelectTable_1 = new JLabel("Select Table");
		lblSelectTable_1.setBounds(270, 11, 81, 50);
		panel_2.add(lblSelectTable_1);
		
		choice_3 = new Choice();
		choice_3.setBounds(358, 23, 137, 20);
		panel_2.add(choice_3);
		
		JButton btnRefrestList = new JButton("Refrest list");
		btnRefrestList.setBounds(189, 72, 119, 26);
		panel_2.add(btnRefrestList);
		
		JLabel lblSelectFolder = new JLabel("Select folder");
		lblSelectFolder.setBounds(145, 183, 81, 14);
		panel_2.add(lblSelectFolder);
		
		
		//select files browser window
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(231, 179, 89, 23);
		panel_2.add(btnBrowse);
		
		/*progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(46, 248, 404, 14);
		
		progressBar_1.setStringPainted(true);
		progressBar_1.setForeground(Color.green);
		progressBar_1.setString("10%");
		
		//progressBar_1.setValue(50);
		panel_2.add(progressBar_1); */
		
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("");
			    
			    try {
					panel.FileChooser1(choice.getItem(choice.getSelectedIndex()),choice_3.getItem(choice_3.getSelectedIndex()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					StringBuilder sb = new StringBuilder(e1.toString());
				    for (StackTraceElement ste : e1.getStackTrace()) {
				        sb.append("\n\tat ");
				        sb.append(ste);
				    }
				    String trace = sb.toString();
				    JOptionPane.showMessageDialog(null, trace);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					StringBuilder sb = new StringBuilder(e1.toString());
				    for (StackTraceElement ste : e1.getStackTrace()) {
				        sb.append("\n\tat ");
				        sb.append(ste);
				    }
				    String trace = sb.toString();
				    JOptionPane.showMessageDialog(null, trace);
				}
			    frame.addWindowListener(
			      new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			          System.exit(0);
			          }
			        }
			      );
			    
			   // JOptionPane.showMessageDialog(frame, "Update completed");
			    }
			
		});
		btnRefrestList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice_3.removeAll();
				String tableNames[] = ul.displayTables(choice.getItem(choice.getSelectedIndex()));
				for(String b: tableNames)
				choice_3.add(b);
			}
		});
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Reports", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblSelectDatabase_1 = new JLabel("Select Database");
		lblSelectDatabase_1.setBounds(0, 11, 81, 20);
		panel_3.add(lblSelectDatabase_1);
		
		choice_1 = new Choice();
		choice_1.setBounds(87, 11, 116, 20);
		panel_3.add(choice_1);
		
		JLabel lblSelectTable = new JLabel("Select Table");
		lblSelectTable.setBounds(252, 8, 81, 26);
		panel_3.add(lblSelectTable);
		
		choice_2 = new Choice();
		choice_2.setBounds(331, 11, 147, 20);
		panel_3.add(choice_2);
		
		JButton button = new JButton("Refrest list");
		button.setBounds(177, 45, 119, 26);
		panel_3.add(button);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(70, 137, 81, 14);
		panel_3.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(70, 193, 59, 14);
		panel_3.add(lblEndDate);
		
		textField = new JTextField();
		textField.setBounds(177, 134, 119, 20);
		panel_3.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(177, 190, 119, 20);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnUploadRules = new JButton("upload LocationMaster");
		btnUploadRules.setBounds(359, 133, 119, 23);
		panel_3.add(btnUploadRules);
		
		JButton btnNewButton_2 = new JButton("upload Rules");
		btnNewButton_2.setBounds(353, 189, 125, 23);
		panel_3.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.setBounds(177, 244, 119, 54);
		panel_3.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()||textField_1.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Please fill all the details.");
				}
				else
				{
					OutputFileGen op = new OutputFileGen();
					try {
						op.generate(textField.getText().toString(), textField_1.getText().toString(),choice_1.getItem(choice_1.getSelectedIndex()),choice_2.getItem(choice_2.getSelectedIndex()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RulesUpdater rul = new RulesUpdater();
				//rul.update();
				JFrame frame = new JFrame("");
			    
			    try {
					panel.FileChooser2(choice_1.getItem(choice_1.getSelectedIndex()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    frame.addWindowListener(
			      new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			          System.exit(0);
			          }
			        }
			      );
				
			}
		});
		btnUploadRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//LocationMaster lm = new LocationMaster();
				//lm.update();
				JFrame frame = new JFrame("");
			    
			    try {
					panel.FileChooser3(choice_1.getItem(choice_1.getSelectedIndex()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    frame.addWindowListener(
			      new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			          System.exit(0);
			          }
			        }
			      );
				
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choice_2.removeAll();
				String tableNames[] = ul.displayTables(choice_1.getItem(choice_1.getSelectedIndex()));
				for(String b: tableNames)
				choice_2.add(b);
			}
		});
		
		
		
		
		updateDatabaseList();
	}
	
	public void updateDatabaseList(){
		String Names[] = ul.display();

		choice.removeAll();
		choice_1.removeAll();
		choice_4.removeAll();
		for(String b: Names)
		{
			if(b!=null)
			{
		choice.add(b);
		choice_1.add(b);
		choice_4.add(b);
			}
			
		}
	}
	
	private String showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        String fileLoc = null;
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office Documents", "docx", "xlsx", "pptx"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        fileChooser.setAcceptAllFileFilterUsed(true);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            fileLoc = selectedFile.getAbsolutePath().toString();
            
        }
        return fileLoc;
	}
}
