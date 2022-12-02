import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;



public class paymentsToPdf {

    public paymentsToPdf(){
        main(null);
    }


    public static void main (String [] args){
        FileInputStream fis = null;
        BufferedReader br = null;
        DataInputStream in =null;
        InputStreamReader isr = null;
        File sourceFile = new File ("C:\\reports\\Files\\transactionRecord.txt") ;
        File destFile = new File ("C:\\reports\\Files\\transactionReport.pdf") ;


        try {
            com.itextpdf.text.Document pdfDoc =new com.itextpdf.text.Document();
            PdfWriter writer = PdfWriter.getInstance (pdfDoc,new FileOutputStream(destFile));
            pdfDoc.open();
            pdfDoc.setMarginMirroring(true);
            pdfDoc.setMargins(36,72,108,180);
            pdfDoc.topMargin();

            BaseFont courier = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252, BaseFont.EMBEDDED);
            Font myfont = new Font(courier);

            Font bold_font =new Font();
            bold_font.setStyle(Font.BOLD);
            bold_font.setSize(10);

            myfont.setStyle(Font.NORMAL);
            myfont.setSize(9);

            pdfDoc.add(new com.itextpdf.text.Paragraph("\n"));

            if (sourceFile.exists()){
                fis = new FileInputStream(sourceFile);
                in = new DataInputStream(fis);
                isr =new InputStreamReader(in);
                br = new BufferedReader(isr);
                String strLine;
                String Heading = "TRANSACTION REPORT";
                com.itextpdf.text.Paragraph Head = new com.itextpdf.text.Paragraph(Heading+ "\n", myfont);
                Head.setAlignment(Element.ALIGN_CENTER); 
                pdfDoc.add(Head);
                pdfDoc.add( Chunk.NEWLINE );
                pdfDoc.add( Chunk.NEWLINE );

                while((strLine=br.readLine()) != null){

                    com.itextpdf.text.Paragraph para = new com.itextpdf.text.Paragraph(strLine + "\n", myfont);
                    para.setAlignment(Element.ALIGN_JUSTIFIED); 
                    pdfDoc.add(para) ;  
                }  
                
            }    

            else{
                System.out.println("No Source file Found");
            }   
            pdfDoc.close();  
            }catch (Exception e) {
            e.printStackTrace();
        }
    }
}