import kiosk.config.AppConfig;
import kiosk.buyer.order.Controller;
import kiosk.prompt.OrderPrompt;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello 전기프!");
        OrderPrompt orderPrompt = AppConfig.orderPrompt();


    }

}
