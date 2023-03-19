package splendor.game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import splendor.card.Card;
import splendor.card.CardStock;
import splendor.card.CardStockBase;
import splendor.card.CardStockComplete;
import splendor.noble.Noble;
import splendor.noble.NobleStock;
import splendor.player.Player;
import splendor.player.Players;
import splendor.token.Token;
import splendor.token.TokenStock;
import splendor.token.TokenStockBase;
import splendor.token.TokenStockComplete;

/**
 *  Game is a class for the main of the game.
 */
public class Game {
        private int gameMode;
        private HashMap<Integer, ArrayList<Card>> board;
        
        /**
         *  Constructor for a Game.
         *  @param choosenGameMode - choice of mode.
         *  
         */
        public Game(int choosenGameMode) {
                board = new HashMap<>();
                gameMode = choosenGameMode;
                board.put(1, new ArrayList<Card>());
                if (gameMode == 1) {
                        board.put(2, new ArrayList<Card>());
                        board.put(3, new ArrayList<Card>());
                        
                }
        }
        
        /**
         *  Returns the board of game.
         *  @return HashMap<Integer, ArrayList<Card>> - the board of game.
         */
        public HashMap<Integer, ArrayList<Card>> getBoard() {
                return board;
        }
        
        /**
         *  Returns a string representation of the board of game.
         *  @param tokenStock - Tokens's stock.
         *  @return String - String representation of the board of game.
         */
        public String toString(TokenStock tokenStock) {
                var str = new StringBuilder();
                for(var lvl = board.size(); lvl > 0; lvl--) {
                        str.append(Card.printLineCards(board.get(lvl), lvl));
                }
                return str.toString() + tokenStock;
        }
        
        /**
    	 * Add a card to the board of game.
    	 * @param card - card to add to the board of game.
    	 */
        public void addCard(Card card) {
                Objects.requireNonNull(card);
                board.get(card.level()).add(card);
        }
        
        /**
         *  Returns a card choosen.
         *  @param choosenNumber - The number of card choosen.
         *  @return Card - The card choosen.
         */
        public Card getCard(int choosenNumber) {
                return board.get(choosenNumber/10).get(choosenNumber%10);
        }
        
        /**
    	 * remove card from the board of game.
    	 * @param card - card to remove from the board of game. 
    	 */
        public void removeCard(Card card) {
                Objects.requireNonNull(card);
                board.get(card.level()).remove(card);
        }
        
        /**
         *  Returns a string representation of the actions of the base mode.
         *  @return String - String representation of the actions of the base mode.
         */
        public static String printActionsBase() {
                return("(1) - Choose 3 tokens of different colors.\n(2) - Choose 2 tokens of the same color.\n(3) - Buy 1 visible development card.");
        }
        
        /**
         *  Returns a string representation of the actions of the full mode.
         *  @return String - String representation of the actions of the full mode.
         */
        public String printActions() {
                if (gameMode == 0)
                        return Game.printActionsBase();
                else return(Game.printActionsBase() + "\n(4) - Reserve 1 development card and take 1 gold (joker).\n(5) - buy 1 a card from your reserved cards.");
        }
        
        /**
         *  Returns a string representation of the tokens's choices.
         *  @return String - String representation of the tokens's choices.
         */
        public static String printTokenChoice() {
                return("(1) : White token | (2) : Blue token | (3) : Green token | (4) : Red token | (5) : Black token");
        }
        
