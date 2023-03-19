package splendor.card;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 *  CardStockBase is a class for a stock of Cards in base mode.
 */
public class CardStockBase implements CardStock {
	private HashMap<Integer, ArrayList<Card>> cardStock;
	
	/**
     *  Constructor for a stock of cards.
     *  
     */
	public CardStockBase() {
		cardStock = new HashMap<>();	
		addLevel(1);
	}
	
	/**
	 * Add a level to the stock of cards.
	 * @param lvl - The value of level to add.
	 */
	public void addLevel(int lvl) {
		cardStock.put(lvl, new ArrayList<Card>());
	}
	
	/**
	 * Add a card.
	 * @param card - card to add.
	 */
	private void add(Card card) {
		var cardLevel = card.level();
		var stockLevel = cardStock.get(cardLevel);
		stockLevel.add(card);
		cardStock.put(cardLevel, stockLevel);
	}
	
	/**
     *  read a line from a text file,turn  into a developpement card and add it to the cards's stock.
     *  @param path - Path of file. 
     *  @throws IOException  - throw IOException if there was a probleme with file.
     */
	public void initializeStock(String path) throws IOException {
		String line;
		try (var reader = Files.newBufferedReader(Path.of(path))) {
			while ((line = reader.readLine()) != null) {
				var tmp = fromText(line);
				add(tmp);
			}
		}
	}
	
	/**
     *  take a string and turn it into a developpement card.
     *  @param val - The string to be transformed. 
     *  @return Card - Card gotten.
     */
	private Card fromText(String val) {
		Objects.requireNonNull(val);	
		var tab = val.split(" : ");
		return switch(tab[0]) {
			
			case "level_1" -> new Card(1,Integer.parseInt(tab[1]), tab[2], Integer.parseInt(tab[3]), Integer.parseInt(tab[4]),Integer.parseInt(tab[5]),Integer.parseInt(tab[6]),Integer.parseInt(tab[7]));
			
			case "level_2" -> new Card(2,Integer.parseInt(tab[1]), tab[2], Integer.parseInt(tab[3]), Integer.parseInt(tab[4]),Integer.parseInt(tab[5]),Integer.parseInt(tab[6]),Integer.parseInt(tab[7]));
			
			case "level_3" -> new Card(3,Integer.parseInt(tab[1]), tab[2], Integer.parseInt(tab[3]), Integer.parseInt(tab[4]),Integer.parseInt(tab[5]),Integer.parseInt(tab[6]),Integer.parseInt(tab[7]));

			default -> throw new IllegalArgumentException("Type of Object Not Recognized");
		};
	}
	
	/**
     *  Returns a string representation of cards's stock.
     *  @return String - String  of cards's stock.
     */
	@Override
	public String toString() {
		var string = new StringBuilder();
		for(var elem : cardStock.entrySet()) {
			for (var card : elem.getValue()) {
				System.out.println("Card level " + elem.getKey() + " : Points=" + card.points() + " Bonus=" + card.bonus()
				+ (card.white() > 0 ? " white=" + card.white() : "") + (card.blue() > 0 ? " blue=" + card.blue() : "")
				+ (card.green() > 0 ? " green=" + card.green() : "") + (card.red() > 0 ? " red=" + card.red() : "")
				+ (card.black() > 0 ? " black=" + card.black() : ""));
			}
		}
		return string.toString();
	}
	
	/**
     *  Returns a random card from a list of cards.
     *  @param lvl - Level of the random card to be taken.
     *  @return Card - The card taken.
     */
	public Card drawRandomCardOfLevel(int lvl) {
		var random = new Random();
		var arrayStock = cardStock.get(lvl);
		return arrayStock.remove(random.nextInt(arrayStock.size()));
	}
}