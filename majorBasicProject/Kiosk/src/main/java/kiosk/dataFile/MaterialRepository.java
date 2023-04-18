package kiosk.dataFile;

import kiosk.domain.Material;
import kiosk.domain.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MaterialRepository {

    private static final HashMap<String, Material> Material_Map = new HashMap<>();

    public void makeMaterial(String fileName) {
        try (Scanner scan = new Scanner(new File(fileName))) {
            while (scan.hasNext()) {
                String str = scan.nextLine();
                //System.out.println(str);
                String[] lineArr = str.split(",");
                this.addMaterial(new Material(lineArr[0].trim(), lineArr[1].trim()));

                /*System.out.print(lineArr[0].trim()); 확인용 프린트문
                System.out.println(lineArr[1].trim());*/
            }
        }catch (FileNotFoundException e){
            System.out.println("FileNotFoundException: " + fileName);
        }
    }

    void addMaterial(Material material){
        Material_Map.put(material.getName(), material);
    }
}
