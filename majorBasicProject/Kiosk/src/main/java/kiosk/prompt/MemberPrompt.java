package kiosk.prompt;

import kiosk.dataFile.DataFile;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MemberPrompt {
    private static final String DATABASE_FILE = DataFile.DATAFILEDIRECTORY + "member.txt";
    private Map<Integer, Member> members;
    private Console console = System.console();

    public MemberPrompt() {
        members = loadMembers();
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

    private void registerMember(Scanner scanner) {
        System.out.print("Member > 사용하실 아이디를 입력해주세요: ");
        String username = scanner.nextLine();

        if (isUsernameTaken(username)) {
            System.out.println("같은 아이디가 존재합니다.");
            return;
        }

        System.out.print("Member > 사용하실 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();

        //System.out.print("사용하실 비밀번호를 입력해주세요: ");
        //char[] passwordChars = console.readPassword();
        //String password = new String(passwordChars);

        System.out.print("사용하실 비밀번호를 한번 더 입력해주세요: ");
        System.out.print("Member >Check 사용하실 비밀번호를 입력해주세요: ");
        String password2 = scanner.nextLine();

//        char[] passwordCheckChars = console.readPassword();
//        String passwordCheck = new String(passwordCheckChars);

        if (password.equals(password2)) {
            int memberNumber = generateMemberNumber();
            members.put(memberNumber, new Member(memberNumber, username, password));
            saveMembers();
            System.out.println("회원가입이 완료되었습니다!");
        } else {
            System.out.println("처음 입력하신 비밀번호와 일치하지 않습니다.");
        }
    }

    private boolean isUsernameTaken(String username) {
        for (Member member : members.values()) {
            if (member.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private int generateMemberNumber() {
        if (members.isEmpty()) {
            return 0;
        } else {
            int lastMemberNumber = members.keySet().stream().max(Integer::compareTo).orElse(0);
            return lastMemberNumber + 1;
        }
    }

    private void login(Scanner scanner) {
        System.out.print("아이디를 입력해주세요: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호를 입력해주세요: ");

        char[] passwordChars = console.readPassword();
        String password = new String(passwordChars);

        for (Member member : members.values()) {
            if (member.getUsername().equals(username)) {
                if (member.getPassword().equals(password)) {
                    System.out.println("로그인 성공!");
                    return;
                } else {
                    System.out.println("비밀번호가 일치하지 않습니다.");
                    return;
                }
            }
        }

        System.out.println("아이디가 일치하지 않습니다.");
    }

    private Map<Integer, Member> loadMembers() {
        Map<Integer, Member> members = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int memberNumber = Integer.parseInt(parts[0]);
                String username = parts[1];
                String password = parts[2];
                members.put(memberNumber, new Member(memberNumber, username, password));
            }
        } catch (IOException e) {
            System.out.println("회원 데이터를 로드하는 중에 오류가 발생했습니다.");
        }

        return members;
    }

    private void saveMembers() {
        try (FileWriter writer = new FileWriter(DATABASE_FILE, false)) {
            for (Member member : members.values()) {
                writer.write(member.getMemberNumber() + ":" + member.getUsername() + ":" + member.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("회원 데이터를 저장하는 중에 오류가 발생했습니다.");
        }
    }

    private void guestLogin() {
        // 비회원 로그인 기능 구현
    }

    public class Member {
        private int memberNumber;
        private String username;
        private String password;

        public Member(int memberNumber, String username, String password) {
            this.memberNumber = memberNumber;
            this.username = username;
            this.password = password;
        }

        public int getMemberNumber() {
            return memberNumber;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
