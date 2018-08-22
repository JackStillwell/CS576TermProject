import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @org.junit.jupiter.api.Test
    void addPieceToBoard() {
        ChessBoard board = new ChessBoard();

        try {
            board.addPieceToBoard("BKa8");
        }
        catch(Exception x)
        {
            fail("Exception triggered: " + x.getMessage());
        }

        String piece = board.getPieceAtCoord(0,7);

        assertEquals("BK", piece);
    }

    @org.junit.jupiter.api.Test
    void checkNumberPawns(){
        ChessBoard board = new ChessBoard();

        try{
            for(int i = 1; i <= 8; i++)
            {
                for(int j = 1; j <= 8; j++) {
                    board.addPieceToBoard("BP" + board.getCoordCharFromCoordNum(i) + j);
                }
            }

            fail("Did not trigger Exception.");
        }

        catch(Exception x)
        {
            assertEquals("Too many pieces already on board: BP", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkNumberKings(){
        ChessBoard board = new ChessBoard();

        try{
            for(int i = 1; i <= 8; i++)
            {
                for(int j = 1; j <= 8; j++) {
                    board.addPieceToBoard("BK" + board.getCoordCharFromCoordNum(i) + j);
                }
            }

            fail("Did not trigger Exception.");
        }

        catch(Exception x)
        {
            assertEquals("Too many pieces already on board: BK", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkValidPieceCode()
    {
        ChessBoard board = new ChessBoard();

        try{
            board.addPieceToBoard("asdf");
            fail("Exception not triggered.");
        }

        catch(Exception x)
        {
            assertEquals("Invalid Input Piece Code: as", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkValidLocationCode()
    {
        ChessBoard board = new ChessBoard();

        try{
            board.addPieceToBoard("BKdf");
            fail("Exception not triggered.");
        }

        catch(Exception x)
        {
            assertEquals("Invalid Location Code: df", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkLocationOccupied()
    {
        ChessBoard board = new ChessBoard();

        try
        {
            board.addPieceToBoard("WPa1");
            board.addPieceToBoard("WNa1");
            fail("Exception not triggered");
        }

        catch(Exception x)
        {
            assertEquals("Location Already Occupied: a1", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkAllCoordConversions()
    {
        ChessBoard board = new ChessBoard();

        try{
            boolean coordConversionCorrect =
                'a' == board.getCoordCharFromCoordNum(0) &&
                'b' == board.getCoordCharFromCoordNum(1) &&
                'c' == board.getCoordCharFromCoordNum(2) &&
                'd' == board.getCoordCharFromCoordNum(3) &&
                'e' == board.getCoordCharFromCoordNum(4) &&
                'f' == board.getCoordCharFromCoordNum(5) &&
                'g' == board.getCoordCharFromCoordNum(6) &&
                'h' == board.getCoordCharFromCoordNum(7) &&
                0 == board.getCoordNumFromCoordChar('a') &&
                1 == board.getCoordNumFromCoordChar('b') &&
                2 == board.getCoordNumFromCoordChar('c') &&
                3 == board.getCoordNumFromCoordChar('d') &&
                4 == board.getCoordNumFromCoordChar('e') &&
                5 == board.getCoordNumFromCoordChar('f') &&
                6 == board.getCoordNumFromCoordChar('g') &&
                7 == board.getCoordNumFromCoordChar('h');

            assertTrue(coordConversionCorrect);
        }
        catch(Exception x)
        {
            fail("Exception triggered: " + x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void testInvalidIntToCharCoordConversion()
    {
        int invalidCoordConversion;

        ChessBoard board = new ChessBoard();

        try{
            invalidCoordConversion = board.getCoordCharFromCoordNum(8);
            fail("Exception not triggered.");
        }
        catch(Exception x)
        {
            assertEquals("Invalid Int to Char Coordinate Conversion: 8", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void testInvalidCharToIntCoordConversion()
    {
        int invalidCoordConversion;

        ChessBoard board = new ChessBoard();

        try{
            invalidCoordConversion = board.getCoordNumFromCoordChar('x');
            fail("Exception not triggered.");
        }
        catch(Exception x)
        {
            assertEquals("Invalid Char to Int Coordinate Conversion: x", x.getMessage());
        }
    }
}