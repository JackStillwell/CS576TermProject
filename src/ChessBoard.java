/*

    Jack Stillwell

    x = blank space
    BK = black king
    BQ = black queen
    BB = black bishop
    BN = black knight
    BR = black rook
    BP = black pawn
    WK = white king
    WQ = white queen
    WB = white bishop
    WN = white knight
    WR = white rook
    WP = white pawn

 */

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {

    private ArrayList<String> validPieces;
    private ArrayList<String> validLocation;
    private String[][] board;

    public ChessBoard()
    {
        String[] validPieceArray = {"x",
                "BK", "BQ", "BB", "BN", "BR", "BP",
                "WK", "WQ", "WB", "WN", "WR", "WP"};

        validPieces = (ArrayList<String>) Arrays.asList(validPieceArray);

        String[] validLocationArray = {
                "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8",
                "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8",
                "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8",
                "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8",
                "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8",
                "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8",
                "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8",
                "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8"
        };

        validLocation = (ArrayList<String>) Arrays.asList(validLocationArray);

        board = new String[8][8];

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                board[i][j] = "x";
            }
        }
    }

    public void addPieceToBoard(String input) throws Exception
    {
        String pieceCode = input.substring(0,2);
        String locationCode = input.substring(2);

        /* Verify Correct Notation */

        // Do the first two characters represent a valid piece?
        if(!validPieces.contains(pieceCode))
        {
            throw new Exception("Invalid Input Piece Code: " + pieceCode);
        }

        /* Verify Not Too Many Pieces on Board */

        // Is the piece already on the board?
        // If K / Q only one of each type can exist
        // NOTE: Modified to allow more than one Queen based on the
        //       pawn exchange rule.
        if(pieceCode.endsWith("K") /* || pieceCode.endsWith("Q") */)
        {
            if(numberOfPiecesOnBoard(pieceCode) > 0)
                throw new Exception("Too many pieces already on board: " + pieceCode);
        }

        // If B / N / R only two of each type can exist
        // NOTE: Disabled to account for pawn exchange rule. The validity
        //       of the board is not a job of the program.
        /*
        if(pieceCode.endsWith("B") || pieceCode.endsWith("N") || pieceCode.endsWith("R"))
        {
            if(numberOfPiecesOnBoard(pieceCode) > 1)
                throw new Exception("Too many pieces already on board: " + pieceCode);
        }
        */

        // If P only 8 can exist
        if(pieceCode.endsWith("P"))
        {
            if(numberOfPiecesOnBoard(pieceCode) > 7)
                throw new Exception("Too many pieces already on board: " + pieceCode);
        }

        /* Verify Valid Location */

        // valid location code?
        if(!validLocation.contains(locationCode))
            throw new Exception("Invalid Location Code: " + locationCode);

        // location already occupied?
        int[] coordinates = getCoordinatesFromLocationCode(locationCode);
        if(!board[coordinates[0]][coordinates[1]].equals("x"))
            throw new Exception("Location Already Occupied: " + locationCode);

        /* Add to Board */
        board[coordinates[0]][coordinates[1]] = pieceCode;
    }

    private int numberOfPiecesOnBoard(String piece)
    {
        int numPieces = 0;
        for(String[] array : board)
        {
            for(String elem : array)
            {
                if(elem.equals(piece))
                    numPieces++;
            }
        }

        return numPieces;
    }

    private int[] getCoordinatesFromLocationCode(String locationCode) throws Exception {
        char firstCoordinate = locationCode.charAt(0);
        int secondCoordinate = Integer.parseInt(locationCode.substring(1));

        return new int[]{getCoordNumFromCoordChar(firstCoordinate), secondCoordinate};
    }

    private int getCoordNumFromCoordChar(char in) throws Exception {
        if(in == 'a')
            return 1;
        else if(in == 'b')
            return 2;
        else if(in == 'c')
            return 3;
        else if(in == 'd')
            return 4;
        else if(in == 'e')
            return 5;
        else if(in == 'f')
            return 6;
        else if(in == 'g')
            return 7;
        else if(in == 'h')
            return 8;
        else
            throw new Exception("Invalid Char to Int Coordinate Conversion: " + in);
    }

    private char getCoordCharFromCoordNum(int in) throws Exception {
        if(in == 1)
            return 'a';
        else if(in == 2)
            return 'b';
        else if(in == 3)
            return 'c';
        else if(in == 4)
            return 'd';
        else if(in == 5)
            return 'e';
        else if(in == 6)
            return 'f';
        else if(in == 7)
            return 'g';
        else if(in == 8)
            return 'h';
        else
            throw new Exception("Invalid Int to Char Coordinate Conversion: " + in);
    }
}