package kiosk.prompt;
import kiosk.dataFile.MaterialRepository;
import kiosk.dataFile.MenuRepository;
import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.domain.Menu;
import kiosk.prompt.OrderPrompt;
import kiosk.domain.Member;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import static kiosk.dataFile.MenuRepository.getMenu_Map;

public class PayPrompt {
    private int total_pay;//장바구니에서 가져온 결제 예정액,먼저 10000원이라고 가정
    private int total_print;//결제예정액 출력

    /**추가변수**/
    Member member;
    String couponInput;
    private ArrayList<Menu> menus = MenuRepository.getMenu_Map();
    private static ArrayList<Material> Material_Map = MaterialRepository.getMaterial_Map();
    CouponPrompt couponPrompt;

    private ArrayList<Menu> Menu_Map = getMenu_Map();
    public PayPrompt(Member member){

        this.member = member;
        total_pay = 0;
        total_print = 0;
        getTotal();
        showPrompt();
    }

    //임시
    public PayPrompt(){

        total_pay = 0;
        total_print = 0;
        getTotal();
        showPrompt();
    }

    public class CouponPrompt {
        private int couponCount;
        private int availableCouponCount;
        private int usedCouponNum;
        private Member member;


        private ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
        
                
        String input;

        /**
         * 추가변수
         */
        private boolean loopCouponPrompt;

        public CouponPrompt(Member member){
            this.member = member;
            
            loopCouponPrompt= true;
            
            while (loopCouponPrompt){
                showCouponPrompt();
                getInput();
                
                if (!(isCouponInputSyntaxValid(input) && isCouponInputSemanticsValid(input)))
                    continue;
                convertCouponInputToNum(input);
                //입력한 개수가 사용가능한 쿠폰 개수보다 많을 경우를 배제.
                if (couponCount > getAvailableUsageOfCoupon(getAvailableCoupon(), getAvailableAmericanoNum()))
                    continue;
                
                updateUserCouponCount();
                setStockByCouponUsage();
                break;
            }
        }



        public int getAvailableCoupon(){
            return member.getSavedCup()/10;
        }

        
        public int getAvailableAmericanoNum() {
            
            ArrayList<Menu> menus = MenuRepository.getMenu_Map();
            Menu americano = null;
            ArrayList<Menu.Ingredient> ingredients;
            int availableIngredient = Integer.MAX_VALUE;
            
            for (Menu menu : menus) {
                if (menu.getMenu().equals("아메리카노") && menu.getBeverageStateOption().equals("ICE")){
                    americano = menu;
                    break;
                }
            }
            
            if (americano == null) 
                return (0);
            
            //아메리카노의 레시피 확인
            ingredients = americano.getIngredient();
            for (Menu.Ingredient ingredient : ingredients) {
                boolean isIngredientsExists = false;
                for (Material material : materials) {
                    if (ingredient.name.equals(material.getName())) {
                        isIngredientsExists = true;
                        int availableAmount = material.getAmount() / ingredient.getNum();
                        availableIngredient = Integer.min(availableAmount, availableIngredient);
                        break;
                    }
                }
                if (!isIngredientsExists)
                    return (0);
            }
            return (availableIngredient);
        }

        public int getAvailableUsageOfCoupon(int couponCount, int americanoNum) {
            if(couponCount>americanoNum){
                return americanoNum;
            }else{
                return couponCount;
            }
        }

        public void getCouponInput() {
            Scanner sc = new Scanner(System.in);
            System.out.print("사용하실 쿠폰 개수를 입력: ");
            input= sc.nextLine();
        }

        // 1에서 10자리 숫자 출력
        public boolean isCouponInputSyntaxValid(String input) {
            if (Pattern.matches("[0-9]{1,10}", input) || Pattern.matches("[0-9]{1,10}개", input))
                return true;
            else
                return false;
        }

        //정수 출력
        public boolean isCouponInputSemanticsValid(String input) {
            String onlyDigit = input.replace("개", "");
            int pseudoInput = Integer.parseInt(onlyDigit);
            
            if ( 0 <= Integer.parseInt(onlyDigit) && Integer.parseInt(onlyDigit) <= Integer.MAX_VALUE)
                return true;
            else 
                return false;
        }

        private void updateUserCouponCount (){
            
            member.setSavedCup(member.getSavedCup() - getCouponCount() * 10);
            usedCouponNum = getCouponCount();
        };

        private void showCouponPrompt (){
            System.out.printf("Coupon [%s] [%d(개)] [%d(개)]> ", member.getId() , getAvailableCoupon() ,getAvailableUsageOfCoupon(getAvailableCoupon(), getAvailableAmericanoNum()));
            getCouponInput();

        }


        public int getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(int couponCount) {
            this.couponCount = couponCount;
        }

        public int getAvailableCouponCount() {
            return availableCouponCount;
        }

        public void setAvailableCouponCount(int availableCouponCount) {
            this.availableCouponCount = availableCouponCount;
        }

        public int getUsedCouponNum() {
            return usedCouponNum;
        }

        public void setUsedCouponNum(int usedCouponNum) {
            this.usedCouponNum = usedCouponNum;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }


        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }


        /**
         * 추가함수
         */
        
        /** 입력한 잔 수에 "개" 문자열이 포함되므로 숫자로 변환 : 부가효과*/
        private void convertCouponInputToNum(String input) {
            String onlyDigit = input.replace("개", "");
            int pseudoInput = Integer.parseInt(onlyDigit);
            
            couponCount = pseudoInput;
        }
        
