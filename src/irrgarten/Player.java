/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author A.Illeras
 */
public class Player {
    //Atributos
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    
    private String name;
    private char IDPlayer;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;
    
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    //Constructor parametros
    public Player(char IDPlayer, float intelligence, float strength){
        this.IDPlayer = IDPlayer;
        this.name = "Player #" + IDPlayer;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }
    
    //Metodo que revive al jugador
    public void resurrect(){
        this.weapons.clear();//Vacio armas
        this.shields.clear();//Vacío escudos
        this.health = INITIAL_HEALTH;//Restauro vida
        this.consecutiveHits = 0;
    }
    
    //Cambia posicion en el laberinto
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    //Comprueba si el jugador ha muerto
    public boolean dead(){
        return this.health <= 0;
    }
    
    //Si el jugador ha sido herido
    public void gotWounded(){
        this.health -= 1;
    }
    
    //Resetea impactos
    public void resetHits(){
        this.consecutiveHits = 0;
    }
    
    //Incrementa impactos consecutivos
    public void incConsecutiveHits(){
        this.consecutiveHits += 1;
    }
    
    //Metodo ataque del jugador
    public float attack(){
        return this.strength + this.sumWeapons();
    }
    
    //Metodo defensa jugador
    public float defensiveEnergy(){
        return this.intelligence + this.sumShields();
    }
    
    //Suma ataque de armas del jugador
    public float sumWeapons(){
        float total = 0;
        for(Weapon w : this.weapons){
            total += w.attack();
        }
        return total;
    }
    
    //Suma proteccion de escudos del jugador
    public float sumShields(){
        float total = 0;
        for(Shield s : this.shields){
            total += s.protect();
        }
        return total;
    }
    
    //Nueva arma
    public Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    //Nuevo escudo
    public Shield newShield(){
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    //METODOS SIGUIENTE PRACTICA
    public Directions move(Directions direction, ArrayList<Directions> validMoves) {
        throw new UnsupportedOperationException();
    }
    
    public void receiveReward() {
        throw new UnsupportedOperationException();
    }
    
    public void receiveWeapon(Weapon w) {
        throw new UnsupportedOperationException();
    }
    
    public void receiveShield(Shield s) {
        throw new UnsupportedOperationException();
    }
    
    public boolean manageHit(float receivedAttack) {
        throw new UnsupportedOperationException();
    }
    
    //Metodo toString
    public String toString() {
        return "Player{" + "name='" + name + '\'' + ", intelligence=" + intelligence + ", strength=" + strength + ", health=" + health + ", position=(" + row + "," + col + ")" + ", consecutiveHits=" + consecutiveHits + ", weapons=" + weapons + ", shields=" + shields + '}';
    }
}
