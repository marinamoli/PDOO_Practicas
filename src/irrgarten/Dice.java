/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author marin
 */
public class Dice {
    
    private static int MAX_USES = 5 ;//(número máximo de usos de armas y escudos)
    private static final float MAX_INTELLIGENCE = 10.0; // (valor máximo para la inteligencia de jugadores y monstruos)
    private static final float MAX_STRENGTH = 10.0; // (valor máximo para la fuerza de jugadores y monstruos)
    private static final float RESURRECT_PROB = 0.3;// (probabilidad de que un jugador sea resucitado en cada turno)
    private static final int WEAPONS_REWARD = 2 ;//(numero máximo de armas recibidas al ganar un combate)
    private static final int SHIELDS_REWARD = 3; // (numero máximo de escudos recibidos al ganar un combate)
    private static final int HEALTH_REWARD = 5; // (numero máximo de unidades de salud recibidas al ganar un combate)
    private static final int MAX_ATTACK = 3 ; //(máxima potencia de las armas)
    private static final int MAX_SHIELD = 2; // (máxima potencia de los escudos)
    
    
    private static Random generator ;
    
    
    public static int randomPos(int max){
        return 0;
        
    }
    
    public static int whoStarts(int nplayers){
        return 0;
    }
    
    
    public static float randomIntelligence(){
        return 0;
    }
    
    public static float randomStrength(){
        return 0;
    }
    
    public static boolean resurrectPlayer(){
        return false;
    }
    
    public static int weaponsReward(){
        return 0;
    }
    
    public static int shieldsReward(){
        return 0;
    }
    
    public static int healthReward(){
        return 0;
    }
    
    public static float weaponPower(){
        return 0;
    }
    
    public static float shieldPower(){
        return 0;
    }
    
    public static int usesLeft(){
        return 0;
    }
    
    public static float intensity(float competence){
        return 0;
        
    }
    
    public static boolean discardElement(int usesLeft){
        return false;
    }
}
