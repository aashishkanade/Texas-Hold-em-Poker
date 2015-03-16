
package texas.holdem.poker;

import java.util.*;

public class Evaluator {
    public static int isRoyalFlush(ArrayList<Card> hand) {
        int high;
        if((high = isStraightFlush(hand))!= -1) {
            if(hand.get(0).getNumber() == 13) {
                return 13;
            }
        }
        return -1;
    }
    
    public static int isStraightFlush(ArrayList<Card> hand) {
        int count = 1;
        int high;
        ArrayList<Card> temp = new ArrayList<>();
        char[] suites = Deck.getSuites();
        for(char c : suites) {
            for(int i=0; i<hand.size(); i++) {
                if(hand.get(i).getSuite() == c) {
                    count++;
                }
            }
            if(count > 5) {
                for(Card card : hand) {
                    if(card.getSuite() == c) {
                        temp.add(card);
                    }
                }
                if((high = isStraight(temp)) != -1)
                    return high;
            }
            else {
                count = 1;
            }
        }
        return -1;
    }
    
    public static int isFourOfAKind(ArrayList<Card> hand) {
        for(int i=0; i<hand.size()-3; i++) {
            if(hand.get(i).getNumber() == hand.get(i+1).getNumber()) {
                if(hand.get(i+1).getNumber() == hand.get(i+2).getNumber())
                    if(hand.get(i+2).getNumber() == hand.get(i+3).getNumber())
                        return hand.get(i).getNumber();
            }
        }
        return -1;
    }
    
    public static int[] isFullhouse(ArrayList<Card> hand) {
        int[] highs = new int[2];
        ArrayList<Card> temp = new ArrayList<>();
        int high;
        int t;
        for(Card c : hand) {
            temp.add(c);
        }
        for(int i=0; i<temp.size()-2; i++) {
            if(temp.get(i).getNumber() == temp.get(i+1).getNumber()) {
                if(temp.get(i+1).getNumber() == temp.get(i+2).getNumber()) {
                    high = temp.get(i).getNumber();
                    temp.remove(i);
                    temp.remove(i);
                    temp.remove(i);
                    if((t = isOnePair(temp)) != -1) {
                        highs[0] = high;
                        highs[1] = t;
                        return highs;
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        return null;
    }
    
    public static int isFlush(ArrayList<Card> hand) {
        int count = 1;
        ArrayList<Card> temp = new ArrayList<>();
        char[] suites = Deck.getSuites();
        for(char c : suites) {
            for(int i=0; i<hand.size(); i++) {
                if(hand.get(i).getSuite() == c) {
                    temp.add(hand.get(i));
                    count++;
                }
            }
            if(count > 5) {
                return temp.get(0).getNumber();
            }
            else {
                temp.clear();
                count = 1;
            }
        }
        return -1;
    }
    
    public static int isStraight(ArrayList<Card> hand) {
        ArrayList<Card> temp = new ArrayList<>();
        for(Card c : hand) {
            temp.add(c);
        }
        int length = temp.size();
        int count = 1;
        for(int i=0; i<length; i++) {
            if(temp.get(i).getNumber() == 14) {
                Card card = new Card();
                card.setNumber(1);
                card.setSuite(temp.get(i).getSuite());
                temp.add(card);
            }
        }
        for(int i=1; i<temp.size();i++) {
            if(temp.get(i).getNumber() == temp.get(i-1).getNumber()-1) {
                count++;
            }
            else {
                count = 1;
            }
            if(count == 5) {
                return temp.get(i-4).getNumber();
            }
        }
        return -1;
    }
    
    public static int isThreeOfAKind(ArrayList<Card> hand) {
        for(int i=0; i<hand.size()-2; i++) {
            if(hand.get(i).getNumber() == hand.get(i+1).getNumber()) {
                if(hand.get(i+1).getNumber() == hand.get(i+2).getNumber())
                    return hand.get(i).getNumber();
            }
        }
        return -1;
    }
    
    public static int[] isTwoPair(ArrayList<Card> hand) {
        int[] highs = new int[3];
        int index = 0;
        int count = 0;
        for(int i=0; i<hand.size()-1; i++) {
            if(hand.get(i).getNumber() == hand.get(i+1).getNumber()) {
                count++;
            }
        }
        if(count > 1) {
            for(int i=0; i<hand.size()-1; i++) {
                if(hand.get(i).getNumber() == hand.get(i+1).getNumber()) {
                    highs[index++] = hand.get(i).getNumber();
                    hand.remove(hand.get(i));
                    hand.remove(hand.get(i));
                    i--;
                }
            }
            highs[index++] = hand.get(0).getNumber();
            return highs;
        }
        return null;
    }
    
    public static int isOnePair(ArrayList<Card> hand) {
        for(int i=0; i<hand.size()-1; i++) {
            if(hand.get(i).getNumber() == hand.get(i+1).getNumber()) {
                return hand.get(i).getNumber();
            }
        }
        return -1;
    }
    
}
