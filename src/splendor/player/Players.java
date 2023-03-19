package splendor.player;

import java.util.ArrayList;
import java.util.List;

/**
 *  PlayerCard is a class for a list of Players.
 */
public class Players {

	private List<Player> players = new ArrayList<Player>();
	private int currentPlayer;

	/**
     *  Constructor for Players.
     *  
     *  @param playersName - Array of the name of players.
     */
	public Players(String[] playersName ) {
		if (playersName.length > 4)
			throw new IllegalArgumentException("too much players");
		if (playersName.length < 2)
			throw new IllegalArgumentException("not enough players");
		
		for (var elem : playersName) {
			players.add(new Player(elem));
		}
		currentPlayer = players.size();
	}
	
	/**
     *  Returns a list of players.
     *  @return List<Player> - list of players.
     */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
     *  Returns the next player.
     *  @return Player - Player who will play.
     */
	public Player next() {
		currentPlayer = currentPlayer + 1 >= players.size() ? 0 : currentPlayer + 1;
		return players.get(currentPlayer);
	}	
}
