import kiosk.config.AppConfig;
import kiosk.dataFile.DataFile;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.dataFile.PwdRepository;
import kiosk.prompt.ManagePrompt;
import kiosk.prompt.OrderPrompt;

import javax.xml.crypto.Data;
import java.io.File;
//import kiosk.prompt.OrderPrompt;

public class Main {
    public static void main(String[] args){

        MenuRepository MR = new MenuRepository();
        System.out.println(System.getProperty("user.dir"));
        MR.makeMenu("C:\\Users\\bacon\\IdeaProjects\\work\\majorBasicProject\\Kiosk\\src\\menu_1.csv");
//        File dir = new File("..\\..\\");
//
//        String[] filenames = dir.list();
//        for (String filename : filenames) {
//            System.out.println("filename : " + filename);
//        }


//
        MaterialRepository MTR = new MaterialRepository();
        MTR.makeMaterial("C:\\Users\\bacon\\IdeaProjects\\work\\majorBasicProject\\Kiosk\\src\\ingredients_1.csv");
//
        PwdRepository PR = new PwdRepository();
        PR.makePwd("C:\\Users\\bacon\\IdeaProjects\\work\\majorBasicProject\\Kiosk\\src\\admin.txt");

        OrderPrompt orderPrompt = AppConfig.orderPrompt();
//        ManagePrompt managePrompt = AppConfig.managePrompt();

    }
}