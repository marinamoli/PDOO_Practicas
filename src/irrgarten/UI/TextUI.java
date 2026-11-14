package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;
import java.util.Scanner;

public class TextUI {
    private static Scanner in = new Scanner(System.in);

    private char readChar() {
        String s = in.nextLine();
        return s.isEmpty() ? ' ' : s.charAt(0);
    }

    public Directions nextMove() {
        System.out.print("Where? (w: UP, s: DOWN, a: LEFT, d: RIGHT): ");

        Directions direction = Directions.DOWN;
        boolean gotInput = false;

        while (!gotInput) {
            char c = readChar();
            switch (c) {
                case 'w':
                    System.out.println(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                    break;
                case 's':
                    System.out.println(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                    break;
                case 'd':
                    System.out.println(" RIGHT\n");
                    direction = Directions.RIGHT;
                    gotInput = true;
                    break;
                case 'a':
                    System.out.println(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;
                    break;
                default:
                    System.out.print("Entrada no válida. Usa w, a, s o d: ");
            }
        }
        return direction;
    }

    public void showGame(GameState gameState) {
        System.out.println("\n========== IRRGARTEN ==========");
        System.out.println("--- LABERINTO ---\n" + gameState.getLabyrinth());
        System.out.println("--- JUGADORES ---\n" + gameState.getPlayers());
        System.out.println("--- MONSTRUOS ---\n" + gameState.getMonsters());
        System.out.println("Turno del jugador: " + gameState.getCurrentPlayer());
        if (gameState.isWinner()) {
            System.out.println("¡Hay un ganador! El jugador " + gameState.getCurrentPlayer()
                    + " ha llegado a la salida.");
        }
        System.out.println("--- LOG ---\n" + gameState.getLog());
        System.out.println("===============================");
    }
}
