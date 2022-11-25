package src;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.*;

import java.awt.*;
import javax.swing.*;

public class Attendance extends JFrame{

    private ArrayList<Student> studlist;
    private Attendance thisScr; 

    public static void main(String[] args) {
        //Small test vv
        // Attendance obj = new Attendance();
        // ArrayList<String> result = obj.getStudentNames();
        // System.out.println(result);

        //temporary
        new MarkAttendanceScreen(new Attendance());
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
        //might place the studlist in the class that will have the attendance gui
        studlist = loadstudents("files/students.txt");
        ArrayList<String> studentNames = new ArrayList<String>();
        for (Student stud : studlist) {
            studentNames.add(stud.getName());
        }

        return studentNames;
    }

    public void markAttendance(String studfile) {
        File studFile = new File(studfile);

    }

    //testing mark attendance screen
    public void MarkScreen(){
        new MarkAttendanceScreen(thisScr);
    }

    //creating a text file of the attendance record
    //by adding the selections of the the gui

}