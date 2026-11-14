package irrgarten;

/**
 *
 * @author A.Illeras
 */
public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private static final int INVALID_POS = -1; // Posición que indica que no está en ninguna casilla

    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;

    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = INVALID_POS;
        this.col = INVALID_POS;
    }

    public boolean dead() {
        return health <= 0;
    }

    public float attack() {
        return Dice.intensity(strength);
    }

    // Diagrama Monster__defend
    public boolean defend(float receivedAttack) {
        boolean isDead = dead();
        if (!isDead) {
            float defensiveEnergy = Dice.intensity(intelligence);
            if (defensiveEnergy < receivedAttack) {
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "M[" + name + ", I:" + intelligence + ", S:" + strength + ", H:" + health
                + ", (" + row + "," + col + ")]";
    }

    private void gotWounded() {
        health--;
    }
}
