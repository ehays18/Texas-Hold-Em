//Ethan Hays

import java.util.ArrayList;

public class Deck
{
	
	ArrayList<Card> contents;
	
	public Deck() {
		contents = new ArrayList<Card>();
		
		for(int i = 2; i < 15; i++) {
			if(i == 14) {
				contents.add(new Card("Spades", i, "Ace"));
			}
			else if(i == 11) {
				contents.add(new Card("Spades", i, "Jack"));
			}
			else if(i == 12) {
				contents.add(new Card("Spades", i, "Queen"));
			}
			else if(i == 13) {
				contents.add(new Card("Spades", i, "King"));
			}
			else {
				contents.add(new Card("Spades", i, ""+i));
			}
		}
		for(int i = 2; i < 15; i++) {
			if(i == 14) {
				contents.add(new Card("Clubs", i, "Ace"));
			}
			else if(i == 11) {
				contents.add(new Card("Clubs", i, "Jack"));
			}
			else if(i == 12) {
				contents.add(new Card("Clubs", i, "Queen"));
			}
			else if(i == 13) {
				contents.add(new Card("Clubs", i, "King"));
			}
			else {
				contents.add(new Card("Clubs", i, ""+i));
			}
		}
		for(int i = 2; i < 15; i++) {
			if(i == 14) {
				contents.add(new Card("Diamonds", i, "Ace"));
			}
			else if(i == 11) {
				contents.add(new Card("Diamonds", i, "Jack"));
			}
			else if(i == 12) {
				contents.add(new Card("Diamonds", i, "Queen"));
			}
			else if(i == 13) {
				contents.add(new Card("Diamonds", i, "King"));
			}
			else {
				contents.add(new Card("Diamonds", i, ""+i));
			}
		}
		for(int i = 2; i < 15; i++) {
			if(i == 14) {
				contents.add(new Card("Hearts", i, "Ace"));
			}
			else if(i == 11) {
				contents.add(new Card("Hearts", i, "Jack"));
			}
			else if(i == 12) {
				contents.add(new Card("Hearts", i, "Queen"));
			}
			else if(i == 13) {
				contents.add(new Card("Hearts", i, "King"));
			}
			else {
				contents.add(new Card("Hearts", i, ""+i));
			}
		}
		
	} //Deck()
	
	
	public Card get(int index) {
		return contents.get(index);
	}
	
	
	//This method performs a standard riffling shuffle on the deck that a human would normally do. 
	//However, the computer performs it perfectly, placing cards from each perfect half of the split deck 
	//alternating one by one. This can cause some problems, because it makes the shuffle not truly random.
	//Because of this, I'll opt for a shuffling method that the computer can make random. I will keep this
	//method as an option, however.
	public void RiffleShuffle() {
		ArrayList<Card> half1 = new ArrayList<Card>();
		ArrayList<Card> half2 = new ArrayList<Card>();
		
		for(int i = 0; i < 26; i++) {
			half1.add(this.contents.get(i));
		}
		for(int i = 26; i < 52; i++) {
			half2.add(this.contents.get(i));
		}
		
		int counter = 0;
		
		for(int i = 0; i < 26; i++) {
			this.contents.set(counter++, half1.get(i));
			this.contents.set(counter++, half2.get(i));
		}
		
		return;
	} //RiffleShuffle()
	
	//This method performs a more random shuffle, by generating a list of random indices
	//to rearrange the contents of the deck into. 
	public void RandomShuffle() {
		ArrayList<Integer> randIndices = new ArrayList<Integer>();
		
		//generate a random order of integers from 0 to 51. This is probably not the most 
		//efficient way to randomly shuffle the deck, but it is certainly more random
		//than the riffle shuffle.
		while(randIndices.size() < 52) {
			
			int randomIndex = (int)(Math.random() * 52);
			//if the list does not already contain the randomly generated number, add it to the list
			if(!randIndices.contains(randomIndex)) { 
				randIndices.add(randomIndex);
			}
			
		} //end while loop
		
		ArrayList<Card> replacement = new ArrayList<Card>();
		
		for(int index : randIndices) {
			replacement.add(this.contents.get(index));
		} //end for loop
		
		this.contents = replacement;
		
		return;		
	} //RandomShuffle()
	
	//This method prints out the contents of the deck in order from top to bottom. This is mostly
	//used as a tool to ensure no cards are repeated, and to ensure the cards were shuffled.
	public void printValue() {
		
		for(Card card : this.contents) {
			card.printValue();
			System.out.println();
		}
		return;	
	} //printValue()
	
}