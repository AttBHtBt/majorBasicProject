import kiosk.config.AppConfig;
import kiosk.dataFile.MenuRepository;
import kiosk.prompt.ManagePrompt;
import kiosk.prompt.OrderPrompt;

public class Main {
    public static void main(String[] args){

        MenuRepository MR = new MenuRepository();
        MR.makeMenu("C:\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\test.csv");
        OrderPrompt orderPrompt = AppConfig.orderPrompt();
        ManagePrompt managePrompt = AppConfig.managePrompt();

    }
}