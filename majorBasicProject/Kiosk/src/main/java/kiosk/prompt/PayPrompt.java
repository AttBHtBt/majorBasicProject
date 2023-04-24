package kiosk.prompt;
import kiosk.domain.Menu;
import kiosk.prompt.OrderPrompt;
import java.util.Scanner;
public class PayPrompt {
    private int total_pay=10000;//장바구니에서 가져온 결제 예정액,먼저 10000원이라고 가정
    private int total_print=total_pay;//결제예정액 출력
    public PayPrompt(){
        showPrompt();
    }
    private void getTotal(){//장바구니에서 결제예정액 가져오는 함수


    }
    private void showPrompt(){
        while (true) {                                      //무한 루프를 돌면서
            //shoppingBasketPrompt();//장바구니와 결제 예정액 출력
            System.out.print("Payment > ");

            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();
            switch (str) {                                  //입력값이
                case "pay -t":                                 //-t인 경우에
                    totalPay();                                  //결제 예정액 총 결제
                    break;                                  //switch문 탈출
                case "pay -s":                              //-s인 경우에
                    partPay();                           //분할결제 함수 호출
                    break;                                  //switch문 탈출
                case "exit":                                //exit인 경우에
                    orderCall();//주문 프롬프트로 이동
                    break;
                default:
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    break;
            }
        }
    };
    private void totalPay(){//총결제 함수
        System.out.println("결제예정액:"+total_print+"원이 결제되었습니다.");
        orderCall();
    }
    private void partPay(){//결제 예정액을 가져와서 차감시키면서 진행(total=결제 예정액,part=분할결제 금액)
        System.out.println("분할결제 시작");
        while(total_pay>0){//결제 예정액이 다 결제 될때까지
            System.out.println("총 결제예정액:"+total_pay);
            System.out.println("분할결제 할려는 금액을 입력하세요.:");
            try {
                Scanner scanner = new Scanner(System.in);
                int once_Pay = scanner.nextInt();//part=분할결제 금액
                total_pay-=once_Pay;
            }catch (Exception e) {
                System.out.println("(오류) 숫자(양의 정수)를 입력하세요.");
            }
        }
        System.out.println(total_print+"원 결제되었습니다.");
        orderCall();
    }
    private void orderCall(){                                //order 프로프트로 전환
        System.out.println("주문 프롬프토로 돌아갑니다.");
        OrderPrompt OrderPrompt = new OrderPrompt();         //orderPrompt 클래스를 생성한다
    }

}
