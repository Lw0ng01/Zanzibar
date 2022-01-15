
import java.util.*;
import java.lang.Math;
import java.util.Random;

public class Zabzibar {
	
	

	public static int setPlayerNum()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("How many players are in your game? ");
		
		int playerNum = sc.nextInt();
		
		while (playerNum < 2)
		{	
			System.out.println("This game can only be played with 2 or more players. ");
			System.out.println("\nHow many players are in your game? ");
					
			playerNum = sc.nextInt();
			
			

		}	
		return playerNum;
	}
	
	public static int setStartChips(int playerNum)
	{
		Scanner sc = new Scanner(System.in);
		
		int defaultChips;
		
		if (playerNum == 2)
		{
			defaultChips = 15;
		}
		else
		{
			defaultChips = 20;
		}
		
		System.out.print("\nWould you like to start with the default amount of chips (" + defaultChips + ")? (Y/N) ");
		String response = sc.next();
		
		if (response.equals("N") || response.equals("n"))
		{
			System.out.print("\nHow many chips would you like to start with? ");
			defaultChips = sc.nextInt();
		}
		return defaultChips;
		
	}
	
	public static void PrintArray(int p[])
	{
		for (int i = 0; i < p.length; i++)
		{
			System.out.println("Index " + i + " value" + p[i]);
		}
	}
	
	public static int startPlayer(int playerNum)
	{
		int startPlayer = -1;
		int headsCount = 0;
		int flip;
		int cPlayerCoin[], pPlayerCoin[]; //0 is tails , 1 is head
		cPlayerCoin = new int[playerNum]; //current flips
		pPlayerCoin = new int[playerNum]; //previous flips 

		Arrays.fill(cPlayerCoin, 1);
		Arrays.fill(pPlayerCoin, 1);
		
		while (headsCount != 1)
		{
			headsCount = 0;
			for (int i = 0; i < playerNum; i++)
			{
				if (cPlayerCoin[i] == 1)
				{
					flip = (int) Math.round(Math.random());
					cPlayerCoin[i] = flip;
					headsCount += flip;
				}
	
			}
			
			if (headsCount == 0)
			{
				cPlayerCoin = pPlayerCoin;
			}
			else
			{
				pPlayerCoin = cPlayerCoin;
			}
		}	
		
		for (int i = 0; i < playerNum; i++)
		{
			if (cPlayerCoin[i] == 1)
			{
				System.out.println("\nPlayer " + i + " starts first! ");
				startPlayer = i;
			}
		}
		return startPlayer;
	}
	
	public static void playRound(int playerNum, int startPlayer, Player player[], int defaultChips)
	{
		Scanner sc = new Scanner(System.in);

		int dice[];
		dice = new int[3];
		
		int pointsArr[];
		pointsArr = new int[playerNum];
		
		int leastPoints = 0;
		int rollCount = 1;
		int roll = 1;
		int rollCount2 = 1;
		int chipsPlayed = 1;
				
		while (roll == 1)
		{
			dice[0] = diceRoll();
			dice[1] = diceRoll();
			dice[2] = diceRoll();
			
			System.out.println("Player " + startPlayer + ", this is your roll: " + dice[0] + "," + dice[1] + "," + dice[2]);
			
			if (rollCount < 3)
			{
				System.out.print("Would you like to roll again? (Y/N) ");
				String rollAgain = sc.nextLine();
				
				if (rollAgain.equals("N") || rollAgain.equals("n"))
				{
					roll = 0;
				}
				else	
				{
					rollCount++;
				}
			}
			else
			{
				roll = 0;
			}
		}		
		
		
		//calculating points of first player
		pointsArr[startPlayer] = dicePointCalc(dice);
		
		//calculating chips of first player
		chipsPlayed = desiredCombo(dice);
		
		//rest of the other players rolls
		for (int i = 0; i < playerNum; i++)
		{	
			if (i != startPlayer)
			{
				roll = 1; //setting roll back to 1
				
				while (roll == 1)
				{
					dice[0] = diceRoll();
					dice[1] = diceRoll();
					dice[2] = diceRoll();
					
					System.out.println("Player " + i + ", this is your roll: " + dice[0] + "," + dice[1] + "," + dice[2]);
					
					if (rollCount2 < rollCount)
					{
						System.out.print("Would you like to roll again? (Y/N) ");
						String rollAgain = sc.nextLine();
						
						if (rollAgain.equals("N") || rollAgain.equals("n"))
						{
							roll = 0;
						}
						else
						{
							rollCount2++;
						}
					}
					else
					{
						roll = 0;
					}
				}
				


				
				//calculating points of other players
				pointsArr[i] = dicePointCalc(dice);
				
				//calculating chips of other players
				chipsPlayed = Math.max(desiredCombo(dice), chipsPlayed);
			}
		}
		
		//loop for finding winner
		int tempLow = pointsArr[0];
		int loserID = 0;
		int totalChips = 0;
		int playerChips = 0;
		
		for (int i = 1; i < pointsArr.length; i++) //finding index of lowest points in array
		{
			
			if (pointsArr[i] < tempLow)
			{
				tempLow = pointsArr[i];
				loserID = i;
			}
		}
		for (int i = 0; i < playerNum; i++)
		{
			if (i != loserID)
			{
				playerChips = player[i].getChipCount();
				totalChips += Math.min(chipsPlayed, playerChips);
				
				player[i].addChipCount(-chipsPlayed);
			}
		}
	

		player[loserID].addChipCount(totalChips); //adding chips to player with lowest points
		
	}
	
	public static int winCheck(int playerNum, Player player[])
	{
		int tempWin = -1;
		
		for (int i = 0; i < playerNum; i++)
		{
			if (player[i].getChipCount() == 0)
			{
				tempWin = i;
			}
		}
		return tempWin;
	}
	
	public static int diceRoll() //rolls dice to get integer 1 - 6
	{
		return (int) Math.round(Math.random() * 5 + 1);		
	}
	
	public static int dicePointCalc(int dice[])
	{
		int dicePoints = 0;
		
		for (int i = 0; i < dice.length; i++)
		{
			if (dice[i] == 1)
			{
				dicePoints += 100;
			}
			else if (dice[i] == 6)
			{
				dicePoints += 60;
			}
			else
			{
				dicePoints += dice[i];
			}
		}
		return dicePoints;
	}
	
	public static int desiredCombo(int dice[])
	{
		int desiredChipCounter = 0;
		
		if (dice[0] == 4 && dice[1] == 5 && dice[2] == 6) //zanzibar
		{
			desiredChipCounter = 4;
		}
		else if (dice[0] == dice[1] && dice[0] == dice[2] && dice[1] == dice[2]) //three of a kind
		{
			desiredChipCounter = 3;
		}
		else if (dice[0] == 1 && dice[1] == 2 && dice[2] == 3) //sequence
		{
			desiredChipCounter = 2;
		}
		else 
		{
			desiredChipCounter = 1;
		}
		return desiredChipCounter;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		int startingPlayer;
		int num_chips;
		int playerNum;
		int winnerID = -1;
		playerNum = setPlayerNum();	//getting the number of players from user
		
		Player playerObj[] = new Player[playerNum]; //initializing player object arrays
		num_chips = setStartChips(playerNum); //getting the number of starting chips from user
		
		//loop for setting number of starting chips 
		for (int i = 0; i < playerNum; i++)
		{
			playerObj[i] = new Player(num_chips);
		}
		

		//determine starting player
		startingPlayer = startPlayer(playerNum);
		System.out.println("\nNumber of players: " + playerNum + " \nNumber of starting chips: " + num_chips + " \nStarting player: " + startingPlayer);
		
		while (winnerID == -1)
		{
			System.out.println("\nNew Round: ");
			
			playRound(playerNum, startingPlayer, playerObj, num_chips);
			
			System.out.println("player 0: " + playerObj[0].getChipCount() + " player 1: " + playerObj[1].getChipCount());
			
			winnerID = winCheck(playerNum, playerObj);
			
			if (winnerID > -1)
			{
				System.out.println("\nCongrats, player " + winnerID + "! You win.");
			}
		}
	}
}
