import kiosk.config.AppConfig;
import kiosk.dataFile.DataFile;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.dataFile.PwdRepository;
import kiosk.prompt.ManagePrompt;

import java.io.File;
import java.util.regex.Pattern;
//import kiosk.prompt.OrderPrompt;

public class Main {
    public static void main(String[] args){


        boolean isIngredientSyntaxValid = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)", "물(ml)");
        System.out.println(isIngredientSyntaxValid);

        MenuRepository MR = new MenuRepository();
        System.out.println("asfasfa" + System.getProperty("user.dir"));
        System.setProperty("user.dir", "C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src");
        System.out.println("asfasfa" + System.getProperty("user.dir"));

       MR.makeMenu("C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\menu_1.csv");

        MaterialRepository MTR = new MaterialRepository();
        MTR.makeMaterial("C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\ingredients_1.csv");


        /*
        MTR.makeMaterial(DataFile.dataFileDirectory + DataFile.ingredientFileName_Testing);

        PwdRepository PR = new PwdRepository();
        PR.makePwd(DataFile.dataFileDirectory + DataFile.adminFileName_Testing);

//        OrderPrompt orderPrompt = AppConfig.orderPrompt();
        ManagePrompt managePrompt = AppConfig.managePrompt();



*/
    }
}