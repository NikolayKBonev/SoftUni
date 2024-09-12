package fairyShop.models;

public class PresentImpl implements Present {
    private String name;
    private int energyRequired;

    public PresentImpl(String name, int energyRequired) {
        this.setName(name);
        this.setEnergyRequired(energyRequired);
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Present name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public int getEnergyRequired() {
        return energyRequired;
    }

    private void setEnergyRequired(int energyRequired) {
        if (energyRequired < 0) {
            throw new IllegalArgumentException("Cannot create a Present requiring negative energy!");
        }
        this.energyRequired = energyRequired;
    }

    @Override
    public void getCrafted() {
        this.energyRequired -= 10;
        if (this.energyRequired < 0) {
            this.energyRequired = 0;
        }
    }

    @Override
    public boolean isDone() {
        return this.energyRequired == 0;
    }
}
