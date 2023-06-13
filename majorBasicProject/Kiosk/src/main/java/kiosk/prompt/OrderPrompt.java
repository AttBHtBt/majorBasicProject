package kiosk.prompt;

import kiosk.dataFile.*;
import kiosk.domain.Material;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

public class OrderPrompt {
    private ArrayList<Menu> menus = MenuRepository.getMenu_Map();
    private String status = "Good";
    private cartRepository cr= new cartRepository();
    
    private ArrayList<Material> Material_Map_Copy = MaterialRepository.deepCopy(MaterialRepository.getMaterial_Map());

    public OrderPrompt(){                
        OrderController();           
    }

    private void OrderController(){
        while(status.equals("Good")){
            showPrompt();                                   //프롬프트를 보여준다

            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();


            switch (str) {                                  //입력값이
                case "pay":                                 //pay인 경우에
                    System.out.println("장바구니");
                    showshoppingBasket();
                    payCall();                              //pay함수 호출
                    status = "exit";                        //status 값을 exit로 바꿔 루프를 돌지 않고 종료하게 만든다
                    break;                                  //switch문 탈출
                case "admin:admin":                              //manage인 경우 : 주문개수 초기화
                    MenuRepository.setOrderCountToZero();
                    manageCall();                           //manage함수 호출
                    status = "exit";                        //status 값을 exit로 바꿔 루프를 돌지 않고 종료하게 만든다
                    break;                                  //switch문 탈출
                case "exit":                                //exit인 경우에
                    exitCall();                             //exit함수 호출
                    break;                                  //switch문 탈출
                default:
                    checkCall(str);                            //check함수 호출
                    break;                                  //switch문 탈출
            }
        }
    }

    private void showMenuIfOption(Menu m) {
        DecimalFormat df= new DecimalFormat("###,###"); // 가격 출력 콤마 추가
        if (m.getBeverageStateOption().equals("HOT")) {
            System.out.printf("%s %s원 (ICE/HOT) (%d잔 / %d잔 남음)\n", m.getMenu(), df.format(m.getPrice()), getAmountOfStockMenu_ICE(m), getAmountOfStockMenu_HOT(m));
        }
    }

    private void showMenuIfNoOption(Menu m) {
        DecimalFormat df= new DecimalFormat("###,###"); // 가격 출력 콤마 추가
        if (m.getBeverageStateOption().equals("-")) {
            System.out.printf("%s %s원 (%d잔 남음)\n", m.getMenu(), df.format(m.getPrice()), getAmountOfStockMenu_noOption(m));
        }
    }


