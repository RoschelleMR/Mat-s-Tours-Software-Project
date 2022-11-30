package src;

import javax.swing.*;
import javax.swing.JFormattedTextField.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import org.jdatepicker.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;
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

public class MarkAttendanceScreen extends JFrame {

    private MarkAttendanceScreen thisScr;
    private Attendance attendanceSheet;
    private JPanel buttonPanel, mainPanel;
    private JComboBox studentBox, periodBox, presenceBox;
    private JButton saveButton;
    private JButton closeButton;

    private JDatePickerImpl datePicker;

    public MarkAttendanceScreen(Attendance attendance) {
        thisScr = this;
        attendanceSheet = attendance;


        setTitle("MARK ATTENDANCE");

        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 5, 15));

        String[] studnames = attendanceSheet.getStudentNames()
                .toArray(new String[attendanceSheet.getStudentNames().size()]);

        mainPanel.add(new JLabel("Student: "));
        studentBox = new JComboBox<String>(studnames);
        mainPanel.add(studentBox);

        mainPanel.add(new JLabel("Period"));
        String[] periods = { "Morning", "Evening" };
        periodBox = new JComboBox<String>(periods);
        mainPanel.add(periodBox);

        mainPanel.add(new JLabel("Attendance Status"));
        String[] presence = { "Present", "Absent" };
        presenceBox = new JComboBox<String>(presence);
        mainPanel.add(presenceBox);

        mainPanel.add(new JLabel("Date:"));

        // Date

        UtilCalendarModel model = new UtilCalendarModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        model.setSelected(true);
        datePicker.setVisible(true);
        mainPanel.add(datePicker);

        // Date

        saveButton = new JButton("Save");
        saveButton.addActionListener(new saveListener());
        buttonPanel.add(saveButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(new closeButtonListener());
        buttonPanel.add(closeButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    public void addtoRecord(String attendanceFile, String studentName, String period, String presenceSelected, String dateSelected){
        File aFile = new File(attendanceFile);
        String firstname, lastname, currentLine;

        try{ 
            
            String [] next = studentName.split(" ");
            firstname = next[0];
            lastname = next[1];
            currentLine = firstname+" "+lastname+" "+period+" "+presenceSelected+" "+dateSelected;

            Boolean state = true;
            List<String> allLines = Files.readAllLines(Paths.get("files/attendanceRecord.txt"));
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
                    JOptionPane.showMessageDialog(thisScr,"You already marked " + studfullname + " " + "for the selected date","Duplicate Detected",
                    JOptionPane.PLAIN_MESSAGE);

                    break;
                }
                
            }

            if(state){
                File tempFile = new File("files/temp.txt");
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


    //ensures the date selected isn't after today's date
    public Boolean dateChecker(String date){
        Boolean state;
        Date currentDate = new Date();
        Date enteredDate = null;

        try {
            enteredDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        if(enteredDate.after(currentDate)){
            JOptionPane.showMessageDialog(thisScr,"Cannot select a date that is after today's date",
        "Error",
        JOptionPane.ERROR_MESSAGE);

        state = false;

        }
        else{
            state = true;
        }

        return state;
    }



    // save listener

    private class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String studentName = studentBox.getSelectedItem().toString();
            String selectedDate = datePicker.getJFormattedTextField().getText();
            String periodOfday = periodBox.getSelectedItem().toString();
            String presenceSelected = presenceBox.getSelectedItem().toString();

            if (dateChecker(selectedDate)){
                addtoRecord("files/attendanceRecord.txt", studentName, periodOfday, presenceSelected, selectedDate);
            }

        }
    }

    // close listener

    private class closeButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            thisScr.setVisible(false);
        }

    }

}
