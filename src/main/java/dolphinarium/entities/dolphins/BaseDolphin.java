// BaseDolphin.java
package dolphinarium.entities.dolphins;

import dolphinarium.entities.foods.Food;
import dolphinarium.common.ExceptionMessages;

public abstract class BaseDolphin implements Dolphin {
    private String name;
    private int energy;

    public BaseDolphin(String name, int energy) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.DOLPHIN_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
        this.energy = energy;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    protected void decreaseEnergy(int amount) {
        this.energy -= amount;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    protected void increaseEnergy(int amount) {
        this.energy += amount;
    }

    @Override
    public void eat(Food food) {
        increaseEnergy(food.getCalories());
    }

    @Override
    public void jump() {
        decreaseEnergy(10);
    }
}
