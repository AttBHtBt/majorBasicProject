package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.domain.Menu;
import kiosk.manager.Admin;



import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MemberRepository {

    public static ArrayList<Member> Member_Map = new ArrayList<>();

    public void makeMember(String fileName) {
        try (Scanner scan = new Scanner(new File(fileName))) {
            Boolean check = true;

            if (!scan.hasNext()) {
                check = false;
                DataFile.isMemberFileVaild = false;
                return;
            }

            while (scan.hasNext()) {
                String str = scan.nextLine();
                String[] lineArr = str.split(",");
                if(lineArr.length != 4){
                    check = false;
                    break;
                }
               // 4개가 다 있는 지 확인

                int cupNum = Integer.parseInt(lineArr[3].trim());

                // 무결성 검사

                check = checkMemberNum(lineArr[0].trim()) && checkMemberIdForm(lineArr[1].trim())
                        && checkMemberPasswordForm(lineArr[2].trim()) && checksavedCup(cupNum);


                if(!check)
                    break;

                this.addMember(new Member(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim(), cupNum));
            }
            /*
            check = check && isDuplicatedIngredientName();
             */
            if (!check)
                DataFile.isMemberFileVaild = false;
        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException: " + fileName);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException: " + fileName);
        }
    }

    public static void addMember(Member member){
        Member_Map.add(member);
    }

    public boolean checkMemberIdForm(String id) {
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

    public boolean checkMemberNum(String num){
        int number;
        if(num == null)
            return false;

        number=Integer.parseInt(num);

        if(number<0)
            return false;
        return true;
    }

    public boolean checksavedCup(int num){
        if(num < 0)
            return false;
        return true;
    }

    public static ArrayList<Member> getMember_Map(){
        return Member_Map;
    }



}