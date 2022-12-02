package src;

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


// 

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

class MyStudentFrame extends JFrame implements ActionListener {

	// Components of the Form
	private Container c;
	private JLabel title;
	private JLabel fname;
	private JLabel lname;
	private JLabel pname;
	private JLabel school;
	private JLabel addr;
	private JLabel pp;
	private JLabel phone;
	private JTextField tphone;
	private JTextField tname;
	private JTextField tpname;
	private JTextField tschool;
	private JTextField taddr;
	private JLabel mno;
	private JTextField tmno;
	private JLabel paymentPlanLabel;
	private JLabel dop;
	private JComboBox date;
	private JComboBox month;
	private JComboBox year;
	private JComboBox ppc;
	private JComboBox pt;
	private JLabel ptype;
    private JRadioButton cash;
	private JRadioButton wiret;
    private ButtonGroup types;
	private JButton save;
	private JButton cancel;
	private JLabel rnum;
	private JTextField trnum;

	

	private String payplan[]
			= { "Weekly", "Monthly", "Termly" };

	

	
	public MyStudentFrame()
	{
		setTitle("Add Student");
		setBounds(300, 90, 600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);

		c = getContentPane();
		c.setLayout(null);

		title = new JLabel("Add Student");
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		title.setSize(300, 30);
		title.setLocation(200, 30);
		c.add(title);

		fname = new JLabel("Student Name");
		fname.setSize(150, 20);
		fname.setLocation(100, 100);
		c.add(fname);
		
		tname = new JTextField();
		tname.setSize(190, 20);
		tname.setLocation(300, 100);
		c.add(tname);

 

        pname = new JLabel("Student's Parent's Name");
		pname.setSize(300, 20);
		pname.setLocation(100, 170);
		c.add(pname);
		
		tpname = new JTextField();
		tpname.setSize(190, 20);
		tpname.setLocation(300, 170);
		c.add(tpname);

        school = new JLabel("Student's School");
		school.setSize(100, 20);
		school.setLocation(100, 215);
		c.add(school);
		
		tschool = new JTextField();
		tschool.setSize(190, 20);
		tschool.setLocation(300, 215);
		c.add(tschool);

        addr = new JLabel("Student's Address");
		addr.setSize(300, 20);
		addr.setLocation(100, 250);
		c.add(addr);
        		
		taddr = new JTextField();
		taddr.setSize(190, 20);
		taddr.setLocation(300, 245);
		c.add(taddr);

        phone = new JLabel("Student's Phone number");
		phone.setSize(300, 20);
		phone.setLocation(100, 325);
		c.add(phone);
        		
		tphone = new JTextField();
		tphone.setSize(190, 20);
		tphone.setLocation(300, 325);
		c.add(tphone);

        pp = new JLabel("Payment Plan");
		pp.setSize(300, 20);
		pp.setLocation(100, 305);
		c.add(pp);       

		

		ppc = new JComboBox(payplan);
		ppc.setSize(150, 20);
		ppc.setLocation(300, 305);
		c.add(ppc);

	

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
			String parentName = tpname.getText().toString();
			String School = tschool.getText().toString();
			String Address = taddr.getText().toString();
			String Phone = tphone.getText().toString();
			String plan=ppc.getSelectedItem().toString();

			
			addStudent("files/addStudent.txt",studentName,parentName,School,Address,plan, Phone);	
			dispose();
            setVisible(false);
            
		}

		else if (e.getSource() == cancel) {
			dispose();
            setVisible(false);
		}
	}
	



	public void addStudent(String studentrec, String studentName, String parentName, String school,String address,String plan , String phone ){
        File aFile = new File(studentrec);
        String firstname, lastname, currentLine;

        try{ 
            
            String [] next = studentName.split(" ");
            firstname = next[0];
            lastname = next[1];
            currentLine = studentName+" "+ parentName+" "+ school+" "+plan+" "+address + " " + phone;

            Boolean state = true;
            List<String> allLines = Files.readAllLines(Paths.get("files/addStudent.txt"));
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
                    JOptionPane.showMessageDialog(c,"You've already recorded this student:  " + studfullname,"Duplicate Detected",
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




class StudentFile {

	MyStudentFrame f = new MyStudentFrame();
	
}