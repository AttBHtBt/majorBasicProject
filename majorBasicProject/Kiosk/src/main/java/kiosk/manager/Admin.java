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
    private static String[] unitTable={"ml","L","g","Kg","개"};

    public static  String checkCommand(String command) {
        String[] commandElements = command.trim().split("\\s+");
        if (!isMainCommand(commandElements[0]))
    /*
        사실 쓰실 때는 checkCommand만 쓰시면 됩니다.
        1.
        앞에 menu가 들어가면 menu명령어 입력규칙을 판단하는데 쓰이는 함수이고
        앞에 stock이 들어가면 stock명령어 입력규칙을 판단하는데 쓰이는 함수이다.

        2.
        주된 함수는 checkCommand인데 입력을 받아서 어떤 명령어 인지를 리턴하며 입력규칙에 맞지 않으면
        "error"를 리턴함.
        ex) return "menu -a";, return "stock -m";, return "exit";, return "error";

        3.
        주의할 점은
        메소드 menuCheckCommand는 menu -a와 menu -m만을 판단하며 menu -m은 판단하지 않음.
        메소드 stockCheckCommand는 stock -a와 stock -m만을 판단하며 stock -m은 판단하지 않음.
        그 이유는 -d 옵션은 입력 인자의 개수가 다르기 때문이다.
    */

    /*
        함수설명
        --public String checkCommand(String command)
            명령어 전체를 입력 받아 맞으면 해당 명령어 문자열을, 틀리면 에러 메세지를 리턴
        -public String menuCheckCommand(String commandOption,String menuOption, String menuPrice, String[] subcommands )
            주 명령어, 메뉴옵션(HOT,ICE), 메뉴가격, 재고정보배열을 입력받아 메뉴 명령어가 맞으면 해당 명령어를, 틀리면 에러 메세지를 리턴
            -m과 -a옵션에서만 사용가능함
        -public String stockCheckCommand(String commandOption, String[] subCommands)
            주 명령어, 재고정보배열을 입력받아 재고 명령어가 맞으면 해당 명령어를, 틀리면 에러메세지를 리턴
            -m과 -a옵션에서만 사용가능함
        -public boolean menuCheckAddCommand(String commandOption)
            메뉴 명령어 옵션을 입력받아(-a,-m,-d)등 -a라면 true를 리턴
        -public boolean menuCheckModifyCommand(String commandOption)
            메뉴 명령어 옵션을 입력받아(-a,-m,-d)등 -m라면 true를 리턴
        -public boolean menuCheckDeleteCommand(String commandOption)
            메뉴 명령어 옵션을 입력받아(-a,-m,-d)등 -d라면 true를 리턴
        -public boolean stockCheckAddCommand(String commandOption)
            재고 명령어 옵션을 입력받아(-a,-m,-d)등 -a라면 true를 리턴
        -public boolean stockCheckDeleteCommand(String commandOption)
            재고 명령어 옵션을 입력받아(-a,-m,-d)등 -m라면 true를 리턴
        -public boolean stockCheckModifyCommand(String commandOption)
            재고 명령어 옵션을 입력받아(-a,-m,-d)등 -d라면 true를 리턴
        -public boolean checkMenuOptionForm(String commandOption)
            메뉴 명령어 옵션을 입력받아(-a,-m,-d)등 -a, -m, -d중 하나라면 true를 리턴
        -public boolean checkIntegerForm(String strInteger)
            문자열을 입력 받아 자연수 형태인이면 true를 리턴
        -public boolean checkPriceForm(String strPrice)
            가격 문자열을 입력 받아 자연수 형태이면 true를 리턴
        -public boolean checkAmountForm(String strAmount)
            재고 문자열을 입력 받아("원두:20:g") 재고 입력 규칙에 맞으면 true를 리턴
        -public boolean checkAmounts(String[] subcommands)
            재고 문자열 배열을 입력 받아 재고 입력 규칙을 모두 판단하고 재고 이름에 중복이 없으면 true리턴
        -public boolean isMainCommand(String mainCommand)
            메인 명령어(menu, stock, exit) 문자열을 입력 받아 "menu", "stock", "exit"중 하나라면 true리턴
        -public String exitCheckCommand(String command)
            문자열을 입력받아 "exit"이면 true를 리턴
     */

    public String checkCommand(String command){//검사완료
        String[] commandElements=command.trim().split("\\s+");
        if(!isMainCommand(commandElements[0]))
            return error;

        if(commandElements.length==1&&exitCheckCommand(commandElements[0]))
            return "exit";

        String mainCommand=commandElements[0];
        String commandOption=commandElements[1];
        String menuOption, menuPrice;
        String[] subCommands;

        switch(mainCommand){
            case "menu":
                if(menuCheckDeleteCommand(commandOption)) {
                    return commandElements.length==3 ? "menu -d" : error;
                }
                else{
                    menuOption=commandElements[3]; menuPrice=commandElements[4];
                    subCommands=Arrays.copyOfRange(commandElements,5,commandElements.length);
                    return menuCheckCommand(commandOption, menuOption, menuPrice, subCommands);
                }

            case "stock":
                if(stockCheckDeleteCommand(commandOption)) {
                    return commandElements.length==3 ? "stock -d" : error;
                }
                else {
                    subCommands = Arrays.copyOfRange(commandElements, 2, commandElements.length);
                    return stockCheckCommand(commandOption, subCommands);
                }
        }
        return error;
    }


    public String menuCheckCommand(String commandOption,String menuOption, String menuPrice, String[] subcommands ) {
        String result;
        if (menuCheckAddCommand(commandOption))
            result = "menu -a";
        else if (menuCheckModifyCommand(commandOption))
            result = "menu -m";
        else
            return error;

        if(!(checkPriceForm(menuPrice)&&checkMenuOptionForm(menuOption)&&checkAmounts(subcommands)))
            return error;
        return result;
    }//검사완료


    public String stockCheckCommand(String commandOption, String[] subCommands){//검사완료
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

    public boolean menuCheckAddCommand(String commandOption){//검사완료
        return commandOption.equals("-a");
    }

    public boolean menuCheckModifyCommand(String commandOption){//검사완료
        return commandOption.equals("-m");
    }
    public boolean menuCheckDeleteCommand(String commandOption){//검사완료
        return commandOption.equals("-d");
    }
    public boolean stockCheckAddCommand(String commandOption){//검사완료
        return commandOption.equals("-a");
    }
    public boolean stockCheckDeleteCommand(String commandOption){//검사완료
        return commandOption.equals("-d");
    }
    public boolean stockCheckModifyCommand(String commandOption){//검사완료
        return commandOption.equals("-m");
    }


    public boolean checkMenuOptionForm(String commandOption){//검사완료
        String lower=commandOption.toLowerCase();
        return lower.equals("ice")||lower.equals("hot");
    }

    public boolean checkIntegerForm(String strInteger){//이름은 IntegerForm이지만 자연수만 맞는 걸로 판단
        int integer;                                   //검사완료
        try{
            integer=Integer.parseInt(strInteger);
        } catch(NumberFormatException e){
            return false;
        }
        return integer<=0;
    }
    public boolean checkPriceForm(String strPrice){//검사완료
        return checkIntegerForm(strPrice);
    }


    public boolean checkAmountForm(String strAmount){//검사완료
        String[] amountElements=strAmount.split(":");
        if(amountElements.length!=3)
            return false;
        String amount=amountElements[1], unit=amountElements[2];
        if(!checkIntegerForm(amount))
            return false;
       for(String u : unitTable){
            if(u.equals(unit))
                return true;
       }
       return false;
    }

    public boolean checkAmounts(String[] subcommands){//재고 중복 체크와 형식체크를 동시에 진행함.
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


    public boolean isMainCommand(String mainCommand){//검사완료
        return mainCommand.equals("menu")||mainCommand.equals("stock")||mainCommand.equals("exit");
    }

    public boolean exitCheckCommand(String command){//검사완료
        return command.equals("exit");
    }

}

