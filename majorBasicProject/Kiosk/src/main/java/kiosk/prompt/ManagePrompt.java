package kiosk.prompt;

import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.domain.Material;
import kiosk.manager.Admin;
import kiosk.domain.ManagePromptToken;

import java.lang.reflect.Array;
import java.util.Arrays;

import java.io.PrintStream;
import kiosk.domain.Menu;
import java.util.*;

public class ManagePrompt {

    private Scanner sc = new Scanner(System.in);
    Admin admin = Admin.getAdmin();
    private String input;
    private List<String> commandLineTokens;
    public ManagePromptToken tokens;
    public ManagePromptToken tokens2;
    
    private boolean loopManagePrompt = true;
    

    //정규표현식

    public ManagePrompt(){
        loopManagePrompt = true;
        while (loopManagePrompt){
            showPrompt();
            getInput();
            classifyCommandLine(input);
        }
    }
    private void showPrompt(){
        System.out.print("Admin >");
    }

    public void getInput(){
        input = sc.nextLine();
    }

    private void classifyCommandLine(String input){
        String cmd = Admin.checkCommand(input);
        commandLineTokens = Arrays.asList(input.trim().split("\\s+"));
        MenuRepository.printMenuRepository();
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
                OrderPrompt orderPrompt = new OrderPrompt();
                loopManagePrompt = false;
                break;
            default:
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
        }
    }

    //[공백]*menu[공백]+[-a][공백]+[메뉴][공백]+[음료상태옵션][공백]+[가격][공백]+[재료(단위):사용 수량[공백*]]+
    // command option menu menuOption price [ingredients]+
    
    /*
        MENU
     */
    
    //tokens : menu, -a, menuName, menuOption, price, [ingredients],...
    // menuOption에 -를 받게 되어있느가?
    private void addMenu(){
        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4).toUpperCase(), unDividedIngredients);
        if (!addMenuSyntaxValid(tokens)){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (!addMenuSemanticsValid(tokens)) {
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (MenuRepository.isMenuNameinRepository(tokens.getMenu(), tokens.getMenuOption())) {
            System.out.println("(오류) 동일한 이름의 메뉴가 동시에 동일한 음료 상태 옵션과 함께 존재합니다.");
            return;
        }
        //- 입력시 HOT/ICE인 메뉴가 존재함 
        if (tokens.getOptionCommand().equals("-")){
            MenuRepository.isHotOrIceInSameMenuName(tokens);
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        //HOT / ICE 입력 시 -인 메뉴가 존재함
        if (tokens.getOptionCommand().toUpperCase().equals("HOT") || tokens.getOptionCommand().toUpperCase().equals("ICE")){
            MenuRepository.isHyphenInSameMenuName(tokens);
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        //INGREDIENTS에 중복된 메뉴가 있음.
        if (MenuRepository.isSameNameInIngredients(tokens.getItems())){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        //String menu, String price, String beverageStateOption, List<String> ["abc(ml):123, ..."]

        
        //HOT OR ICE
        if (!tokens.getMenuOption().equals("-")){
            System.out.print("Admin >");
            getInput();
            if(admin.checkCommand(input).equals("error"))
                return;
            commandLineTokens = Arrays.asList(input.trim().split("\\s+"));
            List<String> unDividedIngredients2 = commandLineTokens.subList(5, commandLineTokens.size());
            tokens2 = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                    commandLineTokens.get(3), commandLineTokens.get(4).toUpperCase(), unDividedIngredients2);
            if ((tokens.getMenuOption().equals("HOT") && tokens2.getMenuOption().equals("HOT")) ||  (tokens.getMenuOption().equals("ICE") && tokens2.getMenuOption().equals("ICE")))
                return ;
            if (!addMenuSyntaxValid(tokens2)){
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                return ;
            }
            if (!addMenuSemanticsValid(tokens2)) {
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                return ;
            }
            if (MenuRepository.isMenuNameinRepository(tokens2.getMenu(), tokens2.getMenuOption())) {
                System.out.println("(오류) 동일한 이름의 메뉴가 동시에 동일한 음료 상태 옵션과 함께 존재합니다.");
                return;
            }
            //- 입력시 HOT/ICE인 메뉴가 존재함 
            if (tokens2.getOptionCommand().equals("-")){
                MenuRepository.isHotOrIceInSameMenuName(tokens2);
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                return ;
            }
            //HOT / ICE 입력 시 -인 메뉴가 존재함
            if (tokens2.getOptionCommand().toUpperCase().equals("HOT") || tokens.getOptionCommand().toUpperCase().equals("ICE")){
                MenuRepository.isHyphenInSameMenuName(tokens2);
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                return ;
            }
            //INGREDIENTS에 중복된 메뉴가 있음.
            if (MenuRepository.isSameNameInIngredients(tokens2.getItems())){
                System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                return ;
            }

            Menu menu = new Menu(tokens.getMenu(), tokens.getPrice(),  tokens.getMenuOption(), unDividedIngredients);
            MenuRepository.addMenu(menu);
            if (tokens.getMenuOption().equals("ICE") || tokens.getMenuOption().equals("HOT")){
                Menu menu2 = new Menu(tokens2.getMenu(), tokens2.getPrice(),  tokens2.getMenuOption(), unDividedIngredients2);
                MenuRepository.addMenu(menu2);
            }
        }
        else {
            Menu menu = new Menu(tokens.getMenu(), tokens.getPrice(),  tokens.getMenuOption(), unDividedIngredients);
            MenuRepository.addMenu(menu);
        }
        MenuRepository.printMenuRepository();
    }
    
    private boolean addMenuSyntaxValid(ManagePromptToken tokens) {
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

    private boolean addMenuSemanticsValid(ManagePromptToken tokens) {
        boolean isValidAddMenuCommand = Admin.isMenuPriceSemanticsValid(Integer.toString(tokens.getPrice()));

        boolean isIngredientSemanticsValid = true;
        for (ManagePromptToken.Item item: tokens.getItems()){
            isIngredientSemanticsValid &= Admin.isRecipieSemanticsValid(item.getOriginalString());
        }
        return (isIngredientSemanticsValid && isValidAddMenuCommand);
    }

    private void modifyMenu() {
        List<String> unDividedIngredients = commandLineTokens.subList(5, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                commandLineTokens.get(3), commandLineTokens.get(4), unDividedIngredients);

        if (!addMenuSyntaxValid(tokens)) {
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return;
        }
        if (!addMenuSemanticsValid(tokens)) {
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return;
        }
        if (!MenuRepository.isMenuNameinRepository(tokens.getMenu(), tokens.getMenuOption()))
            System.out.println("(오류) 메뉴가 레시피 파일에 존재하지 않습니다.");
        else {
            ArrayList<Menu> menus = MenuRepository.getMenu_Map();
            MenuRepository.deleteMenu(tokens.getMenu(), tokens.getMenuOption());
            if (!tokens.getOptionCommand().equals("-")) {
                for (Menu menu : menus) {
                    if (menu.getMenu().equals(tokens.getMenu())) {
                        if (tokens.getMenuOption().equals("HOT"))
                            MenuRepository.deleteMenu(tokens.getMenu(), "ICE");
                        else if (tokens.getMenuOption().equals("ICE"))
                            MenuRepository.deleteMenu(tokens.getMenu(), "HOT");
                    }
                }
            }

            //add
            {
                if (!addMenuSyntaxValid(tokens)) {
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    return;
                }
                if (!addMenuSemanticsValid(tokens)) {
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    return;
                }
                if (MenuRepository.isMenuNameinRepository(tokens.getMenu(), tokens.getMenuOption())) {
                    System.out.println("(오류) 동일한 이름의 메뉴가 동시에 동일한 음료 상태 옵션과 함께 존재합니다.");
                    return;
                }
                //- 입력시 HOT/ICE인 메뉴가 존재함 
                if (tokens.getOptionCommand().equals("-")) {
                    MenuRepository.isHotOrIceInSameMenuName(tokens);
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    return;
                }
                //HOT / ICE 입력 시 -인 메뉴가 존재함
                if (tokens.getOptionCommand().toUpperCase().equals("HOT") || tokens.getOptionCommand().toUpperCase().equals("ICE")) {
                    MenuRepository.isHyphenInSameMenuName(tokens);
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    return;
                }
                //INGREDIENTS에 중복된 메뉴가 있음.
                if (MenuRepository.isSameNameInIngredients(tokens.getItems())) {
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    return;
                }
                //String menu, String price, String beverageStateOption, List<String> ["abc(ml):123, ..."]


                //HOT OR ICE
                if (!tokens.getMenuOption().equals("-")) {
                    getInput();
                    commandLineTokens = Arrays.asList(input.trim().split("\\s+"));
                    List<String> unDividedIngredients2 = commandLineTokens.subList(5, commandLineTokens.size());
                    tokens2 = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2),
                            commandLineTokens.get(3), commandLineTokens.get(4).toUpperCase(), unDividedIngredients2);
                    if (tokens.getMenuOption().equals("HOT") && tokens2.getMenuOption().equals("ICE") || tokens.getMenuOption().equals("ICE") && tokens2.getMenuOption().equals("HOT"))
                        return;
                    if (!addMenuSyntaxValid(tokens2)) {
                        System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                        return;
                    }
                    if (!addMenuSemanticsValid(tokens2)) {
                        System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                        return;
                    }
                    if (MenuRepository.isMenuNameinRepository(tokens2.getMenu(), tokens2.getMenuOption())) {
                        System.out.println("(오류) 동일한 이름의 메뉴가 동시에 동일한 음료 상태 옵션과 함께 존재합니다.");
                        return;
                    }
                    //- 입력시 HOT/ICE인 메뉴가 존재함 
                    if (tokens2.getOptionCommand().equals("-")) {
                        MenuRepository.isHotOrIceInSameMenuName(tokens2);
                        System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                        return;
                    }
                    //HOT / ICE 입력 시 -인 메뉴가 존재함
                    if (tokens2.getOptionCommand().toUpperCase().equals("HOT") || tokens.getOptionCommand().toUpperCase().equals("ICE")) {
                        MenuRepository.isHyphenInSameMenuName(tokens2);
                        System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                        return;
                    }
                    //INGREDIENTS에 중복된 메뉴가 있음.
                    if (MenuRepository.isSameNameInIngredients(tokens2.getItems())) {
                        System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                        return;
                    }

                    Menu menu = new Menu(tokens.getMenu(), tokens.getPrice(), tokens.getMenuOption(), unDividedIngredients);
                    MenuRepository.addMenu(menu);
                    if (tokens.getMenuOption().equals("ICE") || tokens.getMenuOption().equals("HOT")) {
                        Menu menu2 = new Menu(tokens2.getMenu(), tokens2.getPrice(), tokens2.getMenuOption(), unDividedIngredients2);
                        MenuRepository.addMenu(menu2);
                    }
                }
                else{
                    Menu menu = new Menu(tokens.getMenu(), tokens.getPrice(), tokens.getMenuOption(), unDividedIngredients);
                    MenuRepository.addMenu(menu);
                }
            }
        }
    }

      /* TODO
          MENU 추가할거 중복검사 + 데이터에 tokenize한 것들 추가하기.
          addMenuToDatabase()
          deleteMenuFromDatabase()

       */

    //[공백]*menu[공백]+[-d][공백]+[메뉴][공백]+[음료상태옵션][공백]*
    private void deleteMenu(){

        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), commandLineTokens.get(2), commandLineTokens.get(3));
        ArrayList<Menu> menus = MenuRepository.getMenu_Map();

        if (!deleteMenuSyntaxValid())
        {
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (!deleteMenuSemanticsValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (!MenuRepository.isMenuNameinRepository(tokens.getMenu(), tokens.getMenuOption()))
            System.out.println("(오류) 메뉴가 레시피 파일에 존재하지 않습니다.");
        else{
            MenuRepository.deleteMenu(tokens.getMenu(), tokens.getMenuOption());
            if (!tokens.getOptionCommand().equals("-")){
                for (Menu menu: menus){
                    if (menu.getMenu().equals(tokens.getMenu())){
                        if (tokens.getMenuOption().equals("HOT"))
                            MenuRepository.deleteMenu(tokens.getMenu(), "ICE");
                        else if (tokens.getMenuOption().equals("ICE"))
                            MenuRepository.deleteMenu(tokens.getMenu(), "HOT");
                    }
                }
            }
        }
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

        if (!addStockSyntaxValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (!addStockSemanticsValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        
        // tokens.getItems().get(0).getItem() : Item: 재고이름:수량, 재고이름, 수량
        if (MaterialRepository.isDuplicatedMaterial(tokens.getItems().get(0).getItem()))
            System.out.println("(오류) : 이미 데이터베이스에 동일한 재료가 존재합니다.");
        else {
            MaterialRepository.addMaterial(new Material(tokens.getItems().get(0).getItem(), tokens.getItems().get(0).getQuantity()));
        }
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

        if (!addStockSyntaxValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }
        if (!addStockSemanticsValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return ;
        }

        //동일한 재고가 없다면
        if (!MaterialRepository.isDuplicatedMaterial(tokens.getItems().get(0).getItem())){
            System.out.println("(오류) 데이터베이스에 동일한 재료가 존재하지 않습니다.");
        } else {
            MaterialRepository.deleteMaterial(tokens.getItems().get(0).getItem());
            MaterialRepository.addMaterial(new Material(tokens.getItems().get(0).getItem(), tokens.getItems().get(0).getQuantity()));
        }
    }
    
    
    private void deleteStock(){
        //명령줄 형식에 맞는지 확인
        //의미규칙에 맞는지 확인
        List<String> unDividedIngredients = commandLineTokens.subList(2, commandLineTokens.size());
        tokens = new ManagePromptToken(commandLineTokens.get(0), commandLineTokens.get(1), unDividedIngredients);

        if (!deleteStockSyntaxValid()){
            System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            return;
        }
        if (!MaterialRepository.isDuplicatedMaterial(tokens.getItems().get(0).getItem())){
            System.out.println("(오류) 데이터베이스에 동일한 재료가 존재하지 않습니다.");
        } else {
            MaterialRepository.deleteMaterial(tokens.getItems().get(0).getItem());
        }
    }

    private boolean deleteStockSyntaxValid() {
        List<ManagePromptToken.Item> items = tokens.getItems();
        boolean isValid = Admin.isMenuNameSynaxValid(items.get(0).getItem());

        return (isValid);
    }

}