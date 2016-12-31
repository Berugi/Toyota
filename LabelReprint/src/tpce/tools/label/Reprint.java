package tpce.tools.label;

import java.awt.EventQueue;

import javax.print.PrintException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.sun.glass.ui.Application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JLabel;

import org.eclipse.jface.action.Action;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import tpce.tools.label.*;

public class Reprint {

	private JFrame frmReprintLabel;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reprint window = new Reprint();
					window.frmReprintLabel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Reprint() {
		initialize();
	}

	private void initialize() {
		frmReprintLabel = new JFrame();
		frmReprintLabel.setTitle("Reprint Label");
		frmReprintLabel.setResizable(false);
		frmReprintLabel.setAlwaysOnTop(true);
		frmReprintLabel.setBounds(100, 100, 450, 300);
		frmReprintLabel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MenuListener ml = new MenuListener();
		TextBoxListener tl = new TextBoxListener();
		
		JMenuBar menuBar = new JMenuBar();
		frmReprintLabel.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(ml);
		mnNewMenu.add(mntmExit);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{31, 86, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{81, 20, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmReprintLabel.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblScanCase = new JLabel("Scan Case:");
		GridBagConstraints gbc_lblScanCase = new GridBagConstraints();
		gbc_lblScanCase.anchor = GridBagConstraints.WEST;
		gbc_lblScanCase.insets = new Insets(0, 0, 5, 5);
		gbc_lblScanCase.gridx = 2;
		gbc_lblScanCase.gridy = 1;
		frmReprintLabel.getContentPane().add(lblScanCase, gbc_lblScanCase);
		
		textField = new JTextField();
		textField.addActionListener(tl);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				KeyInput(arg0);
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		frmReprintLabel.getContentPane().add(textField, gbc_textField);
		textField.setColumns(15);
		textField.requestFocusInWindow();
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		        textField.requestFocusInWindow();
		    }
		});
	};
	
	  private class MenuListener implements ActionListener {
		  
		    public void actionPerformed(ActionEvent e) {
		      System.out.println(e.getActionCommand());
		      
		      frmReprintLabel.dispose();
		    }
		  }	
	  

	  private class TextBoxListener implements ActionListener {
		  
		    public void actionPerformed(ActionEvent e) {
		    	//Do Nothing when enter is given
		    }
		  }	

	  
	  private void KeyInput(KeyEvent arg0) {
		  
			//System.out.println("KeyEvent");
			//System.out.println(arg0);
			System.out.println(textField.getText() );
			if (textField.getText().length()==8) {
				System.out.println("auto-enter");
				PrintTecLabel label = new PrintTecLabel("caselabel",1,true);
				label.labelAddText(textField.getText(), 12, 100);
				label.labelAddBarcode(textField.getText(), 40, 140);
				try {
					label.printLabel();
				} catch (PrintException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	  }
	  
	}

