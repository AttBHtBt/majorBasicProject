package kiosk.prompt;
import kiosk.domain.Menu;
import kiosk.prompt.OrderPrompt;

import java.util.Scanner;

public class PayPrompt {

    private int number;//장바구니에서 가져온 결제 예정액

    private void getNumber(){//장바구니에서 결제예정액 가져오는 함수

        //number=
    }

    public PayPrompt(){

        showPrompt();
    }

    private void showPrompt(){

        while (true) {                                      //무한 루프를 돌면서
            shoppingBasketPrompt();//장바구니와 결제 예정액 출력

            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();

            switch (str) {                                  //입력값이
                case "-t":                                 //-t인 경우에
                    totalPay();                                  //결제 예정액 총 결제
                    status = "exit";                        //status 값을 exit로 바꿔 루프를 돌지 않고 종료하게 만든다
                    break;                                  //switch문 탈출
                case "-s":                              //-s인 경우에
                    partPay();                           //분할결제 함수 호출
                    break;                                  //switch문 탈출
                case "exit":                                //exit인 경우에
                    exitCall();                             //exit함수 호출
                    break;                                  //switch문 탈출
            }
        }
        orderCall();

    };

    private void orderCall(){                                //order 프로프트로 전환
        System.out.println("order prompt");
        OrderPrompt OrderPrompt = new OrderPrompt();         //orderPrompt 클래스를 생성한다
    }

    private void totalPay(){//총결제 함수
        System.out.println("결제예정액:"+number);
        System.out.println("주문 프롬프토로 돌아갑니다.");


    }
    private void partPay(){//결제 예정액을 가져와서 차감시키면서 진행

        //number=결제 예정액
        while(number>0){//
            System.out.println("결제예정액:"+number);
            System.out.println("분할결제 할려는 금액을 입력하세요.:");

            do{
                int n = scan.nextInt;
            }while(n<=0);
            number-=n;

            /*while(!scanner.hasNextInt()||numPart=scanner.nextInt()<0)  {

                System.out.println("양의 정수만 입력가능합니다.다시 입력해주세요.:");
                scanner=new Scanner(System.in);
            } */        //사용자에게 입력을 받는다





        }




    }


}
