package kiosk.domain;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String menu;      //메뉴이름
    private int price;       //메뉴가격
    private String beverageStateOption;   //음료상태옵션
//    private HashMap<String, Integer> ingredient  = new HashMap<>(); // 재료
    private ArrayList<Ingredient> ingredient = new ArrayList<>();
    private int orderCount = 0;

    public class Ingredient {
        public String name;
        public int num;

        public Ingredient(String name, int num) {
            this.name = name;
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

    }
    public Menu(String menu, String price, String beverageStateOption, ArrayList<ArrayList<String>> dynamicArray) {
        this.menu = menu;
        this.price = Integer.parseInt(price);
        this.beverageStateOption = beverageStateOption;
        for(int j = 0; j<dynamicArray.size(); j++){
            ingredient.add(new Ingredient(dynamicArray.get(j).get(0), Integer.parseInt(dynamicArray.get(j).get(1))));
//            ingredient.put(dynamicArray.get(j).get(0), Integer.parseInt(dynamicArray.get(j).get(1)));
        }
    }

    public Menu(String menu, int price, String beverageStateOption, List<String> unDividedIngredients) {
        this.menu = menu;
        this.price = price;
        this.beverageStateOption = beverageStateOption;
        for(int j = 0; j<unDividedIngredients.size(); j++){
            String str = unDividedIngredients.get(j);
            String[] divided = str.split(":");
            ingredient.add(new Ingredient(divided[0], Integer.parseInt(divided[1])));
//            ingredient.put(divided[0], Integer.parseInt(divided[1]));
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

//    public HashMap<String, Integer> getIngredient() {
//        return ingredient;
//    }
    
    public ArrayList<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBeverageStateOption(String beverageStateOption) {
        this.beverageStateOption = beverageStateOption;
    }

//    public void setIngredient(HashMap<String, Integer> ingredient) {
//        this.ingredient = ingredient;
//    }


    public void setIngredient(ArrayList<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

}
