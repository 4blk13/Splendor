package splendor.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import splendor.card.Card;
import splendor.noble.Noble;
import splendor.token.Token;
import splendor.token.TokenStock;

/**
 *  Player is a class for a Player.
 */
public class Player {

	private final String name;
	private int point = 0;
	private PlayerCard playerCards = new PlayerCard();
	private PlayerToken playerTokens = new PlayerToken();
	private PlayerToken playerBonus = new PlayerToken();
	private ArrayList<Card> cardReserve = new ArrayList<>();
	
	 /**
     *  Constructor for a Player.
     *  
     *  @param name - Name of the player
     */
	public Player(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
     *  Returns a string representation of this player.
     *  @return String - String of "Player : " + name.
     */
	@Override
	public String toString() {
		return "Player : " + name;
	}
	
	/**
     *  Returns a string representation of reserved cards.
     *  @return String - String of reserved cards.
     */
	public String printResevreCard() {
		var string = new StringBuilder();
		var i = 0;
		for(var elem : cardReserve) {
			string.append("(").append(i).append(")").append(" ").append(elem).append("\n");
			i++;
		}
		return string.toString();
	}

	/**
     *  Returns the player's list of cards.
     *  @return PlayerCard - Player's list of cards.
     */
	public PlayerCard getCard() {
		System.out.println("Cards of "+ name);
		return playerCards;
	}
	
	/**
     *  Returns player's list of reserved cards.
     *  @return ArrayList<Card> - Player's list of reserved cards.
     */
	public ArrayList<Card> getCardReserve() {
		return cardReserve;
	}

	/**
     *  Returns player's points.
     *  @return int - Player's points.
     */
	public int getPoint() {
		return point;
	}

	/**
     *  Returns player's Map of Tokens.
     *  @return PlayerToken - player's Map of Tokens.
     */
	public PlayerToken getToken() {
		System.out.print("Tokens of "+ name+" ");
		return playerTokens;
	}

	/**
     *  add a token to player's Map of Tokens.
     *  
     *  @param token - the value of token.
     *  @param nbr - number of token will be added.
     */
	public void addToken(Token token, int nbr) {
		Objects.requireNonNull(token);
		playerTokens.addToken(token, nbr);
	}

	/**
     *  check if the 3 tokens have different colors.
     *  @return boolean - Returns the value of this Boolean object as a boolean primitive.
     */
	private boolean threeDifferentColorTokens(Token t1, Token t2, Token t3) {
		return !t1.equals(t2) && !t2.equals(t3) && !t1.equals(t3);
	}
	
	/**
     *  take 3 different tokens from Token stock, and add them to the player's Map of Tokens.
     *  @param stock - Tokens's stock.
     *  @param t1 - first token to add.
     *  @param t2 - second token to add.
     *  @param t3 - third token to add.
     */
	public void pickThreeDifferentTokens(TokenStock stock, Token t1, Token t2, Token t3) throws IllegalArgumentException, NullPointerException { //Action 1 : Choose 3 tokens of different colors.
		Objects.requireNonNull(stock);
		Objects.requireNonNull(t1);
		Objects.requireNonNull(t2);
		Objects.requireNonNull(t3);
		if (t1.equals(Token.GOLD) || t2.equals(Token.GOLD) || t3.equals(Token.GOLD))
			throw new IllegalArgumentException("You can't take Gold tokens !");
		if(playerTokens.sumToken() > 7) {
			throw new IllegalArgumentException("you can't get more than 10 tokens.");
		}
		
		if (!threeDifferentColorTokens(t1, t2, t3)) {
			throw new IllegalArgumentException("Two tokens have the same color.");
		}
		stock.take(t1, 1);
		stock.take(t2, 1);
		stock.take(t3, 1);
		playerTokens.addToken(t1, 1);
		playerTokens.addToken(t2, 1);
		playerTokens.addToken(t3, 1);
	}
	
	/**
     *  take 2 same tokens from Token stock, and add them to the player's Map of Tokens.
     *  @param stock - Stock of Tokens.
     *  @param t - token to add.
     */
	public void pickTwoSameTokens(TokenStock stock, Token t) throws IllegalArgumentException { //Action 2 :Choose 2 tokens of the same color.
		Objects.requireNonNull(stock);
		Objects.requireNonNull(t);
		
		if(playerTokens.sumToken() > 8) {
			throw new IllegalArgumentException("you can't get more than 10 tokens.");
		}
		
		if (t.equals(Token.GOLD)) {
			throw new IllegalArgumentException("You can't take Gold tokens !");
		}
		
		if(stock.getTokenStock().get(t) >= 4) {
			stock.take(t, 2);
			playerTokens.addToken(t, 2);
		}
		else {
			throw new IllegalArgumentException("You can't take 2 same color token if there is less than 4 of this.");
		}
	}

	/**
     *  check whether the card can be bought by the bonus or the tokens or the joker tokens.
     *  @param tokenStock - Stock of Tokens.
     *  @param card - Card to buy.
     */
	public void buyDevelopmentCard(Card card, TokenStock tokenStock) throws IllegalStateException, NullPointerException {
		Objects.requireNonNull(card);
		Objects.requireNonNull(tokenStock);
		var playerTokensCopy = new HashMap<>(playerTokens.getPlayerToken());
		var tokenStockCopy = new HashMap<>(tokenStock.getTokenStock());
		for(var tokenPrice : card.cardPrice().entrySet()) {
			if (buyDevelopmentCardAux(tokenPrice, playerTokensCopy, tokenStockCopy) == 0) {
				continue;
			}
			else {
				throw new IllegalStateException("You can't buy this card !");
			}
		}
		playerTokens.setPlayerTokens(playerTokensCopy);
		playerCards.addCard(card);
		tokenStock.setTokenStock(tokenStockCopy);
		point+=card.points();
		playerBonus.addToken(card.bonus(), 1);
	}
	
	/**
	 * Auxiliary method that completes buyDevelopmentCard method by doing a precheck if the Player have enough resources (Bonus, Tokens, Gold Token) to be able to cover this color of the token price and if he can, do it the copy of player's tokens and tokens in the copy of the stock.
	 * @param tokenPrice - An Entry representing the token's color as the key and the price of this color as the value
	 * @param playerTokensCopy - A copy of actual player's tokens
	 * @param tokenStockCopy -  A copy of actual tokens in stock
	 * @return 0 if he can buy this  
	 */
	private int buyDevelopmentCardAux(Entry<Token, Integer> tokenPrice, HashMap<Token, Integer> playerTokensCopy, HashMap<Token, Integer> tokenStockCopy) {
		var tokenType = tokenPrice.getKey();
		var playerToken = playerTokensCopy.getOrDefault(tokenType, 0);
		var playerTokenBonus = playerBonus.getPlayerToken().getOrDefault(tokenType, 0);
		var tokenValue = tokenPrice.getValue();
		var playerGold = playerTokensCopy.getOrDefault(Token.GOLD, 0);
		if (playerTokenBonus >= tokenValue) {
			return 0;
		}
		if (playerToken + playerTokenBonus >= tokenValue) {
			buyDevelopmentCardAuxBonusCase(tokenType, tokenValue, playerTokenBonus, playerTokensCopy, tokenStockCopy);
			return 0;
		}
		if (playerGold > 0){
			if (playerToken + playerTokenBonus + playerGold >= tokenValue) {
				buyDevelopmentCardAuxGoldCase(tokenType, playerToken, tokenValue, playerTokenBonus, playerTokensCopy, tokenStockCopy);
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * Auxiliary method that completes buyDevelopmentCardAux by doing the bonus and player's tokens check and replacement
	 * @param tokenType - The color of the Token
	 * @param playerToken - The number of tokens that the player has for of tokenType color
	 * @param tokenValue - The price of the card for the given token color
	 * @param playerTokenBonus - The bonus that the player has for this token color
	 * @param playerTokensCopy - A copy of actual player's tokens
	 * @param tokenStockCopy -  A copy of actual tokens in stock
	 */
	private void buyDevelopmentCardAuxGoldCase(Token tokenType, int playerToken, int tokenValue, int playerTokenBonus, HashMap<Token, Integer> playerTokensCopy, HashMap<Token, Integer> tokenStockCopy) {
		var goldNeeded = tokenValue - (playerToken + playerTokenBonus);
		var newTokenValue = tokenValue - playerTokenBonus - goldNeeded;
		playerTokensCopy.replace(tokenType, playerTokensCopy.get(tokenType) - newTokenValue);
		playerTokensCopy.replace(Token.GOLD, playerTokensCopy.get(Token.GOLD) - goldNeeded);
		tokenStockCopy.replace(tokenType, tokenStockCopy.get(tokenType) + newTokenValue);
		tokenStockCopy.replace(Token.GOLD, tokenStockCopy.get(Token.GOLD) + goldNeeded);
	}
	
	/**
	 * Auxiliary method that completes buyDevelopmentCardAux by doing the bonus and player's tokens and Gold check and replacement
	 * @param tokenType - The color of the Token
	 * @param tokenValue - The price of the card for the given token color
	 * @param playerTokenBonus - The bonus that the player has for this token color
	 * @param playerTokensCopy - A copy of actual player's tokens
	 * @param tokenStockCopy -  A copy of actual tokens in stock
	 */
	private void buyDevelopmentCardAuxBonusCase(Token tokenType,  int tokenValue, int playerTokenBonus, HashMap<Token, Integer> playerTokensCopy, HashMap<Token, Integer> tokenStockCopy) {
		var newTokenValue = tokenValue - playerTokenBonus;
		playerTokensCopy.replace(tokenType, playerTokensCopy.get(tokenType) - newTokenValue);
		tokenStockCopy.replace(tokenType, tokenStockCopy.get(tokenType) + newTokenValue);
	}
	
	/**
     *  Reserve a card and add it to player's list of reserved cards.
     *  @param card - Card to buy.
     */
	public void reserveCard(Card card) throws IllegalStateException, NullPointerException { //Action 4 :  Reserve 1 development card and take 1 gold (joker).
		Objects.requireNonNull(card);
		if(cardReserve.size() < 3) {
			cardReserve.add(card);
		}
		else {
			throw new IllegalStateException("You can't have more than 3 carte on reserve.");
		}
	}
	
	/**
     *  Buy Reserved card from player's list of reserved cards and add it to player's list of cards.
     *  @param card - Card to buy from reserved cards.
     *  @param tokenStock - Stock of Tokens.
     */
	public void buyReservedCard(Card card,TokenStock tokenStock) throws NullPointerException,IllegalStateException { //Action 5 : buy 1 a card from your reserved cards.
		Objects.requireNonNull(card);
		Objects.requireNonNull(tokenStock);
		try{
			buyDevelopmentCard(card,tokenStock);
		}catch(Exception e){
			throw new IllegalStateException("You can't buy this card");
		}
		cardReserve.remove(card);
	}
	
	/**
     *  Check if the player can have a visit from a noble.
     *  @param nobles - The list of nobles.
     *  @return Noble - The noble who can visit the player. Or null if there was no one who can.
     */
	public Noble canGetVisitNoble(ArrayList<Noble> nobles) {
		boolean canGetVisit;
		for (var noble : nobles) {
			canGetVisit = true;
			for(var token : noble.getPrice().entrySet()) {
				if (playerBonus.getPlayerToken().getOrDefault(token.getKey(), 0) < token.getValue()) {
					canGetVisit = false;
					break;
				}
			}
			if (canGetVisit) {
				return noble;
			}
		}
		return null;
	}
	
	/**
     *  Get a visit from a noble.
     *  @param noble - The noble who visit the player.
     */
	public void getNobleVisit(Noble noble) throws NullPointerException {
		Objects.requireNonNull(noble);
		point+=noble.points();
	}
}