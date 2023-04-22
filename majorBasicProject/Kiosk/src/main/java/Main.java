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



        MenuRepository MR = new MenuRepository("C:\\workspace\\kiosk_new\\majorBasicProject\\Kiosk\\src\\");
//        MR.makeMenu(DataFile.dataFileDirectory + DataFile.menuFileName);

        MR.makeMenu("..\\..\\..\\src" + DataFile.menuFileName);
//
//        MaterialRepository MTR = new MaterialRepository();
//        MTR.makeMaterial(DataFile.dataFileDirectory + DataFile.ingredientFileName);
//
//        PwdRepository PR = new PwdRepository();
//        PR.makePwd(DataFile.dataFileDirectory + DataFile.adminFileName);

        OrderPrompt orderPrompt = AppConfig.orderPrompt();
//        ManagePrompt managePrompt = AppConfig.managePrompt();

    }
}