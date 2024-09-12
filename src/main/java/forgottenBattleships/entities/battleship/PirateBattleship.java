package forgottenBattleships.entities.battleship;

public class PirateBattleship extends BaseBattleship {
    private static final int INITIAL_AMMUNITION = 80;
    private static final int HIT_STRENGTH = 10;

    public PirateBattleship(String name, int health) {
        super(name, health, INITIAL_AMMUNITION, HIT_STRENGTH);
    }

    @Override
    protected int getAmmoConsumption() {
        return 10;
    }
}
