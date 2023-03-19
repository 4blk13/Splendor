package splendor.token;

import java.util.HashMap;
import java.util.Objects;

/**
 *  TokenStockBase is a class for a stock of tokens in base mode.
 */
public class TokenStockBase  implements TokenStock {
	
	private static int INITIAL_TOKEN = 7;
	private HashMap<Token, Integer> tokenStock;

	/**
     *  Constructor for a stock of tokens.
     *  
     *  @param nbPlayers - number of players.
     */
	public TokenStockBase(int nbPlayers) {
		if(nbPlayers == 2) {
			setINITIAL_TOKEN(4);
		}
		else if(nbPlayers == 3){
			setINITIAL_TOKEN(5);
		}
		
		tokenStock = new HashMap<>();
		tokenStock.put(Token.WHITE, INITIAL_TOKEN);
		tokenStock.put(Token.BLUE, INITIAL_TOKEN);
		tokenStock.put(Token.GREEN, INITIAL_TOKEN);
		tokenStock.put(Token.RED, INITIAL_TOKEN);
		tokenStock.put(Token.BLACK, INITIAL_TOKEN);
		tokenStock.put(Token.GOLD, 0);
	}
	
	/**
     *  changes field INITIAL_TOKEN.
     *  @param iNITIAL_TOKEN - Initial number of token.
     */
	private static void setINITIAL_TOKEN(int iNITIAL_TOKEN) {
		INITIAL_TOKEN = iNITIAL_TOKEN;
	}

	/**
     *  Returns stock of tokens.
     *  @return HashMap<Token, Integer> - Map of Tokens's stock.
     */
	public HashMap<Token, Integer> getTokenStock() {
		return tokenStock;
	}
	
	/**
     *  changes field tokenStock.
     *  @param tokenStock - stock of tokens.
     */
	public void setTokenStock(HashMap<Token, Integer> tokenStock) {
		this.tokenStock = tokenStock;
	}
	
	/**
	 * Add a token.
	 * @param token - token to add.
	 * @param nbr - number of tokens to add.
	 */
	public void addToken(Token token, int nbr) {
		Objects.requireNonNull(token);
		tokenStock.put(token,tokenStock.get(token) + nbr);
	}
	
	/**
     *  Returns number of a specific token.
     *  @param token - The specific token.
     *  @return Integer - number of the specific token.
     */
	public Integer getToken(Token token) {
		return tokenStock.get(token);
	}

	/**
	 * remove tokens.
	 * @param token - token to remove.
	 * @param nbr - number of tokens to remove.
	 */
	public void take(Token token, int nbr) throws IllegalArgumentException {
		Objects.requireNonNull(token);
		if (nbr < 1) {
			throw new IllegalArgumentException("You must take at least 1 token");
		}
		var stock = tokenStock.get(token);
		if (stock < nbr) {
			throw new IllegalArgumentException("Not enough " + token + " token in stock");
		}
		tokenStock.put(token, tokenStock.get(token) - nbr);
	}
	
	/**
     *  Returns a string representation of tokens's stock.
     *  @return String - String  of tokens's stock.
     */
	@Override
	public String toString() {
		var string = new StringBuilder();
		string.append("Token in stock ");
		string.append("[ White : ").append(tokenStock.get(Token.WHITE)).append(" ] ");
		string.append("[ Blue : ").append(tokenStock.get(Token.BLUE)).append(" ] ");
		string.append("[ Green : ").append(tokenStock.get(Token.GREEN)).append(" ] ");
		string.append("[ Red : ").append(tokenStock.get(Token.RED)).append(" ] ");
		string.append("[ Black : ").append(tokenStock.get(Token.BLACK)).append(" ] ");
		return string.toString();
	}
}