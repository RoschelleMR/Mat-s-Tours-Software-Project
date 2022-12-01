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
    private DefaultTableModel model;
    private JTable table;
    private JLabel searchLabel , cbLabel, cbLabel2;
    private JTextField searchField;
    private JButton searchButton, exitButton;
    private JComboBox comboBox;
    private JComboBox comboBoxP;

    private JMenuBar menu;
    private JMenu privilegesJMenu;
    private JMenuItem attendanceItem, transItem;

    private TableRowSorter<DefaultTableModel> sorter;

    private UserInterface uInterface;

    public StudentListing(UserInterface userInterface)
    {
        uInterface = userInterface;

        String s1[] = {"None", "Manchester High School", "Belair High School", "Bishop Gibson High School", "Decarteret College"};

        String s2[] = {"None", "Paid", "Unpaid"};

        String data[][]={ {"Roschelle Rhoden", "24 Apple Tree Lane", "Manchester High School", "Marcia Rhoden", "(876)212-3355" ,"Paul Rhoden", "(876) 789-6543", "Monthly", "Paid"},    
                          {"Chelsea Porter", "42 Banana Tree Lane", "Manchester High School", "Charmaine Porter", "(876)210-9999","Robert Porter", "(876) 905-5443", "Weekly", "Paid"},
                          {"Trishanna Ford","6 Orange Tree Lane", "Belair High School", "Cheryl Ford", "(876)210-7658","Robert Ford", "(876) 905-7858", "Weekly", "Unpaid"},
                          {"Jordane Anderson","10 MangoTree Lane", "Bishop Gibson High School", "Janet Jackson", "(876)589-5658","Ricardo Anderson", "(876) 875-7553", "Termly", "Unpaid"}}; 

        String [] columnNames ={"Student's Name","Home Address", "School", "Mother's Name", "Contact Number", "Father's Name", "Contact Number", "Payment Plan", "Payment Status"};
        
        //CREATE ELEMENTS
        JPanel panel = new JPanel();

        //MenuBar
        menu = new JMenuBar();
        menu.setBackground(new Color(15,50,100));

        JLabel menuTitle = new JLabel();
        menuTitle.setText("MAT'S TOURS BUS DRIVER");
        menuTitle.setFont(new Font("Ariel",Font.BOLD,12));
        menuTitle.setForeground(Color.WHITE);
        menu.add(menuTitle);
        
        


        privilegesJMenu=new JMenu("Privileges"); 
        privilegesJMenu.setBackground(Color.white);
        privilegesJMenu.setOpaque(true);


        attendanceItem = new JMenuItem("Attendance Register");
        attendanceItem.addActionListener(new attendanceListener());
        privilegesJMenu.add(attendanceItem);
        
        transItem = new JMenuItem("Transcations");
        transItem.addActionListener(new transcationListener());
        privilegesJMenu.add(transItem);


        menu.add(privilegesJMenu);
        setJMenuBar(menu);

        //End of MenuBar

        searchLabel = new JLabel("Search By Name:");
        searchLabel.setForeground(Color.white);
        searchLabel.setFont(new Font("Serif", Font.BOLD, 16));
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        cbLabel = new JLabel("  Filter by School:");
        cbLabel.setFont(new Font("Serif", Font.BOLD, 16));
        cbLabel.setForeground(Color.white);
        comboBox = new JComboBox(s1);
        cbLabel2 = new JLabel(" Filter by Payment Status:");
        cbLabel2.setFont(new Font("Serif", Font.BOLD, 16));
        cbLabel2.setForeground(Color.white);
        comboBoxP = new JComboBox(s2);
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        sorter = new TableRowSorter<DefaultTableModel>(model);

        //TABLE DESIGN
        table.getColumnModel().getColumn(0).setPreferredWidth(150); 
        table.getColumnModel().getColumn(1).setPreferredWidth(150); 
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.getColumnModel().getColumn(8).setPreferredWidth(150);
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
                RowFilter<DefaultTableModel, Object> rf  = RowFilter.regexFilter(comboBox.getSelectedItem().toString(), 3);
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
                RowFilter<DefaultTableModel, Object> rf  = RowFilter.regexFilter(comboBoxP.getSelectedItem().toString(), 9);
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

    private class attendanceListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new Attendance(uInterface);
        }
    }

    private class transcationListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new TransactionScreen();
        }
    }

}
   

