package kiosk.config;
import kiosk.buyer.order.Controller;
import kiosk.prompt.*;
public class AppConfig {
    private static Controller controller;
    private static OrderPrompt orderPrompt;

    public static Controller controller(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    public static OrderPrompt orderPrompt(){
        if(orderPrompt == null){
            orderPrompt = new OrderPrompt();
        }
        return orderPrompt;
    }
}