        private void setStockByCouponUsage() {
            Menu iceAmericano = null;
            for (Menu menu : Menu_Map) {
                if (menu.getMenu().equals("아메리카노") && menu.getBeverageStateOption().equals("ICE")) {
                    iceAmericano = menu;
                    break;
                }
            }
            if (iceAmericano == null)
                return ;
            ArrayList<Menu.Ingredient> ingredients = iceAmericano.getIngredient();

            if (couponCount == 0)
                return ;

            for (Menu.Ingredient ingredient : ingredients) {
                for (Material material : Material_Map) {
                    if (ingredient.getName().equals(material.getName())) {
                        material.setAmount(material.getAmount() - ingredient.getNum() * couponCount);
                    }
                }
            }
        }
    }

    private void getTotal(){//장바구니에서 결제예정액 가져오는 함수

        for(Menu menu: Menu_Map) {
            total_pay += (menu.getPrice() * menu.getOrderCount());
           /*if( menu.getOrderCount()>0){
                System.out.printf("%s/%s/%d\n",menu.getMenu(),menu.getBeverageStateOption(), menu.getOrderCount());
            }*/

        }

        total_print = total_pay;
    }
    private void showPrompt(){
        while (true) {//무한 루프를 돌면서
            //shoppingBasketPrompt();//장바구니와 결제 예정액 출력
            System.out.println("결제 예정액:"+total_pay+"원");
            if(isMember(member)){//회원일 경우 프롬프트

                System.out.print("Payment "+"["+member.getId()+"]"+" > ");//memberID 임시 변수로 객체에서 가져옴

            }
            else {//비회원일 경우 프롬프트
                System.out.print("Payment > ");
            }
            Scanner sc = new Scanner(System.in);            //사용자에게 입력을 받는다
            String str = sc.nextLine();

            switch (str) {                                  //입력값이
                case "pay -t":                                 //-t인 경우에
                    totalPay();                                  //결제 예정액 총 결제
                    break;                                  //switch문 탈출
                case "pay -s":                              //-s인 경우에
                    partPay();                           //분할결제 함수 호출
                    break;                                //switch문 탈출
                case "exit":                                //exit인 경우에
                    orderCall();//주문 프롬프트로 이동
                    break;
                default:
                    System.out.println("(오류) 명령어 문법이 잘못되었습니다.");
                    break;
            }
        }
    };
    private void totalPay(){//총결제 함수
        System.out.println("결제예정액:"+total_print+"원이 결제되었습니다.");
        updateMemberSavedCup(member, menus);
        setTotalStock();
        if (isMember(member))
            couponPrompt = new CouponPrompt(member);
        orderCall();
    }
    private void partPay(){//결제 예정액을 가져와서 차감시키면서 진행(total=결제 예정액/,part=분할결제 금액)
        System.out.println("분할결제 시작");
        while(total_pay>0){//결제 예정액이 다 결제 될때까지
            System.out.println("결제예정액:"+total_pay+"원");
            System.out.println("분할결제 할려는 금액을 입력하세요.:");
            try {
                Scanner scanner = new Scanner(System.in);
                int once_Pay = scanner.nextInt();//part=분할결제 금액
                if(once_Pay<0){
                    System.out.println("(오류) 숫자(양의 정수)를 입력하세요.");
                    continue;}
                total_pay-=once_Pay;
            }catch (Exception e) {
                System.out.println("(오류) 숫자(양의 정수)를 입력하세요.");
            }
        }
        System.out.println(total_print+"원 결제되었습니다.");
        updateMemberSavedCup(member, menus);
        setTotalStock();
        if (isMember(member))
            couponPrompt = new CouponPrompt(member);
        orderCall();
    }
    private void orderCall(){                                //order 프로프트로 전환
        System.out.println("주문 프롬프토로 돌아갑니다.");
        //orderPrompt전환시 사용한거 0으로 전환
        ArrayList<Menu> menu = MenuRepository.getMenu_Map();
        for (Menu m: Menu_Map){
            m.setOrderCount(0);
        }
        OrderPrompt OrderPrompt = new OrderPrompt();         //orderPrompt 클래스를 생성한다
    }
    
    private boolean isMember(Member member) {
        return member == null ? false : true;
    }


    /**
     * 추가
     */
    private void setTotalStock() {
        //Menu_Map, Material_Map
        
        //couponPrompt에서 사용한 쿠폰만큼 아메리카노 ICE의 개수를 증가
        
        for (Menu menu: Menu_Map) {
            decrement_ingredients(menu);
        }
    }
    
    private void decrement_ingredients(Menu menu) {
        int decrement_amount = menu.getOrderCount();
        ArrayList<Menu.Ingredient> ingredients = menu.getIngredient();
        
        if (decrement_amount == 0)
            return ;
        
        for (Menu.Ingredient ingredient : ingredients) {
            for (Material material : Material_Map) {
                if (ingredient.getName().equals(material.getName())) {
                    material.setAmount(material.getAmount() - ingredient.getNum() * decrement_amount);
                }
            }
        }
    }
    
    public void updateMemberSavedCup(Member member, ArrayList<Menu> menus) {
        if (member == null) 
            return ;
        int total_count = 0 ;
        
        for (Menu menu: menus) {
            total_count += menu.getOrderCount();
        }
        
        member.setSavedCup(member.getSavedCup() + total_count);
    }
        



}