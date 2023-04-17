package kiosk.dataFile;

import kiosk.domain.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuRepository {

    private static final HashMap<String, Menu> MENU_Map = new HashMap<>();
    private static final ArrayList<ArrayList<String>> dynamicArray = new ArrayList<ArrayList<String>>();
    int a;
    public MenuRepository(){
    }
    public void makeMenu(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            while(scan.hasNext()){
                String str = scan.nextLine();
                System.out.println(str);
                String[] lineArr = str.split(",");
                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...

                System.out.println("point1");


                //dynamicArray.add(new ArrayList<String>());

                System.out.println("point 2");

                for(int i = 3; i< lineArr.length; i++){
                    String[] array = lineArr[i].split(":");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(array[0].trim());
                    list.add(array[1].trim());
                    dynamicArray.add(list);
                }
                //2차원 arrayList에 재료이름과 재료 수량을 넣는다.
                System.out.println("Mission Complete");

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FileNotFoundException");
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
        }
    }

    private static final List<Menu> menus= new ArrayList<>(
//            Arrays.asList(new Menu("아메리카노","2,500","ICE"),
//                    new Menu("21아메리카노","2,000","HOT"),
//                    new Menu("카페라떼","3,000","ICE"),
//                    new Menu("카페라떼","2,500","HOT")
//            )
    );


    public static List<Menu> getMenus(){
        return menus;
    }



    }
