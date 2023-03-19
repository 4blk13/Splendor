package splendor.card;

import java.io.IOException;

/**
 *  CardStockComplete is a class for a stock of Cards in full mode.
 */
public class CardStockComplete implements CardStock {
	private CardStockBase cardStockBase;
	
	/**
     *  Constructor for a stock of cards.
     *  
     */
	public CardStockComplete() {
		cardStockBase = new CardStockBase();
		cardStockBase.addLevel(2);
		cardStockBase.addLevel(3);
	}
	
	/**
     *  Returns a random card from a list of cards.
     *  @param level - Level of the random card to be taken.
     *  @return Card - The card taken.
     */
	@Override
	public Card drawRandomCardOfLevel(int level) {
		return cardStockBase.drawRandomCardOfLevel(level);
	}

	/**
     *  read a line from a text file,turn it into a developpement card and add it to the cards's stock.
     *  @param string - Path of file. 
     */
	@Override
	public void initializeStock(String string) throws IOException {
		cardStockBase.initializeStock(string);
	}
}