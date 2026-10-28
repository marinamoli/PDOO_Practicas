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
public class Game {
    
    // Atributos privados 
    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;

    // Constructor parametros
    public Game(int nPlayers) {
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.log = "";

        // Crear los jugadores y añadirlos 
        for (int i = 0; i < nPlayers; i++) {
            this.players.add(new Player((char) ('0' + i), Dice.randomIntelligence(), Dice.randomStrength()));
        }

        // Determinar empieza y fijar el jugador con turno
        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        this.currentPlayer = this.players.get(this.currentPlayerIndex);

        // Iniciar un laberinto 
        this.labyrinth = new Labyrinth(5, 5, 4, 4);

        try {
            configureLabyrinth();
        } catch (UnsupportedOperationException e) {}
        
        try {
            labyrinth.spreadPlayers(this.players);
        } catch (UnsupportedOperationException e) {}
    }

    // Si hay un ganador
    public boolean finished() {
        return labyrinth.haveAWinner();
    }

    // Genera una instancia de GameState 
    public GameState getGameState() {
        String labyrinthString = labyrinth.toString();
        
        String playersString = "Players:\n";
        for (Player p : players) {
            playersString += p.toString() + "\n";
        }
        
        String monstersString = "Monsters:\n";
        for (Monster m : monsters) {
            monstersString += m.toString() + "\n";
        }
        
        return new GameState(labyrinthString, playersString, monstersString, currentPlayerIndex, finished(), log);
    }

    // Configura el laberinto 
    public void configureLabyrinth() {
        // Inserción de un monstruo
        Monster m1 = new Monster("Minotauro", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(m1);
        try {
            labyrinth.addMonster(2, 2, m1); 
        } catch (Exception e) {} 
    }

    // Actualiza atributos
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    // Métodos para actualizar información
    public void logPlayerWon() {
        log += "El jugador ha ganado el combate.\n";
    }

    public void logMonsterWon() {
        log += "El monstruo ha ganado el combate.\n";
    }

    public void logResurrected() {
        log += "El jugador ha resucitado.\n";
    }

    public void logPlayerSkipTurn() {
        log += "El jugador ha perdido el turno por estar muerto.\n";
    }

    public void logPlayerNoOrders() {
        log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible).\n";
    }

    public void logNoMonster() {
        log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse.\n";
    }

    public void logRounds(int rounds, int max) {
        log += "Se han producido " + rounds + " de " + max + " rondas de combate.\n";
    }

    
    //Métodos a implementar en la P3
    public boolean nextStep(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }

    public Directions actualDirection(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }

    public GameCharacter combat(Monster monster) {
        throw new UnsupportedOperationException();
    }

    public void manageReward(GameCharacter winner) {
        throw new UnsupportedOperationException();
    }

    public void manageResurrection() {
        throw new UnsupportedOperationException();
    }
}
