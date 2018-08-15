/*
    Jack Stillwell
    Fillipos Volkolos
    CS576 Term Project "Chess"
    23 July 2018
 */

import java.util.ArrayList;

public class ChessVerifier {

    public static void main(String[] args)
    {

    }

    private ArrayList<String> getAvailableMoves(String pieceLocation, ChessBoard board) throws Exception {

        int[] pieceCoordinates = board.getCoordinatesFromLocationCode(pieceLocation);
        String pieceCode = board.getPieceAtCoord(pieceCoordinates[0], pieceCoordinates[1]);

        ArrayList<String> availableMoves = new ArrayList<String>();

        // First, branch by piece type
        if(pieceCode.endsWith("K"))
        {
            // move one in any unoccupied direction, as long as there are no
            // enemy pieces which could capture following that move
            // essentially going to need to run the engine for every piece
            // on the board to ensure there are no capture moves available

            ArrayList<String> restrictedMoves = new ArrayList<>();
            availableMoves.addAll(board.getValidLocations());

            // get all moves for every enemy piece on the board and add to arraylist
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    if(board.getPieceAtCoord(i,j).charAt(0) != pieceCode.charAt(0))
                    {
                        restrictedMoves.addAll(getAvailableMoves(board.getLocationCodeFromCoordinates(i,j), board));
                    }
                }
            }

            // get the difference of that arraylist and all spaces on the board
            availableMoves.removeAll(restrictedMoves);

            // of those remaining, return every piece within 1 of the king on either row or col
            for(String locationCode : availableMoves)
            {
                int[] pieceCoords = board.getCoordinatesFromLocationCode(locationCode);

                if(!validKingMovement(pieceCoordinates[0], pieceCoordinates[1],pieceCoords[0],pieceCoords[1]))
                {
                    availableMoves.remove(locationCode);
                }
            }

