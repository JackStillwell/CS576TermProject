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

public class ChessBoard {

    private String[][] board;

    public ChessBoard()
    {
        board = new String[8][8];

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                board[i][j] = "x";
            }
        }
    }

    public void addPieceToBoard(String input)
    {
        /* Verify Correct Notation */
        /* Add to Board */
    }
}