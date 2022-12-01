

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;




import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

class MyFrame
	extends JFrame
	implements ActionListener {

	// Components of the Form
	private Container c;
	private JLabel title;
	private JLabel name;
	private JTextField tname;
	private JLabel mno;
	private JTextField tmno;
	private JLabel paymentPlanLabel;
	private JLabel dop;
	private JComboBox date;
	private JComboBox month;
	private JComboBox year;
	private JComboBox pp;
	private JComboBox pt;
	private JLabel ptype;
    private JRadioButton cash;
	private JRadioButton wiret;
    private ButtonGroup types;
	private JButton save;
	private JButton cancel;
	private JLabel rnum;
	private JTextField trnum;

	private String dates[]
		= { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20",
			"21", "22", "23", "24", "25",
			"26", "27", "28", "29", "30",
			"31" };
	private String months[]
		= { "01", "02", "03", "04",
			"05", "06", "07", "08",
			"09", "10", "11", "12" };
	private String years[]
		= { "1995", "1996", "1997", "1998",
			"1999", "2000", "2001", "2002",
			"2003", "2004", "2005", "2006",
			"2007", "2008", "2009", "2010",
			"2011", "2012", "2013", "2014",
			"2015", "2016", "2017", "2018",
			"2019", "2020", "2021", "2022",
			"2023", "2024", "2025", "2026",
			"2027", "2028", "2029", "2030",
			"2031", "2032", "2033", "2034",
			"2035", "2036", "2037", "2038",
			"2039", "2040", "2041", "2042",
			"2043", "2044", "2045", "2046",
			"2047" };

	private String payplan[]
			= { "Weekly", "Monthly", "Termly" };

	private String paytype[]
			= { "Cash", "Wire Transfer" };		

	
	public MyFrame()
	{
		setTitle("Record Payments");
		setBounds(300, 90, 600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		title = new JLabel("Record Payments");
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		title.setSize(300, 30);
		title.setLocation(200, 30);
		c.add(title);

		name = new JLabel("Student Name");
		name.setSize(100, 20);
		name.setLocation(100, 100);
		c.add(name);

		tname = new JTextField();
		tname.setSize(190, 20);
		tname.setLocation(250, 100);
		c.add(tname);

		mno = new JLabel("Payment Amount");
		mno.setSize(150, 20);
		mno.setLocation(100, 150);
		c.add(mno);
		

		tmno = new JTextField();
		tmno.setSize(150, 20);
		tmno.setLocation(250, 150);
		c.add(tmno);

		paymentPlanLabel = new JLabel("Payment Plan");
		paymentPlanLabel.setSize(100, 20);
		paymentPlanLabel.setLocation(100, 200);
		c.add(paymentPlanLabel);

		pp = new JComboBox(payplan);
		pp.setSize(150, 20);
		pp.setLocation(250, 200);
		c.add(pp);

		dop = new JLabel("Date of Payment");
		dop.setSize(100, 20);
		dop.setLocation(100, 250);
		c.add(dop);

		date = new JComboBox(dates);
		date.setSize(50, 20);
		date.setLocation(250, 250);
		c.add(date);

		month = new JComboBox(months);
		month.setSize(60, 20);
		month.setLocation(300, 250);
		c.add(month);

		year = new JComboBox(years);
		year.setSize(60, 20);
		year.setLocation(360, 250);
		c.add(year);

		
		ptype = new JLabel("Type");
		ptype.setSize(100, 20);
		ptype.setLocation(100, 300);
		c.add(ptype);

		pt = new JComboBox(paytype);
		pt.setSize(150, 20);
		pt.setLocation(250, 300);
		pt.addActionListener(this);
		c.add(pt);

		rnum = new JLabel("Reciept Number");
		rnum.setSize(150, 20);
		rnum.setLocation(100, 350);
		c.add(rnum);

		trnum = new JTextField();
		trnum.setSize(150, 20);
		trnum.setLocation(250, 350);
		

		

        types = new ButtonGroup();
		types.add(cash);
		types.add(wiret);

		save = new JButton("Save");
		save.setSize(100, 20);
		save.setLocation(200, 400);
		save.addActionListener(this);
		c.add(save);

		cancel = new JButton("Cancel");
		cancel.setSize(100, 20);
		cancel.setLocation(320, 400);
		cancel.addActionListener(this);
		c.add(cancel);

		
		setVisible(true);

	}

	// method actionPerformed()
	// to get the action performed
	// by the user and act accordingly
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == save) {
			//ADD CODE
			String studentName = tname.getText().toString();
            String dd = date.getSelectedItem().toString();
			String mmm = month.getSelectedItem().toString();
			String yyyy = year.getSelectedItem().toString();
			String ddate=dd+"-"+mmm+"-"+yyyy;
            String money = tmno.getText().toString();
			String plan=pp.getSelectedItem().toString();
			String ptype=pt.getSelectedItem().toString();
			String rnum="";
			if (ptype=="Wire Transfer"){
				rnum=trnum.getText().toString();
			}
			
			
			addTransaction("transactionrecord.txt",studentName,money,ddate,plan,ptype,rnum);	
			dispose();
            setVisible(false);
            
		}

		else if (e.getSource() == cancel) {
			dispose();
            setVisible(false);
		}

		else if (e.getSource()== pt){
			if (pt.getSelectedItem().toString()=="Wire Transfer"){
				c.add(trnum);
			}
			else{
				c.remove(trnum);
			}
			
		}
	}
	



	public void addTransaction(String transactionrecord, String studentName, String money, String ddate, String plan, String ptype, String rnum){
        File aFile = new File(transactionrecord);
        String firstname, lastname, currentLine;

        try{ 
            
            String [] next = studentName.split(" ");
            firstname = next[0];
            lastname = next[1];
            currentLine = studentName+" Paid "+money+" "+ddate+" "+plan+" "+ptype+" "+rnum;

            Boolean state = true;
            List<String> allLines = Files.readAllLines(Paths.get("transactionrecord.txt"));
            for (String fileLine : allLines) {
                if(currentLine.equals(fileLine)){
                    state = false;
                    String studfname = new String();
                    String studlname = new String();
                    String studfullname = new String();
                    String nextLine[] = currentLine.split(" ");
                    studfname = nextLine[0];
                    studlname = nextLine[1];
                    studfullname = studfname + " " + studlname;
                    JOptionPane.showMessageDialog(c,"You've already recorded this transaction for " + studfullname,"Duplicate Detected",
                    JOptionPane.PLAIN_MESSAGE);

                    break;
                }
                
            }

            if(state){
                File tempFile = new File("temp.txt");
                PrintWriter pw = new PrintWriter(tempFile);

                pw.println(currentLine);
                for (String fileLine : allLines) {
                    pw.println(fileLine);
                    
                }
                pw.flush();
                pw.close();

                aFile.delete();
                tempFile.renameTo(aFile);
            }
        }
        catch(Exception e){}
    }
	
}




class Transaction {

	public static void main(String[] args) throws Exception
	{
		MyFrame f = new MyFrame();
	}
}
