package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.domain.Menu;
import kiosk.manager.Admin;
import kiosk.prompt.MemberPrompt;


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberRepository {

    MemberPrompt mp = new MemberPrompt();
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
                check = mp.checkMemberNum(lineArr[0].trim()) && mp.checkMemberIdForm(lineArr[1].trim())
                        && mp.checkMemberPasswordForm(lineArr[2]) && mp.checksavedCup(cupNum);

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

    public void insert(String MemberId, String MemberPwd){
        String MemberNum =  String.format("%08d", Member_Map.size());
        Member_Map.add(new Member(MemberNum, MemberId, MemberPwd, 0));
    }

    public static ArrayList<Member> getMember_Map(){
        return Member_Map;
    }



}