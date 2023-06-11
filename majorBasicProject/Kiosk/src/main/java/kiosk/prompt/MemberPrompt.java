package kiosk.prompt;

import kiosk.dataFile.MemberRepository;
import kiosk.domain.Member;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MemberPrompt {    
    private MemberRepository mr = new MemberRepository();
    private ArrayList<Member> members = mr.getMember_Map();

    public MemberPrompt() {
        System.out.println("결제 전 회원 정보를 입력해주세요.");
        System.out.println("회원 정보 입력 후 결제 단계로 넘어갑니다.");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Member > ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "signup":
                    registerMember(scanner);
                    break;
                case "login":
                    login(scanner);
                    break;
                case "exit":
                    running = false;
                    break;
                case "guest":
                    System.out.println("비회원으로 로그인합니다");
                    guestLogin();
                    break;
                default:
                    System.out.println("-------- 명령어 입력 오류 --------");
                    System.out.println("비회원 결제는 \"guest\"");
                    System.out.println("회원 결제를 위한 로그인은 \"login\"");
                    System.out.println("신규 회원 가입은 \"signup\"");
                    System.out.println("종료하려면 \"exit\"를 입력해주세요.");
                    break;
            }
        }
        scanner.close();
    }

    private void guestLogin() {
        PayPrompt guestpp = new PayPrompt();
    }

    private void login(Scanner scanner) {
        System.out.print("Member > 아이디를 입력해주세요: ");
        String username = scanner.nextLine();
        if (!checkMemberIdForm(username)){
            System.out.println("입력 규칙에 맞지 않습니다");
        }

        System.out.print("Member > 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();
        if (!checkMemberIdForm(password)){
            System.out.println("입력 규칙에 맞지 않습니다");
        }

        for(Member member: members){
            if(member.getId().equals(username)){
                if(member.getPawd().equals(password)){
                    System.out.println("로그인 성공!");
                    PayPrompt pp = new PayPrompt(member);
                    return;
                }else{
                    System.out.println("비밀번호가 일치하지 않습니다.");
                }
            }
        }System.out.println("아이디가 일치하지 않습니다.");
    }

    private void registerMember(Scanner scanner) {
        System.out.print("Member > 사용하실 아이디를 입력해주세요: ");
        String username = scanner.nextLine();

        if (isUsernameTaken(username)) {
            System.out.println("같은 아이디가 존재합니다.");
            return;
        }else if (!checkMemberIdForm(username)){
            System.out.println("입력 규칙에 맞지 않습니다");
        }

        System.out.print("Member > 사용하실 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();
        if (!checkMemberPasswordForm(password)){
            System.out.println("입력 규칙에 맞지 않습니다");
        }

        System.out.print("사용하실 비밀번호를 한번 더 입력해주세요: ");
        String password2 = scanner.nextLine();
        if (!checkMemberPasswordForm(password2)){
            System.out.println("입력 규칙에 맞지 않습니다");
        }

        if (password.equals(password2)) {
            int memberNumber = generateMemberNumber();            
            mr.addMember(new Member(String.valueOf(memberNumber),username,password,0));            
            System.out.println("회원가입이 완료되었습니다!");
        } else {
            System.out.println("처음 입력하신 비밀번호와 일치하지 않습니다.");
        }
    }

    private boolean isUsernameTaken(String username) {
        for (Member member : members) {
            if (member.getId().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private int generateMemberNumber() {
        if (members.isEmpty()) {
            return 0;
        } else {
            int lastMemberNumber=0;
            for (Member member : members){
                if(Integer.parseInt(member.getMemberNum()) >= lastMemberNumber){
                    lastMemberNumber=Integer.parseInt(member.getMemberNum());
                }
            }
            return lastMemberNumber + 1;
        }
    }

    private boolean checkMemberIdForm(String id){
        // 아이디가 입력규칙에 맞는지 검사하는 함수.
        // 사용자가 입력한 아이디를 매개변수로 받아서
        // 입력규칙에 맞으면 true를 맞지 않으면 false를 반환.
        // 공백은 허용하지 않는다.
        if (id == null)
            return false;
        if (id.length() < 1 || id.length() > 14)
            return false;
        if (!Pattern.matches("[A-Za-z0-9]*", id))
            return false;
        return true;
    }

    public boolean checkMemberPasswordForm(String password) {
        // 비밀번호가 입력규칙에 맞는지 검사하는 함수.
        // 사용자가 입력한 비밀번호를 매개변수로 받아서
        // 입력규칙에 맞으면 true를 맞지 않으면 false를 반환
        if (password == null)
            return false;
        if (password.length() < 1 || password.length() > 14)
            return false;
        if (!Pattern.matches("[A-Za-z0-9!@#$%^&*()]*", password))
            return false;
        return true;
    }