        /**
         *  The main of the game.
         *  @param args - input.
         */
        public static void main(String[] args) {
                System.out.println("Choose game mode : (0) Base - (1) Complete");
                var sc = new Scanner(System.in);
                int gameMode = -1;
                while (gameMode == -1) {
                        switch(sc.nextInt()) {
                                case 0 : gameMode = 0; break;
                                case 1 : gameMode = 1; break;
                                default :
                                        System.err.println("Invalid game mode choosen");
                        }
                }
                Players players = null;
                Game board;
                CardStock cardStock;
                TokenStock tokenStock;
                NobleStock nobleStock = null;
                try {
                        if (gameMode == 1) {
                                board = new Game(1);
                                cardStock = new CardStockComplete();
                                nobleStock = new NobleStock();
                                nobleStock.initializeStock("Nobles.txt");
                                Collections.shuffle(nobleStock.getNobles());                
                                cardStock.initializeStock("CardsComplete.txt");
                                var nbrPlayers = true;
                                var numberPlayer = 0;
                                do {        
                                        System.out.print("Number of players : ");
                                        try {
                                                numberPlayer = sc.nextInt();
                                                String [] tb = new String[numberPlayer];
                                                for(var i = 0; i < tb.length;i++) {
                                                        System.out.print("Name of player number "+ (i+1) +" : ");
                                                        tb[i] = sc.next();
                                                }
                                                players = new Players(tb);
                                                nbrPlayers = false;
                                        }catch(Exception e) {
                                                System.err.println(e.getMessage());
                                                
                                        }
                                }while(nbrPlayers);
                                nobleStock.setNoble(new ArrayList<Noble>(nobleStock.getNobles().subList(0, numberPlayer + 1)));
                                tokenStock = new TokenStockComplete(players.getPlayers().size());
                                for (int i = 1; i <= 3; i++) {
                                        for (int j = 0; j < 4; j++) {
                                                board.addCard(cardStock.drawRandomCardOfLevel(i));
                                        }
                                }
                        }
                        else {
                                board = new Game(0);
                                cardStock = new CardStockBase();
                                cardStock.initializeStock("Cards.txt");
                                String [] tb = new String[2];
                                for(var i = 0; i < tb.length;i++) {
                                        System.out.print("Name of player number "+ (i+1) +" : ");
                                        tb[i] = sc.next();
                                }
                                players = new Players(tb);
                                tokenStock = new TokenStockBase(players.getPlayers().size());
                                for (int i = 0; i < 4; i++) {
                                        board.addCard(cardStock.drawRandomCardOfLevel(1));
                                }
                        }
                } catch (IOException e) {
                        System.err.println(e.getMessage());
                        sc.close();
                        return;
                }
                Player player = players.next();
                while(player.getPoint() < 15) {
                        var nextPlayer = true;
                        if(gameMode == 1) {
                                System.out.println(Noble.printLineNoble(nobleStock.getNobles()));
                        }
                        System.out.println(board.toString(tokenStock));
                        System.out.println("Turn of " + player);
                        System.out.println("Points of " + player + " = " + player.getPoint());
                        System.out.println(player.getToken());
                        System.out.println(player.getCard());
                        System.out.println(board.printActions());
                        switch(sc.nextInt()) {
                                case 1 : // (1) - Choose 3 tokens of different colors.
                                        System.out.println(Game.printTokenChoice());
                                        System.out.println("Enter 3 different color tokens : ");
                                        try {
                                                player.pickThreeDifferentTokens(tokenStock, Token.values()[sc.nextInt() - 1], Token.values()[sc.nextInt() - 1], Token.values()[sc.nextInt() - 1]);
                                        } catch (Exception e) {
                                                System.err.println(e.getMessage());
                                                nextPlayer = false;
                                        }
                                        break;
                                
                                case 2 : // (2) - Choose 2 tokens of the same color.
                                        System.out.println(Game.printTokenChoice());
                                        try {
                                                player.pickTwoSameTokens(tokenStock, Token.values()[sc.nextInt() - 1]);        
                                        } catch (Exception e) {
                                                System.err.println(e.getMessage());
                                                nextPlayer = false;
                                        }
                                        break;
                                case 3 : // (3) - Buy 1 visible development card.
                                        System.out.println("Choose a card ");
                                        try {
                                                var card = board.getCard(sc.nextInt());
                                                player.buyDevelopmentCard(card, tokenStock);
                                                board.removeCard(card);
                                                board.addCard(cardStock.drawRandomCardOfLevel(card.level()));
                                                if (nobleStock != null) {
                                                        var noble = player.canGetVisitNoble(nobleStock.getNobles());
                                                        if (noble != null) {
                                                                player.getNobleVisit(noble);
                                                                nobleStock.removeNoble(noble);
                                                        }
                                                }
                                                
                                        } catch (Exception e) {
                                                System.err.println(e.getMessage());
                                                nextPlayer = false;
                                        }                                
                                        break;
                                case 4 : // (4) - Reserve 1 development card and take 1 gold (joker).
                                        System.out.println("Do you want it from : (1) visible cards or (2) random one from the deck of cards");
                                        var chose = sc.nextInt();
                                        try {
                                                if(chose == 1) {
                                                        System.out.println("Choose a card ");
                                                        var card = board.getCard(sc.nextInt());
                                                        player.reserveCard(card);
                                                        board.removeCard(card);
                                                        board.addCard(cardStock.drawRandomCardOfLevel(card.level()));
                                                        
                                                        if((tokenStock.getTokenStock().get(Token.GOLD) >= 1)) {
                                                                player.addToken(Token.GOLD, 1);
                                                                tokenStock.take(Token.GOLD, 1);
                                                        }
                                                }else if(chose == 2){
                                                        System.out.println("Which level of Card do you want ?");
                                                        var card = cardStock.drawRandomCardOfLevel(sc.nextInt());
                                                        player.reserveCard(card);
                                                        if((tokenStock.getTokenStock().get(Token.GOLD) >= 1)) {
                                                                player.addToken(Token.GOLD, 1);
                                                                tokenStock.take(Token.GOLD, 1);
                                                        }
                                                        
                                                } else {
                                                        throw new IllegalArgumentException("Incorrect choice!");
                                                }
                                                
                                        } catch (Exception e) {
                                                System.err.println(e.getMessage());
                                                nextPlayer = false;
                                        }                                        
                                        break;
                                case 5 : // (5) - buy 1 a card from your reserved cards.
                                        System.out.println(player.printResevreCard());
                                        System.out.println("Choose a card ");
                                        try {
                                                var card = player.getCardReserve().get(sc.nextInt());
                                                player.buyReservedCard(card, tokenStock);
                                                if (nobleStock != null) {
                                                        var noble = player.canGetVisitNoble(nobleStock.getNobles());
                                                        if (noble != null) {
                                                                player.getNobleVisit(noble);
                                                                nobleStock.removeNoble(noble);
                                                        }
                                                }
                                                
                                        } catch (Exception e) {
                                                System.err.println(e.getMessage());
                                                nextPlayer = false;
                                        }                                
                                        break;
                                default :
                                        System.out.println("Choose an action!");
                                        nextPlayer = false;
                                        break;
                        }
                        
                        if (nextPlayer) {
                                player = players.next();
                        }        
                }
                sc.close();
        }
}