
package texas.holdem.poker;
import java.util.*;

public class Player implements Comparable<Player>{
   
    int id;
    int handRank; 
    int high1;
    int high2;
    int handHigh;
    int budget;
    int bet;
    ArrayList<Card> hand = new ArrayList<>();
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    @Override 
    public int compareTo(Player comparestu) {
        int comparerank=((Player)comparestu).handRank;
        /* For Ascending order*/
        //return this.number-compareage;

        /* For Descending order do like this */
        return comparerank-this.handRank;
    }
    
}
