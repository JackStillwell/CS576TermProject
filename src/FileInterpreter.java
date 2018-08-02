import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileInterpreter {

    private Charset charset;
    private Path filepath;
    private BufferedReader reader;
    private ArrayList<String> fileLineArray;

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

	fileLineArray = new ArrayList<String>();	
	String inLine = "";

	while((inLine = reader.readLine()) != null)
	{
		fileLineArray.add(new String(inLine));
	}
	
	// PERSONAL NOTE: this format of if-throw is my nicer
	//                way of doing assertions. This way I 
	//                get a legible error.
	if(fileLineArray.size() != 3)
	{
		throw new Exception("Incorrect Number of Lines in the File");
	}
    }

    public ChessBoard constructBoardFromFile() throws Exception
    {
        ChessBoard board = new ChessBoard();
        
	String[] whiteLine = fileLineArray[0].split(",");
	String[] blackLine = fileLineArray[1].split(",");

	if(!whiteLine[0].startsWith("WHITE: "))
	{
		throw new Exception("Incorrect format of file in first line");
	}

	addLineToBoard(whiteLine, board);
	
	if(!blackLine[0].startsWith("BLACK: "))
	{
		throw new Exception("Incorrect format of file in second line");
	}

	addLineToBoard(blackLine, board);

        return board;
    }

    private void addLineToBoard(String[] line, ChessBoard board) throws Exception
    {
	// add the first entry, following wherever the space is
	board.addPieceToBoard(line[0].substring(line[0].indexOf(" ")));

	for(int i = 1; i < line.length; i++)
	{
		// skip the space at the start
		// if in correct format, will always have
		// a space as the first character
		board.addPieceToBoard(whiteLine[i].substring(1));
	}
    }

    public String retrievePieceToCalculateFromFile() throws Exception
    {
	String[] requestLine = fileLineArray[2].split(" ");
	
	if(!requestLine[0].startsWith("PIECE TO MOVE:"))
	{
		throw new Exception("Incorrect format of file in first line");
	}

	return requestLine[1];
    }
}
