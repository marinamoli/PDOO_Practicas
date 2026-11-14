package irrgarten;

import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;

    private int currentPlayerIndex;
    private String log;

    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;

    public Game(int nplayers) {
        players = new ArrayList<>();
        for (int i = 0; i < nplayers; i++) {
            char number = (char) ('0' + i);
            players.add(new Player(number, Dice.randomIntelligence(), Dice.randomStrength()));
        }

        currentPlayerIndex = Dice.whoStarts(nplayers);
        currentPlayer = players.get(currentPlayerIndex);

        monsters = new ArrayList<>();
        log = "";

        configureLabyrinth();
        labyrinth.spreadPlayers(players);
    }

    public boolean finished() {
        return labyrinth.haveAWinner();
    }

    // Diagrama Game__nextStep
    public boolean nextStep(Directions preferredDirection) {
        log = "";
        boolean dead = currentPlayer.dead();

        if (!dead) {
            Directions direction = actualDirection(preferredDirection);
            if (direction != preferredDirection) {
                logPlayerNoOrders();
            }

            Monster monster = labyrinth.putPlayer(direction, currentPlayer);

            if (monster == null) {
                logNoMonster();
            } else {
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        } else {
            manageResurrection();
        }

        boolean endGame = finished();
        if (!endGame) {
            nextPlayer();
        }
        return endGame;
    }

    public GameState getGameState() {
        String playersStr = "";
        for (Player p : players) {
            playersStr += p.toString() + "\n";
        }

        String monstersStr = "";
        for (Monster m : monsters) {
            monstersStr += m.toString() + "\n";
        }

        return new GameState(labyrinth.toString(), playersStr, monstersStr,
                currentPlayerIndex, finished(), log);
    }

    private void configureLabyrinth() {
        labyrinth = new Labyrinth(7, 9, 6, 8);

        labyrinth.addBlock(Orientation.HORIZONTAL, 1, 1, 3);
        labyrinth.addBlock(Orientation.VERTICAL, 3, 4, 3);
        labyrinth.addBlock(Orientation.HORIZONTAL, 5, 6, 2);

        Monster goblin = new Monster("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(goblin);
        labyrinth.addMonster(0, 4, goblin);

        Monster orco = new Monster("Orco", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(orco);
        labyrinth.addMonster(3, 2, orco);

        Monster troll = new Monster("Troll", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(troll);
        labyrinth.addMonster(4, 7, troll);
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    // Diagrama Game__actualDirection
    private Directions actualDirection(Directions preferredDirection) {
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        Directions[] validMoves = labyrinth.validMoves(currentRow, currentCol);
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        return output;
    }

    // Diagrama Game__combat
    private GameCharacter combat(Monster monster) {
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;

        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);

        while (!lose && rounds < MAX_ROUNDS) {
            winner = GameCharacter.MONSTER;
            rounds++;

            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);

            if (!lose) {
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }

        logRounds(rounds, MAX_ROUNDS);
        return winner;
    }

    // Diagrama Game__manageReward
    private void manageReward(GameCharacter winner) {
        if (winner == GameCharacter.PLAYER) {
            currentPlayer.receiveReward();
            logPlayerWon();
        } else {
            logMonsterWon();
        }
    }

    // Diagrama Game__manageResurrection
    private void manageResurrection() {
        boolean resurrect = Dice.resurrectPlayer();
        if (resurrect) {
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
    }

    private void logPlayerWon() {
        log += "El jugador ha ganado el combate.\n";
    }

    private void logMonsterWon() {
        log += "El monstruo ha ganado el combate.\n";
    }

    private void logResurrected() {
        log += "El jugador ha resucitado.\n";
    }

    private void logPlayerSkipTurn() {
        log += "El jugador ha perdido el turno por estar muerto.\n";
    }

    private void logPlayerNoOrders() {
        log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible).\n";
    }

    private void logNoMonster() {
        log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse.\n";
    }

    private void logRounds(int rounds, int max) {
        log += "Se han producido " + rounds + " de " + max + " rondas de combate.\n";
    }
}
