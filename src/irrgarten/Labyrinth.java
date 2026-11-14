package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private char[][] labyrinth;
    private Monster[][] monsters;
    private Player[][] players;

    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        this.labyrinth = new char[nRows][nCols];
        this.monsters = new Monster[nRows][nCols];
        this.players = new Player[nRows][nCols];

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                labyrinth[r][c] = EMPTY_CHAR;
            }
        }
        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }

    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow = (orientation == Orientation.VERTICAL) ? 1 : 0;
        int incCol = (orientation == Orientation.HORIZONTAL) ? 1 : 0;
        
        int r = startRow;
        int c = startCol;
        
        while (posOK(r, c) && emptyPos(r, c) && length > 0) {
            labyrinth[r][c] = BLOCK_CHAR;
            r += incRow;
            c += incCol;
            length--;
        }
    }

    public Monster move(Directions direction, Player player) {
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
        return monster;
    }

    public Directions[] validMoves(int row, int col) {
        ArrayList<Directions> output = new ArrayList<>();
        if (canStepOn(row - 1, col)) output.add(Directions.UP);
        if (canStepOn(row + 1, col)) output.add(Directions.DOWN);
        if (canStepOn(row, col - 1)) output.add(Directions.LEFT);
        if (canStepOn(row, col + 1)) output.add(Directions.RIGHT);
        
        return output.toArray(new Directions[0]);
    }

    private boolean posOK(int row, int col) {
        return row >= 0 && row < nRows && col >= 0 && col < nCols;
    }

    private boolean emptyPos(int row, int col) {
        return labyrinth[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col) {
        return labyrinth[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col) {
        return labyrinth[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col) {
        return labyrinth[row][col] == 'C';
    }

    private boolean canStepOn(int row, int col) {
        return posOK(row, col) && (emptyPos(row, col) || monsterPos(row, col) || exitPos(row, col));
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            if (combatPos(row, col)) {
                labyrinth[row][col] = MONSTER_CHAR;
            } else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction) {
        int[] next = {row, col};
        switch (direction) {
            case UP:    next[ROW]--; break;
            case DOWN:  next[ROW]++; break;
            case LEFT:  next[COL]--; break;
            case RIGHT: next[COL]++; break;
        }
        return next;
    }

    private int[] randomEmptyPos() {
        int r, c;
        do {
            r = Dice.randomPos(nRows);
            c = Dice.randomPos(nCols);
        } while (!emptyPos(r, c));
        return new int[]{r, c};
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        Monster output = null;
        if (canStepOn(row, col)) {
            if (posOK(oldRow, oldCol)) {
                Player p = players[oldRow][oldCol];
                if (p == player) {
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            if (monsterPos(row, col)) {
                labyrinth[row][col] = 'C';
                output = monsters[row][col];
            } else {
                labyrinth[row][col] = player.getNumber();
            }
            players[row][col] = player;
            player.setPos(row, col);
        }
        return output;
    }

    public void spreadPlayers(ArrayList<Player> playersList) {
        for (Player p : playersList) {
            int[] pos = randomEmptyPos();
            putPlayer2D(-1, -1, pos[ROW], pos[COL], p);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                sb.append(labyrinth[r][c]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}