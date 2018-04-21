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
            String key = "TuyMove!@#@$!"; // needs to be at least 8 characters for DES
            FileInputStream fis2 = new FileInputStream("core.dll");
            File tempF = File.createTempFile("javatempfile",".mytemp");
            FileOutputStream fos2 = new FileOutputStream(tempF);
            decryptFile.decrypt(key, fis2, fos2);
            
            /* Encrypt File 
            FileInputStream fis2 = new FileInputStream("bu_course.txt");
            FileOutputStream fos2 = new FileOutputStream("encrypted1.txt");
            encryptFile.encrypt(key, fis2, fos2);
            */
            UI ui = new UI(tempF);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        
    }
}
