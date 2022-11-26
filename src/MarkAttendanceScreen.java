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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class MarkAttendanceScreen extends JFrame {

    private MarkAttendanceScreen thisScr;
    private Attendance attendanceSheet;
    private JPanel buttonPanel, mainPanel;
    private JComboBox studentBox, periodBox, presenceBox;
    private JButton saveButton;
    private JButton closeButton;

    private JDatePickerImpl datePicker;

    public MarkAttendanceScreen(Attendance attendance) {
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

        mainPanel.add(new JLabel("Present/Absent"));
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

        closeButton = new JButton("Cancel");
        // closeButton.addActionListener(new cancelListener());
        buttonPanel.add(closeButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    // save listener

    private class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String studentName = studentBox.getSelectedItem().toString();
            String selectedDate = datePicker.getJFormattedTextField().getText();
            System.out.println(selectedDate);
        }
    }

}
