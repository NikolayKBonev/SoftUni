// SpottedDolphin.java
package dolphinarium.entities.dolphins;

public class SpottedDolphin extends BaseDolphin {
    public SpottedDolphin(String name, int energy) {
        super(name, energy);
    }

    @Override
    public void jump() {
        super.jump();
        decreaseEnergy(90);
    }
}
