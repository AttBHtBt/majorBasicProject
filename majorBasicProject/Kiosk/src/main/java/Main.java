import kiosk.config.AppConfig;
import kiosk.dataFile.*;
import kiosk.prompt.ManagePrompt;
import kiosk.prompt.OrderPrompt;

import javax.xml.crypto.Data;
import java.io.File;
//import kiosk.prompt.OrderPrompt;
import kiosk.prompt.OrderPrompt;


public class Main {
    public static void main(String[] args){

        MenuRepository MR = new MenuRepository();
        MR.makeMenu(DataFile.DATAFILEDIRECTORY + DataFile.menuFileName);

        MaterialRepository MTR = new MaterialRepository();
        MTR.makeMaterial(DataFile.DATAFILEDIRECTORY + DataFile.ingredientFileName);

        PwdRepository PR = new PwdRepository();
        PR.makePwd(DataFile.DATAFILEDIRECTORY + DataFile.adminFileName);

        MemberRepository MbR = new MemberRepository();
        MbR.makeMember(DataFile.DATAFILEDIRECTORY + DataFile.memberFileName);

        for(int i = 0; i< MbR.Member_Map.size(); i++){
            System.out.println(MbR.Member_Map.get(i));
        }



        DataFile.regenerate();
        
        OrderPrompt orderPrompt = AppConfig.orderPrompt();

    }
}