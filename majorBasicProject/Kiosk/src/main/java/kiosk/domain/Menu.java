package kiosk.domain;

public class Menu {
    private final String menu;                  //메뉴이름
    private final String price;                 //메뉴가격
    private final String beverageStateOption;   //음료상태옵션


    public Menu(String menu,String price, String beverageStateOption) {
        this.menu = menu;
        this.price=price;
        this.beverageStateOption=beverageStateOption;
    }
    public String getMenu() {return menu;}
    public String getPrice() {return price;}
    public String getBeverageStateOption() {return  beverageStateOption;}
}
