
package texas.holdem.poker;

/**
 *
 * @author Aashish
 */
public class Card implements Comparable<Card> {
    private int number;
    private char suite;
    
    public int getNumber() {
        return number;
    }
    
    public char getSuite() {
        return suite;
    }
    
    public void setNumber(int n) {
        number = n;
    }
    
    public void setSuite(char s) {
        suite = s;
    }
    
    @Override 
    public int compareTo(Card comparestu) {
        int comparenum=((Card)comparestu).number;
        /* For Ascending order*/
        //return this.number-compareage;

        /* For Descending order do like this */
        return comparenum-this.number;
    }
}
