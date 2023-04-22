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


                check = check && Admin.CSVisStockSyntaxValid(str) && Admin.CSVisStockSemanticsValid(str);
                check = check;
                if(!check)
                    break;

                String[] lineArr = str.split(",");
                this.addMaterial(new Material(lineArr[0].trim(), lineArr[1].trim()));

                System.out.print(lineArr[0].trim());
                System.out.println(lineArr[1].trim());
            }
            if(!check) {
                System.out.println("hellow mf");
                regenerateMaterialFile();
            }
        }catch (FileNotFoundException e){
                System.out.println("FileNotFoundException: " + fileName);
        }
    }

    public static HashMap<String, Material> getMaterial_Map(){
        return Material_Map;
    }
    void addMaterial(Material material){
        Material_Map.put(material.getName(), material);
    }

    void deleteMaterial(String name){
        Material_Map.remove(name);
    }

    void regenerateMaterialFile(){
        DataFile.regenerateIngredientCSV();
    }

}
