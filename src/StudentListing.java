package src;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.w3c.dom.events.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

 

public class StudentListing extends JFrame{
    private ArrayList<Student> rlist;
    private DefaultTableModel model;
    private JTable table;
    private JLabel searchLabel , cbLabel, cbLabel2;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox comboBox;
    private JComboBox comboBoxP;
    private TableRowSorter<DefaultTableModel> sorter;

    private UserInterface userInterface;

    public StudentListing(UserInterface uInterface)
    {
        uInterface = userInterface;

        String s1[] = {"None", "Manchester High School", "Belair High School", "Bishop Gibson High School", "Decarteret College"};

        String s2[] = {"None", "Weekly", "Termly", "Monthly"};

        rlist = loadStudents("files/addStudent.txt");

        
        String [] columnNames ={"Student's Name","Home Address", "School", "Parent's Name", "Contact Number", "Payment Plan",};
        
        //CREATE ELEMENTS
        JPanel panel = new JPanel();
        searchLabel = new JLabel("Search By Name:");
        searchLabel.setForeground(Color.white);
        searchLabel.setFont(new Font("Serif", Font.BOLD, 16));
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        cbLabel = new JLabel("  Filter by School:");
        cbLabel.setFont(new Font("Serif", Font.BOLD, 16));
        cbLabel.setForeground(Color.white);
        comboBox = new JComboBox(s1);
        cbLabel2 = new JLabel(" Filter by Payment Plan:");
        cbLabel2.setFont(new Font("Serif", Font.BOLD, 16));
        cbLabel2.setForeground(Color.white);
        comboBoxP = new JComboBox(s2);
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(rlist);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        sorter = new TableRowSorter<DefaultTableModel>(model);

        //TABLE DESIGN
        /*table.getColumnModel().getColumn(0).setPreferredWidth(150); 
        table.getColumnModel().getColumn(1).setPreferredWidth(150); 
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);*/
        
        //table.getColumnModel().getColumn(9).setPreferredWidth(150);
        table.setPreferredScrollableViewportSize(new Dimension(1200,650));
        table.getTableHeader().setBackground(Color.magenta);
        
        table.setRowSorter(sorter);
        
        //INITIATE LISTENERS
        SearchButtonListener s = new SearchButtonListener();
        ComboListener cl= new ComboListener();
        ComboListener2 c2 = new ComboListener2();

        //ADD LISTENERS TO ELEMENTS
        searchButton.addActionListener(s);
        comboBox.addItemListener(cl);
        comboBoxP.addItemListener(c2);

        // ADD ELEMENTS TO PANEL
        panel.add(searchLabel); 
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(cbLabel);
        panel.add(comboBox);
        panel.add(cbLabel2);
        panel.add(comboBoxP);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(new Color(0, 128, 128));

        //ADD PANEL TO FRAME AND SET FRAME ATTRIBUTES
        add(panel);
        setTitle("List of Registered Students");         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);    
        setVisible(true);
    }

    //FILTER DATA IN TABLE BY SCHOOL 
    
    private class ComboListener implements ItemListener
    {
        public void itemStateChanged(ItemEvent e)
        {
            if (comboBox.getSelectedItem().toString() == "None")
            {
                sorter.setRowFilter(null);//removes the filter
            }
            else
            {
                RowFilter<DefaultTableModel, Object> rf  = RowFilter.regexFilter(comboBox.getSelectedItem().toString(), 2);
                sorter.setRowFilter(rf);
            }
            
        }
    }

    //FILTER DATA IN TABLE BY SCHOOL 
    private class ComboListener2 implements ItemListener
    {
        public void itemStateChanged(ItemEvent e)
        {
            if (comboBoxP.getSelectedItem().toString() == "None")
            {
                sorter.setRowFilter(null);//removes the filter
            }
            else
            {
                RowFilter<DefaultTableModel, Object> rf  = RowFilter.regexFilter(comboBoxP.getSelectedItem().toString(), 5);
                sorter.setRowFilter(rf);
            }
        }
    }

    // SEARCH DATE IN TABLE BY FIRST OR LAST NAME 
    private class SearchButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String name = searchField.getText();
            if (name.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(name, 0));
            }
        }
    }

    private void showTable(ArrayList<Student> rlist)
    {
        if (rlist.size() > 0) {
            for (Student s : rlist) {
                addToTable(s);
            }
        }
    }

    private void addToTable(Student s) {
        String[] item = { s.getName(), "" + s.getAddress(), "" + s.getHighSchool(), "" + s.parentName(), "" + s.parentTelNo(), "" + s.paymentPlan() };
        model.addRow(item);
    }

    
    public void addPerson(Student s) {
        rlist.add(s);
        addToTable(s);
    }

    private ArrayList<Student> loadStudents(String pfile)
    {
        Scanner pscan = null;
        ArrayList<Student> rlist = new ArrayList<Student>();
        try {
            pscan = new Scanner(new File(pfile));
            while (pscan.hasNext()) {
                String[] infoLine = pscan.nextLine().split(" ");
                String name = infoLine[0] + " " + infoLine[1];
                String address = infoLine[8] + " " + infoLine[9] + " " + infoLine[10];
                String school = infoLine[4] + " " + infoLine[5] + " " + infoLine[6];
                String pname = infoLine[2] + " " + infoLine[3];
                String pcontact = infoLine[11];
                String pplan = infoLine[7];

                Student s = new Student(name, address, school, pname, pcontact, pplan); 
                
                rlist.add(s);
            }

            pscan.close();
        } catch (IOException e) {
        }
        return rlist;
    }

}
   

