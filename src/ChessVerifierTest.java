import static org.junit.jupiter.api.Assertions.*;

class ChessVerifierTest {

    @org.junit.jupiter.api.Test
    void exampleBoard()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Rf1: e1, d1, c1, b1, a1", cv.testMain("src/FileInterpreterTestFile.txt"));
    }

    @org.junit.jupiter.api.Test
    void pawnTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Pf3: f2, e2", cv.testMain("src/ChessVerifierTestFilePawn.txt"));
    }

    @org.junit.jupiter.api.Test
    void rookTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Rd3: d4, e3, f3, c3, b3, a3", cv.testMain("src/ChessVerifierTestFileRook.txt"));
    }

    @org.junit.jupiter.api.Test
    void knightTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertTrue(true);

        //assertEquals("LEGAL MOVES FOR ");
    }

    @org.junit.jupiter.api.Test
    void bishopTest()
    {

    }

    @org.junit.jupiter.api.Test
    void queenTest()
    {

    }

    @org.junit.jupiter.api.Test
    void kingTest()
    {

    }

    @org.junit.jupiter.api.Test
    void randomBoardTestOne()
    {

    }

    @org.junit.jupiter.api.Test
    void randomBoardTestTwo()
    {

    }

    @org.junit.jupiter.api.Test
    void randomBoardTestThree()
    {

    }

    @org.junit.jupiter.api.Test
    void randomBoardTestFour()
    {

    }

    @org.junit.jupiter.api.Test
    void randomBoardTestFive()
    {

    }
}