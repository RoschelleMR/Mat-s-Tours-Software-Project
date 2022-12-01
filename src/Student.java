package src;

public class Student {
    
    private String name, address, highschool, parentName,
    parentTel, paymentPlan; 

    public Student(String name, String address, String highschool, 
    String parentName, String parentTelephone, String paymentPlan){

        this.name = name;
        this.address = address;
        this.highschool = highschool;
        this.parentName = parentName;
        parentTel = parentTelephone;
        this.paymentPlan = paymentPlan;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getHighSchool(){
        return highschool;
    }

    public String parentName(){
        return parentName;
    }

    public String parentTelNo(){
        return parentTel;
    }

    public String paymentPlan(){
        return paymentPlan;
    }

    

}
