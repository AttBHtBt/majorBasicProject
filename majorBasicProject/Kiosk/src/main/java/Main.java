import kiosk.config.AppConfig;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.prompt.ManagePrompt;
import kiosk.prompt.OrderPrompt;

public class Main {
    public static void main(String[] args){

        MenuRepository MR = new MenuRepository();
        MR.makeMenu("C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\Recipe.csv");

        MaterialRepository MTR = new MaterialRepository();
        MTR.makeMaterial("C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\material.csv");

        OrderPrompt orderPrompt = AppConfig.orderPrompt();
        ManagePrompt managePrompt = AppConfig.managePrompt();

    }
}