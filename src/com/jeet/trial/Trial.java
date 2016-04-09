package com.jeet.trial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Button;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Choice;

public class Trial {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Trial window = new Trial();
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
	public Trial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 485, 383);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 469, 345);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Database", null, panel, null);
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		
		JPanel panel_5 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
				.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
		);
		panel_5.setLayout(null);
		
		JButton btnNewButton_7 = new JButton("Upload Master");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_7.setBounds(312, 40, 128, 23);
		panel_5.add(btnNewButton_7);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(27, 44, 46, 14);
		panel_5.add(lblNewLabel);
		
		Choice choice = new Choice();
		choice.setBounds(125, 44, 151, 20);
		panel_5.add(choice);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(174, 92, 89, 23);
		panel_5.add(btnRefresh);
		panel_4.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Create Table");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(57, 11, 115, 23);
		panel_4.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Empty Table");
		btnNewButton_3.setBounds(57, 62, 115, 23);
		panel_4.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Delete Table");
		btnNewButton_4.setBounds(57, 116, 115, 23);
		panel_4.add(btnNewButton_4);
		panel_3.setLayout(null);
		
		JButton btnNewButton = new JButton("Create Database");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(36, 33, 149, 23);
		panel_3.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete Database");
		btnNewButton_1.setBounds(36, 96, 149, 23);
		panel_3.add(btnNewButton_1);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Transactions", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Reports", null, panel_2, null);
	}
}
