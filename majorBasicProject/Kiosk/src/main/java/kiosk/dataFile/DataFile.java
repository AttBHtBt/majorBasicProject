package kiosk.dataFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.chrono.MinguoEra;

public class DataFile {

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
    
    public static boolean isMenuFileValid = true;
    public static boolean isIngredientFileValid = true;
    public static boolean isAdminFileValid = true;

    /**
     * For Regenerating Testing Only
     */
    public static String menuFileName_Testing = "menu_1.csv";
    public static String ingredientFileName_Testing = "ingredients_1.csv";
    public static String adminFileName_Testing = "admin_1.txt";


    public static void regenerateMenuCSV() {
        File menuFile = new File(DATAFILEDIRECTORY + menuFileName_Testing);
        try {
            FileWriter menuFileW = new FileWriter(menuFile);
            menuFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void regenerateIngredientCSV() {
        File ingredientFile = new File(DATAFILEDIRECTORY + ingredientFileName_Testing);
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
    
    public static void regenerate(){
        if (!isMenuFileValid)
            regenerateMenuCSV();
        if (!isIngredientFileValid)
            regenerateMenuCSV();
        if (!isIngredientFileValid)
            regenerateAdminTxT();
    }
    
    public static void pwd(){
        System.out.println(System.getProperty("user.dir"));
    }
    
    public static void currentDir(String pathName){
        File dir = new File(pathName);
        System.out.println(dir.getAbsolutePath());
    }
}
