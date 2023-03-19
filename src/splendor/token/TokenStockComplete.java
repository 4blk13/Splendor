package splendor.token;

import java.util.HashMap;

/**
 *  TokenStockComplete is a class for a stock of tokens in full mode.
 */
public class TokenStockComplete implements TokenStock {
	private TokenStockBase tokenStockBase;
	
	/**
     *  Constructor for a stock of tokens.
     *  
     *  @param nbPlayers - number of players.
     */
	public TokenStockComplete(int nbPlayers) {
		tokenStockBase = new TokenStockBase(nbPlayers);
		var stockComplete = tokenStockBase.getTokenStock();
		stockComplete.put(Token.GOLD, 5);
		tokenStockBase.setTokenStock(stockComplete);
	}
	
	/**
     *  Returns a string representation of tokens's stock.
     *  @return String - String  of okens's stock.
     */
	@Override
	public String toString() {
		return tokenStockBase.toString() + "[ Gold : " + tokenStockBase.getToken(Token.GOLD) + " ] \n";
	}
	
	/**
     *  Returns stock of tokens.
     *  @return HashMap<Token, Integer> - Map of Tokens's stock.
     */
	@Override
	public HashMap<Token, Integer> getTokenStock() {
		return tokenStockBase.getTokenStock();
	}

	/**
	 * Add a token.
	 * @param token - token to add.
	 * @param nbr - number of tokens to add.
	 */
	@Override
	public void addToken(Token token, int nbr) {
		tokenStockBase.addToken(token, nbr);
	}
	
	/**
	 * remove tokens.
	 * @param token - token to remove.
	 * @param nbr - number of tokens to remove.
	 */
	@Override
	public void take(Token token, int nbr) {
		tokenStockBase.take(token, nbr);
	}
	
	/**
     *  changes field tokenStock.
     *  @param tokenStock - stock of tokens.
     */
	@Override
	public void setTokenStock(HashMap<Token, Integer> tokenStock) {
		tokenStockBase.setTokenStock(tokenStock);
	}
}