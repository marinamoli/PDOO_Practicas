package irrgarten;

public class TestP1 {

    public static void main(String[] args) {

        // ===================== WEAPON =====================
        System.out.println("===== WEAPON =====");
        Weapon w1 = new Weapon(2.0f, 5);
        Weapon w2 = new Weapon(1.5f, 1);
        Weapon w3 = new Weapon(2.5f, 0);

        System.out.println(w1 + " " + w2 + " " + w3);

        System.out.println("w2 ataca: " + w2.attack());  // 1.5
        System.out.println("w2 ataca: " + w2.attack());  // 0.0 (sin usos)
        System.out.println("w2 tras atacar: " + w2);     // W[1.5, 0]

        System.out.println("w1 discard (5 usos, siempre false): " + w1.discard());
        System.out.println("w3 discard (0 usos, siempre true): " + w3.discard());

        // ===================== SHIELD =====================
        System.out.println("\n===== SHIELD =====");
        Shield s1 = new Shield(3.0f, 4);
        Shield s2 = new Shield(1.0f, 1);
        Shield s3 = new Shield(0.5f, 0);

        System.out.println(s1 + " " + s2 + " " + s3);

        System.out.println("s2 protege: " + s2.protect());  // 1.0
        System.out.println("s2 protege: " + s2.protect());  // 0.0 (sin usos)
        System.out.println("s2 tras proteger: " + s2);      // S[1.0, 0]

        System.out.println("s1 discard (4 usos): " + s1.discard());
        System.out.println("s3 discard (0 usos, siempre true): " + s3.discard());

        // ===================== ENUMERADOS =====================
        System.out.println("\n===== ENUMERADOS =====");
        Directions d = Directions.UP;
        Orientation o = Orientation.HORIZONTAL;
        GameCharacter g = GameCharacter.MONSTER;
        System.out.println(d + " " + o + " " + g);

        for (Directions dir : Directions.values()) {
            System.out.print(dir + " ");
        }
        System.out.println();

        // ===================== GAMESTATE =====================
        System.out.println("\n===== GAMESTATE =====");
        GameState gs = new GameState("laberinto", "jugadores", "monstruos", 1, false, "sin eventos");
        System.out.println(gs.getLabyrinth());
        System.out.println(gs.getPlayers());
        System.out.println(gs.getMonsters());
        System.out.println(gs.getCurrentPlayer());
        System.out.println(gs.getWinner());
        System.out.println(gs.getLog());

        // ===================== DICE (100 veces cada método) =====================
        System.out.println("\n===== DICE =====");
        final int N = 100;

        // --- Métodos que devuelven int: comprobamos mínimo y máximo ---
        int minPos = 100, maxPos = -1;
        int minStart = 100, maxStart = -1;
        int minWR = 100, maxWR = -1;
        int minSR = 100, maxSR = -1;
        int minHR = 100, maxHR = -1;
        int minUses = 100, maxUses = -1;

        for (int i = 0; i < N; i++) {
            int p = Dice.randomPos(10);
            minPos = Math.min(minPos, p); maxPos = Math.max(maxPos, p);

            int st = Dice.whoStarts(3);
            minStart = Math.min(minStart, st); maxStart = Math.max(maxStart, st);

            int wr = Dice.weaponsReward();
            minWR = Math.min(minWR, wr); maxWR = Math.max(maxWR, wr);

            int sr = Dice.shieldsReward();
            minSR = Math.min(minSR, sr); maxSR = Math.max(maxSR, sr);

            int hr = Dice.healthReward();
            minHR = Math.min(minHR, hr); maxHR = Math.max(maxHR, hr);

            int u = Dice.usesLeft();
            minUses = Math.min(minUses, u); maxUses = Math.max(maxUses, u);
        }

        System.out.println("randomPos(10)   [0,9] -> min " + minPos + ", max " + maxPos);
        System.out.println("whoStarts(3)    [0,2] -> min " + minStart + ", max " + maxStart);
        System.out.println("weaponsReward   [0,2] -> min " + minWR + ", max " + maxWR);
        System.out.println("shieldsReward   [0,3] -> min " + minSR + ", max " + maxSR);
        System.out.println("healthReward    [0,5] -> min " + minHR + ", max " + maxHR);
        System.out.println("usesLeft        [0,5] -> min " + minUses + ", max " + maxUses);

        // --- Métodos que devuelven float: comprobamos mínimo y máximo ---
        float minInt = 100, maxInt = -1;
        float minStr = 100, maxStr = -1;
        float minWP = 100, maxWP = -1;
        float minSP = 100, maxSP = -1;
        float minIn = 100, maxIn = -1;

        for (int i = 0; i < N; i++) {
            float a = Dice.randomIntelligence();
            minInt = Math.min(minInt, a); maxInt = Math.max(maxInt, a);

            float b = Dice.randomStrength();
            minStr = Math.min(minStr, b); maxStr = Math.max(maxStr, b);

            float c = Dice.weaponPower();
            minWP = Math.min(minWP, c); maxWP = Math.max(maxWP, c);

            float e = Dice.shieldPower();
            minSP = Math.min(minSP, e); maxSP = Math.max(maxSP, e);

            float f = Dice.intensity(5.0f);
            minIn = Math.min(minIn, f); maxIn = Math.max(maxIn, f);
        }

        System.out.println("randomIntelligence [0,10[ -> min " + minInt + ", max " + maxInt);
        System.out.println("randomStrength     [0,10[ -> min " + minStr + ", max " + maxStr);
        System.out.println("weaponPower        [0,3[  -> min " + minWP + ", max " + maxWP);
        System.out.println("shieldPower        [0,2[  -> min " + minSP + ", max " + maxSP);
        System.out.println("intensity(5)       [0,5[  -> min " + minIn + ", max " + maxIn);

        // --- Métodos que devuelven boolean: contamos los true ---
        int resucitados = 0;
        int desc0 = 0, desc1 = 0, desc4 = 0, desc5 = 0;

        for (int i = 0; i < N; i++) {
            if (Dice.resurrectPlayer()) resucitados++;
            if (Dice.discardElement(0)) desc0++;
            if (Dice.discardElement(1)) desc1++;
            if (Dice.discardElement(4)) desc4++;
            if (Dice.discardElement(5)) desc5++;
        }

        System.out.println("resurrectPlayer      (~30): " + resucitados + " de " + N);
        System.out.println("discardElement(0)   (100): " + desc0 + " de " + N);
        System.out.println("discardElement(1)    (~80): " + desc1 + " de " + N);
        System.out.println("discardElement(4)    (~20): " + desc4 + " de " + N);
        System.out.println("discardElement(5)      (0): " + desc5 + " de " + N);
    }
}