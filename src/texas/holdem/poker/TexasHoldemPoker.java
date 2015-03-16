
package texas.holdem.poker;

import java.util.*;

public class TexasHoldemPoker {
    
    private static int curr_bet;
    private static int pot;
    public static HashMap<Integer, String> handRankName = new HashMap<>();
    public static HashMap<Integer, String> cardNumberName = new HashMap<>();
    public static ArrayList<Card> deck = new ArrayList<>();
    public static Random random = new Random();
    public static Scanner sc = new Scanner(System.in);
    
    public static void initHandRankName() {
        handRankName.put(1,"High Card");
        handRankName.put(2,"One Pair");
        handRankName.put(3,"Two Pair");
        handRankName.put(4,"Three of a kind");
        handRankName.put(5,"Straight");
        handRankName.put(6,"Flush");
        handRankName.put(7,"Full House");
        handRankName.put(8,"Four of a kind");
        handRankName.put(9,"Straight Flush");
        handRankName.put(10,"Royal Flush");
    }
    
    public static void initCardNumberName() {
        cardNumberName.put(14,"A");
        cardNumberName.put(2,"2");
        cardNumberName.put(3,"3");
        cardNumberName.put(4,"4");
        cardNumberName.put(5,"5");
        cardNumberName.put(6,"6");
        cardNumberName.put(7,"7");
        cardNumberName.put(8,"8");
        cardNumberName.put(9,"9");
        cardNumberName.put(10,"10");
        cardNumberName.put(11,"J");
        cardNumberName.put(12,"Q");
        cardNumberName.put(13,"K");
        
    }
    
