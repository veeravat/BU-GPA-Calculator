import java.io.*;
public class main
{
    public static void main(String[] args){
        run();
    }

    public static void run(){
        CipherFile decryptFile = new CipherFile();
        CipherFile encryptFile = new CipherFile();
        try {
            String key = "TuyMove!@#@$!"; 
            FileInputStream fis2 = new FileInputStream("core.dll");
            File tempF = File.createTempFile("javatempfile",".mytemp");
            FileOutputStream fos2 = new FileOutputStream(tempF);
            decryptFile.decrypt(key, fis2, fos2);

            UI ui = new UI(tempF);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        
    }
}
