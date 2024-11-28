package core;

import characters.Player;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Events {

    private static final Random random = new Random();
    private static boolean inTheStorm = true;

    private Events() {

    }


    public static void takeDamage(Player player, int maxDamage) {
        int damage = random.nextInt(maxDamage) + 1; // Damage between 1 and maxDamage
        player.takeDamage(damage);
        System.out.println(player.getName() + " took " + damage + " damage! Current health: " + player.getHealth());
    }

    public static void allQuiet() {
        System.out.println("Nothing is happening right now");
        //tell the player if you want to use an item
        //use an item or just skip
    }


    public static void dropLandedNearby() {
        System.out.println("A drop is landed nearby, want to loot it?");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            //choose another event as weapon/item found or takeDamage
            weaponFound();
        } else {
            System.out.println("You ran like a pussy.");
        }
    }

    public static void enemyFound(Player MC, Player NPC) {
        System.out.println("do you want to fight (f) or escape (e)? ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        if (choice.equals("f")) {
            fight(MC, NPC);
        } else {
            escape(MC, NPC);
        }
    }

    private static void fight(Player MC, Player NPC) {
        //choose the damage depends on the weapons and player stats
        MC.attack(NPC);
    }

    private static void escape(Player MC, Player NPC) {
        //You have a chance to be seen by the other player and take damage on his weapon

        //if (mc.getStamina()>val)
        //stamina--;
        //else {
        //	//no escape take damage
        //	takeDamage(15);
        //}
    }

    public static void weaponFound() {
        System.out.println("You found a weapon!!!");
        Scanner scanner = new Scanner(System.in);

        //show what weapon has been found
        System.out.println("do you want to pick " + "NAME OF THE WEAPON" + "up? (y/n)");
        String choice = scanner.nextLine();

        if (Objects.equals(choice, "y")) {
            //add the new weapon to the inventory of the player
        } else {
            //skip an do nothing
        }
    }

    public static void itemFound() {
        System.out.println("You found an item!!!");
        ////show what item has been found
        //if (player.getNumItems() <= 5) {
        //}
        ////add the item to the player inventory
        //else {
        //    //let the player choose if want to change an item with the new one or just leave it there
        //}
    }

    public static void trapFound(Player player) {
        System.out.println("There is a trap, taking damage!!!");
        player.takeDamage(20);
    }

    public static void outOfSafeZone(Player player) {
        System.out.println("taking damage from the storm!!!");

        while (inTheStorm) {
            player.takeDamage(20);

            //if the player has a stamina>val can escape so inTheStorm= false
            //otherwise you will take damage every round if outofsafezone is on
        }

    }

}
