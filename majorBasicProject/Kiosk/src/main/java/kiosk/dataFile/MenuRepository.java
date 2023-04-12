package kiosk.dataFile;

import kiosk.domain.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuRepository {

    private static final List<Menu> menus= new ArrayList<>(
            Arrays.asList(new Menu("아메리카노","2,500","ICE"),
                    new Menu("21아메리카노","2,000","HOT"),
                    new Menu("카페라떼","3,000","ICE"),
                    new Menu("카페라떼","2,500","HOT")
            )
    );

    private MenuRepository(){

    }

    public static List<Menu> getMenus(){
        return menus;
    }



}
