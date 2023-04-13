package kiosk.dataFile;

import kiosk.domain.Menu;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class MenuRepository {
    private static final HashMap<String, Menu> MenuMap= new HashMap<String, Menu>();

    void makeMenu(String fileName){

    }

    private MenuRepository(){

    }

    public static List<Menu> getMenus(){
        return menus;
    }



}
