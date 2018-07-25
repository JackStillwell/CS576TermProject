import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileInterpreter {

    private Charset charset;
    private Path filepath;
    private BufferedReader reader;

    public FileInterpreter(String filepathString)
    {
        charset = Charset.forName("UTF-8");
        try
        {
            filepath = Paths.get(filepathString);
            reader = Files.newBufferedReader(filepath, charset);
        }

        catch (IOException e)
        {
            System.out.println("Could not load specified file");
        }
    }

    public ChessBoard interpretFile()
    {
        ChessBoard board = new ChessBoard();


        return board;
    }
}
