//Ethan Hays 


//I've chosen to make Game a separate class from the runner file mostly for organizational purposes,
//so that the runner is not as cluttered and purely runs the game


//Log:
//		User folding logic complete for now
//		Working on winning logic
//			How to determine which hand is better if handStrength is equal
//		Must work on computer folding logic -- considering only implementing for final round of betting
//			for the time being



import java.util.Scanner;
import java.util.ArrayList;

public class Game
{
	
	public Game() {
		
	}
	
	
	//Runs the main logic of the game
	public void Play() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Would you like to play Texas Hold'em?\nPress 1 to play\n"
				+ "Press 2 to exit");
		int answer = input.nextInt();
		if(answer != 1) {
			System.exit(0);
		}
		
		Deck deck = new Deck();
		//deck.printValue();
		
		//ArrayList<Card> discardPile = new ArrayList<Card>();
		
		Player user = new Player();
		Player computer1 = new Player();
		Player computer2 = new Player();
		
		boolean quitPlaying = false;
		int DealerTurn = 0;
		
		while(!quitPlaying && user.chips > 0) {
			
			boolean userFold = false;
			boolean computer1Fold = false;
			boolean computer2Fold = false;
			int placeInDeck = 0;
			int pot = 0;
			
			if(DealerTurn % 3 == 0) {
				int numShuffle;
				System.out.println("You are the Dealer this round. How many times do you want to shuffle? "
						+ "Must be greater than 0.");
				numShuffle = input.nextInt();
				if(numShuffle <= 0) {
					System.out.println("Please input a number greater than 0: ");
					numShuffle = input.nextInt();
				}
				
				for(int i = 0; i < numShuffle; i++) {
					deck.RandomShuffle();
				}
			}
			else if(DealerTurn % 3 == 1) {
				int shuffles = (int)(Math.random() * 10) + 4;
				for(int i = 0; i < shuffles; i++) {
					deck.RandomShuffle();
				}
				System.out.println("You post the Small Blind this round. (5 chips)");
				user.Bet(5);
			}
			else {
				int shuffles = (int)(Math.random() * 10) + 4;
				for(int i = 0; i < shuffles; i++) {
					deck.RandomShuffle();
				}
				System.out.println("You post the Big Blind this round. (10 chips)");
				user.Bet(10);
			}
			
			System.out.println("BEGINNING OPENING DEAL\n");
			
			OpeningDeal(deck, DealerTurn, placeInDeck, user, computer1, computer2);
			
			//System.out.println("***** " + placeInDeck + " *****\n");
			
			DisplayPlayerInfo(user, computer1, computer2, pot);
			
			RoundOfBetting(input, DealerTurn, user, pot, userFold);
			
			pot += 15;
			
			System.out.println("\nFIRST ROUND OF ADDING TO THE RIVER:");
			
			ArrayList<Card> River = new ArrayList<Card>();
			for(int i = 0; i < 3; i++) {
				BurnAndTurn(deck, placeInDeck, River, user, computer1, computer2);
			}
			
			//System.out.println("***** " + placeInDeck + " *****\n");
			
			ShowRiver(River);
				
			if(!userFold) {
				
				DisplayPlayerInfo(user, computer1, computer2, pot);
			
				RoundOfBetting(input, DealerTurn, user, pot, userFold);
			}
			else { //THE USER HAS FOLDED
				ComputerBetOff(DealerTurn, pot, computer1, computer2);
			} 
			
			System.out.println("\nSECOND ROUND OF ADDING TO THE RIVER:");
			
			BurnAndTurn(deck, placeInDeck, River, user, computer1, computer2);
			
			//System.out.println("***** " + placeInDeck + " *****\n");
			
			ShowRiver(River);
			
			
			if(!userFold) {
				DisplayPlayerInfo(user, computer1, computer2, pot);
				RoundOfBetting(input, DealerTurn, user, pot, userFold);
			}
			else {
				ComputerBetOff(DealerTurn, pot, computer1, computer2);
			}
			
			System.out.println("\nTHIRD AND FINAL ROUND OF ADDING TO THE RIVER:");
			
			BurnAndTurn(deck, placeInDeck, River, user, computer1, computer2);
			
			//System.out.println("***** " + placeInDeck + " *****\n");
			
			ShowRiver(River);
			
			if(!userFold) {
				DisplayPlayerInfo(user, computer1, computer2, pot);
				System.out.println("\n*****FINAL ROUND OF BETTING*****\n\n");
				RoundOfBetting(input, DealerTurn, user, pot, userFold);
			}
			else {
				ComputerBetOff(DealerTurn, pot, computer1, computer2);
			}
			
			System.out.println("\n\n\nBETTING IS OVER, SHOWING CARDS:");
			System.out.println("Pot to win: " + pot + "chips");
			System.out.println("\nUser shows: ");
			user.printHand();
			System.out.println("\nPlayer 2 shows: ");
			computer1.printHand();
			System.out.println("\nPlayer 3 shows: ");
			computer2.printHand();
			
			
			FindWinner(userFold, computer1Fold, computer2Fold, user, computer1, computer2, pot);
			
			
			System.out.println("Press 1 if you would like to continue\n"
					+ "Press 2 if you would like to cash out");
			answer = input.nextInt();
			
			if(answer == 2) {
				System.out.println("You cashed out with " + user.getChips() + " chips.");
				break;
			}
			
			user.clearHand();
			computer1.clearHand();
			computer2.clearHand();
			DealerTurn++;
			System.out.println("\n\n\n\n\n*********************\n      New Round\n*********************\n\n\n\n\n");
			
		} //end of while loop
		return;
	}
	
	
	
	//METHODS
	
	
	
	//Makes a determination of who won the round, based on hand strength and who has folded
	public void FindWinner(boolean userFold, boolean computer1Fold, boolean computer2Fold, Player user, Player computer1, Player computer2, int pot) {
		//Betting and/or winning logic
		if(computer1Fold && computer2Fold) {
			System.out.println("\nUser wins this round with a pot of " + pot + "\n");
		}
		else if(!userFold && !computer1Fold && !computer2Fold) {
			if(user.getHandStrength() > computer1.getHandStrength() && user.getHandStrength() > computer2.getHandStrength()) {
				user.WonThePot(pot);
				System.out.println("\nUser wins this round with a pot of " + pot + "\n");
			}
			else if(user.getHandStrength() == computer1.getHandStrength() && user.getHandStrength() > computer2.getHandStrength()) {
				
			}
			else if(user.getHandStrength() == computer2.getHandStrength() && user.getHandStrength() > computer1.getHandStrength()) {
				
			}
			else if(user.getHandStrength() == computer1.getHandStrength() && user.getHandStrength() == computer2.getHandStrength()) {
				
			}
		}
		else if(!userFold && !computer1Fold && computer2Fold) {
			if(user.getHandStrength() > computer1.getHandStrength()) {
				System.out.println("User wins this round with a pot of " + pot + "\n");
			}
			else if(user.getHandStrength() < computer1.getHandStrength()) {
				System.out.println("Player 2 wins this round with a pot of " + pot + "\n");
			}
			else { //COMPARE HANDS
				
			}
		}
		else if(!userFold && computer1Fold && !computer2Fold) {
			if(user.getHandStrength() > computer2.getHandStrength()) {
				System.out.println("User wins this round with a pot of " + pot + "\n");
			}
			else if(user.getHandStrength() < computer2.getHandStrength()) {
				System.out.println("Player 3 wins this round with a pot of " + pot + "\n");
			}
			else { //COMPARE HANDS
				
			}
		}
		else if(userFold && computer1Fold && !computer2Fold) {
			System.out.println("Player 3 wins this round with a pot of" + pot + "\n");
		}
		else if(userFold && !computer1Fold && computer2Fold) {
			System.out.println("Player 2 wins this round with a pot of " + pot + "\n");
		}
		else {
			if(computer1.getHandStrength() > computer2.getHandStrength()) {
				System.out.println("Player 2 wins this round with a pot of " + pot + "\n");
			}
			else if(computer1.getHandStrength() < computer2.getHandStrength()) {
				System.out.println("Player 3 wins this round with a pot of " + pot + "\n");
			}
			else { //COMPARE HANDS
				
			}
		}
		return;
	}
	
	
	
	//displays the contents of the current river to the player
	public void ShowRiver(ArrayList<Card> River) {
		System.out.println("The River: ");
		for(Card card : River) {
			card.printValue();
			System.out.print("\t\t");
		}
		System.out.println("\n");
		return;
	}
	
	
	
	//Deals two cards to each of the players for the start of the round
	public void OpeningDeal(Deck deck, int DealerTurn, int placeInDeck, Player user, Player computer1, Player computer2) {
		
		if(DealerTurn % 3 == 0) {
			computer1.add(deck.get(placeInDeck++));
			computer2.add(deck.get(placeInDeck++));
			user.add(deck.get(placeInDeck++));
			computer1.add(deck.get(placeInDeck++));
			computer2.add(deck.get(placeInDeck++));
			user.add(deck.get(placeInDeck++));
		}
		else if(DealerTurn % 3 == 1) {
			computer2.add(deck.get(placeInDeck++));
			user.add(deck.get(placeInDeck++));
			computer1.add(deck.get(placeInDeck++));
			computer2.add(deck.get(placeInDeck++));
			user.add(deck.get(placeInDeck++));
			computer1.add(deck.get(placeInDeck++));
		}
		else {
			user.add(deck.get(placeInDeck++));
			computer1.add(deck.get(placeInDeck++));
			computer2.add(deck.get(placeInDeck++));
			user.add(deck.get(placeInDeck++));
			computer1.add(deck.get(placeInDeck++));
			computer2.add(deck.get(placeInDeck++));
		}
		
		return;
	}
	
	
	
	//Displays the player's cards, the chips of all the players, and the current pot
	public void DisplayPlayerInfo(Player user, Player computer1, Player computer2, int pot) {
		System.out.println("Your Cards:");
		user.printHand();
		user.FindHands();
		System.out.println("Estimated hand strength: " + user.getHandStrength() + "/10\n");
		System.out.println("Chips Remaining: ");
		System.out.println("User: " + user.getChips());
		System.out.println("Player 2: " + computer1.getChips());
		System.out.println("Player 3: " + computer2.getChips() + "\n");
		System.out.println("Current Pot: " + pot + "\n");
		return;
	}
	
	
	
	//runs the logic of whose turn it is to make a bet, and how the computer players will react
	public void RoundOfBetting(Scanner input, int DealerTurn, Player user, int pot, boolean userFold) {
		int answer = 0;
		if(DealerTurn % 3 == 0) { //USER BETS LAST
			System.out.println("Press 1 to call\nPress 2 to raise\nPress 3 to Fold\n");
			answer = input.nextInt();
			if(answer == 1) {
				//Must program the computer player to make a bet, if any
			}
			else if(answer == 2) {
				System.out.println("How much would you like to bet? (on top of amount to call)");
				answer = input.nextInt();
				user.Bet(answer);
				pot += answer;
				//Must program the computer player to make a raise with certain conditions
			}
			else {
				System.out.println("You fold, simulating the rest of the round.");		
				userFold = true;
			}
		}
		else if(DealerTurn % 3 == 1) { //USER BETS FIRST
			System.out.println("Press 1 to check\nPress 2 to raise\nPress 3 to Fold\n");
			answer = input.nextInt();
			if(answer == 1) {
				//Must program the computer player to make a bet, if any
			}
			else if(answer == 2) {
				System.out.println("How much would you like to bet?");
				answer = input.nextInt();
				user.Bet(answer);
				pot += answer;
				//Must program the computer player to make a raise with certain conditions
			}
			else {
				System.out.println("You fold, simulating the rest of the round.");			
				userFold = true;
			}
		}
		else {    //USER BETS SECOND
			System.out.println("Press 1 to call\nPress 2 to raise\nPress 3 to Fold\n");
			answer = input.nextInt();
			if(answer == 1) {
				//Must program the computer player to make a bet, if any
			}
			else if(answer == 2) {
				System.out.println("How much would you like to bet? (on top of amount to call)");
				answer = input.nextInt();
				user.Bet(answer);
				pot += answer;
				//Must program the computer player to make a raise with certain conditions
			}
			else {
				System.out.println("You fold, simulating the rest of the round.");			
				userFold = true;
			}
		}
		return;
	}
	
	
	
	//burns a card from the deck, and adds another to the river
	public void BurnAndTurn(Deck deck, int placeInDeck, ArrayList<Card> River, Player user, Player computer1, Player computer2) {
		placeInDeck++; //Burn a card
		
		River.add(deck.get(placeInDeck));
		user.add(deck.get(placeInDeck));
		computer1.add(deck.get(placeInDeck));
		computer2.add(deck.get(placeInDeck));
		placeInDeck++;
		return;
	}
	
	
	
	//runs the logic for how the computer players bet against each other if the user has folded
	public void ComputerBetOff(int DealerTurn, int pot, Player computer1, Player computer2) {
		//computer1 first
		if(DealerTurn % 3 == 0) {
			if(computer1.getHandStrength() > 3) {
				int bet1 = (int)(Math.random() * (computer1.getChips() * .30) + 20);
				computer1.Bet(bet1);
				pot += bet1;
				System.out.println("Player 2 bets " + bet1 + " chips.");
				if(computer2.getHandStrength() > 3) {
					int bet2 = (int)(Math.random() * (computer2.getChips() * .10) + bet1);
					computer2.Bet(bet2);
					pot += bet2;
					if(bet2 == bet1) {
						System.out.println("Player 3 calls " + bet1 + " chips.");
					}
					else {
						System.out.println("Player 3 raises to " + bet2 + " chips.");
						int bet3 = bet2 - bet1;
						computer1.Bet(bet3);
						pot += bet3;
						System.out.println("Player 2 calls.");
					}
				}
				else {
					computer2.Bet(bet1);
					pot += bet1;
					System.out.println("Player 3 calls " + bet1 + " chips.");
				}
			}
			else {
				int bet1 = (int)(Math.random()*(computer1.getChips() * .05));
				computer1.Bet(bet1);
				pot += bet1;
				if(bet1 == 0) {
					System.out.println("Player 2 checks.");
				}
				else {
					System.out.println("Player 2 bets " + bet1 + "chips.");
				}
				if(computer2.getHandStrength() > 1) {
					int bet2 = (int)(Math.random() * (computer2.getChips() * .10) + bet1);
					computer2.Bet(bet2);
					pot += bet2;
					System.out.println("Player 3 raises to " + bet2 + " chips.");
					int bet3 = bet2 - bet1;
					computer1.Bet(bet3);
					pot += bet3;
					System.out.println("Player 2 calls.");
				}
				else {
					computer2.Bet(bet1);
					pot += bet1;
					System.out.println("Player 3 calls " + bet1 + " chips.");
				}
			}
		}
		//computer2 first
		else if(DealerTurn % 3 == 1) {
			if(computer2.getHandStrength() > 3) {
				int bet1 = (int)(Math.random() * (computer2.getChips() * .30) + 20);
				computer2.Bet(bet1);
				pot += bet1;
				System.out.println("Player 3 bets " + bet1 + " chips.");
				if(computer1.getHandStrength() > 3) { 
					int bet2 = (int)(Math.random() * (computer2.getChips() * .10) + bet1);
					computer1.Bet(bet2);
					pot += bet2;
					if(bet2 == bet1) {
						System.out.println("Player 2 calls " + bet1 + " chips.");
					}
					else {
						System.out.println("Player 2 raises to " + bet2 + " chips.");
						int bet3 = bet2 - bet1;
						computer2.Bet(bet3);
						pot += bet3;
						System.out.println("Player 3 calls.");
					}
				} 
				else {
					computer1.Bet(bet1);
					pot += bet1;
					System.out.println("Player 2 calls " + bet1 + " chips.");
				}
			}
			else {
				int bet1 = (int)(Math.random()*(computer2.getChips() * .05));
				computer2.Bet(bet1);
				pot += bet1;
				if(bet1 == 0) {
					System.out.println("Player 3 checks.");
				}
				else {
					System.out.println("Player 3 bets " + bet1 + "chips.");
				}
				if(computer1.getHandStrength() > 1) {
					int bet2 = (int)(Math.random() * (computer1.getChips() * .10) + bet1);
					computer1.Bet(bet2);
					pot += bet2;
					System.out.println("Player 2 raises to " + bet2 + " chips.");
					int bet3 = bet2 - bet1;
					computer2.Bet(bet3);
					pot += bet3;
					System.out.println("Player 3 calls.");
				}
				else {
					computer1.Bet(bet1);
					pot += bet1;
					System.out.println("Player 2 calls " + bet1 + " chips.");
				}
			}
		}
		//computer1 first
		else { 
			if(DealerTurn % 3 == 0) {
				if(computer1.getHandStrength() > 3) {
					int bet1 = (int)(Math.random() * (computer1.getChips() * .30) + 20);
					computer1.Bet(bet1);
					pot += bet1;
					System.out.println("Player 2 bets " + bet1 + " chips.");
					if(computer2.getHandStrength() > 3) {
						int bet2 = (int)(Math.random() * (computer2.getChips() * .10) + bet1);
						computer2.Bet(bet2);
						pot += bet2;
						if(bet2 == bet1) {
							System.out.println("Player 3 calls " + bet1 + " chips.");
						}
						else {
							System.out.println("Player 3 raises to " + bet2 + " chips.");
							int bet3 = bet2 - bet1;
							computer1.Bet(bet3);
							pot += bet3;
							System.out.println("Player 2 calls.");
						}
					}
					else {
						computer2.Bet(bet1);
						pot += bet1;
						System.out.println("Player 3 calls " + bet1 + " chips.");
					}
				}
			}
				else {
					int bet1 = (int)(Math.random()*(computer1.getChips() * .05));
					computer1.Bet(bet1);
					pot += bet1;
					if(bet1 == 0) {
						System.out.println("Player 2 checks.");
					}
					else {
						System.out.println("Player 2 bets " + bet1 + "chips.");
					}
					if(computer2.getHandStrength() > 1) {
						int bet2 = (int)(Math.random() * (computer2.getChips() * .10) + bet1);
						computer2.Bet(bet2);
						pot += bet2;
						System.out.println("Player 3 raises to " + bet2 + " chips.");
						int bet3 = bet2 - bet1;
						computer1.Bet(bet3);
						pot += bet3;
						System.out.println("Player 2 calls.");
					}
					else {
						computer2.Bet(bet1);
						pot += bet1;
						System.out.println("Player 3 calls " + bet1 + " chips.");
					}
				}
		}
	}
	
	
	
}