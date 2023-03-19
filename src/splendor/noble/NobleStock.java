package splendor.noble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import splendor.token.Token;

/**
 *  NobleStock is a class for a list of cards of nobles.
 */
public class NobleStock {
	private ArrayList<Noble> nobleStock;
	
	/**
     *  Constructor for a stock of nobles.
     */
	public NobleStock() {
		nobleStock = new ArrayList<>();
	}
	
	/**
     *  changes field nobleStock.
     *  @param set - list of nobles.
     */
	public void setNoble(ArrayList<Noble> set) {
		nobleStock = set;
	}
	
	/**
	 * remove tokens.
	 * @param noble - noble to remove.
	 */
	public void removeNoble(Noble noble) throws NullPointerException {
		nobleStock.remove(Objects.requireNonNull(noble));
	}
	
	/**
     *  Returns list of nobles.
     *  @return ArrayList<Noble> - list of nobles.
     */
	public ArrayList<Noble> getNobles(){
		return nobleStock;
	}
	
	/**
     *  take a string and turn it into a noble card.
     *  @param txt - The string to be transformed. 
     *  @return Noble - Noble gotten.
     */
	private Noble nobleFromText(String txt) {
		Objects.requireNonNull(txt);	
		var tab = txt.split(" : ");
		var price = new HashMap<Token, Integer>();
		if (Integer.parseInt(tab[2]) > 0) {price.put(Token.WHITE, Integer.parseInt(tab[2]));}
		if (Integer.parseInt(tab[3]) > 0) {price.put(Token.BLUE, Integer.parseInt(tab[3]));}
		if (Integer.parseInt(tab[4]) > 0) {price.put(Token.GREEN, Integer.parseInt(tab[4]));}
		if (Integer.parseInt(tab[5]) > 0) {price.put(Token.RED, Integer.parseInt(tab[5]));}
		if (Integer.parseInt(tab[6]) > 0) {price.put(Token.BLACK, Integer.parseInt(tab[6]));}
		return new Noble(tab[0], Integer.parseInt(tab[1]), price);
	}
	
	/**
	 * Add a noble card.
	 * @param noble - noble card.
	 */
	private void addNoble(Noble noble) throws NullPointerException {
		nobleStock.add(Objects.requireNonNull(noble));
	}
	
	/**
     *  read a line from a text file,turn it into a noble card and add it to the nobles's list.
     *  @param path - Path of file. 
     *  @throws IOException - throw IOException if there was a probleme with file.
     */
	public void initializeStock(String path) throws IOException {
		String line;
		try (var reader = Files.newBufferedReader(Path.of(path))) {
			while ((line = reader.readLine()) != null) {
				addNoble(nobleFromText(line));
			}
		}
	}
	
	/**
     *  Returns a string representation of nobles's stock.
     *  @return String - String  of nobles's stock.
     */
	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (var noble : nobleStock) {
			builder.append(noble).append("\n");
		}
		return builder.toString();
	}
}