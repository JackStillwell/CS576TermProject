import static org.junit.jupiter.api.Assertions.*;

class FileInterpreterTest {

    @org.junit.jupiter.api.Test
    void constructBoardFromFile()
    {
        ChessBoard board = new ChessBoard();
        try {
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFile.txt");
            board = interpreter.constructBoardFromFile();
        }
        catch(Exception x)
        {
            fail("Exception triggered: " + x.getMessage());
        }

        boolean boardCorrect =
                "WR".equals(board.getPieceAtCoord(5,0)) &&
                "WK".equals(board.getPieceAtCoord(6,0)) &&
                "WP".equals(board.getPieceAtCoord(5,1)) &&
                "WP".equals(board.getPieceAtCoord(7,1)) &&
                "WP".equals(board.getPieceAtCoord(6,2)) &&
                "BK".equals(board.getPieceAtCoord(1,7)) &&
                "BN".equals(board.getPieceAtCoord(4,7)) &&
                "BP".equals(board.getPieceAtCoord(0,6)) &&
                "BP".equals(board.getPieceAtCoord(1,6)) &&
                "BP".equals(board.getPieceAtCoord(2,6)) &&
                "BR".equals(board.getPieceAtCoord(0,4));

        assertTrue(boardCorrect);
    }

     @org.junit.jupiter.api.Test
    void retrievePieceToCalculateForFromFile()
    {
        String pieceToCalculate = "";
        try {
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFile.txt");
            pieceToCalculate = interpreter.retrievePieceToCalculateFromFile();
        }
        catch(Exception x)
        {
            fail("Exception triggered: " + x.getMessage());
        }

        assertEquals("Rf1", pieceToCalculate);
    }

    @org.junit.jupiter.api.Test
    void checkFileValid()
    {
        try{
            FileInterpreter interpreter = new FileInterpreter("asdf");
            fail("Failed to trigger exception");
        }

        catch(Exception x)
        {
            assertEquals("Could not load specified file", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkFileCorrectNumberLines()
    {
        try{
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFileNoLines.txt");
            fail("Failed to trigger exception");
        }

        catch(Exception x)
        {
            assertEquals("Incorrect Number of Lines in the File", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkFileCorrectFirstLine()
    {
        try{
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFileBadFormatFirstLine.txt");
            interpreter.constructBoardFromFile();
            fail("Failed to trigger exception");
        }

        catch(Exception x)
        {
            assertEquals("Incorrect format of file in first line", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkFileCorrectSecondLine()
    {
        try{
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFileBadFormatSecondLine.txt");
            interpreter.constructBoardFromFile();
            fail("Failed to trigger exception");
        }

        catch(Exception x)
        {
            assertEquals("Incorrect format of file in second line", x.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void checkFileCorrectThirdLine()
    {
        try{
            FileInterpreter interpreter = new FileInterpreter("src/FileInterpreterTestFileBadFormatThirdLine.txt");
            interpreter.retrievePieceToCalculateFromFile();
            fail("Failed to trigger exception");
        }

        catch(Exception x)
        {
            assertEquals("Incorrect format of file in third line", x.getMessage());
        }
    }
}