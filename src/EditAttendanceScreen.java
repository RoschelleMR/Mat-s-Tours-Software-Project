package src;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdatepicker.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;
import org.jdatepicker.impl.UtilDateModel;

public class EditAttendanceScreen extends JFrame {

    private EditAttendanceScreen thisScr;
    private Attendance attendanceSheet;

    private JPanel buttonPanel, mainPanel;
    private JComboBox studentBox, periodBox, presenceBox;
    private JButton saveButton;
    private JButton closeButton;
    
    private JDatePickerImpl datePicker;

    public EditAttendanceScreen(Attendance attendance){

        thisScr = this;
        attendanceSheet = attendance;

        setTitle("Edit Attendance");

        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 5, 15));

        String[] studNames = attendanceSheet.loadAttendanceRecordNames("files/attendanceRecord.txt");

        mainPanel.add(new JLabel("Student: "));
        studentBox = new JComboBox<String>(studNames);
        mainPanel.add(studentBox);

        mainPanel.add(new JLabel("Period"));
        String[] periods = { "Morning", "Evening" };
        periodBox = new JComboBox<String>(periods);
        mainPanel.add(periodBox);

        // Date

        mainPanel.add(new JLabel("Date:"));

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

        mainPanel.add(new JLabel("New Attendance Status"));
        String[] presence = { "Present", "Absent" };
        presenceBox = new JComboBox<String>(presence);
        mainPanel.add(presenceBox);

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

    public void addEditToRecord(String attendanceFile, String studentName, String period, String presenceSelected, String dateSelected){
        File aFile = new File(attendanceFile);
        String newLine;

        try{ 
            File tempFile = new File("files/temp.txt");
            PrintWriter pw = new PrintWriter(tempFile);

            newLine = studentName+" "+period+" "+presenceSelected+" "+dateSelected;

            List<String> allLines = Files.readAllLines(Paths.get("files/attendanceRecord.txt"));


            for (String fileLine : allLines) {
                if(fileLine.contains(studentName) && fileLine.contains(period) && fileLine.contains(dateSelected)){
                    pw.println(newLine);
                    JOptionPane.showMessageDialog(thisScr,"You edited " + studentName + "'s "+ "attendance","Edit Succesful",
                    JOptionPane.PLAIN_MESSAGE);
                    continue;          
                }

                pw.println(fileLine);
                
            }

            pw.flush();
            pw.close();

            aFile.delete();
            tempFile.renameTo(aFile);
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

    private class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String studentName = studentBox.getSelectedItem().toString();
            String selectedDate = datePicker.getJFormattedTextField().getText();
            String periodOfday = periodBox.getSelectedItem().toString();
            String newPresence = presenceBox.getSelectedItem().toString();

            if (dateChecker(selectedDate)){
                addEditToRecord("files/attendanceRecord.txt", studentName, periodOfday, newPresence, selectedDate);
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
