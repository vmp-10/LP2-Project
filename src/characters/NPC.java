package characters;

@Deprecated
public class NPC extends Player {
    public NPC(String name, int health, int armor, int stamina, double strength) {
        super(name, health, armor, stamina, strength);
    }

    @Override
    public void attack(Player target) {
        int damage = (int) (strength * 10); // Replace 10 for tool damage
        System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage!");
        target.takeDamage(damage);
    }
}