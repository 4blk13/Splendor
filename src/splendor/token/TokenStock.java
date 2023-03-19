package splendor.token;

import java.util.HashMap;
/**
 * interface TokenStock units token stock of the base mode and the full mode.
 */
public interface TokenStock {
	/**
     *  Returns stock of tokens.
     *  @return HashMap<Token, Integer> - Map of Tokens's stock.
     */
	HashMap<Token, Integer> getTokenStock();
	
	/**
	 * Add a token.
	 * @param token - token to add.
	 * @param nbr - number of tokens to add.
	 */
	void addToken(Token token, int nbr);
	
	/**
	 * remove tokens.
	 * @param token - token to remove.
	 * @param nbr - number of tokens to remove.
	 */
	void take(Token token, int nbr);
	
	/**
     *  changes stock of Tokens.
     *  @param tokenStock - stock of Tokens.
     */
	void setTokenStock(HashMap<Token, Integer> tokenStock);
}
