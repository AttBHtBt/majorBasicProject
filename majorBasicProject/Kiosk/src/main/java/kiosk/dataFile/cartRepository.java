package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Menu;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class cartRepository {
    private static final ArrayList<Menu> Menu_Map = MenuRepository.getMenu_Map();
    private static final ArrayList<Material> Material_Map = MaterialRepository.getMaterial_Map();

    private ArrayList<String> allIngredientName = new ArrayList<>();
    private ArrayList<Integer> allIngredientAmount = new ArrayList<>();
    private ArrayList<Integer> remainingStockAmount = new ArrayList<>();


    //메뉴 이름, 옵션, 주문개수로 주문 가능한지 확인

    //재고가 없는 경우에도 처리.
    public int getAvailableOrderAmount(String menuName, String orderOption, int orderAmount){
        //초기화
        int availableAmount = 0;
        allIngredientAmount.clear(); allIngredientName.clear(); remainingStockAmount.clear();

        //menuName+orderOption : 아메리카노 + ice의 hashMap key는 아메리카노ice.
        Menu menu = MenuRepository.getMenuFromNameAndOption(menuName, orderOption);

        //쓰기 불편해서 ingredientName=레시피 이름, ingredientAmount=레시피에 필요한 재료숫자, remainingAmount= DB재고잔량 배열로 변환.
        initiallizationForCalc(menu);
        if (remainingStockAmount.size() != allIngredientAmount.size())
            return (0);

        // 모든 재고 잔량에 맞춰서 주문가능한 개수를 반환.
        availableAmount = calculateAvailableOrderAmount(orderAmount);
        // 주문가능한 숫자에 맞춰서 재고 잔량 수정.
//        setStockAmount(availableAmount);
        //주문 가능한 개수 반환
        return availableAmount;
    }

    private void initiallizationForCalc(Menu menu){

       ArrayList<Menu.Ingredient> ingredients = menu.getIngredient();
       
        for (Menu.Ingredient ingredient: ingredients){
            allIngredientAmount.add(ingredient.getNum());
            allIngredientName.add(ingredient.getName());
        }

        for (String str : allIngredientName){
            for (Material material: Material_Map){
                if(material.getName().equals(str)) {
                    remainingStockAmount.add(material.getAmount());
                    break;
                }
            }
        }
    }

    private int calculateAvailableOrderAmount(int orderAmount){

        int availableOrderAmount = Integer.MAX_VALUE;
        int orderAmountQuotient;
        
        for (int i = 0; i < allIngredientName.size(); i++) {
            orderAmountQuotient = remainingStockAmount.get(i) / allIngredientAmount.get(i);
            availableOrderAmount = Integer.min(availableOrderAmount, orderAmountQuotient);
        }
        return availableOrderAmount;
    }

    private void setStockAmount(int availableOrderAmount){
        
        for (int i = 0; i < allIngredientName.size(); i++) {
            for (Material material: Material_Map){
                if (material.getName().equals(allIngredientName.get(i))){
                    material.setAmount(remainingStockAmount.get(i) - availableOrderAmount * allIngredientAmount.get(i));
                }
            }
//            Material_Map.get(allIngredientName.get(i))
//                    .setAmount(remainingStockAmount.get(i) - availableOrderAmount * allIngredientAmount.get(i));
        }
    }
}