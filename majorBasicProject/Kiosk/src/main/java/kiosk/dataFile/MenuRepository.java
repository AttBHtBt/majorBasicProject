package kiosk.dataFile;

import kiosk.domain.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuRepository {

    private static final HashMap<String, Menu> MENU_Map = new HashMap<>();
    private static final ArrayList<ArrayList<String>> dynamicArray = new ArrayList<ArrayList<String>>();
    int a;
    public MenuRepository(){
    }
    void makeMenu(String fileName){
        File csv = new File(fileName);
        //원래 fileName 자리에 filePath가 들어가야 하는 거라서 이게 체크 해봐야 함
        BufferedReader br = null;
        String line = "";

        try{
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null){
                String[] lineArr = line.split(",");
                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...

                String[] array = new String[2];
                //dynamicArray.add(new ArrayList<String>());

                ArrayList<String> list = new ArrayList<>();
                for(int i = 3; i< lineArr.length; i++){
                    array = lineArr[i].split(":");
                    dynamicArray.get(i - 3).add(array[0]);
                    dynamicArray.get(i - 3).add(array[1]);
                }
                //2차원 arrayList에 재료이름과 재료 수량을 넣는다.

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch(java.io.IOException e){
            System.out.println("java.io.IOException");
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
