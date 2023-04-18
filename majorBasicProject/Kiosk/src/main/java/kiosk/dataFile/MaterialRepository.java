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

                /*System.out.print(lineArr[0].trim()); 확인용 프린트문
                System.out.println(lineArr[1].trim());*/
            }
            if(!check)
                regenerateMaterialFile();
        }catch (FileNotFoundException e){
                System.out.println("FileNotFoundException: " + fileName);
        }
    }

    void addMaterial(Material material){
        Material_Map.put(material.getName(), material);
    }

    void regenerateMaterialFile(){
        // 파일에 문제 있으면 여기서 regenerate 할 예정.
    }
}
