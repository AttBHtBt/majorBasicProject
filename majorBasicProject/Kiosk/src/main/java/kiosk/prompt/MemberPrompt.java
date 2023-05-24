package kiosk.prompt;

import kiosk.dataFile.DataFile;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.dataFile.PwdRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MemberPrompt {
    private boolean stat= true;
    public MemberPrompt(){
        MemberController();
    }
    private void MemberController() {
        while(stat){
            System.out.println("결제 전 회원 정보를 입력해주세요.");
            System.out.println("회원 정보 입력 후 결제 단계로 넘어갑니다.");
            System.out.print("Member >");

            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();

            switch (str) {
                case "guest":
                    payCall();
                    stat=false;
                    break;

                case "login":
                    loginCall();
                    stat=false;
                    break;

                case "signup":
                    signupCall();
                    stat=false;
                    break;

                case "exit":                                //exit인 경우에 : 주문개수 초기화
                    MenuRepository.setOrderCountToZero();
                    exitCall();
                    stat=false;
                    break;

                default:
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
            }
        }
    }
    private void payCall(){
        PayPrompt payPrompt = new PayPrompt();
    }
    private void exitCall(){
        OrderPrompt orderPrompt = new OrderPrompt();
    }
    private void loginCall(){

    }
    private void signupCall(){

    }
}
