package kiosk.dataFile;

import kiosk.domain.ManagePromptToken;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import javax.swing.*;
import javax.swing.plaf.MenuBarUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleToIntFunction;


/*
* 파일 무결성 검사 : kiosk.manager.Admin의 *SyntaxValid 함수들 사용해서 각각외 요소들 확인 가능.
 */

public class MenuRepository {

    private static final ArrayList<Menu> MENU_Map = new ArrayList<>();
    private static final ArrayList<Menu> forValidationTest = new ArrayList<>();
    public MenuRepository(){
    }
    public void makeMenu(String fileName){
            try (Scanner scan = new Scanner(new File(fileName))) {
                Boolean check = true;
                if (!scan.hasNext()){
                    check = false;
                    DataFile.isMenuFileValid = false;
                    return;
                }
                while (scan.hasNext() && check) {
                    String str = scan.nextLine();
                    String[] lineArr = str.split(",");
                    if (!(lineArr.length >= 4)) {
                        check = false;
                        DataFile.isMenuFileValid = false;
                        return;
                    }

                    //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...
                    check = check && Admin.isMenuPriceSyntaxValid(lineArr[1].trim())
                            && Admin.isMenuPriceSemanticsValid(lineArr[1].trim())
                            && Admin.isMenuOptionSyntaxValid(lineArr[2].trim());


                    for (int j = 3; j < lineArr.length; j++) {
                        check = check && Admin.isRecipieSyntaxValid(lineArr[j].trim())
                                && Admin.isRecipieSemanticsValid(lineArr[j].trim());
                        if (!check){
                            check = false;
                            DataFile.isMenuFileValid = false;
                            return;
                        }
                    }

                    ArrayList<ArrayList<String>> dynamicArray = new ArrayList<>();
                    for (int i = 3; i < lineArr.length; i++) {
                        String[] array = lineArr[i].split(":");
                        ArrayList<String> list = new ArrayList<>();
                        list.add(array[0].trim());
                        list.add(array[1].trim());
                        dynamicArray.add(list);
                    }
                    //2차원 arrayList에 재료이름과 재료 수량을 넣는다.
                    MENU_Map.add(new Menu(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim().toUpperCase(), dynamicArray));
//                        public Menu(String menu, String price, String beverageStateOption, ArrayList<ArrayList<String>> dynamicArray)
                        forValidationTest.add(new Menu(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim(), dynamicArray));
                }
                check = check && CheckMenuDu(forValidationTest) && CheckRecipeDu(forValidationTest)
                        && CheckOption(forValidationTest);
                if (!check){
                    check = false;
                    DataFile.isMenuFileValid = false;
                    return;
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                System.out.println("FileNotFoundException: FileName" + fileName);
            }
//            catch (IndexOutOfBoundsException e) {
//                System.out.println("IndexOutOfBoundsException");
//            }
            forValidationTest.clear();
        }
    
    
    //중복아님.
    public static boolean isMenuFilevalid(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            Boolean check = true;
            if (!scan.hasNext()) {
                check = false;
                DataFile.isMenuFileValid = false;
                return false;
            }
            while(scan.hasNext() && check){
                String str = scan.nextLine();
                String[] lineArr = str.split(",");
                if (!(lineArr.length >=4)) {
                    check = false;
                    DataFile.isMenuFileValid = false;
                    return false;
                }

                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...
                check = check && Admin.isMenuPriceSyntaxValid(lineArr[1].trim())
                        && Admin.isMenuPriceSemanticsValid(lineArr[1].trim())
                        && Admin.isMenuOptionSyntaxValid(lineArr[2].trim());

                
                for(int j = 3; j< lineArr.length; j++){
                    check = check && Admin.isRecipieSyntaxValid(lineArr[j].trim())
                            && Admin.isRecipieSemanticsValid(lineArr[j].trim());
                    if(!check){
                        check = false;
                        DataFile.isMenuFileValid = false;
                        return false;
                    }
                }

                ArrayList<ArrayList<String>> dynamicArray = new ArrayList<>();
                for(int i = 3; i< lineArr.length; i++){

                    String[] array = lineArr[i].split(":");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(array[0].trim());
                    list.add(array[1].trim());
                    dynamicArray.add(list);
                }
                forValidationTest.add(new Menu(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim(), dynamicArray));
            }

            /* 얘네 test인데 check랑 and 연산 해주기는 해야 함
            check = check && CheckMenuDu(forValidationTest);
            check = check;
            check =  check && CheckRecipeDu(forValidationTest);
            check = check;
            check =  check && CheckOption(forValidationTest);
            check = check;
            */
            check = check && CheckMenuDu(forValidationTest) && CheckRecipeDu(forValidationTest)
                    && CheckOption(forValidationTest);
            //중복 메뉴있는지 찾고


            //중복 레시피있는지 찾고 레시피 안에서 중복 찾기
            //hypen이 있다면 ICE/HOT이 있는지 찾고
            //ICE/HOT이 있다면, hypne이 있는지 찾기.
            //ICE/HOT이 동시에 있는지 찾기.
//            
//            check = check && isDuplicateInMenuName(forValidationTest);
//            check = check && isDuplicateRecipie(forValidationTest);
//            {
//                Menu former;
//                Menu latter;
//                for (int i = 0; i < forValidationTest.size(); i++){
//                    former = forValidationTest.get(i);
//                    for (int j = i + 1; j < forValidationTest.size(); j++){
//                        latter = forValidationTest.get(j);
//                        //메뉴 중복
//                        if ()
//                        if (former.getMenu().equals(latter.getMenu())){
//                            if (former.getBeverageStateOption().equals("-") && (latter.getBeverageStateOption().toUpperCase().equals("HOT") || latter.getBeverageStateOption().toUpperCase().equals("ICE"))){
//                                check = false;
//                            }
//                        }
//                    }
//                    
//                }
//            }
//                
//            
            if (!check){
                check = false;
                DataFile.isMenuFileValid = false;
                return false;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FileNotFoundException: FileName" + fileName);
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
        }
        forValidationTest.clear();
        return true;
    }
    //중복 메뉴있는지 찾고
    private static boolean CheckMenuDu(ArrayList<Menu> fvt){
        for(int i = 0; i<fvt.size(); i++){
            for(int j = i+1; j<fvt.size(); j++){
                if(fvt.get(i).getMenu().equals(fvt.get(j).getMenu())){
                    if(fvt.get(i).getBeverageStateOption().equals(fvt.get(j).getBeverageStateOption()))
                        return false;
                }
            }
        }
        return true;
    }

    //중복 레시피있는지 찾고 레시피 안에서 중복 찾기
    private static boolean CheckRecipeDu(ArrayList<Menu> fvt){
        for(int i = 0; i< fvt.size(); i++){
            for(int j = 0; j<fvt.get(i).getIngredient().size(); j++){
                for(int r = j+1; r<fvt.get(i).getIngredient().size(); r++){
                    if(fvt.get(i).getIngredient().get(j).getName().
                            equals(fvt.get(i).getIngredient().get(r).getName())){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //hypen이 있다면 ICE/HOT이 있는지 찾고
    //ICE/HOT이 있다면, hypne이 있는지 찾기.
    //ICE/HOT이 동시에 있는지 찾기.
    // 하이픈이 있다면 ice.hot이 없어야 하고, ice나 핫이 있다면 그 반대가 있으면서 하이픈은 존재해서는 안된다.
    // 하나씩 보는데, 앞에서 이미 한 얘인지 한번 조사해줘야 한다.
    
//    private static boolean CheckOption2(ArrayList<Menu> fvt){
//        Menu latter, former;
//        boolean hasPair = false;
//        
//        for (int i = 0; i < fvt.size(); i++){
//            former = fvt.get(i);
//            hasPair = false;
//            for (int j = 1; j < fvt.size(); j++){
//                latter = fvt.get(j);
//                
//                if (former.getMenu().equals(latter.getMenu()) && former.getBeverageStateOption().equals("-") && (latter.getBeverageStateOption().equals("ICE") || latter.getBeverageStateOption().equals("HOT")))
//                    return false;
//                if (former.getMenu().equals(latter.getMenu()) && (former.getBeverageStateOption().equals(latter.getBeverageStateOption())))
//                    return false;
//                if (former.getBeverageStateOption().equals("HOT")){
//                    if (latter.getBeverageStateOption().equals("ICE") && former.getMenu().equals(latter.getBeverageStateOption()))
//                        hasPair = true;
//                }
//                if (former.getBeverageStateOption().equals("ICE")){
//                    if (latter.getBeverageStateOption().equals("HOT") && former.getMenu().equals(latter.getBeverageStateOption()))
//                        hasPair = true;
//                }
//            }
//            if (!hasPair && !former.getMenu().equals("-"))
//                return false;
//        }
//        return true;
//    }
private static boolean CheckOption(ArrayList<Menu> fvt) {
        /*
            메뉴를 이름과 메뉴 옵션을 가져오고 '-'라면 같은 메뉴가 있는지만 체크한다.
            만약 HOT이라면 리스트를 순회하면서 같은 이름이 있는지를 체크하고 ICE인지를 확인한다.
         */
    String menuName;
    String menuOption;
    int size=fvt.size();
    int j;
    for(int i=0;i<size;i++){
        j=0;
        menuName=fvt.get(i).getMenu();
        menuOption=fvt.get(i).getBeverageStateOption();
        switch(menuOption){
            case "-":
                for (j=0; j < size; j++) {
                    if (menuName.equals(fvt.get(j).getMenu())&&i!=j)
                        return false;
                }
                break;
            case "HOT":
                for (j=0; j < size; j++) {
                    if (menuName.equals(fvt.get(j).getMenu()) && fvt.get(j).getBeverageStateOption().equals("ICE"))
                        break;
                }
                if(j== size)
                    return false;
                break;
            case "ICE":
                for (j=0; j < size; j++) {
                    if (menuName.equals(fvt.get(j).getMenu()) && fvt.get(j).getBeverageStateOption().equals("HOT"))
                        break;
                }
                if(j== size)
                    return false;
                break;
            default:
                return false;
        }
    }
    return true;
}


    public static ArrayList<Menu> getMenu_Map(){
        return MENU_Map;
    }
    public static void addMenu(Menu menu){// 중복 검사는 아직 안 함
        MENU_Map.add(menu);
    }

    public static void deleteMenu(String name, String option){
        ArrayList<Menu> menus = MenuRepository.getMenu_Map();
        for (Menu menu: menus) {
            if (menu.getMenu().equals(name) && menu.getBeverageStateOption().equals(option)){
                menus.remove(menu);
                return ;
            }
        }
    }

    public static void regenerateMenuFile(){
        // 파일에 문제 있으면 여기서 regenerate 할 예정.
        DataFile.regenerateMenuCSV();
    }

    public static boolean isMenuNameinRepository(String menuName, String orderOption){
        ArrayList<Menu> menus = MenuRepository.getMenu_Map();
        for (Menu menu: menus) {
            if (menu.getMenu().equals(menuName) && menu.getBeverageStateOption().equals(orderOption.toUpperCase())){
                return true;
            }
        }
        return false;
    }
    
    public static Menu getMenuFromNameAndOption(String menuName, String orderOption) {
        for (Menu menu : MENU_Map) {
//            System.out.printf("[%s], [%s]\n", menu.getMenu(), menu.getBeverageStateOption());
            if (menu.getMenu().equals(menuName) && menu.getBeverageStateOption().equals(orderOption)) {
                return menu;
            }
        }
        return null;
    }
    
    public static void setOrderCountToZero(){
        for (Menu menu: MENU_Map){
            menu.setOrderCount(0);
        }
    }

    //- 입력시 HOT/ICE인 메뉴가 존재함 
    public static boolean isHotOrIceInSameMenuName(ManagePromptToken tokens) {
        for (Menu menu: MENU_Map){
            if (menu.getMenu().equals(tokens.getMenu()) &&
                    (menu.getBeverageStateOption().toUpperCase().equals("HOT") ||
                    menu.getBeverageStateOption().toUpperCase().equals("ICE")))
               return true;
        }
        return false;
    }
    
    //HOT / ICE 입력 시 -인 메뉴가 존재함
    //INGREDIENTS에 중복된 메뉴가 있음.
    
    //HOT / ICE 입력 시 -인 메뉴가 존재함
     public static boolean isHyphenInSameMenuName(ManagePromptToken tokens){
         for (Menu menu: MENU_Map){
             if (menu.getMenu().equals(tokens.getMenu()) &&
                     menu.getBeverageStateOption().equals("-"))
                 return true;
         }
         return false;
     }
    
    //INGREDIENTS에 중복된 메뉴가 있음.
    public static boolean isSameNameInIngredients(List<ManagePromptToken.Item> items){
        ManagePromptToken.Item former;
        ManagePromptToken.Item latter;
        
        for(int i = 0; i < items.size(); i++) {
            former = items.get(i);
            for (int j = i+1; j < items.size(); j++) {
                latter = items.get(j);
                if (latter.getItem().equals(former.getItem()))
                    return true;
            }
        }
        return false;
    }
    
    public static void printMenuRepository(){
        for (Menu menu: MENU_Map){
            System.out.println(menu.toString());
        }
    }
    
    public static void dataFileError(){
        System.out.println("치명적오류");
        System.exit(1);
    }
    
}
