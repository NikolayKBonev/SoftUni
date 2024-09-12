package forgottenBattleships.entities.battleship;

public abstract class BaseBattleship implements Battleship {
    private String name;
    private int health;
    private int ammunition;
    private int hitStrength;

    protected BaseBattleship(String name, int health, int ammunition, int hitStrength) {
        setName(name);
        setHealth(health);
        this.ammunition = ammunition;
        this.hitStrength = hitStrength;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Ship name cannot be null or empty.");
        }
        this.name = name;
    }

    private void setHealth(int health) {
        if (health <= 0) {
            throw new IllegalArgumentException("Ship's health cannot be below or equal to 0.");
        }
        this.health = health;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getAmmunition() {
        return this.ammunition;
    }

    @Override
    public int getHitStrength() {
        return this.hitStrength;
    }

    @Override
    public void attack(Battleship battleship) {
        this.ammunition = Math.max(0, this.ammunition - getAmmoConsumption());
        battleship.takeDamage(this);
    }

    @Override
    public void takeDamage(Battleship battleship) {
        this.health = Math.max(0, this.health - battleship.getHitStrength());
    }

    protected abstract int getAmmoConsumption();
}
