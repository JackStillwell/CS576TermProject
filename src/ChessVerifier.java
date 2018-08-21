/*
    Jack Stillwell
    Filippos Vokolos
    CS576 Term Project "Chess"
    23 July 2018
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ChessVerifier {

    public static void main(String[] args)
    {

    }

    String testMain(String inputFileName)
    {
        try {
            FileInterpreter fi = new FileInterpreter(inputFileName);
            ChessBoard board = fi.constructBoardFromFile();
            String piece = fi.retrievePieceToCalculateFromFile();
            String pieceLocation = piece.substring(1);

            ArrayList<String> availableMoves = getAvailableMoves(pieceLocation, board);

            StringBuilder returnStringBuilder = new StringBuilder();
            returnStringBuilder.append("LEGAL MOVES FOR ").append(piece).append(":");

            for(String s : availableMoves)
            {
                returnStringBuilder.append(" ").append(s).append(",");
            }

            returnStringBuilder.deleteCharAt(returnStringBuilder.lastIndexOf(","));

            return returnStringBuilder.toString();
        }
        catch(Exception x)
        {
            return "ERROR: " + x.getMessage();
        }
    }

    private ArrayList<String> getAvailableMoves(String pieceLocation, ChessBoard board) throws Exception {

        int[] pieceCoordinates = board.getCoordinatesFromLocationCode(pieceLocation);
        String pieceCode = board.getPieceAtCoord(pieceCoordinates[0], pieceCoordinates[1]);

        ArrayList<String> availableMoves = new ArrayList<>();

        // First, branch by piece type
        if(pieceCode.endsWith("K"))
        {
            // move one in any unoccupied direction, as long as there are no
            // enemy pieces which could capture following that move

            // new plan -- execute all possible moves for the king and
            // use those new boards to calculate if the king would be
            // in danger, if so remove that option

            char oppositeSide = 'B';

            if(pieceCode.startsWith("B"))
                oppositeSide = 'W';

            // Step 1: Calculate Possible Moves

            ArrayList<String> possibleMoves = getKingMoves(pieceCoordinates[0], pieceCoordinates[1], board, pieceCode.charAt(0));

            // Step 2: Use those moves to build all possible boards after the move

            ArrayList<ChessBoard> possibleBoards = new ArrayList<>();

            for(String locationCode : possibleMoves)
            {
                String[][] rawBoard = board.getRawBoardArray();
                int[] coords = board.getCoordinatesFromLocationCode(locationCode);

                // put the king in the possible position
                rawBoard[coords[0]][coords[1]] = pieceCode;

                // fill in the space left behind
                rawBoard[pieceCoordinates[0]][pieceCoordinates[1]] = "x";

                possibleBoards.add(new ChessBoard(rawBoard));
            }

            // Step 3: ensure the king is not in danger in any
            // of the possible boards, if it is, remove that board

            possibleBoards.removeIf(predicateIsPieceInDanger(pieceLocation, oppositeSide));

            // Step 4: find the locations of the king in the surviving boards
            // and add those locations to availableMoves

            for( ChessBoard possibleBoard : possibleBoards)
            {
                int[] kingCoords = possibleBoard.getCoordsOfPiece(pieceCode);

                availableMoves.add(board.getLocationCodeFromCoordinates(kingCoords[0],kingCoords[1]));
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

    private ArrayList<String> getStraightLineMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<>();

        // go from current position positive along row
        straightLineMovementLoop(col, row, availableMoves, board, sideColor, true, col+1, false);

        // go from current position negative along row
        straightLineMovementLoop(col, row, availableMoves, board, sideColor, false, col-1, false);

        // go from current position positive along column
        straightLineMovementLoop(col, row, availableMoves, board, sideColor, true, row+1, true);

        // go from current position negative along column
        straightLineMovementLoop(col, row, availableMoves, board, sideColor, false, row-1, true);

        return availableMoves;
    }

    private ArrayList<String> getDiagonalLineMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<>();

        int localRow = row + 1;
        int localCol = col + 1;

        // go from current position to positive row positive col
        while(localRow < 8 && localCol < 8)
        {
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

            localRow++;
            localCol++;
        }

        // reset between checks
        localRow = row + 1;
        localCol = col - 1;

        // go from current position to positive row negative col
        while(localRow < 8 && localCol >= 0)
        {
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

            localRow++;
            localCol--;
        }

        // reset between checks
        localRow = row - 1;
        localCol = col + 1;

        // go from current position to negative row positive col
        while(localRow >= 0 && localCol < 8)
        {
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

            localRow--;
            localCol++;
        }

        // reset between checks
        localRow = row - 1;
        localCol = col - 1;

        // go from current position to negative row negative col
        while(localRow >= 0 && localCol >= 0)
        {
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

            localRow--;
            localCol--;
        }

        return availableMoves;
    }

    private ArrayList<String> getPawnMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        // pawn -- forward in one direction
        // diagonal capture, two movements if in original place

        ArrayList<String> availableMoves = new ArrayList<>();

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

    private ArrayList<String> getKnightMovement(int row, int col, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> availableMoves = new ArrayList<>();

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

    private void straightLineMovementLoop(int col,
                                          int row,
                                          ArrayList<String> availableMoves,
                                          ChessBoard board,
                                          char sideColor,
                                          boolean countUp,
                                          int countFrom,
                                          boolean isColumn)
            throws Exception
    {
        // variable for positive vs negative
        if(countUp)
        {
            for(int i = countFrom; i < 8; i++)
            {
               if(straightLineMovementLogic(row, col, i, availableMoves, board, isColumn, sideColor))
                   break;
            }
        }

        else
        {
            for(int i = countFrom; i >= 0; i--)
            {
                if(straightLineMovementLogic(row, col, i, availableMoves, board, isColumn, sideColor))
                   break;
            }
        }
    }

    private boolean straightLineMovementLogic(int row,
                                           int col,
                                           int i,
                                           ArrayList<String> availableMoves,
                                           ChessBoard board,
                                           boolean isColumn,
                                           char sideColor) throws Exception
    {
        // tests for whether column or row and matching piece x check
        if (
                (isColumn && board.getPieceAtCoord(i, col).equals("x")) ||
                (!isColumn && board.getPieceAtCoord(row, i).equals("x"))
        ) {
            if(isColumn)
                availableMoves.add(board.getLocationCodeFromCoordinates(i, col));
            else
                availableMoves.add(board.getLocationCodeFromCoordinates(row, i));
            return false;
        }

        else
        {
            // add capture move if not same color
            if (isColumn)
            {
                if (!board.getPieceAtCoord(i, col).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(i, col));

                return true;
            }

            else
            {
                if (!board.getPieceAtCoord(row, i).startsWith(sideColor + ""))
                    availableMoves.add(board.getLocationCodeFromCoordinates(row, i));

                return true;
            }
        }
    }

    private ArrayList<String> getKingMoves(int i, int j, ChessBoard board, char sideColor) throws Exception
    {
        ArrayList<String> kingMoves = new ArrayList<>();

        // all 8 possible movements
        if(board.getPieceAtCoord(i, j+1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i, j+1));
        if(board.getPieceAtCoord(i, j-1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i, j-1));
        if(board.getPieceAtCoord(i+1, j).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i+1, j));
        if(board.getPieceAtCoord(i-1, j).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i-1, j));
        if(board.getPieceAtCoord(i+1, j+1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i+1, j+1));
        if(board.getPieceAtCoord(i+1, j-1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i+1, j-1));
        if(board.getPieceAtCoord(i-1, j+1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i-1, j+1));
        if(board.getPieceAtCoord(i-1, j-1).charAt(0) != sideColor)
            kingMoves.add(board.getLocationCodeFromCoordinates(i-1, j-1));

        return kingMoves;
    }

    private boolean isPieceInDanger(String locationCode, char oppositeColor, ChessBoard board)
    {
        try {

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (board.getPieceAtCoord(i, j).charAt(0) == oppositeColor &&
                            board.getPieceAtCoord(i, j).charAt(1) != 'K')
                    {
                        ArrayList<String> availableMoves = getAvailableMoves(
                                board.getLocationCodeFromCoordinates(i, j),
                                board);

                        if(availableMoves.contains(locationCode))
                            return true;
                    }
                    if (board.getPieceAtCoord(i, j).charAt(0) == oppositeColor &&
                            board.getPieceAtCoord(i, j).charAt(1) == 'K')
                    {
                        ArrayList<String> kingMoves = getKingMoves(i,j,board, oppositeColor);

                        if(kingMoves.contains(locationCode))
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
        catch(Exception x)
        {
            return false;
        }
    }

    private Predicate<ChessBoard> predicateIsPieceInDanger(String locationCode, char oppositeColor)
    {
            return p -> isPieceInDanger(locationCode, oppositeColor, p);
    }
}