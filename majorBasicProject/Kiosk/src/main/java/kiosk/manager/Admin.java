package kiosk.manager;
import java.util.Arrays;

import java.util.regex.*;

/*
* Singleton Pattern 적용 : 클래스 객체 하나만 생성할 수 있도록 .
* */
public class Admin {

    // Singleton Pattern START
    private static Admin onlyOneAdmin = null;
    private Admin(){}

    public static Admin getAdmin(){
        if (onlyOneAdmin==null)
            onlyOneAdmin = new Admin();
        return onlyOneAdmin;
    }
    // Singleton Pattern END
    
    
    private static String regexPRICE = "[0-9]{1,10}";
    private static String regexOPTION = "[a-zA-Z]{1,3}";
    private static String regexMENU = "[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*";
    private static String regexINGREDIENT = "[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)" + ":" + regexPRICE;
    private static String regexINGREDIENTS = String.format("[%s[ ]+]*[%s[ ]*]",regexINGREDIENT, regexINGREDIENT);

    private static final String SPMORE = "[ ]+";
    private static String SP = "[ ]*";
    private static final String MENUA = "[ ]*menu[ ]+[-]a[ ]+" + regexMENU + SPMORE + regexOPTION + SPMORE + regexPRICE + SPMORE + regexINGREDIENTS + SP;
//    menu -a 딸기쉐이크 ICE    3000   얼음(개):10       우유(ml):100 딸기퓨레(ml):20
    private static final String MENUM = "[ ]*menu[ ]+[-]m[ ]+" + regexMENU + SPMORE + regexOPTION + SPMORE + regexPRICE + SPMORE + regexINGREDIENTS + SP;
    private static final String MENUD = "[ ]*menu[ ]+[-]d[ ]+" + regexMENU + SPMORE + regexOPTION + SP;
    private static final String STOCKA = "[ ]*stock[ ]+[-]a[ ]+" + regexINGREDIENT + SP;
    private static final String STOCKM = "[ ]*stock[ ]+[-]m[ ]+" + regexINGREDIENT + SP;
    private static final String STOCKD= "[ ]*stock[ ]+[-]d[ ]+" + regexMENU + SP;
    private static final String cmdExit = "[ ]*exit[ ]*";
    
    private static String error="error";
    private static String[] unitTable={"ml","L","g","Kg","개"};              //민수 : 이건 왜 넣은거에요?

    public static String checkCommand(String command){//검사완료
        String[] commandElements=command.trim().split("\\s+");
        
        if (commandElements.length < 3)
            return error;
        if(!isMainCommand(commandElements[0]))
            return error;
        
        if (Pattern.matches(cmdExit, command))
            return "exit";
        else if (Pattern.matches(MENUA, command))
            return "menu -a";
        else if (Pattern.matches(MENUM, command))
            return "menu -m";
        else if (Pattern.matches(MENUD, command))
            return "menu -d";
        else if (Pattern.matches(STOCKA, command))
            return "stock -a";
        else if (Pattern.matches(STOCKM, command))
            return "stock -m";
        else if (Pattern.matches(STOCKD, command))
            return "stock -d";
        else 
            return "error";
    }


    public static String menuCheckCommand(String commandOption,String menuOption, String menuPrice, String[] subcommands ) {
        String result;
        if (menuCheckAddCommand(commandOption))
            result = "menu -a";
        else if (menuCheckModifyCommand(commandOption))
            result = "menu -m";
        else
            return error;

        //문법형식 체크 + 의미규칙 체크?
        if(!(checkPriceForm(menuPrice)&&checkMenuOptionForm(menuOption)&&checkAmounts(subcommands)))
            return error;
        return result;
    }//검사완료


    public static String stockCheckCommand(String commandOption, String[] subCommands){//검사완료
        String result;
        if (stockCheckAddCommand(commandOption))
            result = "stock -a";
        else if (stockCheckModifyCommand(commandOption))
            result = "stock -m";
        else
            return error;
        if(!checkAmounts(subCommands))
            return error;

        return result;
    }

    public static boolean menuCheckAddCommand(String commandOption){//검사완료
        return commandOption.equals("-a");
    }

    public static boolean menuCheckModifyCommand(String commandOption){//검사완료
        return commandOption.equals("-m");
    }
    public static boolean menuCheckDeleteCommand(String commandOption){//검사완료
        return commandOption.equals("-d");
    }
    public static boolean stockCheckAddCommand(String commandOption){//검사완료
        return commandOption.equals("-a");
    }
    public static boolean stockCheckDeleteCommand(String commandOption){//검사완료
        return commandOption.equals("-d");
    }
    public static boolean stockCheckModifyCommand(String commandOption){//검사완료
        return commandOption.equals("-m");
    }


    public static boolean checkMenuOptionForm(String commandOption){//검사완료
        String lower=commandOption.toLowerCase();
        return lower.equals("ice")||lower.equals("hot")||lower.equals("-");         // 관리쪽에서는 ("-", "HOT", "ICE")를 받아서 -를 받도록 수정함.
    }

