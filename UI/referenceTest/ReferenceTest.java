import java.util.LinkedList;
import java.util.List;

import pp.A;

class RefOBJ extends A{
    String name;
}


class ReferenceTest {
    static void changeName(RefOBJ obj){
        obj.name = "My New Name";
    }
    static void createSstackOverflow(int j){
        try{
        j++;
        System.out.println(j);
        createSstackOverflow(j);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    static void createOutOfMemory(){
        int index = 0;
        List<byte[]> list = new LinkedList<>();

        while (true){
            byte[] b = new byte[10*1024*1024];
            list.add(b);
            Runtime rt = Runtime.getRuntime();
            System.out.printf("[%3s] Available heap memory: %s%n", index++, rt.freeMemory());

        }

        

    }
    public static void main(String[] args) {
        
       // RefOBJ obj = new RefOBJ();
       // obj.name = "My Name";
       // changeName(obj);
       // System.out.println(obj.name);

      //  createSstackOverflow(0);
     // createOutOfMemory();

     int l=0;
     System.out.println(l);
     System.out.println(100>>>2);

     for(int i=0;true;i++){
        System.out.println(i);
     }

    }
}