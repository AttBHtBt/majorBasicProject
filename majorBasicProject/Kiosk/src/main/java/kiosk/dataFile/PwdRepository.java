package kiosk.dataFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PwdRepository {

    String pwd;

    public void makePwd(String fileName) {
        Boolean check = true;
        try (Scanner scan = new Scanner(new File(fileName))) {
            check = check && this.isAdminFileValid(scan);
            //ㅗ디
            if(check)
                pwd = scan.nextLine().trim();
            else
                regeneratePwdFile();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + fileName);
        }
    }


    /*
     * isAdminFileValid :
     *
     * "admin:admin" == true
     * "admin:admin " == false
     * "admin:admin asdf" == false
     * "admin:admin\nfadf"== false
     * "admin:admin\t" == false
     */
    private boolean isAdminFileValid(Scanner scan) {
        while (scan.hasNext()) {
            String str = scan.nextLine();
            System.out.println(str + Pattern.matches("admin:admin", str) + scan.hasNext());
            if (!Pattern.matches("admin:admin", str) || scan.hasNext()) {
                //admin:admin이 아닐 경우 + admin:admin 이외의 것이 적혀있을 경우 무결성 오류
                System.out.println("isNotValid [admin.txt] file");
                return false;
            }
        }
        return true;
    }

    void regeneratePwdFile(){
        DataFile.regenerateAdminTxT();
    }
}
