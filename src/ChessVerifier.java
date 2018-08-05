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

    private String[] getAvailableMoves(String pieceLocation, ChessBoard board) throws Exception {

        int[] pieceCoordinates = board.getCoordinatesFromLocationCode(pieceLocation);
        String pieceCode = board.getPieceAtCoord(pieceCoordinates[0], pieceCoordinates[1]);

        ArrayList<String> availableMoves = new ArrayList<String>();
getStraightLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0))
        // First, branch by piece type
        if(pieceCode.endsWith("K"))
        {
            // move one in any unoccupied direction, as long as there are no
            // enemy pieces which could capture following that move
            // essentially going to need to run the engine for every piece
            // on the board to ensure there are no capture moves available
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

            return (String[]) availableMoves.toArray();
        }

        else if(pieceCode.endsWith("N"))
        {
            // this is the Knight -- figuring out the
            // weird jumping pattern here will be annoying
        }

        else if(pieceCode.endsWith("B"))
        {
            // bishop -- diagonals
            return (String[]) getDiagonalLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0))
                    .toArray();
        }

        else if(pieceCode.endsWith("R"))
        {
            // rook -- just up and down
            return (String[]) getStraightLineMovement(
                    pieceCoordinates[0],
                    pieceCoordinates[1],
                    board,
                    pieceCode.charAt(0))
                    .toArray();
        }

        else if(pieceCode.endsWith("P"))
        {
            // pawn -- forward in one direction
            // diagonal capture, two movements if in original place
        }

        else
        {
            throw new Exception("Unknown piece type: " + pieceCode.substring(1));
        }

        // TODO: DELETE THIS ONCE IMPLEMENTED
        return (String[]) availableMoves.toArray();
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
}