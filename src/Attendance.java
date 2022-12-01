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

    private JButton markButton, refreshButton, editButton, closeButton;

    private JMenuBar menu;

    public Attendance(){

        thisScr = this;

        setLayout(new GridLayout(1,3, 10, 0));
        setTitle("ATTENDANCE SHEET");
        setSize(1500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menu = new JMenuBar();
        menu.setBackground(new Color(15,50,100));

        closeButton = new JButton("EXIT");
        closeButton.setBackground(Color.red);
        closeButton.setForeground(Color.white);
        closeButton.addActionListener(new closeListener());
        menu.add(closeButton);

        JLabel menuTitle = new JLabel();
        menuTitle.setText("MAT'S TOURS ATTENDANCE REGISTER");
        menuTitle.setFont(new Font("Ariel",Font.BOLD,12));
        menuTitle.setForeground(Color.WHITE);
        menu.add(menuTitle);
        setJMenuBar(menu);

        // MORNING PANEL

        JPanel morningPanel = new JPanel();
        morningPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); 

        JLabel morningLabel = new JLabel();
        morningLabel.setText("MORNING ATTENDANCE");
        morningLabel.setFont(new Font("Ariel",Font.BOLD,18));
        morningPanel.add(morningLabel);


        studNames = getStudentNames();
        String[] columnNames = {"Student", "Present/Absent", "Date"};
        morning_model = new DefaultTableModel(columnNames,0);
        morning_table = new JTable(morning_model);
        morning_table.setRowHeight(20);
        morning_table.getTableHeader().setFont(new Font("Ariel",Font.BOLD,12));
        morning_table.getTableHeader().setOpaque(false);
        morning_table.getTableHeader().setBackground(Color.BLUE);
        morning_table.getTableHeader().setForeground(Color.white);

        JScrollPane scrollPane_Morning = new JScrollPane(morning_table);
        morningPanel.add(scrollPane_Morning);

        // EVENING PANEL

        JPanel eveningPanel = new JPanel();

        JLabel eveningLabel = new JLabel();
        eveningLabel.setText("EVENING ATTENDANCE");
        eveningLabel.setFont(new Font("Ariel",Font.BOLD,18));
        eveningPanel.add(eveningLabel);

        evening_model = new DefaultTableModel(columnNames,0);
        evening_table = new JTable(evening_model);
        evening_table.setRowHeight(20);
        evening_table.getTableHeader().setOpaque(false);
        evening_table.getTableHeader().setBackground(Color.BLUE);
        evening_table.getTableHeader().setForeground(Color.white);
        evening_table.getTableHeader().setFont(new Font("Ariel",Font.BOLD,12));

        JScrollPane scrollPane_Evening = new JScrollPane(evening_table);
        eveningPanel.add(scrollPane_Evening);

        

        //shows the table data
        showTable("files/attendanceRecord.txt");

        //  BUTTON PANEL

        JPanel buttonPanel = new JPanel();


        markButton = new JButton("Mark Attendance");
        markButton.addActionListener(new markListener());
        buttonPanel.add(markButton);
        
        refreshButton = new JButton("Refresh Tables");
        refreshButton.addActionListener(new refreshListener());
        buttonPanel.add(refreshButton);

        editButton = new JButton("Edit Student Attendance");
        editButton.addActionListener(new editListener());
        buttonPanel.add(editButton);


        add(morningPanel);
        add(eveningPanel);
        add(buttonPanel);

        setVisible(true);

    }




    public static void main(String[] args) {

        new Attendance();

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

    public String [] loadAttendanceRecordNames(String enteredFile){
        Scanner studNscan = null;
        ArrayList <String> studNameList = new ArrayList<String>();
        try {
            studNscan = new Scanner(new File(enteredFile));
            while (studNscan.hasNext()) {
                String[] nextLine = studNscan.nextLine().split(" ");
                String name = nextLine[0] + " " + nextLine[1];

                if(studNameList.contains(name)){
                    continue;
                }
                else{
                    studNameList.add(name);
                }
                
            } 

            studNscan.close();
        }

        catch (IOException e) {
        }

        // return studlist;
        
        String[] studNameArr = new String[studNameList.size()];
        studNameArr = studNameList.toArray(studNameArr);
        return studNameArr;
    }

    private class editListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            new EditAttendanceScreen(thisScr);
            
            
            
        }
        
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

    private class closeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            thisScr.dispose();            
        }
        
    }


    
}