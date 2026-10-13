/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author A.Illeras
 */
public class Monster {
    //Atributos del monstruo
    private static final int INVALID_POS = -1;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    //Constructor parametros
    public Monster(String name, float intelligence, float strength){
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.row = INVALID_POS;
        this.col = INVALID_POS;
    }
    
    //Metodo si el monstruo está vivo o no
    public boolean dead(){
        return this.health <= 0;
    }
    
    //Ataque del monstruo 
    public float attack(){
        return Dice.intensity(this.strength);
    }
    
    //Actualizar la posicion del monstruo
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    //Monstruo herido
    public void gotWounded(){
        this.health -= 1;
    }
    
    //Metodo de defensa del monstruo
    public boolean defend(float receivedAttack){
        throw new UnsupportedOperationException(); //para que falle a proposito
    }
    
    //Metodo toString
    public String toString(){
        return "Monster{" + "name=' " + name + '\'' + ", intelligence=" + intelligence + ", strength=" + strength + ", health=" + health + ", position=(" + row + "," + col + ")" + '}';
    }
}
