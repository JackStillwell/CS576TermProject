import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class FileInterpreter {

	private ArrayList<String> fileLineArray;

	FileInterpreter(String filepathString) throws Exception {
		Charset charset = Charset.forName("UTF-8");
		BufferedReader reader;
		try
		{
			Path filepath = Paths.get(filepathString);
			reader = Files.newBufferedReader(filepath, charset);
		}

		catch (IOException e)
		{
			throw new Exception("Could not load specified file");
		}

		fileLineArray = new ArrayList<>();
		String inLine;

		while((inLine = reader.readLine()) != null)
		{
			fileLineArray.add(inLine);
		}

		// PERSONAL NOTE: this format of if-throw is my nicer
		//                way of doing assertions. This way I
		//                get a legible error.
		if(fileLineArray.size() != 3)
		{
			throw new Exception("Incorrect Number of Lines in the File");
		}
	}

	ChessBoard constructBoardFromFile() throws Exception
	{
		ChessBoard board = new ChessBoard();

		String[] whiteLine = fileLineArray.get(0).split(",");
		String[] blackLine = fileLineArray.get(1).split(",");

		if(!whiteLine[0].startsWith("WHITE: "))
		{
			throw new Exception("Incorrect format of file in first line");
		}

		addLineToBoard(whiteLine, board, 'W');

		if(!blackLine[0].startsWith("BLACK: "))
		{
			throw new Exception("Incorrect format of file in second line");
		}

		addLineToBoard(blackLine, board, 'B');

		return board;
	}

	private void addLineToBoard(String[] line, ChessBoard board, char sideColor) throws Exception
	{
		// add the first entry, following wherever the space is
		board.addPieceToBoard(sideColor + line[0].substring(line[0].indexOf(" ") + 1));

		for(int i = 1; i < line.length; i++)
		{
			// skip the space at the start
			// if in correct format, will always have
			// a space as the first character
			board.addPieceToBoard(sideColor + line[i].substring(1));
		}
	}

	String retrievePieceToCalculateFromFile() throws Exception
	{
		String[] requestLine = fileLineArray.get(2).split(" ");

		if(!(requestLine[0].equals("PIECE") && requestLine[1].equals("TO") && requestLine[2].equals("MOVE:")))
		{
			throw new Exception("Incorrect format of file in third line");
		}

		return requestLine[3];
	}
}
