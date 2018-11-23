import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LogicForCSV {



    public void WritePairOfWords(List<ForExcel> obj){

        List<ForExcel> pairOfwords = new ArrayList<>();
        pairOfwords = obj;
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter("C:\\Users\\PARTHIT PATEL\\Desktop\\COMBO.csv");
            fileWriter.append("Term1, Term2");
            fileWriter.append("\n");
            for(ForExcel c:pairOfwords){
                if(c.getTerm1()!=null){
                fileWriter.append(c.getTerm1());
                fileWriter.append(", ");
                String temp = String.valueOf(c.getTerm2());
                fileWriter.append(temp);
                fileWriter.append("\n");}
            }

        }catch(Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }finally {
            {
                try{
                    fileWriter.flush();
                    fileWriter.close();
                }catch(Exception iox){
                    iox.printStackTrace();
                }

            }
        }
    }

}
