package fairyShop.models;

public class Sleepy extends BaseHelper {
    private static final int INITIAL_ENERGY = 50;

    public Sleepy(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void work() {
        super.work();
        int newEnergy = this.getEnergy() - 5;
        if (newEnergy < 0) {
            newEnergy = 0;
        }
        this.setEnergy(newEnergy);
    }
}
