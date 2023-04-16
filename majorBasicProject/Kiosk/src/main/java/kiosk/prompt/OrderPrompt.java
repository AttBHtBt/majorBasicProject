package kiosk.prompt;

import kiosk.dataFile.MenuRepository;
import kiosk.domain.Menu;

import java.util.Scanner;
import java.util.List;

public class OrderPrompt {
    private List<Menu> menus = MenuRepository.getMenus();    

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
        for(Menu m : menus){
            System.out.println(m.getMenu()+" "+m.getBeverageStateOption()+" "+m.getPrice()+"원");
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
        return;        
    }
}
