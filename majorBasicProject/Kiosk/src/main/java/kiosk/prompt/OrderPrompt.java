package kiosk.prompt;

import kiosk.dataFile.MenuRepository;
import kiosk.domain.Menu;

import java.util.List;

public class OrderPrompt {
    private List<Menu> menus = MenuRepository.getMenus();
    public OrderPrompt(){
        showPrompt();
    }

    private void showPrompt(){
        System.out.println("Menu");
        System.out.println("메뉴, 가격과 선택할 수 있는 메뉴 옵션입니다.");
        System.out.println("---------------------------------------");
        for(Menu m : menus){
            System.out.println(m);
        }
        System.out.println("---------------------------------------");
        System.out.println("핫, 아이스 두가지 선택이 가능한 메뉴는 ICE 선택시 500원이 추가 됩니다.");
        shoppingBasketPrompt();
        System.out.println("---------------------------------------");
        System.out.println("아래의 입력 대기 줄에 주문할 메뉴를 입력해주세요.");
        System.out.println("최종결제를 원한다면 'pay'를 입력해주세요.");
    }

    private void shoppingBasketPrompt(){

    }
}
