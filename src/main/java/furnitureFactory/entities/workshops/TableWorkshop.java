package furnitureFactory.entities.workshops;

public class TableWorkshop extends BaseWorkshop {
    private static final int REDUCE_FACTOR = 50;

    public TableWorkshop(int woodQuantity) {
        super(woodQuantity, REDUCE_FACTOR);
    }

    @Override
    public void produce() {
        super.produce();
    }
}
