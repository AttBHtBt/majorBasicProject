package kiosk.prompt;

import kiosk.dataFile.MenuRepository;
import kiosk.domain.Menu;

import java.io.PrintStream;
import java.util.List;

public class ManagePrompt {
    private List<Menu> menus = MenuRepository.getMenus();
    public ManagePrompt(){
        showPrompt();
    }

    public void showPrompt(){
        System.out.print("Admin > ");
    }
}