package handball.entities.equipment;

public class Kneepad extends BaseEquipment {
    private static final int PROTECTION = 120;
    private static final double PRICE = 15.0;

    public Kneepad() {
        super(PROTECTION, PRICE);
    }
}