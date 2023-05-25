package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.prompt.MemberPrompt;


import javax.swing.*;
import java.util.ArrayList;

public class MemberRepository {

    private MemberPrompt mp= new MemberPrompt();
    private static final ArrayList<Material> Member_Map = new ArrayList<>();

    public void makeMember(String fileName) {

        boolean check = mp.checkMemberIdForm() &&
        mp.checkMemberPasswordForm() &&
        mp.checkPassword();

    }

    public static void addMember(Member member){

    }



}
