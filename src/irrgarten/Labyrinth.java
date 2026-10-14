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
public class Labyrinth {
    //Atributos clase
    private static final char BLOCK_CHAR = 'X';//Para los muros
    private static final char EMPTY_CHAR = '-';//Casilla vacía
    private static final char MONSTER_CHAR = 'M';//Monstruo
    private static final char COMBAT_CHAR = 'C';//Combate monstruo-jugador
    private static final char EXIT_CHAR = 'E';//Salida del laberinto
    
    private static final int ROW = 0;
    private static final int COL = 1;
    
    private int nRows;//N total de filas
    private int nCols;//N total de columnas
    private int exitRow;//Fila en la que está la salida
    private int exitCol;//Columna en la que está la salida
    
    private Monster[][] monsters;//TAblero de monstruos
    private Player[][] players;//Tablero de jugadores
    private char[][] labyrinth;//Tablero del laberinto con las letras en las casillas
    
    //Constructor parametros
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        
        //Creo las matrices
        this.monsters = new Monster[nRows][nCols];
        this.players = new Player[nRows][nCols];
        this.labyrinth = new char[nRows][nCols];
        
        //Relleno vacio
        for(int i=0; i<nRows; i++){
            for(int j=0; j<nCols; j++){
                this.labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        
        //Coloco salida del laberinto
        this.labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }
    
    //Hay jugador en la casilla de salida
    public boolean haveAWinner(){
        return this.players[exitRow][exitCol] != null;
    }
    
    //Metodo toString
    public String toString() {
        String res = "Labyrinth:\n";
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                res += this.labyrinth[i][j] + " ";
            }
            res += "\n"; // Salto de línea al terminar una fila
        }
        return res;
    }
    
    //MEtodo para añadir un monstruo
    public void addMonster(int row, int col, Monster monster){
        if(this.posOK(row, col) && this.emptyPos(row, col)){
            this.labyrinth[row][col] = MONSTER_CHAR;//Coloco monstruo
            this.monsters[row][col] = monster;//GUardo el monstruo en el tablero de monstruos
            monster.setPos(row, col);
        }
    }
    
    //Metodo que comprueba si una posicion es factible o no
    public boolean posOK(int row, int col){
        return (row >= 0 && row < this.nRows && col >= 0 && col < this.nCols);
    }
    
    //Comprueba si la casilla esta vacia
    public boolean emptyPos(int row, int col){
        return this.labyrinth[row][col] == EMPTY_CHAR;
    }
    
    //Comprueba si en esa casilla hay monstruo
    public boolean monsterPos(int row, int col){
        return this.labyrinth[row][col] == MONSTER_CHAR;
    }
    
    //Comprueba si es casilla de salida
    public boolean exitPos(int row, int col){
        return this.labyrinth[row][col] == EXIT_CHAR;
    }
    
    //Comprueba si es casilla de combate
    public boolean combatPos(int row, int col){
        return this.labyrinth[row][col] == COMBAT_CHAR;
    }
    
    //Comprueba si la casilla se puede pisar (esta dentro y es vacia, monstruo o salida)
    public boolean canStepOn(int row, int col){
        return this.posOK(row, col) && (this.emptyPos(row, col) || this.monsterPos(row, col) || this.exitPos(row, col));
    }
    
    //Actualizar casilla tras movimiento
    public void updateOldPos(int row, int col){
        if(this.posOK(row, col)){
            if(this.combatPos(row, col)){
                this.labyrinth[row][col] = MONSTER_CHAR;
            }else{
                this.labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }
    
    //Calcula la posicion a la que nos moveriamos 
    public int[] dir2Pos(int row, int col, Directions direction){
        int[] nextPos = new int[2];
        nextPos[ROW] = row;
        nextPos[COL] = col;
        
        //dependiendo de hacia donde miremos
        switch(direction){
            case UP: nextPos[ROW] -= 1; break;
            case DOWN: nextPos[ROW] += 1; break;
            case RIGHT: nextPos[COL] += 1; break;
            case LEFT: nextPos[COL] -= 1; break;
        }
        return nextPos;
    }
    
    //Busca una casilla vacia al azar
    public int[] randomEmptyPos(){
        int[] pos = new int[2];
        //solo salgo cuando encuentre una casilla vacia
        while(true){
            int randomRow = Dice.randomPos(this.nRows);//una fila al azar
            int randomCol = Dice.randomPos(this.nCols);//una columan al azar
            
            if(this.emptyPos(randomRow, randomCol)){
                pos[ROW] = randomRow;
                pos[COL] = randomCol;
                break;
            }
        }
        return pos;
    }
    
    //METODOS PRACTICA 3
    public void spreadPlayers(ArrayList<Player> players) {
        throw new UnsupportedOperationException();
    }
    
    public Monster putPlayer(Directions direction, Player player) {
        throw new UnsupportedOperationException();
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException();
    }
    
    public ArrayList<Directions> validMoves(int row, int col) {
        throw new UnsupportedOperationException();
    }
    
    public Monster putPlayer2D() {
        throw new UnsupportedOperationException();
    }
}
