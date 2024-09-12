package furnitureFactory.entities.workshops;

public class DeckingWorkshop extends BaseWorkshop {
    private static final int REDUCE_FACTOR = 150;

    public DeckingWorkshop(int woodQuantity) {
        super(woodQuantity, REDUCE_FACTOR);
    }

    @Override
    public void produce() {
        super.produce();
    }
}
