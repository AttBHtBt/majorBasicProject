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
            System.out.printf("%s %s원 (ICE/HOT)\n", m.getMenu(), df.format(m.getPrice()));
        }
    }

    private void showMenuIfNoOption(Menu m) {
        DecimalFormat df= new DecimalFormat("###,###"); // 가격 출력 콤마 추가
        if (m.getBeverageStateOption().equals("-")) {
            System.out.printf("%s %s원\n", m.getMenu(), df.format(m.getPrice()));
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
        PayPrompt payPrompt = new PayPrompt();                  //payPrompt 클래스를 생성한다
    }
    private void manageCall(){                                  //manage 함수
        ManagePrompt managePrompt = new ManagePrompt();         //managePrompt 클래스를 생성한다
    }
    private void exitCall(){                                    //exit 함수
        try {
            DataFile.convertMenuRepositoryToCSV();
            DataFile.convertMaterialRepositoryToCSV();
            
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

                available_order_amount=cr.getAvailableOrderAmount(ordline_menu, ordline_state, parseInt(ordline_howmany_front));
                if(available_order_amount >= parseInt(ordline_howmany_front)){
                    for (Menu m: menus){
                        if(m.getMenu().equals(ordline_menu) && m.getBeverageStateOption().equals(ordline_state)) {
                            System.out.println("주문이 장바구니에 추가되었습니다.\n");
                            m.setOrderCount(m.getOrderCount() + parseInt(ordline_howmany_front)); //개수 항목 ++;
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
}
