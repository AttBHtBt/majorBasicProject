package kiosk.prompt;

import kiosk.manager.Admin;
import kiosk.domain.ManagePromptToken;

import java.util.Arrays;

import java.io.PrintStream;
import java.util.*;

public class ManagePrompt {

    private Scanner sc = new Scanner(System.in);

    Admin admin = Admin.getAdmin();
    private String input;

    private List<String> commandLineTokens;
    public ManagePromptToken tokens;

    //정규표현식

    public ManagePrompt(){
        showPrompt();
        getInput();
        classifyCommandLine(input);
    }
    private void showPrompt(){
        System.out.print("Admin > ");
    }

    public void getInput(){
        input = sc.nextLine();
    }

    private void classifyCommandLine(String input){
        String cmd = Admin.checkCommand(input);

        commandLineTokens = Arrays.asList(input.trim().split("\\s+"));
        switch (cmd)
        {
            case "menu -a":
                addMenu();
                break;
            case "menu -m":
                modifyMenu();
                break;
            case "menu -d":
                deleteMenu();
                break;
            case "stock -a":
                addStock();
                break;
            case "stock -m":
                modifyStock();
                break;
            case "stock -d":
                deleteStock();
                break;
            case "exit":
                //prompt 전환 함수
                break;
            default :
                commandLineSyntaxError(input);
        }
    }

    //[공백]*menu[공백]+[-a][공백]+[메뉴][공백]+[음료상태옵션][공백]+[가격][공백]+[재료(단위):사용 수량[공백*]]+
    // command option menu menuOption price [ingredients]+
    private void addMenu(){
//        if (!addMenuSyntaxValid()){
//            System.out.println("Syntax Error");
//            return ;
//        }
//        if (!addMenuSemanticsValid()){
//            System.out.println("SemanticsError");
//            return ;
//        }

        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4), unDividedIngredients);
        System.out.println(tokens.toString());

//        if (isAlreadyInMenuDatabase())
//            printerror();
//        addMenutoMenuDatabase(tokens);

    }
    private void modifyMenu(){
//        if (!addMenuSyntaxValid()){
//            System.out.println("Syntax Error");
//            return ;
//        }
//        if (!addMenuSemanticsValid()){
//            System.out.println("SemanticsError");
//            return ;
//        }
        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4), unDividedIngredients);
        System.out.println(tokens.toString());
//        if (!deleteMenuFromDatabase() && !addMenutoMenuDatabase())
//            return;     //하나라도 실패했다면
//        else {
//                //modify가 성공했다면
//        }
    }

    //[공백]*menu[공백]+[-d][공백]+[메뉴][공백]*
    private void deleteMenu(){
//        if (!modifyMenuSyntaxValid()){
//            System.out.println("Syntax Error");
//            return ;
//        }
//        if (!modifyMenuSemanticsValid()){
//            System.out.println("SemanticsError");
//            return ;
//        }
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2), commandLineTokens.get(3));
        System.out.println(tokens.toString());
//        if (!deleteMenutoDatabase())
//            return;
    }
    private void addStock(){
        //명령줄 형식에 맞는지 확인
        //의미규칙에 맞는지 확인
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);
        System.out.println(tokens.toString());
        //동일한 재고가 있다면
//        if (!addStocktoDatabase())
//            return ;
    }
    private void modifyStock(){
        //명령줄 형식에 맞는지 확인
        //의미규칙에 맞는지 확인
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);
        System.out.println(tokens.toString());
        //동일한 재고가 없다면
//        if (!deleteStockfromDatabase())
//            return;
//        addStockToDatabase();
    }
    private void deleteStock(){
        //명령줄 형식에 맞는지 확인
        //의미규칙에 맞는지 확인
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);
        System.out.println(tokens.toString());

        //동일한 재고가 없다면
//        if (!deleteStockfromDatabase())
//            return;
    }
    private void commandLineSyntaxError(String input){

    }

}