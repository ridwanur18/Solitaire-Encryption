/**
* Your name here: Ridwanur Rahman
* Your McGill ID here: 260828139
**/
package assignment2;
import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /* 
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {

		this.numOfCards = numOfCardsPerSuit*numOfSuits + 2;

		if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13 || numOfSuits < 1 || numOfSuits > suitsInOrder.length)	{
			throw new IllegalArgumentException("Invalid input.");
		}

		int index = 0;
		Card[] cards = new Card[numOfCards];
		this.head = new PlayingCard("clubs", 1);
		this.head.next = this.head;
		this.head.prev = this.head;
		cards[0] = this.head;

		for (int i=0; i<numOfSuits; i++) {
			//char letter = suitsInOrder[i].toUpperCase().charAt(0);
			//String suit = String.valueOf(letter);
			String suit = suitsInOrder[i];
			for (int j=1; j<=numOfCardsPerSuit; j++) {
				index++;
				if (i==0)	{
					if (j==numOfCardsPerSuit)	{
						index--;
						continue;
					}
					Card nextCard = new PlayingCard(suit, j+1);
					cards[index] = nextCard;
				}
				else {
					Card nextCard = new PlayingCard(suit, j);
					cards[index] = nextCard;
				}
			}
		}

		Joker redJoker = new Joker("red");
		Joker blackJoker = new Joker("black");
		cards[numOfCards-2] = redJoker;
		cards[numOfCards-1] = blackJoker;

		for (int i=0; i<index+2; i++)	{
			cards[i].next = cards[i+1];
			cards[i+1].prev = cards[i];
		}

		this.head.prev = cards[numOfCards-1];
		cards[numOfCards-1].next = this.head;

	}

	public static void main (String[] args)	{
		//Deck d = new Deck(5, 2);
		//deck.printDeck();
		//Card c = deck.head.next.next;
		//deck.moveCard(c, 2);
		//System.out.println(deck.head == null);
		//System.out.println(deck);
		//deck.shuffle();
		//deck.printDeck();

		//Deck deck = new Deck(3, 2);

		//deck.shuffle();     // i used seed 37 for the shuffle, so if you
		// use that your output should be the same as mine
		// see Daniel's tester on shuffling

	}

	//Helper to check deck
	public void printDeck()	{

		if(this.numOfCards==1)
		{
			System.out.println("Card: " + this.head+". Value: " + this.head.getValue());
			return;
		}
		Card currentCard = this.head;
		System.out.println("Previous\tCurrent\t\t\tNext ");
		for(int cardIndex = 0; cardIndex < this.numOfCards; cardIndex++)
		{
			System.out.println(currentCard.prev + " <--------- " + currentCard + " ---------> " + currentCard.next + ", Values are: " +currentCard.prev.getValue() + " and " + currentCard.getValue() + " and " + currentCard.next.getValue());
			currentCard = currentCard.next;
		}

		System.out.println("Number of cards: " + numOfCards);

	}

    /* 
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {

		this.numOfCards = 0;
		Card temp = d.head;
		this.addCard(temp.getCopy());

		for (int i=1; i<d.numOfCards; i++)	{
			temp = temp.next;
			this.addCard(temp.getCopy());
		}

    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {}

    /* 
     * TODO: Adds the specified card at the bottom of the deck. This 
     * method runs in $O(1)$. 
     */
    public void addCard(Card c) {

		if (this.numOfCards == 1)	{
			this.head.next = c;
			c.prev = this.head;
			this.head.prev = c;
			c.next = this.head;
			this.numOfCards++;
		}
		else if (this.numOfCards == 0)	{
			this.head = c;
			c.next = this.head;
			c.prev = this.head;
			this.numOfCards++;
		}
		else	{
			Card tmpLast = this.head.prev;
			this.head.prev.next = c;
			c.prev = tmpLast;
			c.next = this.head;
			this.head.prev = c;
			this.numOfCards++;
		}

    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf. 
     * This method runs in O(n) and uses O(n) space, where n is the total 
     * number of cards in the deck.
     */
    public void shuffle() {

		int n = this.numOfCards;
		Card[] cardsShuffle = new Card[n];
		Card pointer = this.head;

		if (n == 0)	{
			return;
		}

		cardsShuffle[0] = pointer;
		gen.setSeed(10);

		for (int i=1; i<n; i++)	{
			pointer = pointer.next;
			cardsShuffle[i] = pointer;
		}

		for (int i=n-1; i>0; i--)	{
			int j = gen.nextInt(i+1);
			Card tmpCard = cardsShuffle[i];
			cardsShuffle[i] = cardsShuffle[j];
			cardsShuffle[j] = tmpCard;
		}

		this.head = cardsShuffle[0];

		for (int i=0; i<n-1; i++)	{
			cardsShuffle[i].next = cardsShuffle[i+1];
			cardsShuffle[i+1].prev = cardsShuffle[i];
		}

		this.head.prev = cardsShuffle[n-1];
		cardsShuffle[n-1].next = this.head;

    }

    /*
     * TODO: Returns a reference to the joker with the specified color in 
     * the deck. This method runs in O(n), where n is the total number of 
     * cards in the deck. 
     */
    public Joker locateJoker(String color) {

		Card pointer = this.head;

		while (!(pointer instanceof Joker))	{
			pointer = pointer.next;
		}

		Joker j = (Joker) pointer;
		if (j.getColor() == color)	{
			return j;
		}
		else {
			pointer = pointer.next;
			while (!(pointer instanceof Joker))	{
				pointer = pointer.next;
			}
			return ((Joker) pointer);
		}

	}

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can 
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */
    public void moveCard(Card c, int p) {

		int counter = 0;

		while (counter != p) {
			Card tmpBefore = c.prev;
			Card tmp = c.next;
			Card tmpAfter = c.next.next;
			tmp.next = c;
			c.prev = tmp;
			c.next = tmpAfter;
			tmpAfter.prev = c;
			tmpBefore.next = tmp;
			tmp.prev = tmpBefore;
			counter++;
		}

		Joker j = (Joker) c;
		String color = j.getColor();

		if (c instanceof Joker && c.prev == this.head)	{
			Card tmp = c.next;
			tmp.prev = c;
			c.next = tmp;
			c.prev = this.head;
			this.head.next = c;
		}
		else if (c instanceof Joker && c.prev.prev == this.head && color == "black") {
			Card tmpAfter = c.next.next;
			Card tmp = c.next;
			Card tmpBefore = c.prev;
			c.prev = tmp;
			tmp.next = c;
			c.next = tmpAfter;
			tmpAfter.prev = c;
			tmp.prev = tmpBefore;
			tmpBefore.next = tmp;
		}

    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You 
     * can assume that the input cards belong to the deck and the first one is 
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {

		if (secondCard.next == this.head && firstCard != this.head && firstCard.prev != this.head)	{
			Card dummyHead = this.head;
			Card tmpBefore = firstCard.prev;
			Card tmpAfter = firstCard.next;

			this.head = firstCard;
			this.head.prev = tmpBefore;
			tmpBefore.next = this.head;
			this.head.next = tmpAfter;
			tmpAfter.prev = this.head;
			secondCard.next = dummyHead;
			dummyHead.prev = secondCard;
		}
		else if (secondCard.next == this.head && firstCard.prev == this.head)	{
			Card dummyHead = this.head;
			Card tmp = firstCard.next;

			this.head = firstCard;
			this.head.next = tmp;
			tmp.prev = this.head;
			this.head.prev = dummyHead;
			dummyHead.next = this.head;
			secondCard.next = dummyHead;
			dummyHead.prev = secondCard;
		}
		else if (firstCard.prev == secondCard)	{
			return;
		}
		else if (firstCard.prev == this.head && secondCard.next == this.head.prev)	{
			Card dummyHead = this.head;
			Card dummyTail = secondCard.next;

			this.head = dummyTail;
			this.head.next = firstCard;
			firstCard.prev = this.head;
			this.head.prev = dummyHead;
			dummyHead.next = this.head;
			dummyHead.prev = secondCard;
			secondCard.next = dummyHead;
		}
		else if (firstCard == this.head && secondCard.next == this.head.prev)	{
			Card tmp = this.head.prev;

			this.head = tmp;
			this.head.next = firstCard;
			firstCard.prev = this.head;
			this.head.prev = secondCard;
			secondCard.next = this.head;
		}
		else if (firstCard == this.head && secondCard.next != this.head && secondCard.next.next != this.head)	{
			Card tmpHead = secondCard.next;
			Card tmpTail = this.head.prev;

			this.head = tmpHead;
			this.head.prev = secondCard;
			secondCard.next = this.head;
			tmpTail.next = firstCard;
			firstCard.prev = tmpTail;
		}
		else if (firstCard.prev == this.head && secondCard.next != this.head && secondCard.next.next != this.head)	{
			Card tmpHead = secondCard.next;
			Card tmpTail = this.head;
			Card dummyTail = this.head.prev;

			this.head = tmpHead;
			this.head.prev = tmpTail;
			tmpTail.next = this.head;
			tmpTail.prev = secondCard;
			secondCard.next = tmpTail;
			tmpTail.next = firstCard;
			firstCard.prev = tmpTail;
			dummyTail.next = firstCard;
			firstCard.prev = dummyTail;
		}
		else if (firstCard.prev != this.head && secondCard.next == this.head.prev && firstCard != this.head)	{
			Card tmpHead = secondCard.next;
			Card tmpTail = this.head;
			Card dummyTail = firstCard.prev;

			this.head = tmpHead;
			this.head.next = firstCard;
			firstCard.prev = this.head;
			this.head.prev = dummyTail;
			dummyTail.next = this.head;
			secondCard.next = tmpTail;
			tmpTail.prev = secondCard;
		}
		else {
			Card dummyHead = this.head;
			Card tmpTail = firstCard.prev;
			Card tmpHead = secondCard.next;
			Card dummyTail = this.head.prev;

			this.head = tmpHead;
			this.head.prev = tmpTail;
			tmpTail.next = this.head;
			firstCard.prev = dummyTail;
			dummyTail.next = firstCard;
			secondCard.next = dummyHead;
			dummyHead.prev = secondCard;
		}

    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the 
     * bottom card is equal to a multiple of the number of cards in the deck, 
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {

		int n = this.head.prev.getValue();
		n = n % this.numOfCards;
		int counter = 1;
		Card pointer = this.head;

		while (counter != n)	{
			pointer = pointer.next;
			counter++;
		}

		if (n==this.numOfCards-1 || n==0)	{
			return;
		}
		else {
			Card tmpHead = pointer.next;
			Card tail = this.head.prev;
			Card dummyHead = this.head;
			Card tmp = this.head.prev.prev;

			this.head = tmpHead;
			this.head.prev = tail;
			tail.next = this.head;
			tail.prev = pointer;
			pointer.next = tail;
			dummyHead.prev = tmp;
			tmp.next = dummyHead;
		}

    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the 
     * card on the top of the deck, and counting down that many cards. If the 
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {

		int n = this.head.getValue();
		n = n % this.numOfCards;
		Card pointer = this.head;
		int counter = 0;

		while (counter != n)	{
			pointer = pointer.next;
			counter++;
		}

		if (pointer instanceof Joker)	{
			return null;
		}

		return pointer;

	}

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {

		Joker redJoker = this.locateJoker("red");
		this.moveCard(redJoker, 1);
		System.out.println("after move redjoker");
		this.printDeck();
		Joker blackJoker = this.locateJoker("black");
		this.moveCard(blackJoker, 2);
		System.out.println("after move blackjoker");
		this.printDeck();

		int n = this.numOfCards;
		Card pointer = this.head;
		int counter = 0;

		while (counter != n && !(pointer instanceof Joker))	{
			pointer = pointer.next;
			counter++;
		}

		String color = ((Joker) pointer).getColor();
		if (color == "red")	{
			this.tripleCut(redJoker, blackJoker);
		}
		else	{
			this.tripleCut(blackJoker, redJoker);
		}
		System.out.println("after triplecut");
		this.printDeck();

		this.countCut();
		System.out.println("after countcut");
		this.printDeck();
		Card keyCard = this.lookUpCard();

		if (keyCard == null)	{
			return -1;
		}

		int keystreamValue = keyCard.getValue();

		return keystreamValue;

	}


    public abstract class Card { 
	public Card next;
	public Card prev;

	public abstract Card getCopy();
	public abstract int getValue();

    }

    public class PlayingCard extends Card {
	public String suit;
	public int rank;

	public PlayingCard(String s, int r) {
	    this.suit = s.toLowerCase();
	    this.rank = r;
	}

	public String toString() {
	    String info = "";
	    if (this.rank == 1) {
		//info += "Ace";
		info += "A";
	    } else if (this.rank > 10) {
		String[] cards = {"Jack", "Queen", "King"};
		//info += cards[this.rank - 11];
		info += cards[this.rank - 11].charAt(0);
	    } else {
		info += this.rank;
	    }
	    //info += " of " + this.suit;
	    info = (info + this.suit.charAt(0)).toUpperCase();
	    return info;
	}

	public PlayingCard getCopy() {
	    return new PlayingCard(this.suit, this.rank);   
	}

	public int getValue() {
	    int i;
	    for (i = 0; i < suitsInOrder.length; i++) {
		if (this.suit.equals(suitsInOrder[i]))
		    break;
	    }

	    return this.rank + 13*i;
	}

    }

    public class Joker extends Card{
	public String redOrBlack;

	public Joker(String c) {
	    if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
		throw new IllegalArgumentException("Jokers can only be red or black"); 

	    this.redOrBlack = c.toLowerCase();
	}

	public String toString() {
	    //return this.redOrBlack + " Joker";
	    return (this.redOrBlack.charAt(0) + "J").toUpperCase();
	}

	public Joker getCopy() {
	    return new Joker(this.redOrBlack);
	}

	public int getValue() {
	    return numOfCards - 1;
	}

	public String getColor() {
	    return this.redOrBlack;
	}

    }

}