    public static ArrayList<Player> createPlayers(int n) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<n; i++) {
            Player p = new Player();
            for(int j=0; j<2; j++) {
                Card card = deck.get(random.nextInt(deck.size()));
                p.hand.add(card);
                //System.out.print("["+card.getNumber()+","+card.getSuite()+"]   ");
                deck.remove(card);
            }
            p.id = i+1;
            players.add(p);
            p.budget = 300;
        }
        return players;
    }
    
    public static void displayBoard(ArrayList<Card> board) {
        for(Card c : board) {                                                   // Print the board
            System.out.print("["+cardNumberName.get(c.getNumber())+","+c.getSuite()+"]  ");
        }
        
        System.out.println();
    }
    
    public static void displayPlayerCards(ArrayList<Player> players) {
    
        System.out.println("***** TABLE IS AS FOLLOWS*****");
        
        for(Player p : players) {                                               // Print the first two cards of each player
            System.out.print("Player "+(p.id)+"   ");
            for(int i=0; i<2;i++) { 
                System.out.print("["+cardNumberName.get(p.hand.get(i).getNumber())+","+p.hand.get(i).getSuite()+"]   ");
            }
            //System.out.println("Bid : "+p.bid);
            System.out.println();
        }
        
    }
    
    
    public static ArrayList<Player> setRank(ArrayList<Player> players) {
        int high1;
        int[] highs = new int[3];
        for(Player p : players) {
            Collections.sort(p.hand);
            p.handHigh = p.hand.get(0).getNumber();
            if( (high1 = Evaluator.isRoyalFlush(p.hand)) != -1) {
                p.handRank = 10;
                p.high1 = high1;
            }
            else if((high1 = Evaluator.isStraightFlush(p.hand)) != -1) {
                p.handRank = 9;
                p.high1 = high1;
            }
            else if((high1 = Evaluator.isFourOfAKind(p.hand)) !=- 1) {
                p.handRank = 8;
                p.high1 = high1;
            }
            else if((highs = Evaluator.isFullhouse(p.hand)) != null) {
                p.handRank = 7;
                p.high1 = highs[0];
                p.high2 = highs[1];
            }
            else if((high1 = Evaluator.isFlush(p.hand)) != -1) {
                p.handRank = 6;
                p.high1 = high1;
            }
            else if((high1 = Evaluator.isStraight(p.hand)) != -1) {
                p.handRank = 5;
                p.high1 = high1;
            }
            else if((high1 = Evaluator.isThreeOfAKind(p.hand)) != -1) {
                p.handRank = 4;
                p.high1 = high1;
            }
            else if((highs = Evaluator.isTwoPair(p.hand)) != null) {
                p.handRank = 3;
                p.high1 = highs[0];
                p.high2 = highs[1];
                p.handHigh = highs[2];
            }
            else if((high1 = Evaluator.isOnePair(p.hand)) != -1) {
                p.handRank = 2;
                p.high1 = high1;
            }
            else {
                p.handRank = 1;
                p.high1 = p.handHigh;
            }
        }
        
        return players;
    }
    
    public static int getMaxHigh1(ArrayList<Player> players) {
        int max = players.get(0).high1;
        for(int i =1; i<players.size();i++) {
            if(players.get(i).high1 > max) {
                max = players.get(i).high1;
            }
        }
        return max;
    }
    
    public static int getMaxHigh2(ArrayList<Player> players) {
        int max = players.get(0).high2;
        for(int i =1; i<players.size();i++) {
            if(players.get(i).high2 > max) {
                max = players.get(i).high2;
            }
        }
        return max;
    }
    
    public static void showWinner(ArrayList<Player> players) {
        
        System.out.println("Pot won is "+pot);
        
        if(players.size() == 1) {
            players.get(0).budget += pot;
            System.out.println("Winner is Player "+players.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(players.get(0).high1));
        }
        else {
            ArrayList<Player> temp = new ArrayList<>();
            int max;
            
            if(players.get(0).handRank == 1) {
                max = getMaxHigh1(players);
                for(Player p : players) {
                    if(p.high1 == max) {
                        temp.add(p);
                    }
                }
                System.out.print("Tie between players ");
                for(Player p : temp) {
                    System.out.print(p.id+" ");
                    p.budget += pot/temp.size();
                }
                System.out.println(" with"+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1));
                temp.clear();
            }
            
            if(players.get(0).handRank == 2) {
                max = getMaxHigh1(players);
                for(Player p : players) {
                    if(p.high1 == max) {
                        temp.add(p);
                    }
                }
                if(temp.size() == 1) {
                    temp.get(0).budget += pot;
                    System.out.println("Winner is Player "+temp.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1));
                }
                else {
                    if(temp.get(0).handHigh > temp.get(1).handHigh) {
                        temp.get(0).budget += pot;
                        System.out.println("Winner is Player "+temp.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1) + " with high card "+cardNumberName.get(temp.get(0).handHigh));
                    }
                    else if(temp.get(1).handHigh > temp.get(0).handHigh) {
                        temp.get(1).budget += pot;
                        System.out.println("Winner is Player "+temp.get(1).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(1).high1) + " with high card "+cardNumberName.get(temp.get(0).handHigh));
                    }
                    else {
                        temp.get(0).budget += pot/2;
                        temp.get(1).budget += pot/2;
                        System.out.println("Players "+temp.get(0).id+" and "+temp.get(1).id+" tied with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(1).high1));
                    }
                }
                temp.clear();
            }
            if(players.get(0).handRank == 3 || players.get(0).handRank == 7) {
                max = getMaxHigh1(players);
                for(Player p : players) {
                    if(p.high1 == max) {
                        temp.add(p);
                    }
                }
                if(temp.size() == 1) {
                    temp.get(0).budget += pot;
                    System.out.println("Winner is Player "+temp.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1) +" , "+cardNumberName.get(temp.get(0).high2));
                }
                else {
                    if(temp.get(0).high2 > temp.get(1).high2) {
                        temp.get(0).budget += pot;
                        System.out.println("Winner is Player "+temp.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1)+" , "+cardNumberName.get(temp.get(0).high2));
                    }
                    else if(temp.get(1).high2 > temp.get(0).high2) {
                        temp.get(1).budget += pot;
                        System.out.println("Winner is Player "+temp.get(1).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(1).high1)+" , "+cardNumberName.get(temp.get(1).high2));
                    }
                    else {
                        if(temp.get(0).handHigh > temp.get(1).handHigh) {
                            temp.get(0).budget += pot;
                            System.out.println("Winner is Player "+temp.get(0).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1)+" , "+cardNumberName.get(temp.get(0).high2)+" with high card: "+cardNumberName.get(temp.get(0).handHigh));
                        }
                        else if(temp.get(1).handHigh > temp.get(0).handHigh) {
                            temp.get(1).budget += pot;
                            System.out.println("Winner is Player "+temp.get(1).id+" with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(1).high1)+" , "+cardNumberName.get(temp.get(1).high2)+" with high card: "+cardNumberName.get(temp.get(1).handHigh));
                        }
                        else {
                            temp.get(0).budget += pot/2;
                            temp.get(1).budget += pot/2;
                            System.out.println("Players "+temp.get(0).id+" and "+temp.get(1).id+" tied with "+handRankName.get(players.get(0).handRank)+" on: "+cardNumberName.get(temp.get(0).high1)+" , "+cardNumberName.get(temp.get(0).high2));
                        }
                    }
                }
            }
            
            if(players.get(0).handRank == 4 || players.get(0).handRank == 5 || players.get(0).handRank == 6) {
                max = getMaxHigh1(players);
                for(Player p : players) {
                    if(p.high1 == max) {
                        p.budget += pot;
                        System.out.println("Winner is Player "+p.id+" with "+handRankName.get(p.handRank)+" on: "+cardNumberName.get(p.high1) + " with high1 card "+cardNumberName.get(p.handHigh));
                        break;
                    }
                }
            }
 
        }
    }
    
    public static void playRound(ArrayList<Player> players) {
        
        curr_bet = 30;
        
        initHandRankName();
        initCardNumberName();
        
        displayPlayerCards(players);                                            // Displays all the players cards
        
        System.out.println();
        
        ArrayList<Card> board = new ArrayList<>();
        for(int i=0; i<5; i++) {
            Card card = deck.get(random.nextInt(deck.size()));
            board.add(card);
            for(Player p : players) {
                p.hand.add(card);
            }
            deck.remove(card);
        }
        
        int choice, raiseAmount, l=0;
        while(players.get(l).bet != curr_bet) {
            if(players.get(l).budget == 0) {
                l++;
            }
            else {
                System.out.println("Player "+players.get(l).id);
            System.out.println("1. Play ("+(curr_bet - players.get(l).bet)+") 2.Raise 3. Fold");
            if((choice = sc.nextInt()) == 1) {
                if((players.get(l).budget - players.get(l).bet - curr_bet) >= 0)
                    players.get(l).bet = curr_bet;
                }
            else if(choice == 2) {
                System.out.println("Raise by? (Max"+(players.get(l).budget - players.get(l).bet)+")");
                while(((raiseAmount = sc.nextInt()) > (players.get(l).budget - players.get(l).bet)) && raiseAmount == 0) {
                    System.out.println("Exceeded current budget. Enter amount less than "+players.get(l).budget);
                }
                curr_bet += raiseAmount;
                if((players.get(l).budget - players.get(l).bet - curr_bet) >= 0) {
                    players.get(l).bet = curr_bet;
                }
            }
            else {
                pot += players.get(l).bet;
                players.remove(players.get(l));
            }
            System.out.println();
            if(l >= players.size()-1)
                l = 0;
            else
                l++;
            }
        }
        
        for(Player p: players) {
            pot += p.bet;
            p.budget -= p.bet;
        }
        
        displayBoard(board);                                                    // Displays the board
        
        System.out.println();
        
        if(players.size() == 1) {
           players.get(0).budget += pot;
           System.out.println("Winner is Player "+players.get(0).id+" by default "+players.get(0).budget);
           
        }
        else {
            players = setRank(players);                                         // Assigns rank to each player based on hand strength

            Collections.sort(players);                                          //Sorts players based on hand strength

            ArrayList<Player> winners = new ArrayList<>();
            winners.add(players.get(0));
            for(int i=1; i< players.size(); i++)
                if(players.get(0).handRank == players.get(i).handRank) {
                    winners.add(players.get(i));
                }

            showWinner(winners);
            
            System.out.println();
            for(Player p : players) {
                System.out.println("Player "+p.id+" Budget = "+p.budget);
            }
        }
        
    }
    
    public static void test() {
        ArrayList<Card> a = new ArrayList<>();
        Card c = new Card();
        c.setNumber(10);
        c.setSuite('h');
        a.add(c);
        c = new Card();
        c.setNumber(5);
        c.setSuite('s');
        a.add(c);
        c = new Card();
        c.setNumber(8);
        c.setSuite('s');
        a.add(c);
        c = new Card();
        c.setNumber(2);
        c.setSuite('s');
        a.add(c);
        c = new Card();
        c.setNumber(3);
        c.setSuite('s');
        a.add(c);
        c = new Card();
        c.setNumber(9);
        c.setSuite('c');
        a.add(c);
        c = new Card();
        c.setNumber(14);
        c.setSuite('s');
        a.add(c);
        Collections.sort(a);
        int high1;
        if((high1 = Evaluator.isStraightFlush(a)) != -1) {
            System.out.println("Yes");
        }
        else{
            System.out.println("no");
        }
    }
    
    public static void main(String[] args) {
        
        System.out.println("WELCOME TO POKER\n");
        
        ArrayList<Player> players = new ArrayList<>();
        
        deck = Deck.generateDeck();                                             // Generate a deck of cards.
        players = createPlayers(3);                                             // Generate players with their respective hands
        
        playRound(players);
        
    }
    
}
