package kiosk.prompt;
import kiosk.domain.Menu;
import kiosk.prompt.OrderPrompt;

import java.util.Scanner;


class MinusException extends Exception {}
public class PayPrompt {

    private int number=10000;//장바구니에서 가져온 결제 예정액,먼저 10000원이라고 가정

    private void getNumber(){//장바구니에서 결제예정액 가져오는 함수

        //number=
    }

    public PayPrompt(){

        showPrompt();
    }




    private void showPrompt(){

        while (true) {                                      //무한 루프를 돌면서
            //shoppingBasketPrompt();//장바구니와 결제 예정액 출력
            System.out.println("명령어 입력해주세요.");
            System.out.println("-t:총결제,-s:분할결제,exit");

            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();

            switch (str) {                                  //입력값이
                case "-t":                                 //-t인 경우에
                    totalPay();                                  //결제 예정액 총 결제
                                           //status 값을 exit로 바꿔 루프를 돌지 않고 종료하게 만든다
                    break;                                  //switch문 탈출
                case "-s":                              //-s인 경우에
                    partPay();                           //분할결제 함수 호출
                    break;                                  //switch문 탈출
                case "exit":                                //exit인 경우에

                    orderCall();//주문 프롬프트로 이동
                    break;                                  //switch문 탈출
            }
        }


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

            try {

                Scanner scan = new Scanner(System.in);
                int n = scan.nextInt();

                if(n<0){
                    throw new MinusException();
                }
                number-=n;
                //System.out.println("결제 예정액:"+number);

            }

            catch(MinusException m){
                System.out.println("양수를 입력하시오!");
            }
            catch(NumberFormatException z) {
                System.out.println("숫자(양의 정수)를 입력하세요");

            }




        }




    }


}
