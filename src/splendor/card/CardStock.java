package splendor.card;

import java.io.IOException;

/**
 * interface CardStock units Card stock of the base mode and the full mode.
 */
public interface CardStock {
	
	/**
     *  Returns a random card from a list of cards.
     *  @param level - Level of the random card to be taken.
     *  @return Card - The card taken.
     */
	Card drawRandomCardOfLevel(int level);
	
	/**
     *  read a line from a text file,turn it into a developpement card and add it to the cards's stock.
     *  @param string - Path of file. 
     *  @throws IOException - throw IOException if there was a probleme with file.
     */
	void initializeStock(String string) throws IOException;

}
