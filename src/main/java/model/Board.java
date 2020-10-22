package model;


import system.SystemService;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Board {
   private Object[][] gameBoard;
   private int mines = 0;

    @Size(min = 2, max = 6)
    public Board(int size) {
        super();
        gameBoard = new Object[size][size];
        initializeBoard(gameBoard);
    }

    public Board() {
        super();
    }

    public List<Box> getGameBoardAsList (){
        List <Box> boxList = new ArrayList<Box>();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                Box box = ((Box) this.gameBoard[i][j]);
                box.setPosi(i);
                box.setPosj(j);
                boxList.add(box);
            }
        }

       return boxList;
    }

    /**
     * Initializes the board, adding all needed settings to use it
     *
     * @param gameBoard
     */
    private void initializeBoard(Object[][] gameBoard) {
        System.out.println("Initializing board with length " + gameBoard.length);

        // add boxes

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                Box box = new Box();

                //count number of mines on board
                if (box.getMine() == Boolean.TRUE) {
                    this.mines++;
                }
                //setting no mine when mines on board surpass calculation
                if ((this.mines) > ((gameBoard.length * 2) / 3) + 1) box.setMine(Boolean.FALSE);

                box.setPosi(i);
                box.setPosj(j);
                gameBoard[i][j] = box;

            }
        }

        // set mines number around each box
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                ((Box) gameBoard[i][j]).setMinesAround(calculateMinesAround(i, j, gameBoard.length));

            }
        }
    }

    /**
     * calculates mines around a box
     *
     * @param i
     * @param j
     * @return
     */
    private int calculateMinesAround(int i, int j, int length) {

        int count = 0;
        // look for valid positions --
        if ((i - 1 >= 0) && (j-1 >= 0 )){
            if (((Box) gameBoard[i - 1][j - 1]).getMine()) count ++;
        }
        // look for valid positions = -
        if ((i >= 0) && (j-1 >= 0 )){
            if (((Box) gameBoard[i][j - 1]).getMine()) count ++;
        }
        // look for valid positions + -
        if ((i +1 < length) && (j-1 >= 0 )){
            if (((Box) gameBoard[i + 1][j - 1]).getMine()) count ++;
        }
        // look for valid positions - =
        if ((i - 1 >= 0) && (j >= 0 )){
            if (((Box) gameBoard[i - 1][j]).getMine()) count ++;
        }
        // look for valid positions + =
        if ((i + 1 < length) && (j >= 0 )){
            if (((Box) gameBoard[i + 1][j]).getMine()) count ++;
        }
        // look for valid positions - +
        if ((i - 1 >= 0) && (j + 1 < length )){
            if (((Box) gameBoard[i - 1][j + 1]).getMine()) count ++;
        }
        // look for valid positions = +
        if ((i >= 0) && (j + 1 < length)){
           // System.out.println("posi "+i+" posj "+j);
            if (((Box) gameBoard[i][j + 1]).getMine()) count ++;
        }
        // look for valid positions + +
        if ((i + 1 < length) && (j + 1 < length )){
            if (((Box) gameBoard[i + 1][j + 1]).getMine()) count ++;
        }
            return count;
    }



    /**
     * Prints mines on board
     */
    public void printMines (){
        System.out.println("Printing mines on Board");
        System.out.println("");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                String result ;
                if ( ((Box) (this.gameBoard[i][j])).getMine().booleanValue() == Boolean.TRUE)  result ="x"; else result = " ";
                System.out.print("[" +result+"]");
            }
            System.out.println();
        }
        System.out.println();
        printAroundMines();
    }

    /**
     * Prints mines on board
     */
    public void printUserBoard (){
        System.out.println("Printing user Board");
        System.out.println("");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                String result ;
                if ( ((Box) (this.gameBoard[i][j])).getHidden() == Boolean.TRUE) {
                    result ="H";
                } else {
                    result = String.valueOf(( (Box) (this.gameBoard[i][j])).minesAround) ;
                };
                System.out.print("[" +result+"]");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints the number of mines around the box
     */
    public void printAroundMines (){
        System.out.println("Printing number of mines around the box");
        System.out.println("");
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                int result =  ( (Box) (this.gameBoard[i][j])).getMinesAround();

                System.out.print("[" +result+"]");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * left click on selected position
     * @param userName
     * @param pos_i
     * @param pos_j
 *
     */
    public boolean leftclick(String userName, int pos_i, int pos_j) {

        ((Box)gameBoard[pos_i][pos_j]).setHidden(Boolean.FALSE);
        if (  ((Box)gameBoard[pos_i][pos_j]).getMine() ) {
            System.out.println("MINE EXPLODED!!!!!!!!!!!!!!!!!");
            return Boolean.TRUE;
        }
        unHiddenBoxes(pos_i,pos_j);
        return Boolean.FALSE;
    }

    /**
     * left click on selected position to put flag
     * @param pos_i
     * @param pos_j
     *
     */
    public void rightclick(int pos_i, int pos_j) {

        BoxStatus actualStatus = ((Box)gameBoard[pos_i][pos_j]).getStatus();

        System.out.println("actualstatus");
        System.out.println(actualStatus);

        ((Box)gameBoard[pos_i][pos_j]).setStatus(actualStatus.getNext());

        System.out.println("newstatus");
        System.out.println(((Box)gameBoard[pos_i][pos_j]).getStatus());

    }

    /**
     * Propagates the unhide after clicking a box
     * @param pos_i
     * @param pos_j
     */
    private void unHiddenBoxes(int pos_i, int pos_j) {
       if ( !((Box)gameBoard[pos_i][pos_j]).getMine() &&   ((Box) gameBoard[pos_i][pos_j]).getHidden() == Boolean.TRUE) {

           ((Box) gameBoard[pos_i][pos_j]).setHidden(Boolean.FALSE);

           //valid position --
           if (pos_i - 1 >= 0 && pos_j - 1 >= 0) {
               unHiddenBoxes(pos_i - 1, pos_j - 1);
           }
           //valid position +-
           if (pos_i + 1 < gameBoard.length && pos_j - 1 >= 0) {
               unHiddenBoxes(pos_i + 1, pos_j - 1);
           }
           //valid position -+
           if (pos_i - 1 >= 0 && pos_j + 1 < gameBoard.length) {
               unHiddenBoxes(pos_i - 1, pos_j + 1);
           }
           //valid position ++
           if (pos_i + 1 < gameBoard.length && pos_j + 1 < gameBoard.length) {
               unHiddenBoxes(pos_i + 1, pos_j + 1);
           }
       }
    }




    /**
     * Checks if the user won
     * @return
     */
    public Boolean checkWin() {
        // hidden places = number of mines return true
        int hiddenPlaces = 0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

               if (( (Box) (this.gameBoard[i][j])).getHidden() ) hiddenPlaces++;


            }
        }
        System.out.println("Hidden Places: "+ hiddenPlaces);
        return (hiddenPlaces == ( (gameBoard.length * 2) / 3) + 1) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Object[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Object[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }
}

