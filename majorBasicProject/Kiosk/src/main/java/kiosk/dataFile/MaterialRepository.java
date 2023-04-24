package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaterialRepository {
    private static final ArrayList<Material> Material_Map = new ArrayList<>();
    public void makeMaterial(String fileName) {
        try (Scanner scan = new Scanner(new File(fileName))) {
            Boolean check = true;
            while (scan.hasNext()) {
                String str = scan.nextLine();

                check = check && Admin.CSVisStockSyntaxValid(str) &&
                        Admin.CSVisStockSemanticsValid(str);
                if(!check)
                    break;

                String[] lineArr = str.split(",");
                this.addMaterial(new Material(lineArr[0].trim(), lineArr[1].trim()));
            }
            if (!check)
                DataFile.isIngredientFileValid = false;
        }catch (FileNotFoundException e){
                System.out.println("FileNotFoundException: " + fileName);
        }
    }
    
    //중복 아님.
    public static boolean isIngredientFileValid (String fileName) {
        try (Scanner scan = new Scanner(new File(fileName))) {
            Boolean check = true;
            while (scan.hasNext()) {
                String str = scan.nextLine();

                check = check && Admin.CSVisStockSyntaxValid(str) &&
                        Admin.CSVisStockSemanticsValid(str);
                if(!check)
                    break;

                String[] lineArr = str.split(",");
            }
            if (!check)
                DataFile.isIngredientFileValid = false;
        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException: " + fileName);
        }
        return true;
    }

    public static ArrayList<Material> getMaterial_Map(){
        return Material_Map;
    }
    
    public static boolean isDuplicatedMaterial(String ingredient){
        
        for (Material material: Material_Map) {
            if (material.getName().equals(ingredient)){
                return true;
            }
        }
        return false;
    }
    public static void addMaterial(Material material){
        Material_Map.add(material);
    }

    public static void deleteMaterial(String name){
        for (Material material: Material_Map) {
            if (material.getName().equals(name)){
                Material_Map.remove(material);
                return;
            }
        }
    }
    
    public static Material getMaterialFromName(String name){
        for (Material material: Material_Map) {
            if (material.getName().equals(name)) {
                return material;
            }
        }
        return null;
    }
}
