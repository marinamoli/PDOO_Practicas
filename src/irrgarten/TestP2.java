/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author A.Illeras
 */
public class TestP2 {

    public static void main(String[] args) {
        // Arranco el juego con dos jugadores
        System.out.println("[*] Creando juego con 2 jugadores...");
        Game game = new Game(2);
        
        // Compruebo el estado inicial
        System.out.println("\n[*] Obteniendo GameState inicial:");
        GameState state = game.getGameState();
        System.out.println("- Turno del jugador (Índice del jugador): " + state.getCurrentPlayer());
        System.out.println("- Hay un ganador: " + state.isWinner());
        System.out.println("\n- Jugadores en partida:\n" + state.getPlayers());
        
        // Compruebo como va el cambio de turno
        System.out.println("[*] Pasando al siguiente turno...");
        game.nextPlayer();
        System.out.println("- Nuevo turno (Índice del jugador): " + game.getGameState().getCurrentPlayer());

        // Pruebo como genera la actualización de información de partida
        System.out.println("\n[*] Probando el sistema de registro de eventos...");
        game.logPlayerWon();
        game.logMonsterWon();
        game.logResurrected();
        game.logRounds(5, 10);
        
        System.out.println("- Estado actual:");
        System.out.println(game.getGameState().getLog());

        //System.out.println("[*] Comprobando protección de métodos de la P3 (UnsupportedOperationException)...");
        try {
            game.manageResurrection();
            System.err.println("[ERROR] manageResurrection no lanzó la excepción.");
        } catch (UnsupportedOperationException e) {
            System.out.println("[ÉXITO] manageResurrection lanzó UnsupportedOperationException correctamente.");
        }
    }
}