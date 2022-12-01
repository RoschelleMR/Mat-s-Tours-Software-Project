public class transaction {
    
    private String name;
    private String paymentPlan;
    private String paymentStatus;
    private String paymentAmount;
    private String receiptNumber;
    private String paymentType; 
    private String date;

    public transaction(String name, String paymentStatus, String paymentAmount,String date, String paymentPlan, String paymentType, String receiptNumber){

        this.name = name;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.receiptNumber = receiptNumber;
        this.paymentType = paymentType;
        this.paymentPlan = paymentPlan;
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public String getPaymentAmount(){
        return paymentAmount;
    }

    public String getPaymentPlan(){
        return paymentPlan;
    }

    public String getPaymentType(){
        return paymentType;
    }

    public String getReceiptNumber(){
        return receiptNumber;
    }

    public String getPaymentStatus(){
        return paymentStatus;
    }
    public String date(){
        return date;
    }
    

}