import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class ViewReportsScreen extends JPanel{

    //private Reports thisRef;
    private JPanel pnl;
    //private JPanel reportTypepnl;


    private JLabel reportTypeLabel;

    private JButton viewFinancial;
    private JButton viewAttendance;
    private JButton cmdClose;
    

    public ViewReportsScreen(){
        //thisRef = this;
        pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(400,300));
       // reportTypepnl= new JPanel();
        
        

        //Main Panel
        setLayout(new BorderLayout());
        pnl.setBackground(Color.decode("#211e1c"));

        //Type of Report Label
        reportTypeLabel = new JLabel("SELECT TYPE OF REPORT", SwingConstants.CENTER);
        reportTypeLabel.setBackground(Color.decode("#6bdd70"));
        


        //Button Panel
        viewFinancial  = new JButton("Create Financial Report");
        viewAttendance = new JButton("Create Attendance Report");
        cmdClose = new JButton("Close");

        //Button Colour 
        viewFinancial.setBackground(Color.decode("#00ccff")); 
        viewAttendance.setBackground(Color.decode("#ffff66"));
        cmdClose.setBackground(Color.decode("#e25e54"));

        // Button listeners
        viewFinancial.addActionListener(new vfListener());
        viewAttendance.addActionListener(new vaListener());
        cmdClose.addActionListener(new CloseListener());

        pnl.add(reportTypeLabel);
        pnl.add(viewFinancial);
        pnl.add(viewAttendance);
        pnl.add(cmdClose);
        pnl.setBackground(Color.decode("#d9d9d9"));

        add(reportTypeLabel, BorderLayout.CENTER);
        add(pnl, BorderLayout.SOUTH);

    }

    

    //Action Listeners
    private class vfListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //financialpnl.setVisible(true);
            new paymentsToPdf();
            JOptionPane.showMessageDialog(pnl,"PDF CREATED SUCCESSFULLY","Success",JOptionPane.PLAIN_MESSAGE);
        } 

    }

    private class vaListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //attendancepnl.setVisible(true);
            new attendanceToPdf();
            JOptionPane.showMessageDialog(pnl,"PDF CREATED SUCCESSFULLY","Success",JOptionPane.PLAIN_MESSAGE);
        } 

    }



    private class CloseListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    ////Driver
    public static void main(String[] args) {
        JFrame frame = new JFrame("View Reports");

        frame.setContentPane(new ViewReportsScreen());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}


