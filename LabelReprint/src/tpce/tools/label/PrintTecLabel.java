package tpce.tools.label;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuilder;

import javax.print.PrintException;

import tpce.tools.label.PrintTextFile;


public class PrintTecLabel {
	
	private int caseHeight = 0;
	private int caseWide = 0;
	private int nrOfLabels = 1;
	private int itemCounter = 0;
	
	private String header = null;
	private String footer = null;
	private StringBuilder labelData= null;
	
	private String printer = null;
	
	private PrintWriter output;
	
	private enum labelTypes {
		pnseal, caselabel
	}
	
	// Constructors
	
	public PrintTecLabel(int CHeight, int CWide, int nrl, boolean cut) {
		this.setCaseHeight(CHeight);
		this.setCaseWide(CWide);
		labelData = null;
	}

	public PrintTecLabel(String labelType, int nrl, boolean cut){
		
		switch (labelTypes.valueOf(labelType)) {
		case pnseal:
			this.setCaseHeight(420);
			this.setCaseWide(630);
			labelData = new StringBuilder();
			this.setHeader("{D0420,0630,0380|}");
			this.labelAddLine("{C|}");
			this.setFooter("{XS;I," + "0000".substring(1, 4-Integer.toString(nrl).length()) + Integer.toString(nrl) + ",00" + (cut?"1":"0") + "2C4011|}");
			break;
		case caselabel:
			this.setCaseHeight(600);
			this.setCaseWide(1450);
			labelData = new StringBuilder();
			this.setHeader("{D0600,1450,0520|}");
			this.labelAddLine("{C|}");
			this.setFooter("{XS;I," + "0000".substring(1, 4-Integer.toString(nrl).length()) + Integer.toString(nrl) + ",00" + (cut?"1":"0") + "2C4011|}");
			break;
		default: 
			break;
		}
		
	}
	
	//Getter - Setter
	
	public int getCaseHeight() {
		return caseHeight;
	}

	public void setCaseHeight(int caseHeight) {
		this.caseHeight = caseHeight;
	}

	public int getCaseWide() {
		return caseWide;
	}

	public void setCaseWide(int caseWide) {
		this.caseWide = caseWide;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public int getNrOfLabels() {
		return nrOfLabels;
	}

	public void setNrOfLabels(int nrOfLabels) {
		this.nrOfLabels = nrOfLabels;
	}

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public String getLabelData(){
		return labelData.toString();
	}
	
	//Methods
	
	private void labelAddLine(String lineToAdd){
		if (labelData.length()>0){
			labelData.append(System.getProperty("line.separator"));
		}
		labelData.append(lineToAdd);
	}
	
	public void labelAddText(String textToAdd, int Xpos, int Ypos){
		/**
		 * {PC00;0014,0050,05,05,K,00,B|}
		 * {RC00;GASKET, CYLINDER    |}
		 */

		labelAddLine("{PC" + String.format("%01d", itemCounter) + ";" + String.format("%04d", Xpos) + "," + String.format("%04d", Ypos) + ",05,05,K,00,B|}");
		labelAddLine("{RC" + String.format("%01d", itemCounter) + ";" + textToAdd+"|}");
		this.itemCounter++;
	}
	
	public void labelAddBarcode(String barcodeToAdd, int Xpos, int Ypos){
		
//		{XB00;0080,0350,3,3,04,04,08,08,03,0,0070|}
//		{RB00;8LLO6080|}
//		max itemCounter = 31 !!!
		
		try {
			labelAddLine("{XB" + String.format("%01d", itemCounter) + ";"
					+ String.format("%04d", Xpos) + ","
					+ String.format("%04d", Ypos)
					+ ",3,3,02,02,06,06,02,0,0100|}");
			labelAddLine("{RB" + String.format("%01d", itemCounter) + ";"
					+ barcodeToAdd + "|}");
			this.itemCounter++;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void printLabel() throws PrintException, IOException {
		try {
			output = new PrintWriter("C:\\Users\\Public\\Documents\\createdlabel.txt");
			output.println(this.getHeader());
			output.println(this.getLabelData());
			output.println(this.getFooter());
			output.close();
			PrintTextFile.printTextFile("C:\\Users\\Public\\Documents\\createdlabel.txt",1,"TestPrinter");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
