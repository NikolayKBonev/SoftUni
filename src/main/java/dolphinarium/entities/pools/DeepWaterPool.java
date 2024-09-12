// DeepWaterPool.java
package dolphinarium.entities.pools;

public class DeepWaterPool extends BasePool {
    private static final int CAPACITY = 5;

    public DeepWaterPool(String name) {
        super(name, CAPACITY);
    }
}