    private void showPrompt(){
        DecimalFormat df= new DecimalFormat("###,###"); // 가격 출력 콤마 추가

        System.out.println("Menu");
        System.out.println("메뉴, 가격과 선택할 수 있는 메뉴 옵션입니다.");
        System.out.println("----------------------------------------------------------------");

        for (Menu m: menus){
            showMenuIfOption(m);
            showMenuIfNoOption(m);
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("핫, 아이스 두가지 선택이 가능한 메뉴는 ICE 선택시 500원이 추가 됩니다.");
        System.out.println("\n현재 장바구니에 담긴 메뉴는 아래와 같습니다.");
        showshoppingBasket();
        System.out.println("----------------------------------------------------------------");
        System.out.println("아래의 입력 대기 줄에 주문할 메뉴를 입력해주세요.");
        System.out.println("최종 결제를 원한다면 'pay'를 입력해주세요.");
        System.out.print("Kiosk >");
    }
    private void showshoppingBasket(){
        DecimalFormat df= new DecimalFormat("###,###"); // 가격 출력 콤마 추가
        for (Menu m: menus){ // 장바구니 추가
            if(m.getOrderCount()>0)
                System.out.println(m.getMenu()+"/"+m.getBeverageStateOption()+"/"+m.getOrderCount()+"잔/");
        }
    }
    private void payCall(){                                     //pay 함수
        MemberPrompt mp = new MemberPrompt();                  //memberPrompt 클래스를 생성한다
        mp.run();
    }
    private void manageCall(){                                  //manage 함수
        ManagePrompt managePrompt = new ManagePrompt();         //managePrompt 클래스를 생성한다
    }
    private void exitCall(){                                    //exit 함수
        try {
            DataFile.convertMenuRepositoryToCSV();
            DataFile.convertMaterialRepositoryToCSV();
            DataFile.convertMemberRepositoryToCSV();
            
            boolean regenerate = MenuRepository.isMenuFilevalid(DataFile.DATAFILEDIRECTORY + DataFile.menuFileName) ||
                MaterialRepository.isIngredientFileValid(DataFile.DATAFILEDIRECTORY + DataFile.menuFileName) ||
                PwdRepository.isAdminFileValid(new Scanner(new File(DataFile.DATAFILEDIRECTORY + DataFile.adminFileName)));
            if (regenerate)
                DataFile.regenerate();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);                                         //프로그램 전체 종료
    }              
    private void checkCall(String s){               //홍
            //구분하기
            String ord_line = s;
            String ordline_menu = "",ordline_state = "",ordline_howmany = " ";
            String ordline_howmany_front = "" , ordline_howmany_rear = "";
            int slash_cnt=0,ordline_checkswitch=0,available_order_amount=0;

            for(int j=0;j < ord_line.length();j++) {
                if (ord_line.charAt(j) == '/') slash_cnt++;
            }
            if(slash_cnt==0){
                System.out.println("존재하지 않는 명령어입니다.");
                return ;
            }
            String []splitted = ord_line.split("/");

            if(slash_cnt==1 && splitted.length==2) {
                ordline_menu=splitted[0];
                ordline_state="-";
                ordline_howmany=splitted[1];
            }
            else if(slash_cnt==2 && splitted.length==3) {
                ordline_menu=splitted[0];
                ordline_state=splitted[1];
                if(ordline_state.equals("-"))ordline_state="asd";
                ordline_state=ordline_state.toUpperCase();
                ordline_howmany=splitted[2];
            }
            for (Menu m: menus){
                if(m.getMenu().equals(ordline_menu) && m.getBeverageStateOption().equals(ordline_state))  ordline_checkswitch++;
            }

            if ('0' <= ordline_howmany.charAt(0) && ordline_howmany.charAt(0) <= '9') { //구분자 뒤 첫글자가 숫자인가?
                int p = 0;
                for (int h = 0; h < ordline_howmany.length(); h++) {
                    if ('0' <= ordline_howmany.charAt(h) && ordline_howmany.charAt(h) <= '9') { // 구분자 뒤 숫자의 길이 체크
                        p++;
                    } else break; // 문자 출현 시 break; 12개12 -> '개' break;
                }
                ordline_howmany_front = ordline_howmany.substring(0, p); // 숫자부분
                ordline_howmany_rear = ordline_howmany.substring(p, ordline_howmany.length()); // 숫자 뒷부분
                
                //숫자 범위를넘어가면
                if (!Admin.isMenuPriceSyntaxValid(ordline_howmany_front) || !Admin.isMenuPriceSemanticsValid(ordline_howmany_front))
                    ordline_howmany_front = "0";
                
                if (0 < parseInt(ordline_howmany_front) && ((ordline_howmany_rear.equals("잔") || ordline_howmany_rear.equals("개")) || ordline_howmany_rear.equals("")))
                    ordline_checkswitch++; // 숫자 부분이 1이상 인티저이고 숫자 뒷부분이 "잔" 또는 "개" 또는 ""이면
            }


            if(ordline_checkswitch>1){
                /*System.out.println("옳은 입력임");
                System.out.println("메뉴:"+ordline_menu);
                System.out.println("옵션:"+ordline_state);
                System.out.println("개수:"+ordline_howmany_front);
                System.out.println(ordline_howmany_rear);    //입력 확인용 코드*/

                available_order_amount=cr.getAvailableOrderAmount(ordline_menu, ordline_state, parseInt(ordline_howmany_front), Material_Map_Copy);
                if(available_order_amount >= parseInt(ordline_howmany_front)){
                    for (Menu m: menus){
                        if(m.getMenu().equals(ordline_menu) && m.getBeverageStateOption().equals(ordline_state)) {
                            System.out.println("주문이 장바구니에 추가되었습니다.\n");
                            m.setOrderCount(m.getOrderCount() + parseInt(ordline_howmany_front)); //개수 항목 ++;
                            
                            //INGREDIENT복사
//                            주문한 개수에 따라 temp재고를 변경해주어야함.
                            changeCopiedMaterialByOrderAmount(m, parseInt(ordline_howmany_front));
                        }
                    }
                }
                else{
                    System.out.println("재고 문제로 현재 해당 메뉴는 "+available_order_amount+"잔까지 주문이 가능합니다.\n");
                }

            }
            else{
                /*System.out.println(ordline_checkswitch);
                System.out.println("메뉴:"+ordline_menu);
                System.out.println("옵션:"+ordline_state);
                System.out.println("개수:"+ordline_howmany_front);
                System.out.println(ordline_howmany_rear);*/    //입력 확인용 코드
                System.out.println("메뉴명, 음료 상태 옵션, 개수 순서대로 공백없이 \'/\'로 구분해 입력해주세요.");
                System.out.println("음료 상태 옵션 선택이 없는 주문(핫초코, 요거트 스무디 등)은");
                System.out.println("메뉴명과 개수만을 공백 없이 \'/\'로 구분해 입력해주세요.\n");
                System.out.println("주문 입력 예시)\n아메리카노/ICE/2잔 또는\n요거트스무디/1개\n");
                System.out.println("----------------------------------------------------------------");
            }
    }
    
    private int getAmountOfStockMenu_noOption(Menu m) {
        ArrayList<Menu.Ingredient> ingredients = null;
        ArrayList<Material> materials = Material_Map_Copy;
//        ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
        int amount = Integer.MAX_VALUE;

        ingredients = m.getIngredient();
        
        for (Menu.Ingredient ingredient : ingredients) {
            boolean isExists = false;
            for (Material material : materials) {  
                if (ingredient.getName().equals(material.getName())){
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                return (0);
            }
        }
        
        for (Menu.Ingredient ingredient : ingredients) {
            for (Material material : materials) {
                if (ingredient.getName().equals(material.getName())) {
                    int count = material.getAmount() / ingredient.getNum();
                    amount = Integer.min(amount, count);
                    break;
                }
            }
        }
        return amount;
    }


    private int getAmountOfStockMenu_ICE(Menu m) {
        ArrayList<Menu.Ingredient> ingredients_ice = null;
        ArrayList<Material> materials = Material_Map_Copy;
//        ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
        String menuName = m.getMenu();

        boolean isIceExists = false;

        int amount_ice = Integer.MAX_VALUE;

        //ICE or HOT?

        //HOT
        for (Menu menu : menus) {
            if (menuName.equals(menu.getMenu()) && menu.getBeverageStateOption().equals("ICE")) {
                isIceExists= true;
                ingredients_ice= menu.getIngredient();
                for (Menu.Ingredient ingredient : ingredients_ice) {
                    boolean isIngredientExists = false;
                    for (Material material : materials) {
                        if (ingredient.getName().equals(material.getName())) {
                            isIngredientExists = true;
                            int count = material.getAmount() / ingredient.getNum();
                            amount_ice= Integer.min(amount_ice, count);
                            break;
                        }
                    }
                    if (!isIngredientExists) {
                        return 0;
                    }
                }
                break;
            }
        }
        return amount_ice;
    }


    private int getAmountOfStockMenu_HOT(Menu m) {
        ArrayList<Menu.Ingredient> ingredients_hot = null;
        ArrayList<Material> materials = Material_Map_Copy;
//        ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
        String menuName = m.getMenu();

        boolean isHotExists = false;

        int amount_hot = Integer.MAX_VALUE;

        //ICE or HOT?

        //HOT
        for (Menu menu : menus) {
            if (menuName.equals(menu.getMenu()) && menu.getBeverageStateOption().equals("HOT")) {
                isHotExists= true;
                ingredients_hot= menu.getIngredient();
                for (Menu.Ingredient ingredient : ingredients_hot) {
                    boolean isIngredientExists = false;
                    for (Material material : materials) {
                        if (ingredient.getName().equals(material.getName())) {
                            isIngredientExists = true;
                            int count = material.getAmount() / ingredient.getNum();
                            amount_hot= Integer.min(amount_hot, count);
                            break;
                        }
                    }
                    if (!isIngredientExists) {
                        return 0;
                    }
                }
                break;
            }
        }
        return amount_hot;
    }
    
    private void changeCopiedMaterialByOrderAmount(Menu m, int amountOfInput) {
        
        ArrayList<Menu.Ingredient> ingredients = m.getIngredient();
        
        for (Menu.Ingredient ingredient : ingredients) {
            for (Material material : Material_Map_Copy) {
                if (ingredient.getName().equals(material.getName())) {
                    material.setAmount(material.getAmount() - ingredient.getNum() * amountOfInput);
                    break;
                }
            }
        }
    }
    
    
    
    
    
    private int getAmountOfStockMenu_Option(Menu m) {
        ArrayList<Menu.Ingredient> ingredients_hot = null;
        ArrayList<Menu.Ingredient> ingredients_ice = null;
        ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
        String menuName = m.getMenu();
        
        boolean isIceExists = false;
        boolean isHotExists = false;
        
        
        int amount_ice = Integer.MAX_VALUE;
        int amount_hot = Integer.MAX_VALUE;
        
        
        //ICE or HOT?

        //HOT
        for (Menu menu : menus) {
            if (menuName.equals(menu.getMenu()) && menu.getBeverageStateOption().equals("HOT")) {
                isHotExists = true;
                ingredients_hot = menu.getIngredient();
                for (Menu.Ingredient ingredient : ingredients_hot) {
                    boolean isIngredientExists = false;
                    for (Material material : materials) {
                        if (ingredient.getName().equals(material.getName())) {
                            isIngredientExists = true;
                            int count = material.getAmount() / ingredient.getNum();
                            amount_hot = Integer.min(amount_hot, count);
                            break;
                        }
                    }
                    if (!isIngredientExists) {
                        amount_hot = 0;
                        break;
                    }
                }
                break;
            }
        }

        for (Menu menu : menus) {
            if (menuName.equals(menu.getMenu()) && menu.getBeverageStateOption().equals("ICE")) {
                isIceExists = true;
                ingredients_ice = menu.getIngredient();
                for (Menu.Ingredient ingredient : ingredients_ice) {
                    boolean isIngredientExists = false;
                    for (Material material : materials) {
                        if (ingredient.getName().equals(material.getName())) {
                            isIngredientExists = true;
                            int count = material.getAmount() / ingredient.getNum();
                            amount_ice = Integer.min(amount_ice, count);
                            break;
                        }
                    }
                    if (!isIngredientExists)
                        return (0);
                }
                break;
            }
        }
        
        if (isIceExists && isHotExists) {
            return amount_hot + amount_ice;
        }
        else if (isIceExists && !isHotExists || !isIceExists && isHotExists) {
            return isIceExists ? amount_ice : amount_hot;
        }
        else if (!(isIceExists && isHotExists))
            return (0);
        else    //default
            return (0);
    }
    
}
