package irrgarten;

import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    
    private int currentPlayerIndex;
    private String log;
    
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;

    public Game(int nPlayers) {
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.log = "";
        
        for (int i = 0; i < nPlayers; i++) {
            char num = (char) ('0' + i);
            players.add(new Player(num, Dice.randomIntelligence(), Dice.randomStrength()));
        }
        
        this.currentPlayerIndex = Dice.whoStarts(nPlayers);
        configureLabyrinth();
        labyrinth.spreadPlayers(players);
    }

    public boolean finished() {
        return labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection) {
        log = "";
        Player currentPlayer = players.get(currentPlayerIndex);
        
        if (!currentPlayer.dead()) {
            Directions[] validMoves = labyrinth.validMoves(currentPlayer.getRow(), currentPlayer.getCol());
            Directions direction = currentPlayer.move(preferredDirection, validMoves);
            Monster monster = labyrinth.move(direction, currentPlayer);
            
            if (monster != null) {
                GameCharacter winner = combat(monster);
                if (winner == GameCharacter.PLAYER) {
                    currentPlayer.receiveReward();
                    logPlayerWon();
                } else {
                    logMonsterWon();
                }
            } else {
                logNoMonster();
            }
        } else {
            manageRewardGame();
        }
        
        boolean win = finished();
        if (!win) {
            nextPlayer();
        }
        return win;
    }

    public GameState getGameState() {
        StringBuilder pStr = new StringBuilder();
        for (Player p : players) pStr.append(p.toString()).append("\n");
        
        StringBuilder mStr = new StringBuilder();
        for (Monster m : monsters) mStr.append(m.toString()).append("\n");

        return new GameState(
            labyrinth.toString(),
            pStr.toString(),
            mStr.toString(),
            currentPlayerIndex,
            finished(),
            log
        );
    }

    private void configureLabyrinth() {
        this.labyrinth = new Labyrinth(5, 5, 4, 4);
        labyrinth.addBlock(Orientation.HORIZONTAL, 0, 1, 3);
        labyrinth.addBlock(Orientation.VERTICAL, 1, 1, 2);
        
        Monster m1 = new Monster("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(m1);
        labyrinth.addMonster(3, 3, m1);
    }

    private void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % players.size();
    }

    private GameCharacter combat(Monster monster) {
        int rounds = 0;
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean lose = monster.defend(currentPlayer.attack());
        
        while (!lose && rounds < MAX_ROUNDS) {
            rounds++;
            lose = currentPlayer.defend(monster.attack());
            if (!lose) {
                lose = monster.defend(currentPlayer.attack());
            }
        }
        
        logRounds(rounds, MAX_ROUNDS);
        return lose ? GameCharacter.PLAYER : GameCharacter.MONSTER;
    }

    private void manageRewardGame() {
        logResurrected();
        players.get(currentPlayerIndex).resurrect();
    }

    private void logPlayerWon() { log += "El jugador ha ganado el combate.\n"; }
    private void logMonsterWon() { log += "El monstruo ha ganado el combate.\n"; }
    private void logResurrected() { log += "El jugador ha resucitado.\n"; }
    private void logPlayerSkip() { log += "El jugador está muerto y salta el turno.\n"; }
    private void logNoMonster() { log += "El jugador se ha movido a una casilla vacía.\n"; }
    private void logRounds(int rounds, int max) { log += "Combate finalizado tras " + rounds + " rondas.\n"; }
}