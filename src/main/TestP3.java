package main;

import irrgarten.Controller.Controller;
import irrgarten.Game;
import irrgarten.UI.TextUI;

public class TestP3 {
    public static void main(String[] args) {
        TextUI view = new TextUI();
        Game game = new Game(2);
        Controller controller = new Controller(game, view);
        controller.play();
    }
}
