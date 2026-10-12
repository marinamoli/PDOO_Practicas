package irrgarten;

public class TestP1 {

    public static void main(String[] args) {
        System.out.println("Hola desde Irrgarten");
        
        Weapon w = new Weapon(2.0f, 5);
        System.out.println(w);           // W[2.0, 5]
        System.out.println(w.attack());  // 2.0
        System.out.println(w);           // W[2.0, 4]
        
        for (int i = 0; i < 5; i++) {
          System.out.println(w.attack());
            }
        System.out.println(w);  // W[2.0, 0]
        
        
        Shield s = new Shield(3.0f, 4);
        System.out.println(s);            // S[3.0, 4]
        System.out.println(s.protect());  // 3.0
        System.out.println(s);            // S[3.0, 3]
    }
}