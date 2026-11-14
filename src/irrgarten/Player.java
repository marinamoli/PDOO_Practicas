package irrgarten;

import java.util.ArrayList;

public class Player {
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 2;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private static final int INVALID_POS = -1;

    private String name;
    private char number;
    private int intelligence;
    private int strength;
    private int health;
    private int row;
    private int col;
    private int consecutiveHits;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    public Player(char number, int intelligence, int strength) {
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
        this.weapons.clear();
        this.shields.clear();
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public char getNumber() { return number; }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean dead() {
        return this.health <= 0;
    }

    public Directions move(Directions direction, Directions[] validMoves) {
        int size = validMoves.length;
        if (size > 0 && contained(direction, validMoves)) {
            return direction;
        } else if (size > 0) {
            return validMoves[0];
        } else {
            return Directions.RIGHT;
        }
    }

    public int attack() {
        return this.strength + sumWeapons();
    }

    public boolean defend(int receivedAttack) {
        return manageHit(receivedAttack);
    }

    public void receiveReward() {
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for (int i = 0; i < wReward; i++) {
            Weapon w = newWeapon();
            receiveWeapon(w);
        }
        for (int i = 0; i < sReward; i++) {
            Shield s = newShield();
            receiveShield(s);
        }
        int extraHealth = Dice.healthReward();
        this.health += extraHealth;
    }

    private boolean contained(Directions direction, Directions[] validMoves) {
        for (Directions d : validMoves) {
            if (d == direction) return true;
        }
        return false;
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private void receiveWeapon(Weapon w) {
        for (int i = weapons.size() - 1; i >= 0; i--) {
            if (weapons.get(i).discard()) {
                weapons.remove(i);
            }
        }
        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(w);
        }
    }

    private void receiveShield(Shield s) {
        for (int i = shields.size() - 1; i >= 0; i--) {
            if (shields.get(i).discard()) {
                shields.remove(i);
            }
        }
        if (shields.size() < MAX_SHIELDS) {
            shields.add(s);
        }
    }

    private int sumWeapons() {
        int sum = 0;
        for (Weapon w : weapons) {
            sum += w.attack();
        }
        return sum;
    }

    private int sumShields() {
        int sum = 0;
        for (Shield s : shields) {
            sum += s.protect();
        }
        return sum;
    }

    private int defensiveEnergy() {
        return this.intelligence + sumShields();
    }

    private boolean manageHit(int receivedAttack) {
        int defense = defensiveEnergy();
        if (defense < receivedAttack) {
            gotWounded();
            incConsecutiveHits();

            if (this.consecutiveHits >= HITS2LOSE || dead()) {
                resetHits();
                return true;
            }
        } else {
            resetHits();
        }
        return false;
    }

    private void gotWounded() {
        this.health--;
    }

    private void incConsecutiveHits() {
        this.consecutiveHits++;
    }

    private void resetHits() {
        this.consecutiveHits = 0;
    }

    @Override
    public String toString() {
        return "Player #" + number + " [H:" + health + ", S:" + strength + ", I:" + intelligence + 
               ", Pos:(" + row + "," + col + "), W:" + weapons.size() + ", Sh:" + shields.size() + "]";
    }
}