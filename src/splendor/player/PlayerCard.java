package splendor.player;

import java.util.ArrayList;
import java.util.Objects;

import splendor.card.*;

/**
 *  PlayerCard is a class for a list of Player's cards.
 */
public class PlayerCard {

	private ArrayList<Card> playerCards;
	
	/**
     *  Constructor for a player's list of cards.
     *  
     */
	public PlayerCard() {
		playerCards = new ArrayList<Card>();
	}

	/**
     *  Add a card to player's list of cards.
     *  @param card - card to add
     */
	public void addCard(Card card) {
		Objects.requireNonNull(card);
		playerCards.add(card);
	}
	
	/**
     *  Returns a string representation of list of cards.
     *  @return String - String of list of cards.
     */
	@Override
	public String toString() {
		var string = new StringBuilder();
		var i = 1;

		for (var elem : playerCards) {
			string.append(i).append("-").append(elem).append("\n");
			i++;
		}
		return string.toString();
	}

	/**
     *  Check if two lists of cards are the same.
     *  @return boolean - Returns the value of this Boolean object as a boolean primitive.
     */
	@Override
	public boolean equals(Object o) {
		return o instanceof PlayerCard player && player.playerCards.containsAll(playerCards);
	}

}