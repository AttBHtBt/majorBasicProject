package kiosk.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    private final String menu;      //메뉴이름
    private final int price;       //메뉴가격
    private final String beverageStateOption;   //음료상태옵션
    private final HashMap<String, Integer> ingredient  = new HashMap<>(); // 재료

    public Menu(String menu, String price, String beverageStateOption, ArrayList<ArrayList<String>> dynamicArray) {
        this.menu = menu;
        this.price = Integer.parseInt(price);
        this.beverageStateOption = beverageStateOption;
        for(int j = 0; j<dynamicArray.size(); j++){
            ingredient.put(dynamicArray.get(j).get(0), Integer.parseInt(dynamicArray.get(j).get(1)));
        }
    }

    public String getMenu() {
        return menu;
    }

    public int getPrice() {
        return price;
    }

    public String getBeverageStateOption() {
        return beverageStateOption;
    }

    public HashMap<String, Integer> getIngredient() {
        return ingredient;
    }
}
