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

        assertEquals("LEGAL MOVES FOR Nf7: g5, h6, h8, e5, d6, d8", cv.testMain("src/ChessVerifierTestFileKnight.txt"));
    }

    @org.junit.jupiter.api.Test
    void bishopTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Bb5: c6, c4, d3, a6", cv.testMain("src/ChessVerifierTestFileBishop.txt"));
    }

    @org.junit.jupiter.api.Test
    void queenTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Qd7: e8, e6, c8, c6, d8, d6, d5, e7, f7, c7, b7, a7", cv.testMain("src/ChessVerifierTestFileQueen.txt"));
    }

    @org.junit.jupiter.api.Test
    void kingTest()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Kc5: ", cv.testMain("src/ChessVerifierTestFileKing.txt"));
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