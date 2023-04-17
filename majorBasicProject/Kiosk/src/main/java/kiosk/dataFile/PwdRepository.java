package kiosk.dataFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PwdRepository {

    String pwd;

    public void makeMenu(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            while(scan.hasNext()){
                String str = scan.nextLine();
                this.pwd = str;
            }
        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException");
        }
    }
}
