package kiosk.domain;

import java.util.HashMap;

public class Menu {
    private final String menu;      //메뉴이름
    private final int price;       //메뉴가격
    private final String beverageStateOption;   //음료상태옵션
    private final HashMap<String, Integer> ingredient  = new HashMap<String, Integer>(); // 재료

    public Menu(String menu, int price, String beverageStateOption, String IgName, int IgNum) {
        this.menu = menu;
        this.price = price;
        this.beverageStateOption = beverageStateOption;
        this.ingredient.put(IgName, IgNum);
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
