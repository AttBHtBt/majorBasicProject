package kiosk.prompt;

import kiosk.dataFile.*;
import kiosk.domain.Material;
import kiosk.domain.Menu;

import java.io.File;
import java.io.FileNotFoundException;
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


    private void showPrompt(){
        System.out.println("Menu");
        System.out.println("메뉴, 가격과 선택할 수 있는 메뉴 옵션입니다.");
        System.out.println("----------------------------------------------------------------");

        for (Menu m: menus){
            System.out.println(m.getMenu()+" "+m.getBeverageStateOption()+" "+m.getPrice()+"원");
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("핫, 아이스 두가지 선택이 가능한 메뉴는 ICE 선택시 500원이 추가 됩니다.");
        System.out.println("\n현재 장바구니에 담긴 메뉴는 아래와 같습니다.");
        showshoppingBasket();
        System.out.println("----------------------------------------------------------------");
        System.out.println("아래의 입력 대기 줄에 주문할 메뉴를 입력해주세요.");
        System.out.println("최종결제를 원한다면 'pay'를 입력해주세요.");
        System.out.print("order > ");
    }
    private void showshoppingBasket(){                        //장바구니 호출

    }
    private void payCall(){                                     //pay 함수
        PayPrompt payPrompt = new PayPrompt();                  //payPrompt 클래스를 생성한다
    }
    private void manageCall(){                                  //manage 함수
        ManagePrompt managePrompt = new ManagePrompt();         //managePrompt 클래스를 생성한다
    }
    private void exitCall(){                                    //exit 함수
        try {
            boolean regenerate = MenuRepository.isMenuFilevalid(DataFile.DATAFILEDIRECTORY + DataFile.menuFileName) ||
                MaterialRepository.isIngredientFileValid(DataFile.DATAFILEDIRECTORY + DataFile.menuFileName) ||
                PwdRepository.isAdminFileValid(new Scanner(new File(DataFile.DATAFILEDIRECTORY + DataFile.adminFileName)));
            
            if (regenerate)
                DataFile.regenerate();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        MenuRepository.regenerateMenuFile();
            
        System.exit(0);                                         //프로그램 전체 종료
    }              
    private void checkCall(String s){               //홍
            //구분하기
            String ord_line = s;
            String ordline_menu = null,ordline_state = null,ordline_howmany = null;
            String ordline_howmany_front = null , ordline_howmany_rear = null;
            int slash_cnt=0,ordline_checkswitch=0,available_order_amount=0;

            for(int j=0;j < ord_line.length();j++){
                if(ord_line.charAt(j)=='/')slash_cnt++;
            }
            if (0 < slash_cnt && slash_cnt < 3) {
                if (slash_cnt == 1) { // 구분자 하나일 때 ,첫 구분자 위치 뒤" /" 추가
                    int k = ord_line.indexOf("/");
                    ord_line=ord_line.replace(ord_line.substring(k+1,ord_line.length()), "-/" + ord_line.substring(k+1,ord_line.length()));
                }

                String a[]=ord_line.split("/");
                ordline_menu= a[0]; //시작 부분 부터 첫 구분자 앞까지

                ordline_state=a[1];
                ordline_state=ordline_state.toUpperCase();
                //첫 구분자 뒤부터 두 번째 구분자 앞까지, 대문자 변환

                ordline_howmany=a[2];
                //두 번째 구분자 뒤
                if('0'<=ordline_howmany.charAt(0) && ordline_howmany.charAt(0)<='9') { //구분자 뒤 첫글자가 숫자인가?
                    int p=0;
                    for (int h = 0; h < ordline_howmany.length(); h++) {
                        if ('0' <= ordline_howmany.charAt(h) && ordline_howmany.charAt(h) <= '9') { // 구분자 뒤 숫자의 길이 체크
                            p++;
                        }
                        else break; // 문자 출현 시 break; 12개12 -> '개' break;
                    }
                    ordline_howmany_front=ordline_howmany.substring(0,p); // 숫자부분
                    ordline_howmany_rear=ordline_howmany.substring(p,ordline_howmany.length()); // 숫자 뒷부분
                    if(0<parseInt(ordline_howmany_front) && ((ordline_howmany_rear.equals("잔") || ordline_howmany_rear.equals("개")) || ordline_howmany_rear.equals("")))
                        ordline_checkswitch++; // 숫자 부분이 1이상 인티저이고 숫자 뒷부분이 "잔" 또는 "개" 또는 ""이면
                }
                for (Menu m: menus){
                    if(m.getMenu().equals(ordline_menu) && m.getBeverageStateOption().equals(ordline_state))  ordline_checkswitch++;
                }
            }
            if(ordline_checkswitch>=1){
                System.out.println("옳은 입력임");
                System.out.println("메뉴:"+ordline_menu);
                System.out.println("옵션:"+ordline_state);
                System.out.println("개수:"+ordline_howmany_front);

                available_order_amount=cr.getAvailableOrderAmount(ordline_menu, ordline_state,parseInt(ordline_howmany_front));
                if(available_order_amount >= parseInt(ordline_howmany_front)){
                    for (Menu m: menus){
                        if(m.getMenu().equals(ordline_menu) && m.getBeverageStateOption().equals(ordline_state))
                            m.setOrderCount(m.getOrderCount() + parseInt(ordline_howmany_front)); //개수 항목 ++;
                    }
                }
                else{
                    System.out.println("재고 문제로 현재 해당 메뉴는 "+available_order_amount+"잔까지 주문이 가능합니다.");
                }

            }
            else{
                System.out.println("메뉴명, 음료 상태 옵션, 개수 순서대로 공백없이 \'/\'로 구분해 입력해주세요.");
                System.out.println("음료 상태 옵션 선택이 없는 주문(핫초코, 요거트 스무디 등)은");
                System.out.println("메뉴명과 개수만을 공백 없이 \'/\'로 구분해 입력해주세요.\n");
                System.out.println("주문 입력 예시)\n아메리카노/ICE/2잔 또는\n요거트스무디/1개");
                System.out.println("----------------------------------------------------------------");
            }
        showPrompt();
    }
}
