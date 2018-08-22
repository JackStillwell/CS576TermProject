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

        assertEquals("LEGAL MOVES FOR Kc5: c4, b6, b4", cv.testMain("src/ChessVerifierTestFileKing.txt"));
    }

    @org.junit.jupiter.api.Test
    void randomBoardTestOne()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Kf7: g7, e7, g8, e8", cv.testMain("src/ChessVerifierTestFileRandomOne.txt"));
    }

    @org.junit.jupiter.api.Test
    void randomBoardTestTwo()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Kg3: g4, f3, h2, f4, f2", cv.testMain("src/ChessVerifierTestFileRandomTwo.txt"));
    }

    @org.junit.jupiter.api.Test
    void randomBoardTestThree()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Ke5: e6, f6, d6", cv.testMain("src/ChessVerifierTestFileRandomThree.txt"));
    }

    @org.junit.jupiter.api.Test
    void randomBoardTestFour()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Kd3: c2", cv.testMain("src/ChessVerifierTestFileRandomFour.txt"));
    }

    @org.junit.jupiter.api.Test
    void randomBoardTestFive()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Ke4: f3", cv.testMain("src/ChessVerifierTestFileRandomFive.txt"));
    }
}