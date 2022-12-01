package src;

import javax.swing.*;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserInterface extends JFrame{
    
    private UserInterface thisScr;
    private StudentListing studentlistScr;


    private JPanel buttonPanel, mainPanel;
    private JComboBox userBox, periodBox, presenceBox;
    private JButton saveButton, changePassButton, closeButton;

    private JTextField txtPassword;

    public UserInterface(){
        thisScr = this;

        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 5, 15));

        String[] users = {"Bus Driver","Bus Monitor"};
        mainPanel.add(new JLabel("Select User: "));
        userBox = new JComboBox<String>(users);
        mainPanel.add(userBox);

        mainPanel.add(new JLabel("Password: ")); 
        txtPassword = new JTextField(20);
        mainPanel.add(txtPassword);

        saveButton = new JButton("Enter");
        saveButton.addActionListener(new saveListener());
        buttonPanel.add(saveButton);

        changePassButton = new JButton("Change Password");
        changePassButton.addActionListener(new changePassListener());
        buttonPanel.add(changePassButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(new closeButtonListener());
        buttonPanel.add(closeButton);


        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new UserInterface();
    }

    public String getUser(){
        String userSelected = userBox.getSelectedItem().toString();
        return userSelected;
    }

    public void userNavigator(String userSelected, String pass){

        List<String> allLines;
        try {
            allLines = Files.readAllLines(Paths.get("files/loginInfo.txt"));
            for (String fileLine : allLines){
                if(fileLine.contains(userSelected)){
                    String [] nextLine = fileLine.split(" ");
                    String fulluser = nextLine[0] + " " + nextLine[1];
                    if(fulluser.equals("Bus Driver")){
                        if(pass.equals(nextLine[2])){
                            new StudentListing(thisScr);
                            thisScr.dispose();
                        }
            
                        else{
                            JOptionPane.showMessageDialog(thisScr,"Incorrect password for "+ userSelected, "Error",
                            JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    else if (fulluser.equals("Bus Monitor")){
                        if(pass.equals(nextLine[2])){
                            new Attendance(thisScr);
                            thisScr.dispose();
                        }
            
                        else{
                            JOptionPane.showMessageDialog(thisScr,"Incorrect password for "+ userSelected, "Error",
                            JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    





    // save listener

    private class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userSelected = userBox.getSelectedItem().toString();
            String password = txtPassword.getText();


            userNavigator(userSelected, password);

        }
    }

    // close listener

    private class closeButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    private class changePassListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new ChangePasswordScreen();
        }
    }

}
