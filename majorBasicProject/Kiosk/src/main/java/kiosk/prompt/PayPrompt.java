package kiosk.prompt;

import kiosk.domain.Menu;

public class PayPrompt {

    public PayPrompt(){
        showPrompt();
    }
    private void showPrompt(){

        System.out.println("---------------------------------------");
        System.out.println("총 결제를 할려면 'pay -t'를 입력해주세요.");
        System.out.println("분할 결제를 할려면 'pay -s'를 입력해주세요.");
        //장바구니 출력
        //switch(){};
        System.out.println("---------------------------------------");
        shoppingBasketPrompt();
        System.out.println("---------------------------------------");
        System.out.println("아래의 입력 대기 줄에 주문할 메뉴를 입력해주세요.");
    }

    private void partPrompt(){
        System.out.println("분할결제 시작");
        System.out.println("아래의 입력 대기 줄에 금액을 입력해주세요.");
        System.out.println("---------------------------------------");






    }
}
