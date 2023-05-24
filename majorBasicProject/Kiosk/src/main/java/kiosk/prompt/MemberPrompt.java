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
    public static int memberNumber=getMemberNumber();
    public MemberPrompt(){
        MemberController();
    }
    private void MemberController() {
        System.out.println("결제 전 회원 정보를 입력해주세요.");
        System.out.println("회원 정보 입력 후 결제 단계로 넘어갑니다.");
        System.out.print("Member >");

        Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
        String str = sc.nextLine();

        while(stat){
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
        /*
        1. Scanner로 아이디와 비밀번호 입력 받기
        2. isIdExists() (false면 1.로)
        3. checkPassword() (false면 1.로)
        4. payCall()
         */
    }
    private void signupCall(){
        /*
        1. Scanner로 아이디 입력 받음.
        2. checkMemberIdForm() (false면 1.로)
        3. Scanner로 비밀번호 입력 받음.
        4. checkMemberPasswordForm() (false면 3. 으로)
        5. Scanner로 비밀번호 다시 입력 받음.
        6. eqauls() (false면 5로)
        7. memberNumber++
        8. insert()
        */
    }
    public boolean isIdExists(String id){
        /*로그인 과정에서 회원이 입력한 아이디가 회원정보 파일에 존재하는지 검사하는 함수.
        시용자가 입력한 아이디를 입력받아서
        회원번호 파일에 입력한 아이디가 존재하면 true를, 존재하지 않으면 false를 return한다.
         */
    }
    public boolean checkPassword(String id, String password){
        /*사용자가 입력한 비밀번호가 사용자가 입력한 아이디에 매칭되는 비밀번호인지를 검사하는 함수.
        사용자가 입력한 비밀번호와 사용자가 입력한 비밀번호를 입력 받아서
        사용자가 입력한 비밀번호에 매칭되는 비밀번호와 사용자가 입력한 비밀번호가 일치하면 true,
        일치하지 않으면 false를 return.
         */
    }
    public boolean checkMemberIdForm(String id){
        /*아이디가 입력규칙에 맞는지 검사하는 함수.
        사용자가 입력한 아이디를 매개변수로 받아서
        입력규칙에 맞으면 true를 맞지 않으면 false를 return.
        */
    }

    public boolean checkMemberPasswordForm(String password){
        /*비밀번호가 입력규칙에 맞는지 검사하는 함수.
        사용자가 입력한 비밀번호를 매개변수로 받아서
        입력규칙에 맞으면 true를 맞지 않으면 false를 return
        */
    }
    public void insert(String memberId, String memberPassword){
        /*회원정보 파일에 회원정보를 저장하는 함수.
        사용자가 입력한 아이디와 입력규칙검사와 중복 검사를 완료한 비밀번호를 매개변수로 받아서
        회원정보 파일에 저장.
         */
    }
    public int getMemberNumber(){
        /*신규 가입한 회원에게 할당할 회원번호를 얻는 함수.
        데이터 파일을 확인해서 마지막으로 가입한 회원의 회원번호보다 큰 번호를 return.
         */
    }




}
