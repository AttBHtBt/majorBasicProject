package kiosk.prompt;

import kiosk.dataFile.DataFile;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.dataFile.PwdRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;

import java.util.*;
import java.util.regex.Pattern;

public class MemberPrompt {
    private boolean stat= true;
    public static int memberNumber=getMemberNumber();
    public static Scanner scanner=new Scanner(System.in);
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
                    pay new PayPrompt();
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
    public void nonMemberPayCall() { PayPrompt payPrompt=new PayPrompt();}
    private void memberPayCall(member){
        PayPrompt payPrompt = new PayPrompt();
    }
    private void exitCall(){
        OrderPrompt orderPrompt = new OrderPrompt();
    }
    private void loginCall(){
        String id,password;
        String memberStr;
        String[] memberInfo;
        while(id!='q'&&password!='q'){
            System.out.print("아이디를 입력해주세요:");
            id=scanner.nextLine();
            System.out.print("비밀번호를 입력해주세요:");
            password=scanner.nextLine();
            if(!isIdExists(id))
                System.out.println("존재하지 않는 아이디입니다.");
            else if(memberStr=checkPassword(password)!=null)
                System.out.println("비밀번호가 일치하지 않습니다.");
            else{
                memberInfo=memberStr.split(",");
                Member member=new Member(memberInfo[0],memberInfo[1],memberInfo[2],memberInfo[3]);
                System.out.println("로그인이 완료되었습니다.\n반갑습니다."+member.id+"님");
                memberPayCall(member);
                return;
            }
        }
        /*
        1. Scanner로 아이디와 비밀번호 입력 받기
        2. isIdExists() (false면 1.로)
        3. checkPassword() (false면 1.로)
        new Member
        4. payCall()
         */
    }
    private void signupCall(){
        String id,password;
        String rePassword;
        while(!id.equals("q")&&!password.equals("q")){
            System.out.println("사용하실 아이디를 입력해주세요. 아이디는 영문과 숫자만 사용하여 1자리 이상 14이하로 설정해주세요.");
            System.out.print("아이디:");
            id=scanner.nextLIne();
            if(!checkMemberIdForm(id)) {
                System.out.println("시용하실 수 없는 아이디입니다. 다시 입력해주세요.");
                continue;
            }
            else if(isIdExists(id)) {
                System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.");
                continue;
            }


            System.out.println("사용하실 비밀번호를 입력해주세요. 비밀번호는 영문, 숫자, 특수문자만을 사용하여 1자리 이상 14자리 이하로 설정해주세요.");
            System.out.print("비밀번호:");
            password=scanner.nextLine();
            if(!checkMemberPasswordForm()){
                System.out.println("사용하실 수 없는 비밀번호입니다. 다시 입력해주세요.");
                continue;
            }
            System.out.println("비밀번호를 다시 한 번 입력해주세요.");
            while(true){
                System.out.print("비밀번호 재입력:");
                rePassword=scanner.nextLine();
                if(rePassword.equals("q"))
                    return;
                if(password.equals(rePassword))
                    break;
                else
                    System.out.println("처음 입력하신 비밀번호와 일치하지 않습니다. 다기 입력해주세요.");
            }
            System.out.println("회원가입이 완료되었습니다. 환영합니다. "+id+"님.");
            memberNumber++;
            insert(id,password);
            memberPayCall(new Member(memberNumber,id,password));
        }
        /*
        1. Scanner로 아이디 입력 받음.
        2. checkMemberIdForm() (false면 1.로)
        3. Scanner로 비밀번호 입력 받음.
        4. checkMemberPasswordForm() (false면 3. 으로)
        5. Scanner로 비밀번호 다시 입력 받음.
        6. eqauls() (false면 5로)
        7. memberNumber++
        8. insert()
        9. payCall()
        */
    }
<<<<<<< HEAD
    public boolean isIdExists(String id){
        FileReader fileReader new FileReader("C:\\soPrj\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\production\\member.csv");
        BufferReader reader=new BufferReader(fileReader);
        String line
        String memberId;
        while((line=reader.readLine())!=null){
            memberId=line.split(",")[1];
            if(memberId.equals(id))
                return true;
        }
        return false;
        /*로그인 과정에서 회원이 입력한 아이디가 회원정보 파일에 존재하는지 검사하는 함수.
        시용자가 입력한 아이디를 입력받아서
        회원번호 파일에 입력한 아이디가 존재하면 true를, 존재하지 않으면 false를 return한다.
         */

    }
    public String checkPassword(String id, String password){
        /*사용자가 입력한 비밀번호가 사용자가 입력한 아이디에 매칭되는 비밀번호인지를 검사하는 함수.
        사용자가 입력한 비밀번호와 사용자가 입력한 비밀번호를 입력 받아서
        사용자가 입력한 비밀번호에 매칭되는 비밀번호와 사용자가 입력한 비밀번호가 일치하면 true,
        일치하지 않으면 false를 return.
         */
        FileReader fileReader new FileReader("C:\\soPrj\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\production\\member.csv");
        BufferReader reader=new BufferReader(fileReader);
        String line,memberId,memberPassword;
        String[] temp;
        while((line=reader.readLine())!=null){
            temp=line.split(",")[1];
            memberId=temp[1];
            memberPassword=temp[2];
            if(memberId.equals(id)&&memberPassword.equals(password))
                return line;
        }
        return null;
    }
    public boolean checkMemberIdForm(String id){
        /*아이디가 입력규칙에 맞는지 검사하는 함수.
        사용자가 입력한 아이디를 매개변수로 받아서
        입력규칙에 맞으면 true를 맞지 않으면 false를 return.
        */
        //공백은 허용하지 않는다.
        if(id==null)
            return false;
        if(id.length()<1||id.length()>14)
            return false;
        if(!Pattern.matches("[A-Za-z0-9]*"))
            return false;

        return true;
    }

    public boolean checkMemberPasswordForm(String password){
        /*비밀번호가 입력규칙에 맞는지 검사하는 함수.
        사용자가 입력한 비밀번호를 매개변수로 받아서
        입력규칙에 맞으면 true를 맞지 않으면 false를 return
        */
        if(password==null)
            return false;
        if(password.length()<1||password.length()>14)
            return false;
        if(!Pattern.matches("[A-Za-z0-9!@#$%^&*()]*"))
            return false;

        return true;
    }
    public void insert(String memberId, String memberPassword){
        /*회원정보 파일에 회원정보를 저장하는 함수.
        사용자가 입력한 아이디와 입력규칙검사와 중복 검사를 완료한 비밀번호를 매개변수로 받아서
        회원정보 파일에 저장.
         */
        try{
            FileWriter fileWriter=new FileWriter("member.csv",true);
            BufferedWriter writer=new BufferedWriter(fileWriter);
            writer.write(memberNumber+","+memberId+","+memberPassword+",0\n");
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public int getMemberNumber(){
        /*신규 가입한 회원에게 할당할 회원번호를 얻는 함수.
        데이터 파일을 확인해서 마지막으로 가입한 회원의 회원번호보다 큰 번호를 return.
         */
        FileReader fileReader new FileReader("C:\\soPrj\\workspace\\kiosk\\majorBasicProject\\Kiosk\\src\\production\\member.csv");
        BufferReader reader=new BufferReader(fileReader);
        String line;
        int maxMemberNum=0,maxtemp;
        while((line=reader.readLine())!=null){
            maxtemp=Integer.parseInt(line.split(",")[0]);
            maxMemberNum=maxMemberNum<maxtemp?maxtemp:maxMemberNum;
        }
        return maxMemberNum;
    }

}