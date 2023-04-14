package kiosk.dataFile;

import kiosk.domain.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class MenuRepository {

    private static final HashMap<String, Menu> MENU_Map = new HashMap<>();

    void makeMenu(String fileName){
        File csv = new File("fileName");
        //원래 fileName 자리에 filePath가 들어가야 하는 거라서 이게 체크 해봐야 함
        BufferedReader br = null;
        String line = "";

        try{
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null){
                String[] lineArr = line.split(",");
                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...
                int[][] recipe = new
                int i = 3;
                while(lineArr[i] != null){

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final List<Menu> menus= new ArrayList<>(
//            Arrays.asList(new Menu("아메리카노","2,500","ICE"),
//                    new Menu("21아메리카노","2,000","HOT"),
//                    new Menu("카페라떼","3,000","ICE"),
//                    new Menu("카페라떼","2,500","HOT")
//            )
    );


    private MenuRepository(){

    }

    public static List<Menu> getMenus(){
        return menus;
    }



}
