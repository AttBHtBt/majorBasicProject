package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.prompt.MemberPrompt;


import javax.swing.*;
import java.util.ArrayList;

public class MemberRepository {
    
    private Member member;

    private MemberPrompt mp= new MemberPrompt();
    public static ArrayList<Member> Member_Map = new ArrayList<>();

    public void makeMember(String fileName) {

        boolean check = mp.checkMemberIdForm("") &&
        mp.checkMemberPasswordForm("") &&
        mp.checkPassword("");
        Member_Map.add(member);
    }
    
    

    public static void addMember(Member member){
        Member_Map.add(member);
    }
    



}
