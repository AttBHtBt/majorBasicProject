package kiosk.dataFile;

import kiosk.domain.Menu;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/*
* 파일 무결성 검사 : kiosk.manager.Admin의 *SyntaxValid 함수들 사용해서 각각외 요소들 확인 가능.
 */


public class MenuRepository {

    private static final HashMap<String, Menu> MENU_Map = new HashMap<>();

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
                this.addMenu(new Menu(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim(), dynamicArray));
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

    public static HashMap<String, Menu> getMenu_Map(){
        return MENU_Map;
    }
    public static void addMenu(Menu menu){// 중복 검사는 아직 안 함
        String key = menu.getMenu()+menu.getBeverageStateOption();
        MENU_Map.put(key, menu);
    }

    public static void deleteMenu(String name, String option){
        String findKey = name + option;
        MENU_Map.remove(findKey);
    }

    public static void regenerateMenuFile(){
        // 파일에 문제 있으면 여기서 regenerate 할 예정.
        DataFile.regenerateMenuCSV();
    }



    public static boolean isMenuNameinRepository(String menuName, String orderOption){
        if (MENU_Map.get(menuName+orderOption) == null)
            return false;
        return true;
    }
}
