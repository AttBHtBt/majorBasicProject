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
        }
    }

    //[공백]*menu[공백]+[-a][공백]+[메뉴][공백]+[음료상태옵션][공백]+[가격][공백]+[재료(단위):사용 수량[공백*]]+
    // command option menu menuOption price [ingredients]+


    /*
        MENU
     */
    private void addMenu(){

        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4), unDividedIngredients);
        System.out.println(tokens.toString());

        if (!addMenuSyntaxValid()){
            System.out.println("Syntax Error");
            return ;
        }
        if (!addMenuSemanticsValid()){
            System.out.println("SemanticsError");
            return ;
        }

//        if (isAlreadyInMenuDatabase())
//            printerror();
//        addMenutoMenuDatabase(tokens);

    }

    private boolean addMenuSyntaxValid() {
        boolean isValidAddMenuCommand = (
                Admin.menuCheckAddCommand(tokens.getOptionCommand()) &&
                Admin.isMenuNameSynaxValid(tokens.getMenu()) &&
                Admin.isMenuOptionSyntaxValid(tokens.getMenuOption()) &&
                Admin.isMenuPriceSyntaxValid(Integer.toString(tokens.getPrice()))
        );

        boolean isIngredientSyntaxValid = true;

        List<ManagePromptToken.Item> items = tokens.getItems();
        for (ManagePromptToken.Item item: items){
            isIngredientSyntaxValid &= Admin.isRecipieSyntaxValid(item.getOriginalString());
        }
        return (isIngredientSyntaxValid && isValidAddMenuCommand);
    }

    private boolean addMenuSemanticsValid() {
        boolean isValidAddMenuCommand = Admin.isMenuPriceSemanticsValid(Integer.toString(tokens.getPrice()));

        boolean isIngredientSemanticsValid = true;
        for (ManagePromptToken.Item item: tokens.getItems()){
            isIngredientSemanticsValid &= Admin.isRecipieSemanticsValid(item.getOriginalString());
        }
        return (isIngredientSemanticsValid && isValidAddMenuCommand);
    }

    private void modifyMenu(){
        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4), unDividedIngredients);
        System.out.println(tokens.toString());

        if (!addMenuSyntaxValid()){
            System.out.println("Syntax Error");
            return ;
        }
        if (!addMenuSemanticsValid()) {
            System.out.println("SemanticsError");
            return ;

//        if (!deleteMenuFromDatabase() && !addMenutoMenuDatabase())
//            return;     //하나라도 실패했다면
//        else {
//                //modify가 성공했다면
//        }
        }
    }

      /* TODO
          MENU 추가할거 중복검사 + 데이터에 tokenize한 것들 추가하기.
          addMenuToDatabase()
          deleteMenuFromDatabase()

       */
//    private boolean addMenuToMenuDatabase() {
//        return false;
//    }
//
//    private boolean deleteMenuFromDatabase() {
//        return false;
//    }

    //[공백]*menu[공백]+[-d][공백]+[메뉴][공백]*
    private void deleteMenu(){

        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2), commandLineTokens.get(3));
        System.out.println(tokens.toString());

        if (!deleteMenuSyntaxValid())
        {
            System.out.println("Syntax Error");
            return ;
        }
        if (!deleteMenuSemanticsValid()){
            System.out.println("SemanticsError");
            return ;
        }

//        if (!deleteMenuFromDatabase())
//            return;
    }

    private boolean deleteMenuSyntaxValid() {
        return (Admin.isMenuNameSynaxValid(tokens.getMenu()) &&
                Admin.isMenuOptionSyntaxValid(tokens.getMenuOption()));
    }

    private boolean deleteMenuSemanticsValid() {
        return true;
    }



    /*
        STOCK
        TODO:
            addStockToDatabase();
            deleteStockFromDatabase();
     */
    private void addStock(){
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);
        System.out.println(tokens.toString());

        if (!addStockSyntaxValid()){
            System.out.println("Syntax Error");
            return ;
        }
        if (!addStockSemanticsValid()){
            System.out.println("SemanticsError");
            return ;
        }

        //동일한 재고가 있다면
//        if (!addStocktoDatabase())
//            return ;
    }

    private boolean addStockSyntaxValid() {

        List<ManagePromptToken.Item> items = tokens.getItems();
        boolean isOnlyOneItemElement = items.size() == 1;
        boolean isValid = Admin.isStockSyntaxValid(items.get(0).getOriginalString());

        return (isOnlyOneItemElement && isValid);
    }

    private boolean addStockSemanticsValid() {
        List<ManagePromptToken.Item> items = tokens.getItems();
        boolean isValid = Admin.isStockSemanticsValid(items.get(0).getOriginalString());

        return (isValid);

    }

    private void modifyStock(){
        //애초에 Token으로 나눌수 있는지부터 확인.
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);
        System.out.println(tokens.toString());

        if (!addStockSyntaxValid()){
            System.out.println("Syntax Error");
            return ;
        }
        if (!addStockSemanticsValid()){
            System.out.println("SemanticsError");
            return ;
        }

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

        if (!deleteStockSyntaxValid()){
            System.out.println("Syntax Error");
            return ;
        }
        //동일한 재료가 없다면
//        if (!deleteStockFromDatabase())
//            return;
    }

    private boolean deleteStockSyntaxValid() {
        List<ManagePromptToken.Item> items = tokens.getItems();
        boolean isValid = Admin.isMenuNameSynaxValid(items.get(0).getItem());

        return (isValid);
    }

}
