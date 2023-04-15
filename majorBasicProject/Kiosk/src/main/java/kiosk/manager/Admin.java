package kiosk.manager;
import java.util.Arrays;

/*
* Singleton Pattern 적용 : 클래스 객체 하나만 생성할 수 있도록 .
* */
public class Admin {

    // Singleton Pattern
    private static Admin onlyOneAdmin = null;
    private Admin(){}

    public static Admin getAdmin(){
        if (onlyOneAdmin==null)
            onlyOneAdmin = new Admin();
        return onlyOneAdmin;
    }
    // Singleton Pattern end

    private static String error="error";
    public static  String checkCommand(String command) {
        String[] commandElements = command.trim().split("\\s+");
        if (!isMainCommand(commandElements[0]))
            return error;

        String mainCommand = commandElements[0];
        String commandOption = commandElements[1];
        String[] subCommands = Arrays.copyOfRange(commandElements, 2, commandElements.length);


        switch (mainCommand) {
            case "menu":
                return menuCheckCommand(commandOption);
            case "stock":
                return stockCheckCommand(commandOption);
            case "exit":
                if (commandElements.length == 1)
                    return "exit";
                else
                    return error;
            default:
                return error;
        }
    }


    private static String menuCheckCommand(String command) {
        String result;
        if (menuCheckAddCommand(command))
            result = "menu -a";
        else if (menuCheckModifyCommand(command))
            result = "menu -m";
        else if (menuCheckDeleteCommand(command))
            result = "menu -d";
        else
            return error;

        return result;
    }

    private static String stockCheckCommand(String command){
        String result;
        if (stockCheckAddCommand(command))
            result = "stock -a";
        else if (stockCheckModifyCommand(command))
            result = "stock -m";
        else if (stockCheckDeleteCommand(command))
            result = "stock -d";
        else
            return error;

        return result;
    }

    private static boolean menuCheckAddCommand(String command){
        return command.equals("-a");
    }

    private static boolean menuCheckModifyCommand(String command){
        return command.equals("-m");
    }
    private static boolean menuCheckDeleteCommand(String command){
        return command.equals("-d");
    }
    private static boolean stockCheckAddCommand(String command){
        return command.equals("-a");
    }
    private static boolean stockCheckDeleteCommand(String command){
        return command.equals("-d");
    }
    private static boolean stockCheckModifyCommand(String command){
        return command.equals("-m");
    }


    private static boolean checkOptionForm(String option){
        String lower=option.toLowerCase();
        return lower.equals("ice")||lower.equals("hot");
    }

//    private static boolean checkPriceForm(String price){
//
//    }
//    private static boolean checkAmountForm(String amount){
//
//    }
//    private static boolean checkIngredientDuplication(String[] command){
//
//    }

    private static boolean isMainCommand(String mainCommand){
        return mainCommand.equals("menu")||mainCommand.equals("stock")||mainCommand.equals("exit");
    }

    private static String exitCheckCommand(String command){
        if(command.equals("exit"))
            return "exit";
        else
            return error;
    }
}

