package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Menu;
import kiosk.manager.Admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MaterialRepository {

    private static final HashMap<String, Material> Material_Map = new HashMap<>();

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

    public static HashMap<String, Material> getMaterial_Map(){
        return Material_Map;
    }
    
    public static boolean isDuplicatedMaterial(String ingredient){
        if (Material_Map.get(ingredient) == null)
            return false;
        else 
            return true;
    }
    public static void addMaterial(Material material){
        Material_Map.put(material.getName(), material);
    }

    public static void deleteMaterial(String name){
        Material_Map.remove(name);
    }
}
