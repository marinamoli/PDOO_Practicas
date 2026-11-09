package ui;

import irrgarten.Directions;
import irrgarten.GameState;
import java.util.Scanner;

public class TextUI {
    private Scanner scanner = new Scanner(System.in);

    public Directions nextMove() {
        System.out.print("Introduce dirección (W: UP, S: DOWN, A: LEFT, D: RIGHT): ");
        String c = scanner.nextLine().toUpperCase();
        switch (c) {
            case "W": return Directions.UP;
            case "S": return Directions.DOWN;
            case "A": return Directions.LEFT;
            case "D": return Directions.RIGHT;
            default: return Directions.RIGHT;
        }
    }

    public void showGame(GameState gameState) {
        System.out.println("\n========== ESTADO DEL JUEGO ==========");
        System.out.println("LABERINTO:\n" + gameState.getLabyrinth());
        System.out.println("JUGADORES:\n" + gameState.getPlayers());
        System.out.println("MONSTRUOS:\n" + gameState.getMonsters());
        System.out.println("TURNO: Jugador #" + gameState.getCurrentPlayer());
        System.out.println("LOGS:\n" + gameState.getLog());
        System.out.println("======================================");
    }
}