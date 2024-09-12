package furnitureFactory.entities.workshops;

import furnitureFactory.entities.wood.Wood;

public abstract class BaseWorkshop implements Workshop {
    private int woodQuantity;
    private int producedFurnitureCount;
    private int woodQuantityReduceFactor;

    protected BaseWorkshop(int woodQuantity, int woodQuantityReduceFactor) {
        this.woodQuantity = woodQuantity > 0 ? woodQuantity : 0;
        this.producedFurnitureCount = 0;
        this.woodQuantityReduceFactor = woodQuantityReduceFactor;
    }

    @Override
    public int getWoodQuantity() {
        return this.woodQuantity;
    }

    @Override
    public int getProducedFurnitureCount() {
        return this.producedFurnitureCount;
    }

    @Override
    public int getWoodQuantityReduceFactor() {
        return this.woodQuantityReduceFactor;
    }

    @Override
    public void addWood(Wood wood) {
        this.woodQuantity += wood.getWoodQuantity();
    }

    @Override
    public void produce() {
        if (this.woodQuantity < this.woodQuantityReduceFactor) {
            this.woodQuantity = 0;
        } else {
            this.woodQuantity -= this.woodQuantityReduceFactor;
        }
        this.producedFurnitureCount++;
    }
}
