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

public class ChangePasswordScreen extends JFrame{

    private ChangePasswordScreen thisScr;


    private JPanel buttonPanel, mainPanel;
    private JComboBox userBox;
    private JButton saveButton;
    private JButton closeButton;

    private JTextField txtPassword,txtNewPassword;
    
    
    public ChangePasswordScreen(){

        thisScr = this;

        setTitle("Change Password");

        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 5, 15));

        String[] users = {"Bus Driver","Bus Monitor"};
        mainPanel.add(new JLabel("Select User: "));
        userBox = new JComboBox<String>(users);
        mainPanel.add(userBox);

        mainPanel.add(new JLabel(" Enter Password: ")); 
        txtPassword = new JTextField(20);
        mainPanel.add(txtPassword);

        mainPanel.add(new JLabel(" Enter New Password: ")); 
        txtNewPassword = new JTextField(20);
        mainPanel.add(txtNewPassword);

        saveButton = new JButton("Enter");
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

    public void changePassword(String Username, String pass, String newPass){
     
        File loginFile = new File("files/loginInfo.txt");
        String newLine;

        try{ 
            File tempFile = new File("files/temp.txt");
            PrintWriter pw = new PrintWriter(tempFile);

            newLine = Username+" "+newPass;

            List<String> allLines = Files.readAllLines(Paths.get("files/loginInfo.txt"));


            for (String fileLine : allLines) {
                if(fileLine.contains(Username) && fileLine.contains(pass)){
                    pw.println(newLine);
                    JOptionPane.showMessageDialog(thisScr,"You edited the " + Username + "'s "+ "password","Edit Succesful",
                    JOptionPane.PLAIN_MESSAGE);
                    continue;          
                }

                pw.println(fileLine);
                
            }

            pw.flush();
            pw.close();

            loginFile.delete();
            tempFile.renameTo(loginFile);
        }
        catch(Exception e){}

    }

    // save listener

    private class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userSelected = userBox.getSelectedItem().toString();
            String password = txtPassword.getText();
            String newPassword = txtNewPassword.getText();

            changePassword(userSelected, password, newPassword);

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
