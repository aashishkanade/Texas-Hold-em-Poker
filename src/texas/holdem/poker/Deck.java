

package texas.holdem.poker;

import java.util.*;

public class Deck {
    private static final char[] suites = {'h', 'd', 'c', 's'};
    private static final int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,14};
    private static final ArrayList<Card> deck = new ArrayList<>();
    
    public static ArrayList<Card> generateDeck() {                                              //This function creates and returns a standard deck of cards
        for(int i=0; i<suites.length; i++){
            for(int j=0; j<numbers.length;j++) {
                Card card = new Card();
                card.setSuite(suites[i]);
                card.setNumber(numbers[j]);
                deck.add(card);
            }
        }
        return deck;
    }
    
    public static char[] getSuites() {
        return suites;
    }
    
    public static int[] getNumbers() {
        return numbers;
    }
}