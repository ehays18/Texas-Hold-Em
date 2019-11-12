//Ethan Hays


import java.util.ArrayList;

public class Hand
{
	
	ArrayList<Card> cards;
	int handStrength; //There are 10 possible hands in poker, each with a ranking
	
	public Hand() {
		cards = new ArrayList<Card>();
		handStrength = 0;
	}
	
	
	//adds a card to hand
	public void add(Card card) {
		cards.add(card);
		return;
	}
	
	//clears hand
	public void clear() {
		cards.clear();
		return;
	}
	
	public boolean contains(int val) {
		boolean hasVal = false;
		for(Card c : cards) {
			if(c.value == val) {
				hasVal = true;
			}
		}
		return hasVal;
	}
	
	
	//determines if the hand contains a royal flush
	public boolean HasRoyalFlush() {
		boolean applies = true;
		
		if(!HasStraightFlush()) {
			applies = false;
		}
		else {
			boolean ten = false;
			boolean jack = false;
			boolean queen = false;
			boolean king = false;
			boolean ace = false;
			for(int i = 0; i < cards.size(); i++) {
				if(cards.get(i).value == 10) {
					ten = true;
				}
				else if(cards.get(i).value == 11) {
					jack = true;
				}
				else if(cards.get(i).value == 12) {
					queen = true;
				}
				else if(cards.get(i).value == 13) {
					king = true;
				}
				else if(cards.get(i).value == 14) {
					ace = true;
				}
			}
			if(!ten || !jack || !queen || !king || !ace) {
				applies = false;
			}
		}
		return applies;
	}
	
	
	//determines if the hand contains a straight-flush
	public boolean HasStraightFlush() {
		boolean applies = false;
		if(cards.size() >= 5) {
			for(int i = 0; i < cards.size(); i++) {
				int lowest = cards.get(i).value;
				String matchingSuit = cards.get(i).suit;
				
				if(HasContiguousMatchingValue(lowest, matchingSuit)) {
					
					if(HasContiguousMatchingValue(lowest + 1, matchingSuit)) {
						
						if(HasContiguousMatchingValue(lowest + 2, matchingSuit)) {
							
							if(HasContiguousMatchingValue(lowest + 3, matchingSuit)) {
								
								applies = true;
							}
						}
					}
				}
				
			}
			
		}
		return applies;
	}
	
	
	//determines if the hand contains a four-of-a-kind
	public boolean HasFourOfAKind() {
		boolean applies = false;
		if(cards.size() >= 4) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				int val = cards.get(i).value;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == val) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
			}
			if(mostRepetitions == 4) {
				applies = true;
			}
		}
		return applies;
	}
	
	
	//determines if the hand contains a full house
	public boolean HasFullHouse() {
		boolean applies = false;
		boolean hasThree = false;
		int threeVal = 0;
		if(cards.size() >= 5) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				int val = cards.get(i).value;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == val) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
				if(repetitions == 3) {
					threeVal = val;
				}
			}
			if(mostRepetitions == 3) {
				hasThree = true;
			}
		}
		if(hasThree) {
			for(int i = 0; i < cards.size(); i++) {
				int twoRepetitions = 0;
				int twoVal = cards.get(i).value;
				
				if(twoVal == threeVal) {
					continue;
				}
				
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == twoVal) {
						twoRepetitions++;
					}
				}
				
				if(twoRepetitions == 2) {
					applies = true;
				}
			}
		}
		return applies;
	}
	
	
	//determines if the hand contains a flush
	public boolean HasFlush() {
		boolean allMatch = false;
		if(cards.size() >= 5) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				String flushSuit = cards.get(i).suit;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).suit.equals(flushSuit)) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
			}
			if(mostRepetitions >= 5) {
				allMatch = true;
			}
		}
		return allMatch;
	}
	
	//determines if there is a card that follows the value given that is also of the same suit 
	public boolean HasContiguousMatchingValue(int val, String matchingSuit) {
		boolean contiguousMatching = false;
		int nextVal;
		if(val == 14) {
			nextVal = 2;
		}
		else {
			nextVal = val + 1;
		}
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).value == nextVal && cards.get(i).suit.equals(matchingSuit)) {
				contiguousMatching = true;
			}
		}
		return contiguousMatching;
	}
	
	
	//determines if there is a card that follows the value given
	public boolean HasContiguousValue(int val) {
		boolean contiguous = false;
		int nextVal;
		if(val == 14) {
			nextVal = 2;
		}
		else {
			nextVal = val + 1;
		}
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).value == nextVal) {
				contiguous = true;
			}
		}
		return contiguous;
	}
	
	
	//determines if the hand contains a straight
	public boolean HasStraight() {
		boolean applies = false;
		if(cards.size() >= 5) {
			for(int i = 0; i < cards.size(); i++) {
				int lowest = cards.get(i).value; 
				
				if(HasContiguousValue(lowest)) {
					
					if(HasContiguousValue(lowest + 1)) {
						
						if(HasContiguousValue(lowest + 2)) {
							
							if(HasContiguousValue(lowest + 3)) {
								
								applies = true;
							}
						}
					}
				}
				
			}
			
		}
		return applies;
	}
	
	
	//determines if the hand contains a three-of-a-kind
	public boolean HasThreeOfAKind() {
		boolean applies = false;
		if(cards.size() >= 3) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				int val = cards.get(i).value;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == val) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
			}
			if(mostRepetitions == 3) {
				applies = true;
			}
		}
		return applies;
	}
	
	
	//determines if the hand contains a two-pair
	public boolean HasTwoPair() {
		boolean applies = false;
		boolean hasTwo = false;
		int pairOneVal = 0;
		if(cards.size() >= 4) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				int val = cards.get(i).value;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == val) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
				if(repetitions == 2) {
					pairOneVal = val;
					break;
				}
			}
			if(mostRepetitions == 2) {
				hasTwo = true;
			}
		}
		if(hasTwo) {
			for(int i = 0; i < cards.size(); i++) {
				int twoRepetitions = 0;
				int twoVal = cards.get(i).value;
				
				if(twoVal == pairOneVal) {
					continue;
				}
				
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == twoVal) {
						twoRepetitions++;
					}
				}
				
				if(twoRepetitions == 2) {
					applies = true;
				}
			}
		}
		return applies;
	}
	
	
	//determines if the hand contains a pair
	public boolean HasPair() {
		boolean applies = false;
		if(cards.size() >= 2) {
			int mostRepetitions = 0;
			for(int i = 0; i < cards.size(); i++) {
				int repetitions = 0;
				int val = cards.get(i).value;
				for(int r = 0; r < cards.size(); r++) {
					if(cards.get(r).value == val) {
						repetitions++;
					}
				}
				if(repetitions > mostRepetitions) {
					mostRepetitions = repetitions;
				}
			}
			if(mostRepetitions == 2) {
				applies = true;
			}
		}
		return applies;
	}
	
	
	//finds the strongest hand that can be played with the cards in the hand
	public void FindHands() {
		if(cards.size() >= 5) {
			if(HasRoyalFlush()) {
				handStrength = 10;
			}
			else if(HasStraightFlush()) {
				handStrength = 9;
			}
			else if(HasFourOfAKind()) {
				handStrength = 8;
			}
			else if(HasFullHouse()) {
				handStrength = 7;
			}
			else if(HasFlush()) {
				handStrength = 6;
			}
			else if(HasStraight()) {
				handStrength = 5;
			}
			else if(HasThreeOfAKind()) {
				handStrength = 4;
			}
			else if(HasTwoPair()) {
				handStrength = 3;
			}
			else if(HasPair()) {
				handStrength = 2;
			}
			else {	//High Card
				handStrength = 1;
			}
		}
		else if(cards.size() == 4) {
			if(HasFourOfAKind()) {
				handStrength = 8;
			}
			else if(HasThreeOfAKind()) {
				handStrength = 4;
			}
			else if(HasTwoPair()) {
				handStrength = 3;
			}
			else if(HasPair()) {
				handStrength = 2;
			}
			else {	//High Card
				handStrength = 1;
			}
		}
		else if(cards.size() == 3) {
			if(HasThreeOfAKind()) {
				handStrength = 4;
			}
			else if(HasPair()) {
				handStrength = 2;
			}
			else {	//High Card
				handStrength = 1;
			}
		}
		else if(cards.size() == 2) {
			if(HasPair()) {
				handStrength = 2;
			}
			else {	//High Card
				handStrength = 1;
			}
		}
		return;
	}
	
	//displays the contents of the hand
	public void printHand() {
		
		for(int i = 0; i < 2; i++) {
			cards.get(i).printValue();
			System.out.print("\t");
		}
		System.out.println();
		return;	
	} //printHand()
	
	
	
}