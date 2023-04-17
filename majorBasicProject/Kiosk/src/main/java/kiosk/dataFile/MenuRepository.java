package kiosk.dataFile;

import kiosk.domain.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuRepository {

    private static final HashMap<String, Menu> MENU_Map = new HashMap<>();

    int a;
    public MenuRepository(){
    }
    public void makeMenu(String fileName){
        try(Scanner scan =  new Scanner(new File(fileName))){
            while(scan.hasNext()){
                String str = scan.nextLine();
                //System.out.println(str);
                String[] lineArr = str.split(",");
                //메뉴이름, 메뉴가격, 메뉴옵션, 레시피...

                //System.out.println("point1");


                //dynamicArray.add(new ArrayList<String>());

               // System.out.println("point 2");
                ArrayList<ArrayList<String>> dynamicArray = new ArrayList<>();
                for(int i = 3; i< lineArr.length; i++){
                    String[] array = lineArr[i].split(":");
                    ArrayList<String> list = new ArrayList<>();
                    list.add(array[0].trim());
                    list.add(array[1].trim());
                    dynamicArray.add(list);
                }

                this.addMenu(new Menu(lineArr[0].trim(), lineArr[1].trim(), lineArr[2].trim(), dynamicArray));
                //2차원 arrayList에 재료이름과 재료 수량을 넣는다.

                // 얘네는 체크용으로 만들어 놓은 코드이니 신경 안써도 괜찮다.
                //System.out.println("Mission Complete");

         /*       for(int i = 0; i<3; i++){
                    System.out.println(lineArr[i]);
                }

                for(int j = 0; j<dynamicArray.size(); j++){
                    System.out.print(dynamicArray.get(j).get(0));
                    System.out.println(dynamicArray.get(j).get(1));
                }*/


            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FileNotFoundException");
        } catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
        }
    }

    private void addMenu(Menu menu){// 중복 검사는 아직 안 함
        MENU_Map.put(menu.getMenu(), menu);
    }



    }
