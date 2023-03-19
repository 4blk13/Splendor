package splendor.noble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import splendor.token.Token;

/**
 * Noble is a record for a card of noble.
 */
public record Noble(String name, int points, HashMap<Token, Integer> price) {

	/**
	 * Constructor for a Noble.
	 * @param name - name of the noble card.
	 * @param price - price of the noble card.
	 * @param points - points of the noble card.
	 */
	public Noble {
		Objects.requireNonNull(name);
		if (points < 0) {
			throw new IllegalArgumentException("Points must positive");
		}
		if (price == null) {
			throw new NullPointerException("Price of the noble is null");
		}
	}

	/**
	 * Returns the value of the price record component.
	 * 
	 * @return HashMap<Token, Integer> - The price of noble's card.
	 */
	public HashMap<Token, Integer> getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Noble : name = " + name + ", Points = " + points + " Price = " + price + "".toString();
	}

	/**
	 * Returns string representation of list of nobles.
	 * 
	 * @param nobleList - The nobles's list to be presented.
	 * @return String - string representation of nobles's list.
	 */
	public static String printLineNoble(List<Noble> nobleList) {
		var str = new StringBuilder();
		var nobleListSize = nobleList.size();
		str.append(" ______________    ".repeat(nobleListSize + 1)).append("\n").append("|              |   ");
		for (var noble : nobleList) {str.append("|       ").append(noble.points).append("      |   ");}; str.append("\n").append("|              |   ");
		var array = new ArrayList<String[]>();
		for (var noble : nobleList) {array.add(noble.name().split("_"));}
		for (var string : array) {str.append("| ").append(string[0]).append(" ".repeat(13 - string[0].length())).append("|   ");} str.append("\n").append("|              |   ");
		for (var string : array) {str.append("| ").append(string[1]).append(" ".repeat(13 - string[1].length())).append("|   ");} 
		str.append("\n").append("|              |   ".repeat(nobleListSize + 1)).append("\n").append("|   ").append("Nobles : ").append("  |   ");
		for (var noble : nobleList) {var whiteToken = noble.getPrice().getOrDefault(Token.WHITE, 0);str.append((whiteToken > 0) ? "|   White=" + whiteToken + "    |   " : "|              |   ");};
		str.append("\n").append("|              |   ");
		for (var noble : nobleList) {var blueToken = noble.getPrice().getOrDefault(Token.BLUE, 0); str.append((blueToken > 0) ? "|   Blue=" + blueToken + "     |   " : "|              |   ");};
		str.append("\n").append("|              |   ");
		for (var noble : nobleList) {var greenToken = noble.getPrice().getOrDefault(Token.GREEN, 0);str.append((greenToken > 0) ? "|   Green=" + greenToken + "    |   " : "|              |   ");};
		str.append("\n").append("|              |   ");
		for (var noble : nobleList) {var redToken = noble.getPrice().getOrDefault(Token.RED, 0);str.append((redToken > 0) ? "|   Red=" + redToken + "      |   " : "|              |   ");};
		str.append("\n").append("|              |   ");
		for (var noble : nobleList) {var blackToken = noble.getPrice().getOrDefault(Token.BLACK, 0);str.append((blackToken > 0) ? "|   Black=" + blackToken + "    |   " : "|              |   ");};
		return str.append("\n").append(" ______________    ".repeat(nobleListSize + 1)).toString();
	}
}