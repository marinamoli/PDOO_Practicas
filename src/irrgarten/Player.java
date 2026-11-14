package irrgarten;

import java.util.ArrayList;

public class Player {
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private static final int INVALID_POS = -1; // Posición que indica que no está en ninguna casilla

    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    public Player(char number, float intelligence, float strength) {
        this.name = "Player #" + number;
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = INVALID_POS;
        this.col = INVALID_POS;
        this.consecutiveHits = 0;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }

    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        resetHits();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getNumber() {
        return number;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean dead() {
        return health <= 0;
    }

    // Diagrama Player__move
    public Directions move(Directions direction, Directions[] validMoves) {
        int size = validMoves.length;
        boolean contained = false;
        for (Directions d : validMoves) {
            if (d == direction) {
                contained = true;
            }
        }

        if (size > 0 && !contained) {
            Directions firstElement = validMoves[0];
            return firstElement;
        } else {
            return direction;
        }
    }

    public float attack() {
        return strength + sumWeapons();
    }

    public boolean defend(float receivedAttack) {
        return manageHit(receivedAttack);
    }

    // Diagrama Player__receiveReward
    public void receiveReward() {
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();

        for (int i = 0; i < wReward; i++) {
            Weapon wnew = newWeapon();
            receiveWeapon(wnew);
        }

        for (int i = 0; i < sReward; i++) {
            Shield snew = newShield();
            receiveShield(snew);
        }

        int extraHealth = Dice.healthReward();
        health += extraHealth;
    }

    @Override
    public String toString() {
        return "P[" + name + ", I:" + intelligence + ", S:" + strength + ", H:" + health
                + ", (" + row + "," + col + "), hits:" + consecutiveHits
                + ", W:" + weapons + ", Sh:" + shields + "]";
    }

    // Diagrama Player__receiveWeapon
    private void receiveWeapon(Weapon w) {
        // Se recorre con un iterador para poder eliminar durante el recorrido
        java.util.Iterator<Weapon> it = weapons.iterator();
        while (it.hasNext()) {
            Weapon wi = it.next();
            boolean discard = wi.discard();
            if (discard) {
                it.remove();
            }
        }

        int size = weapons.size();
        if (size < MAX_WEAPONS) {
            weapons.add(w);
        }
    }

    // Diagrama Player__receiveShield
    private void receiveShield(Shield s) {
        java.util.Iterator<Shield> it = shields.iterator();
        while (it.hasNext()) {
            Shield si = it.next();
            boolean discard = si.discard();
            if (discard) {
                it.remove();
            }
        }

        int size = shields.size();
        if (size < MAX_SHIELDS) {
            shields.add(s);
        }
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float sumWeapons() {
        float sum = 0;
        for (Weapon w : weapons) {
            sum += w.attack();
        }
        return sum;
    }

    private float sumShields() {
        float sum = 0;
        for (Shield s : shields) {
            sum += s.protect();
        }
        return sum;
    }

    private float defensiveEnergy() {
        return intelligence + sumShields();
    }

    // Diagrama Player__manageHit
    private boolean manageHit(float receivedAttack) {
        float defense = defensiveEnergy();

        if (defense < receivedAttack) {
            gotWounded();
            incConsecutiveHits();
        } else {
            resetHits();
        }

        boolean lose;
        if ((consecutiveHits == HITS2LOSE) || dead()) {
            resetHits();
            lose = true;
        } else {
            lose = false;
        }
        return lose;
    }

    private void resetHits() {
        consecutiveHits = 0;
    }

    private void gotWounded() {
        health--;
    }

    private void incConsecutiveHits() {
        consecutiveHits++;
    }
}
