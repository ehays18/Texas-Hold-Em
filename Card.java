//Ethan Hays


public class Card
{
	String suit;
	int value;
	String name;
	
	public Card() {
		suit = "";
		value = 0;
		name = "";
	}
	
	public Card(String s, int v, String n) {
		suit = s;
		value = v;
		name = n;
	}
	
	//returns 1 if card1 is a higher value than card2, 0 if equal, -1 if lower value
	public int HighCard(Card card1, Card card2) {
		int isHigher;
		if(card1.value > card2.value) {
			isHigher = 1;
		}
		else if(card1.value == card2.value) {
			isHigher = 0;
		}
		else {
			isHigher = -1;
		}
		return isHigher;
	}
	
	public int compareHand() {
		return 0;
	}
	
	public void printValue() {
		System.out.print("" + name + " of " + suit);
	}
	
}