package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Member;
import kiosk.domain.Menu;

import java.io.*;
import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFile {

    private static final ArrayList<Menu> menus = MenuRepository.getMenu_Map();
    private static final ArrayList<Material> materials = MaterialRepository.getMaterial_Map();
    private static final ArrayList<Member> members = MemberRepository.getMember_Map();
    public static String DATAFILEDIRECTORY = "." + File.separator +
            ".." + File.separator +
            ".." + File.separator +
            ".." + File.separator +
            ".." + File.separator +
            "src" + File.separator
            ;

    public static String menuFileName = "menu.csv";
    public static String ingredientFileName = "ingredients.csv";
    public static String adminFileName = "admin.txt";
    public static String memberFileName = "member.csv";
    
    public static boolean isMenuFileValid = true;
    public static boolean isIngredientFileValid = true;
    public static boolean isAdminFileValid = true;
    public static boolean isMemberFileVaild = true;

    /**
     * For Regenerating Testing Only
     */
    public static String menuFileName_Testing = "menu_1.csv";
    public static String ingredientFileName_Testing = "ingredients_1.csv";
    public static String adminFileName_Testing = "admin_1.txt";

    public static void regenerateMenuCSV() {
        File menuFile = new File(DATAFILEDIRECTORY + menuFileName);
        try {
            FileWriter menuFileW = new FileWriter(menuFile);
            menuFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void regenerateIngredientCSV() {
        File ingredientFile = new File(DATAFILEDIRECTORY + DataFile.ingredientFileName);
        try {
            FileWriter ingredientFileW = new FileWriter(ingredientFile);
            ingredientFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void regenerateAdminTxT() {
        File adminFile = new File(DATAFILEDIRECTORY + adminFileName);
        try {
            FileWriter adminFileW = new FileWriter(adminFile);
            adminFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void regenerateMemeberCSV(){
        File memberFile = new File(DATAFILEDIRECTORY + DataFile.memberFileName);
        try {
            FileWriter memberFileW = new FileWriter(memberFile);
            memberFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void regenerate(){
//        MenuRepository.isMenuFilevalid(DATAFILEDIRECTORY + menuFileName);
//        MaterialRepository.isIngredientFileValid(DATAFILEDIRECTORY + DataFile.ingredientFileName);
//        try{
//            PwdRepository.isAdminFileValid(new Scanner(new File(DataFile.DATAFILEDIRECTORY + DataFile.adminFileName)));
//        } catch (FileNotFoundException e){
//        }
        if (!isMenuFileValid)
            regenerateMenuCSV();
        if (!isIngredientFileValid)
            regenerateIngredientCSV();
        if (!isAdminFileValid)
            regenerateAdminTxT();
        if (!isMemberFileVaild)
            regenerateMemeberCSV();
        if (!isMenuFileValid || !isIngredientFileValid || !isAdminFileValid || !isMemberFileVaild){
            System.out.println("치명적 오류");
            System.exit(1);
        }
    }
    
    public static void pwd(){
        System.out.println(System.getProperty("user.dir"));
    }
    
    public static void currentDir(String pathName){
        File dir = new File(pathName);
        System.out.println(dir.getAbsolutePath());
    }

    //convertMenuRepositoryToCSV
    //convertMaterialRepositoryToCSV
    public static void convertMenuRepositoryToCSV() {
        File menuFile = new File(DATAFILEDIRECTORY + menuFileName);
        try {
            FileWriter menuFileW = new FileWriter(menuFile);
            BufferedWriter writer = new BufferedWriter(menuFileW);
            for (Menu menu: menus) {
                writer.write(getMenuLine(menu));
                if (!((menus.get(menus.size() -1).getMenu() + menu.getBeverageStateOption())
                        == (menu.getMenu() + menu.getBeverageStateOption())))
                    writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertMaterialRepositoryToCSV() {
        File ingredientFile = new File(DATAFILEDIRECTORY + ingredientFileName);
        try {
            FileWriter ingredientFileW = new FileWriter(ingredientFile);
            BufferedWriter writer = new BufferedWriter(ingredientFileW);
            for (Material material: materials) {
                writer.write(getMaterialLine(material));
                if (!(materials.get(materials.size() -1).getName() == material.getName()))
                    writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertMemberRepositoryToCSV(){
        File memberFile = new File(DATAFILEDIRECTORY + memberFileName);
        try {
            FileWriter memberFileW = new FileWriter(memberFile);
            BufferedWriter writer = new BufferedWriter(memberFileW);
            for (Member member: members) {
                writer.write(getMemberLine(member));
                if (!((members.get(members.size() -1).getMemberNum()
                        == member.getMemberNum())))
                    writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMemberLine(Member member){
        String lineStr;
        lineStr = String.format("%s,%s,%s,%s", member.getMemberNum(), member.getId(), member.getPawd(), member.getSavedCup());
        return lineStr;
    }

    public static String getMenuLine(Menu menu){
        String lineStr;
        ArrayList<Menu.Ingredient> ingredients = menu.getIngredient();
        
        lineStr = String.format("%s,%s,%s,", menu.getMenu(), String.valueOf(menu.getPrice()), menu.getBeverageStateOption().toUpperCase());
        for (Menu.Ingredient ingredient: ingredients){
            lineStr += String.format("%s:%s,", ingredient.getName(), String.valueOf(ingredient.getNum()));
        }
        return lineStr.substring(0, lineStr.length()-1);
    }
    
    public static String getMaterialLine(Material material){
        String lineStr;
        lineStr = String.format("%s,%s", material.getName(), material.getAmount());
        return lineStr;
    }


    /**
     * 추가 함수
     */
    public static int convertMemberNumToInteger(String str) {
        str = str.trim();
        int digit =     1;
        int number =    0;
         
        for (int i = str.length() - 1; i >= 0; i--) {
            number += (Integer.parseInt(String.valueOf(str.charAt(i))) * digit);
            digit *= 10;
        }
        return number;
    }
    
}
