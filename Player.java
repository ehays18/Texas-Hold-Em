


public class Player 
{
	
	int chips;
	Hand hand;
	
	public Player() {
		chips = 2000;
		hand = new Hand();
	}
	
	public void add(Card card) {
		hand.add(card);
		return;
	}
	
	public int Bet(int amount) {
		chips -= amount;
		return amount;
	}
	
	public int getChips() {
		return chips;
	}
	
	public void WonThePot(int winnings) {
		chips += winnings;
		return;
	}
	
	public void FindHands() {
		hand.FindHands();
		return;
	}
	
	public int getHandStrength() {
		return hand.handStrength;	
	}
	
	public void printHand() {
		hand.printHand();
		return;
	}
	
	public void clearHand() {
		hand.clear();
		return;
	}
	
	public boolean contains(int value) {
		boolean hasVal = hand.contains(value);
		return hasVal;
	}
	
}