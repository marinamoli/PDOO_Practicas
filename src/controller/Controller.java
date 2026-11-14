package controller;

import irrgarten.Game;
import ui.TextUI;

public class Controller {
    private Game game;
    private TextUI view;

    public Controller(Game game, TextUI view) {
        this.game = game;
        this.view = view;
    }

    public void play() {
        boolean end = false;
        while (!end) {
            view.showGame(game.getGameState());
            end = game.nextStep(view.nextMove());
        }
        view.showGame(game.getGameState());
        System.out.println("¡JUEGO FINALIZADO!");
    }
}