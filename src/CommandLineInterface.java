public class CommandLineInterface {
    public static void main(String[] args)
    {
        ChessVerifier cv = new ChessVerifier();

        try {
            System.out.println(cv.testMain(args[0]));
        }
        catch(Exception x)
        {
            System.out.println("Error: " + x.getMessage());
        }
    }
}