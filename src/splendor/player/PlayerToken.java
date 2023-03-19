package splendor.player;

import java.util.HashMap;
import java.util.Objects;

import splendor.token.Token;

/**
 *  PlayerToken is a class for a Map of Player's tokens.
 */
public class PlayerToken {
	private HashMap<Token, Integer> playerTokens;

	/**
     *  Constructor for Player's Tokens.
     *  
     */
	public PlayerToken() {
		playerTokens = new HashMap<Token, Integer>();
		playerTokens.put(Token.WHITE, 0);
		playerTokens.put(Token.BLUE, 0);
		playerTokens.put(Token.GREEN, 0);
		playerTokens.put(Token.RED, 0);
		playerTokens.put(Token.BLACK, 0);
	}
	
	/**
     *  Returns player's Map of Tokens.
     *  @return HashMap<Token, Integer> - Map of Tokens.
     */
	public HashMap<Token, Integer> getPlayerToken() {
		return playerTokens;
	}

	/**
     *  changes player's Map of Tokens.
     *  @param playerTokens - Map of Tokens.
     */
	public void setPlayerTokens(HashMap<Token, Integer> playerTokens) {
		this.playerTokens = playerTokens;
	}
	
	/**
     *  add a token to player's Map of Tokens.
     *  
     *  @param token - the value of token.
     *  @param i - number of token will be added.
     */
	public void addToken(Token token, int i) {
		Objects.requireNonNull(token);
		playerTokens.put(token, playerTokens.getOrDefault(token, 0) + i);
	}
	
	/**
     *  add a token to player's Map of Tokens from the bonus of a card.
     *  
     *  @param bonus - the string presentation of the value of token.
     *  @param i - number of token will be added.
     */
	public void addToken(String bonus, int i) throws IllegalArgumentException {
		Objects.requireNonNull(bonus);
		try {
			var str = bonus.replaceAll(" ", "");
			var token = Token.valueOf(str.toUpperCase());
			addToken(token, i);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid token String given");
		}
	}
	
	/**
     *  calculates the sum of the number of tokens.
     *  
     *  @return int - the sum of the number of tokens.
     */
	public int sumToken() {
		var sum = 0;
		for (var elem : playerTokens.entrySet()) {
			sum += elem.getValue();
		}
		return sum;
	}
	
	/**
     *  Returns a string representation of Map of Tokens.
     *  @return String - String of Map of Tokens.
     */
	@Override
	public String toString() {
		var string = new StringBuilder();
		var separator = "";
		for (var elem : playerTokens.entrySet()) {
			if (elem.getValue() == 0) {
				string.append("");
			} else {
				string.append(separator).append(elem.getKey()).append(" : ").append(elem.getValue());
			}
			separator = "; ";
		}
		return string.toString();
	}
}