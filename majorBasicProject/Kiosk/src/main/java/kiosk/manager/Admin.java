package kiosk.manager;

public class Admin {
    String error="error";
    public String checkCommand(String command){
        String[] commandElements=command.split("\\s+");
        if(!isMainCommand(commandElements[0]))
            return error;

        String mainCommand=commandElements[0];
        String commandOption=commandElements[1];
        String[] subCommands=Arrays.copyofRange(commandElements,2,commandElements.length);


        switch(mainCommand){
            case menu:
                return menuCheckCommand(commandOption);

            case stock:
                return stockCheckCommand(commandOption);

            case exit:
                if(commandElements.length==1)
                    return "exit";
                else
                    return error;
        }




    }

    public String menuCheckCommand(String command) {
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

    public String stockCheckCommand(String command){
        String result;
        if (stockCheckAddCommand(command))
            result = "menu -a";
        else if (stockCheckModifyCommand(command))
            result = "menu -m";
        else if (stockCheckDeleteCommand(command))
            result = "menu -d";
        else
            return error;

        return result;
    }

    public boolean menuCheckAddCommand(String command){
        return command.equals("-a");
    }

    public boolean menuCheckModifyCommand(String command){
        return command.equals("-m");
    }
    public boolean menuCheckDeleteCommand(String command){
        return command.equals("-d");
    }
    public boolean stockCheckAddCommand(String command){
        return command.equals("-a");
    }
    public boolean stockCheckDeleteCommand(String command){
        return command.equals("-d");
    }
    public boolean stockCheckModifyCommand(String command){
        return command.equals("-m");
    }


    public boolean checkOptionForm(String option){
        String lower=option.toLowerCase();
        return lower.equals("ice")||lower.equals("hot");
    }

    public boolean checkPriceForm(String price){

    }
    public boolean checkAmountForm(String amount){

    }
    public boolean checkIngredientDuplication(String[] command){

    }

    public boolean isMainCommand(String mainCommand){
        return mainCommand.equals("menu")||mainCommand.equals("stock")||mainCommand.equals("exit");
    }

    public String exitCheckCommand(String command){
        if(command.equals("exit"))
            return "exit";
        else
            return error;
    }

}

