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

    private String[] getAvailableMoves(String inputPiece, ChessBoard board) throws Exception {
        String pieceCode = inputPiece.substring(0,2);
        String locationCode = inputPiece.substring(2);

        ArrayList<String> availableMoves = new ArrayList<String>();

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
            // will need to do the diagonal calculations here
        }

        else if(pieceCode.endsWith("N"))
        {
            // this is the Knight -- figuring out the
            // weird jumping pattern here will be annoying
        }

        else if(pieceCode.endsWith("B"))
        {
            // bishop -- diagonals
        }

        else if(pieceCode.endsWith("R"))
        {
            // rook -- just up and down
        }

        else if(pieceCode.endsWith("P"))
        {
            // pawn -- diagonal in one direction
            // is their capture a jump or occupy? I'm not sure
        }
        else
        {
            throw new Exception("Unknown piece type: " + pieceCode.substring(1));
        }

        return (String[]) availableMoves.toArray();
    }
}