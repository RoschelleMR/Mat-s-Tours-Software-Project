package src;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

public class Attendance extends JFrame{

    private ArrayList<Student> studlist;
    private Attendance thisScr; 

    private ArrayList<String> studNames, eveningList;
    private DefaultTableModel morning_model, evening_model;
    private JTable morning_table, evening_table;

    private JButton markButton, refreshButton;

    public Attendance(){

        thisScr = this;

        // setLayout(new GridLayout(2,2, 5, 5));
        setLayout(null);
        setTitle("ATTENDANCE SHEET");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MORNING PANEL

        JPanel morningPanel = new JPanel();
        morningPanel.setBounds(10, 10, 500, 500);
        // morningPanel.setBackground(Color.GREEN);

        JLabel morningLabel = new JLabel();
        morningLabel.setText("Morning Attendance");
        morningLabel.setVerticalAlignment(JLabel.BOTTOM);
        morningLabel.setHorizontalAlignment(JLabel.CENTER);
        morningLabel.setFont(new Font("Ariel",Font.BOLD,20));
        morningPanel.add(morningLabel);


        studNames = getStudentNames();
        String[] columnNames = {"Student", "Present/Absent", "Date"};
        morning_model = new DefaultTableModel(columnNames,0);
        morning_table = new JTable(morning_model);

        JScrollPane scrollPane_Morning = new JScrollPane(morning_table);
        morningPanel.add(scrollPane_Morning);

        // EVENING PANEL

        JPanel eveningPanel = new JPanel();
        // eveningPanel.setBackground(Color.GREEN);
        eveningPanel.setBounds(520, 10, 500, 500);

        JLabel eveningLabel = new JLabel();
        eveningLabel.setText("Evening Attendance");
        eveningLabel.setVerticalAlignment(JLabel.BOTTOM);
        eveningLabel.setHorizontalAlignment(JLabel.CENTER);
        eveningLabel.setFont(new Font("Ariel",Font.BOLD,20));
        eveningPanel.add(eveningLabel);

        evening_model = new DefaultTableModel(columnNames,0);
        evening_table = new JTable(evening_model);
        JScrollPane scrollPane_Evening = new JScrollPane(evening_table);
        eveningPanel.add(scrollPane_Evening);

        

        showTable("files/attendanceRecord.txt");

        //  BUTTON PANEL
        // button not aligned correctly

        JPanel buttonPanel = new JPanel();
        // buttonPanel.setBackground(Color.red);
        buttonPanel.setBounds(500, 0, 500, 50);

        markButton = new JButton("Mark Attendance");
        markButton.addActionListener(new markListener());
        buttonPanel.add(markButton);
        
        refreshButton = new JButton("Refresh Tables");
        refreshButton.addActionListener(new refreshListener());
        buttonPanel.add(refreshButton);

        add(morningPanel);
        add(eveningPanel);
        add(buttonPanel);

        setVisible(true);

    }




    public static void main(String[] args) {
        //Small test vv
        // Attendance obj = new Attendance();
        // ArrayList<String> result = obj.getStudentNames();
        // System.out.println(result);

        //temporary
        // new MarkAttendanceScreen(new Attendance());
        new Attendance();

        // new Attendance();
    }

    private ArrayList<Student> loadstudents(String studfile) {
        Scanner studscan = null;
        studlist = new ArrayList<Student>();
        try {
            studscan = new Scanner(new File(studfile));
            while (studscan.hasNext()) {
                String[] nextLine = studscan.nextLine().split(" ");
                String name = nextLine[0] + " " + nextLine[1];
                // the nextLine indexes are not accurate as the form of
                // the student file has not been fully determined as yet
                String address = nextLine[2];
                String highschool = nextLine[3];
                String parentName = nextLine[4];
                String parentTel = nextLine[5];
                String paymentPlan = nextLine[6];

                Student student = new Student(name, address, highschool, parentName, parentTel, paymentPlan);
                studlist.add(student);
            } 

            studscan.close();
        }

        catch (IOException e) {
        }

        return studlist;
    }

    public ArrayList<String> getStudentNames() {
        studlist = loadstudents("files/students.txt");
        ArrayList<String> studentNames = new ArrayList<String>();
        for (Student stud : studlist) {
            studentNames.add(stud.getName());
        }

        return studentNames;
    }

    public void showTable(String aFile){
        Scanner ascan = null;
    try{
        ascan  = new Scanner(new File(aFile));
        while(ascan.hasNext())
        {
            // String [] nextLine = ascan.nextLine().split(" ");
            String name = ascan.next()+" "+ascan.next();
            String period = ascan.next();
            String presence = ascan.next();
            String date = ascan.next();
            
            
            if (period.equals("Evening")){
                String [] recordItem = {name, presence, date};
                evening_model.addRow(recordItem);
                
            }
            else if (period.equals("Morning")){
                String [] recordItem = {name, presence, date};
                morning_model.addRow(recordItem);
            }
        }

        ascan.close();
      }
    catch(IOException e){}
    }


    private class markListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            new MarkAttendanceScreen(thisScr);
            
            
            
        }
        
    }

    private class refreshListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // evening_model.fireTableDataChanged();
            // morning_model.fireTableDataChanged();
            // evening_table.revalidate();
            // morning_table.revalidate();

            thisScr.dispose();
            new Attendance();
            
        }
        
    }

    
}