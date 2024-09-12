package furnitureFactory.entities.wood;

public abstract class BaseWood implements Wood {
    private int woodQuantity;

    protected BaseWood(int woodQuantity) {
        this.woodQuantity = woodQuantity;
    }

    @Override
    public int getWoodQuantity() {
        return this.woodQuantity;
    }
}
