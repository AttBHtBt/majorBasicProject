package kiosk.dataFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.chrono.MinguoEra;

public class DataFile {
    public static String dataFileDirectory = ".\\Kiosk\\src\\";
    public static String menuFileName = "menu.csv";
    public static String ingredientFileName = "ingredients.csv";
    public static String adminFileName = "admin.txt";

    /**
     * For Regenerating Testing Only
     */
    public static String menuFileName_Testing = "menu_1.csv";
    public static String ingredientFileName_Testing = "ingredients_1.csv";
    public static String adminFileName_Testing = "admin_1.txt";


    public static void regenerateMenuCSV() {
        File menuFile = new File(dataFileDirectory + menuFileName_Testing);
        try {
            FileWriter menuFileW = new FileWriter(menuFile);
            menuFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void regenerateIngredientCSV() {
        File ingredientFile = new File(dataFileDirectory + ingredientFileName_Testing);
        try {
            FileWriter ingredientFileW = new FileWriter(ingredientFile);
            ingredientFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void regenerateAdminTxT() {
        File menuFile = new File(dataFileDirectory + menuFileName_Testing);
        File ingredientFile = new File(dataFileDirectory + ingredientFileName_Testing);
        File adminFile = new File(dataFileDirectory + adminFileName_Testing);
        try {
            FileWriter adminFileW = new FileWriter(adminFile);
            adminFileW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
