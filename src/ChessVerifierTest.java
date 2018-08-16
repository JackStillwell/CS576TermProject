import static org.junit.jupiter.api.Assertions.*;

class ChessVerifierTest {

    @org.junit.jupiter.api.Test
    void exampleBoard()
    {
        ChessVerifier cv = new ChessVerifier();

        assertEquals("LEGAL MOVES FOR Rf1: e1, d1, c1, b1, a1", cv.testMain("src/FileInterpreterTestFile.txt"));
    }

}