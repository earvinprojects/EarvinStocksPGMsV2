package tw.idv.earvin.stockpgms.stocks_modules;

import java.io.File;

public class FileDemo {
   public static void main(String[] args) {
      
      File f = null;
      File f1 = null;
      String path = "";
      boolean bool = false;
      
      try{
         // create new files
         f = new File("C:\\Users\\linea_000\\Dropbox\\PersonalData\\Book1.ods");
         
         // create new canonical form file object
         f1 = f.getCanonicalFile();
         
         // returns true if the file exists
         bool = f1.exists();
         
         // returns absolute pathname
         path = f1.getAbsolutePath();
         
         // if file exists
         if(bool)
         {
            // prints
            System.out.print(path+" Exists? "+ bool);
         }
         
      }catch(Exception e){
         // if any error occurs
         e.printStackTrace();
      }
   }
}
