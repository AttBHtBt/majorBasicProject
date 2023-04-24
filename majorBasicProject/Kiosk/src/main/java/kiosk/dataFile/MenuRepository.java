package kiosk.dataFile;

import kiosk.domain.ManagePromptToken;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/*
* 파일 무결성 검사 : kiosk.manager.Admin의 *SyntaxValid 함수들 사용해서 각각외 요소들 확인 가능.
 */

public class MenuRepository {

    private static final ArrayList<Menu> MENU_Map = new ArrayList<>();
    private static final ArrayList<Menu> forValidationTest = new ArrayList<>();
    public MenuRepository(){
    }
    public void makeMenu(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            Boolean check = true;
            while(scan.hasNext() && check){
                String str = scan.nextLine();
                String[] lineArr = str.split(",");
                if (!(lineArr.length >=4)) {
                    check = false;
                    break;
                }
                
                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...
                check = check && Admin.isMenuPriceSyntaxValid(lineArr[1].trim())
                        && Admin.isMenuPriceSemanticsValid(lineArr[1].trim())
                        && Admin.isMenuOptionSyntaxValid(lineArr[2].trim());
                

                for(int j = 3; j< lineArr.length; j++){
                    check = check && Admin.isRecipieSyntaxValid(lineArr[j].trim())
                            && Admin.isRecipieSemanticsValid(lineArr[j].trim());
                    if(!check)
                        break;
                }

                ArrayList<ArrayList<String>> dynamicArray = new ArrayList<>();
                for(int i = 3; i< lineArr.length; i++){

                    String[] array = lineArr[i].split(":");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(array[0].trim());
                    list.add(array[1].trim());
                    dynamicArray.add(list);
                }
                //2차원 arrayList에 재료이름과 재료 수량을 넣는다.
            }
            if (!check)
                DataFile.isMenuFileValid = false;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FileNotFoundException: FileName" + fileName);
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
        }
    }
    
    
    //중복아님.
    public static boolean isMenuFilevalid(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            Boolean check = true;
            while(scan.hasNext() && check){
                String str = scan.nextLine();
                String[] lineArr = str.split(",");
                if (!(lineArr.length >=4)) {
                    check = false;
                    break;
                }

                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...
                check = check && Admin.isMenuPriceSyntaxValid(lineArr[1].trim())
                        && Admin.isMenuPriceSemanticsValid(lineArr[1].trim())
                        && Admin.isMenuOptionSyntaxValid(lineArr[2].trim());

                
                for(int j = 3; j< lineArr.length; j++){
                    check = check && Admin.isRecipieSyntaxValid(lineArr[j].trim())
                            && Admin.isRecipieSemanticsValid(lineArr[j].trim());
                    if(!check)
                        break;
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
            //중복 메뉴있는지 찾고
            //중복 레시피있는지 찾고
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
            if (!check)
                DataFile.isMenuFileValid = false;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FileNotFoundException: FileName" + fileName);
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
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
        //findKey=name+option
        for (Menu menu: MENU_Map) {
            if (menu.getMenu().equals(name) && menu.getBeverageStateOption().equals(option)){
                MENU_Map.remove(menu);
                return ;
            }
        }
    }

    public static void regenerateMenuFile(){
        // 파일에 문제 있으면 여기서 regenerate 할 예정.
        DataFile.regenerateMenuCSV();
    }

    public static boolean isMenuNameinRepository(String menuName, String orderOption){
        for (Menu menu: MENU_Map) {
            if (menu.getMenu().equals(menuName) && menu.getBeverageStateOption().equals(orderOption)){
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
    
}
