package kiosk.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagePromptToken {

    private String mainCommand;
    private String optionCommand;
    private String menu;
    private String menuOption;
    private int price;

    // 재료(단위):사용 수량 -> 재료 + 사용 수량으로 분리.
    private List<Item> items = new ArrayList<>();

    public ManagePromptToken() {
    }

    public ManagePromptToken(String mainCommand, String optionCommand, String menu, String menuOption, String price, List<String> items) {
        this.mainCommand = mainCommand;
        this.optionCommand = optionCommand;
        this.menu = menu;
        this.menuOption = menuOption;
        this.price = Integer.parseInt(price);

        generateItems(items);
    }

    public ManagePromptToken(String mainCommand, String optionCommand, String menu, String menuOption) {
        this.mainCommand = mainCommand;
        this.optionCommand = optionCommand;
        this.menu = menu;
        this.menuOption = menuOption;
    }

    public ManagePromptToken(String mainCommand, String optionCommand, List<String> items) {
        this.mainCommand = mainCommand;
        this.optionCommand = optionCommand;
        generateItems(items);
    }

    private void generateItems(List<String> items){
        String i;
        double quantity;
        for (String item: items){
            String[] tokens = item.split(":");
            if (tokens.length == 0){
                this.items.add(new Item(item, "123", 0));
            }
            if (tokens.length == 1){
                this.items.add(new Item(item, tokens[0], 0));
            }
            if (tokens.length == 2){
                this.items.add(
                        new Item(item, tokens[0], Integer.parseInt(tokens[1])));
            }
        }
    }

    //재료(단위):사용수량 -> String 재료(단위), float 사용수량
    public class Item{

        protected String originalString;
        protected String item;
        protected int quantity;
        public Item(String originalString, String item, int quantity) {
            this.originalString = originalString;
            this.item = item;
            this.quantity = quantity;
        }


        public String getOriginalString() {
            return originalString;
        }

        public void setOriginalString(String originalString) {
            this.originalString = originalString;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    };

    public String getMainCommand() {
        return mainCommand;
    }

    public void setMainCommand(String mainCommand) {
        this.mainCommand = mainCommand;
    }

    public String getOptionCommand() {
        return optionCommand;
    }

    public void setOptionCommand(String optionCommand) {
        this.optionCommand = optionCommand;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
        this.menuOption = menuOption;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

//    @Override
//    public String toString() {
//
//        String printItems = "";
//        for (Item item: items) {
//            printItems = printItems + "\t" + item.item + ":" + item.quantity;
//        }
//
//        return "ManagePromptToken{" +
//                "mainCommand='" + mainCommand + '\'' +
//                ", optionCommand='" + optionCommand + '\'' +
//                ", menu='" + menu + '\'' +
//                ", menuOption='" + menuOption + '\'' +
//                ", price=" + price +
//                ", items=" + printItems +
//                '}';
//
//    }
}
