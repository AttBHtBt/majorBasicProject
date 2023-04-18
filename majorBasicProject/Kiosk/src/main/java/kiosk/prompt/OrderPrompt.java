package kiosk.prompt;

import kiosk.dataFile.MenuRepository;
import kiosk.domain.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

public class OrderPrompt {
    private HashMap<String, Menu> menus = MenuRepository.getMenu_Map();

    public OrderPrompt(){                
        OrderController();           
    }

    private String OrderController(){
        while(true){
            showPrompt();
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();

            // if(str.equals("pay")){
            //     payCall();
            // }
            // if(str.equals("manage")){
            //     manageCall();
            // }
            // if(str.equals("exit")){
            //     exitCall();
            // }

            switch(str) {
                case "pay" :
                    payCall();
                    str="pay";
                    break;
                case "manage" :
                    manageCall();
                    str="manage";
                    break;
                case "exit" :
                    exitCall();                    
                    break;
                default :
                    checkCall();
                    break;
            }

        }
    }

    private void showPrompt(){
        System.out.println("Menu");
        System.out.println("메뉴, 가격과 선택할 수 있는 메뉴 옵션입니다.");
        System.out.println("----------------------------------------------------------------");
        for(HashMap.Entry<String, Menu> entry: menus.entrySet()){
            String key = entry.getKey();
            Menu menu = entry.getValue();
            System.out.println(menu.getMenu()+" "+menu.getBeverageStateOption()+" "+menu.getPrice()+"원");
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("핫, 아이스 두가지 선택이 가능한 메뉴는 ICE 선택시 500원이 추가 됩니다.");
        shoppingBasketPrompt();
        System.out.println("----------------------------------------------------------------");
        System.out.println("아래의 입력 대기 줄에 주문할 메뉴를 입력해주세요.");
        System.out.println("최종결제를 원한다면 'pay'를 입력해주세요.");
        //사용자 입력처리
        //종료라면 exitcall
        //관리라면 managecall
        //결제라면 paycall
        //아니라면 checkCall
    }
    private void shoppingBasketPrompt(){

    }
    private void exitCall(){                //석
        System.out.println("exit Done");
        System.exit(0);
        return;
    }
    private void manageCall(){              //석
        System.out.println("manage Done");
        return;
    }
    private void payCall(){                 //석
        System.out.println("pay Done");
        return;
    }
    private void checkCall(){               //홍
        OrderController();
        //구분하기
        /*String ord_line = ;
        int slash_cnt=0,ordline_checkswitch=0;
        for(int j=0;j < ord_line.length();j++){
            if(ord_line[j]=='/')slash_cnt++;
        }
        if (0 < slash_cnt && slash_cnt < 3) {
            if (slash_cnt == 1) { // 구분자 하나일 때 ," /" 추가
                int k = ord_line.indexOf("/");
                ord_line.replace(ord_line.substring(k + 1), " /" + ord_line.substring(k + 1));
            }
            String ordline_menu = null,ordline_state = null,ordline_howmany = null;
            ordline_menu= ord_line.substring(0,ord_line.indexOf("/")-1); //시작 부분 부터 첫 구분자 앞까지

            ordline_state=ord_line.substring(ord_line.indexOf("/")+1 , ord_line.indexOf("/",ord_line.indexOf("/")+1)-1);
            ordline_state.toUpperCase();
            //첫 구분자 뒤부터 두 번째 구분자 앞까지인데 substring, 두 번째 구분자를 찾는 indexOf는 첫 구분자 뒤에서부터 시작, 대문자로 변환

            ordline_howmany=ord_line.substring(ord_line.indexOf("/",ord_line.indexOf("/")+1)+1,ord_line.length()-1);
            //두 번째 구분자 뒤에서 부터 끝까지 substr
            if("0"<=ordline_howmany[0] && ordline_howmany[0]<="9") { //구분자 뒤 첫글자가 숫자인가?
                int p=0;
                for (int h = 0; h < ordline_howmany.length(); h++) {
                    if ("0" <= ordline_howmany[h] && ordline_howmany[h] <= "9") { // 구분자 뒤 숫자의 길이 체크
                        p++;
                    }
                    else break; // 문자 출현 시 break; 12개12 -> '개' break;
                }
                String ordline_howmany_front = null , ordline_howmany_rear = null;
                ordline_howmany_front=ordline_howmany.substring(0,p); // 숫자부분
                ordline_howmany_rear=ordline_howmany.substring(p+1,ordline_howmany.length()-1); // 숫자 뒷부분
                if(0<parseInt(ordline_howmany_front) && (ordline_howmany_rear=="잔"||ordline_howmany_rear=="개"))
                    ordline_checkswitch++; // 숫자 부분이 1이상 인티저이고 숫자 뒷부분이 '잔' 또는 '개'이면
            }
            for(Menu m : menus)
                if(m.getMenu()==ordline_menu && m.getBeverageStateOption()==ordline_state)  ordline_checkswitch++;
        }
        if(ordline_checkswitch>1){
            //옳은 입력임
        }
        else{
            System.out.println("메뉴명, 음료 상태 옵션, 개수 순서대로 공백없이 \'/\'로 구분해 입력해주세요.");
            System.out.println("음료 상태 옵션 선택이 없는 주문(핫초코, 요거트 스무디 등)은");
            System.out.println("메뉴명과 개수만을 공백 없이 \'/\'로 구분해 입력해주세요.\n");
            System.out.println("주문 입력 예시)\n아메리카노/ICE/2잔 또는\n요거트스무디/1개");
            System.out.println("----------------------------------------------------------------");
        }
        showPrompt();*/

        //옳은 입력이라면 inventoryRepository의 __함수 부르기
        //showPrompt();

        //안좋은 입력이면 거절메세지 후 showPrompt();
    }
}