    public static boolean checkIntegerForm(String strInteger){//이름은 IntegerForm이지만 자연수만 맞는 걸로 판단
        int integer;                                   //검사완료
        try{
            integer=Integer.parseInt(strInteger);
        } catch(NumberFormatException e){
            return false;
        }
        return integer<=0;              //return integer>0?
    }
    public static boolean checkPriceForm(String strPrice){//검사완료
        return checkIntegerForm(strPrice);
    }


    public static boolean checkAmountForm(String strAmount){//검사완료
        String[] amountElements=strAmount.split(":");
        String ingredient = amountElements[0];
        String quantity = amountElements[1];

        if(amountElements.length!=2)
            return false;
        if (!Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)", ingredient))
            return false;
        if(!checkIntegerForm(quantity))
            return false;
        else
            return true;
//        String amount=amountElements[0], unit=amountElements[1];
//       for(String u : unitTable){           //저희 unittable이 있었나요?
//            if(u.equals(unit))
//                return true;
//       }
    }

    public static boolean checkAmounts(String[] subcommands){//재고 중복 체크와 형식체크를 동시에 진행함.
        int n= subcommands.length;
        String checked;
        for(int i=0;i<n;i++){
            if(!checkAmountForm(subcommands[i]))
                return false;
            checked=subcommands[i];
            for(int j=i+1;j<n;j++){
                if(checked.equals(subcommands[j]))
                    return false;
            }
        }
        return true;
    }


    public static boolean isMainCommand(String mainCommand){//검사완료
        return mainCommand.equals("menu")||mainCommand.equals("stock")||mainCommand.equals("exit");
    }

    public static boolean exitCheckCommand(String command){//검사완료
        return command.equals("exit");
    }




    /*
    * Added BY CMS : 4번항목 데이터 요소에 대한 의미규칙과 문법규칙을 나누어 작성.
     */
    
    public boolean checkMENUA(String cmd){
        return (Pattern.matches("[ ]*menu[ ]+[-]a", cmd));
    }
    

    //TEST 필요

    //RecipieSyntaxValid : 딸기:123.12에 소수도 받던가? => 소수점을 받을 수 없기 때문에 그냥 정수라고 수정했음.
    public static boolean isRecipieSyntaxValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(":");
        if (splittedIngredients.length != 2) return false;
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        boolean isIngredientSyntaxValid = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)", ingredient);
        boolean isQuanitySyntaxValid = Pattern.matches("^[0-9]{1,10}", quantity);

        return (isIngredientSyntaxValid && isQuanitySyntaxValid);
    }

    public static boolean isRecipieSemanticsValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(":");
        if (splittedIngredients.length != 2) return false;
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        int quantityToNumber = Integer.parseInt(quantity);
        boolean isQuantityRangeValid = (1 <= quantityToNumber && quantityToNumber <= Integer.MAX_VALUE);

        return (isQuantityRangeValid);
    }

    public static boolean isMenuPriceSyntaxValid(String strInteger)
    {
        return (Pattern.matches("^[0-9]{1,10}", strInteger));
    }

    public static boolean isMenuPriceSemanticsValid(String strInteger)
    {
        return (Integer.parseInt(strInteger) >= 0 && Integer.parseInt(strInteger) <= Integer.MAX_VALUE);
    }

    public static boolean isMenuOptionSyntaxValid(String commandOption){
        String lower=commandOption.toLowerCase();
        return lower.equals("ice")||lower.equals("hot")||lower.equals("-");
    }



    public static boolean isStockSyntaxValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(":");
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        boolean isIngredientSyntaxValid = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)", ingredient);
        boolean isQuanitySyntaxValid = Pattern.matches("^[0-9]{1,10}", quantity);

        return (isIngredientSyntaxValid && isQuanitySyntaxValid);
    }

    public static boolean isStockSemanticsValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(":");
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        int quantityToNumber = Integer.parseInt(quantity);
        boolean isQuantityRangeValid = (0 <= quantityToNumber && quantityToNumber <= Integer.MAX_VALUE * 1.0);

        return (isQuantityRangeValid);
    }

    public static boolean isAdminSyntaxValid(String str)
    {
        return (str.equals("admin:admin"));
    }

    public static boolean isMenuNameSynaxValid(String str){
        return (Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*", str));
    }


    public static boolean CSVisStockSyntaxValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(",");
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        boolean isIngredientSyntaxValid = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣][0-9a-zA-Zㄱ-ㅎ가-힣]*\\([a-zA-Zㄱ-ㅎ가-힣]+\\)", ingredient);
        boolean isQuanitySyntaxValid = Pattern.matches("^[0-9]{1,10}", quantity);

        return (isIngredientSyntaxValid && isQuanitySyntaxValid);
    }

    public static boolean CSVisStockSemanticsValid(String str)
    {
        String[] splittedIngredients;
        String ingredient;
        String quantity;

        splittedIngredients = str.trim().split(",");
        ingredient = splittedIngredients[0];
        quantity = splittedIngredients[1];

        int quantityToNumber = Integer.parseInt(quantity);
        boolean isQuantityRangeValid = (0 <= quantityToNumber && quantityToNumber <= Integer.MAX_VALUE * 1.0);

        return (isQuantityRangeValid);
    }
}