            return availableMoves;
        }

        else if(pieceCode.endsWith("Q"))
        {
            // can move in any direction, ending in an occupied space
            ArrayList<String> diagMoves = getDiagonalLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));

            ArrayList<String> straightMoves = getStraightLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));

            availableMoves.addAll(diagMoves);
            availableMoves.addAll(straightMoves);

            return availableMoves;
        }

        else if(pieceCode.endsWith("N"))
        {
            return getKnightMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));
        }

        else if(pieceCode.endsWith("B"))
        {
            // bishop -- diagonals
            return getDiagonalLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));
        }

        else if(pieceCode.endsWith("R"))
        {
            // rook -- just up and down
            return getStraightLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));
        }

        else if(pieceCode.endsWith("P"))
        {
            return getPawnMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0));
        }

        else
        {
            throw new Exception("Unknown piece type: " + pieceCode.substring(1));
        }
    }

    @SuppressWarnings("Duplicates")
    private ArrayList<String> getStraightLineMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<String>();

        // go from current position positive along row
        for(int i = col+1; i < 8; i++)
        {
            if(board.getPieceAtCoord(row, i).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(row, i));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(row,i).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, i));
                break;
            }
        }

        // go from current position negative along row
        for(int i = col-1; i >= 0; i--)
        {
            if(board.getPieceAtCoord(row, i).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(row, i));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(row,i).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, i));
                break;
            }
        }

        // go from current position positive along column
        for(int i = row+1; i < 8; i++)
        {
            if(board.getPieceAtCoord(i, col).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(i, col));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(i,col).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(i, col));
                break;
            }
        }

        // go from current position negative along column
        for(int i = row-1; i >= 0; i--)
        {
            if(board.getPieceAtCoord(i, col).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(i, col));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(i,col).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(i, col));
                break;
            }
        }

        return availableMoves;
    }

    @SuppressWarnings("Duplicates")
    private ArrayList<String> getDiagonalLineMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<String>();

        int localRow = row;
        int localCol = col;

        // go from current position to positive row positive col
        while(localRow < 8 && localCol < 8)
        {
            localRow++;
            localCol++;

            if(board.getPieceAtCoord(localRow, localCol).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(localRow, localCol));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(localRow,localCol).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(localRow,localCol));
                break;
            }
        }

        // reset between checks
        localRow = row;
        localCol = col;

        // go from current position to positive row negative col
        while(localRow < 8 && localCol >= 0)
        {
            localRow++;
            localCol--;

            if(board.getPieceAtCoord(localRow, localCol).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(localRow, localCol));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(localRow,localCol).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(localRow,localCol));
                break;
            }
        }

        // reset between checks
        localRow = row;
        localCol = col;

        // go from current position to negative row positive col
        while(localRow >= 0 && localCol < 8)
        {
            localRow--;
            localCol++;

            if(board.getPieceAtCoord(localRow, localCol).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(localRow, localCol));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(localRow,localCol).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(localRow,localCol));
                break;
            }
        }

        // reset between checks
        localRow = row;
        localCol = col;

        // go from current position to negative row negative col
        while(localRow >= 0 && localCol >= 0)
        {
            localRow--;
            localCol--;

            if(board.getPieceAtCoord(localRow, localCol).equals("x"))
            {
                availableMoves.add(board.getLocationCodeFromCoordinates(localRow, localCol));
            }
            else
            {
                // add capture move if not same color
                if(!board.getPieceAtCoord(localRow,localCol).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(localRow,localCol));
                break;
            }
        }

        return availableMoves;
    }

    @SuppressWarnings("Duplicates")
    private ArrayList<String> getPawnMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        // pawn -- forward in one direction
        // diagonal capture, two movements if in original place

        ArrayList<String> availableMoves = new ArrayList<String>();

        if(sideColor == 'W')
        {
            // allow double move if in original position
            if(col == 1)
            {
                if(board.getPieceAtCoord(row, 2).equals("x"))
                {
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, 2));

                    if(board.getPieceAtCoord(row, 3).equals("x"))
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row, 3));
                    }
                }
            }

            // check the space directly in front for empty, and the spaces to either side for
            // enemy pieces
            else
            {
                if(col + 1 < 8 && board.getPieceAtCoord(row, col + 1).equals("x"))
                {
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, col + 1));

                    if(row + 1 < 8 && board.getPieceAtCoord(row + 1, col + 1).charAt(0) == 'B')
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row + 1, col + 1));
                    }

                    if(row - 1 >= 0 && board.getPieceAtCoord(row - 1, col + 1).charAt(0) == 'B')
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row - 1, col + 1));
                    }
                }
            }
        }

        else if(sideColor == 'B')
        {
            // allow double move if in original position
            if(col == 6)
            {
                if(board.getPieceAtCoord(row, 5).equals("x"))
                {
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, 5));

                    if(board.getPieceAtCoord(row, 4).equals("x"))
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row, 4));
                    }
                }
            }

            // check the space directly in front for empty, and the spaces to either side for
            // enemy pieces
            else
            {
                if(col - 1 >= 0 && board.getPieceAtCoord(row, col - 1).equals("x"))
                {
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, col - 1));

                    if(row + 1 < 8 && board.getPieceAtCoord(row + 1, col - 1).charAt(0) == 'W')
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row + 1, col - 1));
                    }

                    if(row - 1 >= 0 && board.getPieceAtCoord(row - 1, col - 1).charAt(0) == 'W')
                    {
                        availableMoves.add(board.getLocationCodeFromCoordinates(row - 1, col - 1));
                    }
                }
            }
        }

        else
        {
            throw new Exception("Unknown Side Color: " + sideColor);
        }

        return availableMoves;
    }

    @SuppressWarnings("Duplicates")
    private ArrayList<String> getKnightMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<String>();

        //up 1 left 2
        if(row + 1 < 8 && col - 2 >= 0 &&
                (board.getPieceAtCoord(row + 1, col - 2).equals("x") ||
                 board.getPieceAtCoord(row + 1, col - 2).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row + 1, col - 2));
        }

        // up 2 left 1
        if(row + 2 < 8 && col - 1 >= 0 &&
                (board.getPieceAtCoord(row + 2, col - 1).equals("x") ||
                 board.getPieceAtCoord(row + 2, col - 1).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row + 2, col - 1));
        }

        // up 1 right 2
        if(row + 1 < 8 && col + 2 < 8 &&
                (board.getPieceAtCoord(row + 1, col + 2).equals("x") ||
                 board.getPieceAtCoord(row + 1, col + 2).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row + 1, col + 2));
        }

        // up 2 right 1
        if(row + 2 < 8 && col + 1 < 8 &&
                (board.getPieceAtCoord(row + 2, col + 1).equals("x") ||
                 board.getPieceAtCoord(row + 2, col + 1).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row + 2, col + 1));
        }

        // down 1 left 2
        if(row - 1 >= 0 && col - 2 >= 0 &&
                (board.getPieceAtCoord(row - 1, col - 2).equals("x") ||
                 board.getPieceAtCoord(row - 1, col - 2).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row - 1, col - 2));
        }

        // down 2 left 1
        if(row - 2 >= 0 && col - 1 >= 0 &&
                (board.getPieceAtCoord(row - 2, col - 1).equals("x") ||
                 board.getPieceAtCoord(row - 2, col - 1).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row - 2, col - 1));
        }

        // down 1 right 2
        if(row - 1 >= 0 && col + 2 < 8 &&
                (board.getPieceAtCoord(row - 1, col + 2).equals("x") ||
                 board.getPieceAtCoord(row - 1, col + 2).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row - 1, col + 2));
        }

        // down 2 right 1
        if(row - 2 >= 0 && col + 1 < 8 &&
                (board.getPieceAtCoord(row - 2, col + 1).equals("x") ||
                 board.getPieceAtCoord(row - 2, col + 1).charAt(0) !=  sideColor))
        {
            availableMoves.add(board.getLocationCodeFromCoordinates(row - 2, col + 1));
        }

        return availableMoves;
    }

    private boolean validKingMovement(int kingRow, int kingCol, int row, int col)
    {
        // Diagonal Movement
        if((kingRow - row == -1 || kingRow - row == 1) &&
           (kingCol - col == -1 || kingCol - col == 1))
        {
            return true;
        }

        else if(kingRow - row == 0 &&
           (kingCol - col == -1 || kingCol - col == 1))
        {
            return true;
        }

        else if((kingRow - row == -1 || kingRow - row == 1) &&
           kingCol - col == 0)
        {
            return true;
        }

        else
            return false;
    }
}