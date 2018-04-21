import java.io.*;
import java.util.*;

public class Course
{
    private String[] courseCode = new String[2500];
    private String[] courseName = new String[2500];
    private String[] courseCredit = new String[2500];
    private int found,total;
    public Course(File myDB)
    {
        int i = 0;
        try{
            FileInputStream fstream = new FileInputStream(myDB.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split(" ");
                Record(tokens[0],tokens[1],tokens[2],i);//process record , etc
                i++;
            }
            in.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        total = i;
        //System.out.println((i+1)+" Record read");
    }

    private void Record(String courseCode,String courseName,String courseCredit,int index){
        this.courseCode[index] = courseCode;
        this.courseName[index] = courseName;
        this.courseCredit[index] = courseCredit;
        //System.out.println(courseCode+courseName+courseCredit);
    }

    public boolean searchCode(String code){

        for(int i=0;i<total;i++){
            if(courseCode[i].equals(code)){
                found = i;
                return true;
            }
        }
        return false;
    }

    public String getFullCode(){
        return courseName[found];
    }
    public int getCredit(){
        return Integer.parseInt(courseCredit[found]);
    }
}
