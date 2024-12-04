package core;

import characters.MC;
import characters.Player;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

//TODO: Verify if player or NPC, if NPC, no prints nor inputs allowed
public class Events {

	private static final Random random = new Random();
	private static boolean inTheStorm = true;
	private static Scanner scanner = new Scanner(System.in);

	private Events() {

	}

	public static void takeDamage(Player player, int maxDamage) {
		int damage = random.nextInt(maxDamage) + 1; // Damage between 1 and maxDamage
		player.takeDamage(damage);
		System.out.println(player.getName() + " took " + damage + " damage! Current health: " + player.getHealth());
	}

	public static void allQuiet(Player player) {
		if (player.isHuman() == true) {
			System.out.println("Nothing is happening right now");

			if (player.hasItems()) {
				System.out.println("Do you want to use an item from your inventory? (y/n)");

				String choice = "";

				while (true) {
					try {
						choice = scanner.nextLine().trim().toLowerCase();

						if (!choice.equals("y") && !choice.equals("n")) {
							throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
						}
						break; // exit the loop is has invalid input
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}

				if (choice.equals("y")) {
					// Display items available
					System.out.println("Displaying available items...");
					// Logic for choosing and using an item
				} else {
					System.out.println("You did nothing.");
				}
			}

		}
	}

	public static void dropLandedNearby(Player player) {
		if (player.isHuman() == true) {
			System.out.println("A drop has landed nearby, do you want to loot it?");

			String choice = "";
			while (true) {
				try {
					choice = scanner.nextLine().trim().toLowerCase();

					if (!choice.equals("y") && !choice.equals("n")) {
						throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
					}
					break; // exit the loop is has invalid input
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}

			if (choice.equals("y")) {
				// choose another event as weapon/item found or takeDamage
				int option = random.nextInt(3);
				switch (option) {
				case 0 -> weaponFound(player);
				case 1 -> itemFound(player);
				case 2 -> trapFound(player);
				}
			} else {
				System.out.println("You ran away.");
			}
		}
	}

	public static void enemyFound(Player MC, Player NPC) {
	    String choice = "";
	    System.out.print("You found a player, do you want to fight [y/n]? "); 
	    while (true) {
	        try {
	            choice = scanner.nextLine().trim().toLowerCase();

	            if (!choice.equals("y") && !choice.equals("n")) {
	                throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
	            }
	            break; // exit the loop if the input is valid
	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    
	    if (choice.equals("y")) {
	        fight(MC, NPC);
	    } else {
	        escape(MC, NPC);
	    }
	}


	private static void fight(Player MC, Player NPC) {
		// choose the damage depends on the weapons and player stats
		MC.attack(NPC);
	}

	private static void escape(Player MC, Player NPC) {
		// You have a chance to be seen by the other player and take damage on his
		// weapon
		System.out.println("trying to escape!!");
		int staminaLoss = random.nextInt(2);
		int stamina = MC.getStamina();
		if (stamina > 5)
			MC.setStamina(stamina - staminaLoss);
		else {
			// no escape take damage
			NPC.attack(MC);
		}
	}

	public static void weaponFound(Player player) {
		if (player.isHuman() == true) {
			System.out.println("You found a weapon!!!");

			// show what weapon has been found
			// TODO: Show difference in stats, you need Javi's part done for that
			System.out.println("Do you want to pick " + "NAME OF THE WEAPON" + "up? (y/n)");
			String choice = "";
			while (true) {
				try {
					choice = scanner.nextLine().trim().toLowerCase();

					if (!choice.equals("y") && !choice.equals("n")) {
						throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
					}
					break; // exit the loop is has invalid input
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}

			if (Objects.equals(choice, "y")) {
				// add the new weapon to the inventory of the player
			} else {
				System.out.println("Ok that's fine, there's your current inventory");
				// show the inventory to the player
				// skip an do nothing
			}
		}
	}

	public static void itemFound(Player player) {
		if (player.isHuman() == true) {
			System.out.println("You found an item!!!");
			// show what item has been found
			if (player.getItems().size() <= 5) {

			}
			// add the item to the player inventory
			else {
				// let the player choose if want to change an item with the new one or just
				// leave it there
				String choice = "";
				while (true) {
					try {
						choice = scanner.nextLine().trim().toLowerCase();

						if (!choice.equals("y") && !choice.equals("n")) {
							throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
						}
						break; // exit the loop is has invalid input
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
				if (choice.equals("y")) {
					// change item with one of the inventory
					// TODO: inventory swap
				} else {
					System.out.print("Ok that's fine");
				}
			}
		}
	}

	public static void trapFound(Player player) {
		if (player.isHuman() == true) {
			int chanceToTakeDamage = random.nextInt(100) + 1;
			if (chanceToTakeDamage >= 50) {
				System.out.println("There is a trap and you fell for it.");
				player.takeDamage(20);
			} else {
				System.out.println("You got lucky and you avoided a trap.");
			}
		}
	}

	public static void outOfSafeZone(Player player) {
		if (player.isHuman() == true) {
			System.out.println("taking damage from the storm!!!");

			player.takeDamage(20);

			// if the player has a stamina>val can escape so inTheStorm= false
			// otherwise you will take damage every round if outofsafezone is on
			// TODO: this part has to be in game flow during rounds
		}
	}

	public static void main(String[] args) {

		Player player = new Player("Trump", 100, 150, 100, 0.9, true); // you can use Players.preMadePlayers.get()
		Player npc = new Player("gino", 100, 150, 100, 0.9, false);
		ArrayList<Player> players = new ArrayList<>();
		players.add(player);
		
		int index = 0;
		while (true) {
			Random random = new Random();
			int event = random.nextInt(7);
			System.out.println(event);
			switch (event) {
			// If trap placed, event that kills random player can be active
			case 0 -> enemyFound(players.get(index), npc);
			case 1 -> weaponFound(players.get(index));
			case 2 -> itemFound(players.get(index));
			case 3 -> trapFound(players.get(index));
			case 4 -> outOfSafeZone(players.get(index));
			case 5 -> dropLandedNearby(players.get(index));
			case 6 -> allQuiet(players.get(index));
			case 7 -> takeDamage(players.get(index), 10);
			}
		}
	}
}
