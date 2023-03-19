package splendor.card;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import splendor.token.Token;

/**
 *  Card is a record for a Card.
 */
public record Card(int level, int points, String bonus, int white, int blue, int green, int red, int black) {
	
	/**
     *  Constructor for a Card.
     *  @param level - Level of the card.
     *  @param points - points of the card.
     *  @param bonus - bonus of the card.
     *  @param white - white token of the card.
     *  @param blue - blue token of the card.
     *  @param green - green token of the card.
     *  @param red - red token of the card.
     *  @param black - black token of the card.
     *  
     */
	public Card {
		Objects.requireNonNull(level);
		Objects.requireNonNull(points);
		Objects.requireNonNull(bonus);
		Objects.requireNonNull(white);
		Objects.requireNonNull(blue);
		Objects.requireNonNull(green);
		Objects.requireNonNull(red);
		Objects.requireNonNull(black);
	}

	@Override
	public String toString() {
		return ("Card level " + level + " : PV=" + points + " Bonus=" + bonus
		+ (white > 0 ? " white =" + white : "") + (blue > 0 ? " blue=" + blue : "")
		+ (green > 0 ? " green =" + green : "") + (red > 0 ? " red=" + red : "")
		+ (black > 0 ? " black =" + black : ""));
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Card card && card.level == level && card.points == points
				&& card.bonus.equals(bonus) && card.white == white && card.blue == blue && card.green == green
				&& card.red == red && card.black == black;
	}
	
	/**
     *  Returns the map of the card's price.
     *  @return HashMap<Token, Integer> - The price of card.
     */
	public HashMap<Token, Integer> cardPrice() {
		var map = new HashMap<Token, Integer>();
		if (white > 0) map.put(Token.WHITE, white);
		if (blue > 0) map.put(Token.BLUE, blue);
		if (green > 0) map.put(Token.GREEN, green);
		if (red > 0) map.put(Token.RED, red);
		if (black > 0) map.put(Token.BLACK, black);
		return map;
	}
	
	/**
     *  Returns string representation of list of cards.
     *  @param cardList - The cards's list to be presented.
     *  @param cardLvl - The Level of cards's list to be presented.
     *  @return String - string representation of cards's list.
     */
	public static String printLineCards(List<Card> cardList, int cardLvl) {
		var str = new StringBuilder();
		var cardListSize = cardList.size();
		str.append(" ___________    ".repeat(cardListSize + 1)).append("\n").append("|           |   ");
		for(var card : cardList) {str.append("| ").append(card.points).append("   ").append(card.bonus).append(" ".repeat(5 - card.bonus.length())).append(" |   ");}
		str.append("\n");
		str.append("|           |   ".repeat(cardListSize + 1)).append("\n").append("|           |   ");
		for(var card : cardList) { str.append(card.white > 0 ? "| White=" + card.white + "   |   " : "|           |   ");}
		str.append("\n").append("|           |   ");
		for(var card : cardList) { str.append(card.blue > 0 ? "| Blue=" + card.blue + "    |   " : "|           |   ");}
		str.append("\n").append("| Level : " + cardLvl + " |   ");
		for(var card : cardList) { str.append(card.green > 0 ? "| Green=" + card.green + "   |   " : "|           |   ");}
		str.append("\n").append("|           |   ");
		for(var card : cardList) { str.append(card.red > 0 ? "| Red=" + card.red + "     |   " : "|           |   ");}
		str.append("\n").append("|           |   ");
		for(var card : cardList) { str.append(card.black > 0 ? "| Black=" + card.black + "   |   " : "|           |   ");}
		str.append("\n").append("|           |   ".repeat(cardListSize + 1)).append("\n").append("|           |   ");
		for(var i = 0; i < cardList.size(); i++) {str.append("|     ").append(cardLvl).append(i).append("    |   ");}
		return str.append("\n").append(" ___________    ".repeat(cardListSize + 1)).append("\n").toString();
	}
}
