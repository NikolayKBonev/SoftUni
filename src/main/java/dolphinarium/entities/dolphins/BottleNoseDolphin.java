// BottleNoseDolphin.java
package dolphinarium.entities.dolphins;

public class BottleNoseDolphin extends BaseDolphin {
    public BottleNoseDolphin(String name, int energy) {
        super(name, energy);
    }

    @Override
    public void jump() {
        super.jump();
        decreaseEnergy(190);
    }
}
