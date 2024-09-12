// ControllerImpl.java
package dolphinarium.core;

import dolphinarium.entities.foods.*;
import dolphinarium.entities.pools.*;
import dolphinarium.entities.dolphins.*;
import dolphinarium.repositories.FoodRepository;
import dolphinarium.repositories.FoodRepositoryImpl;
import dolphinarium.common.*;

import java.util.*;

public class ControllerImpl implements Controller {
    private FoodRepository foodRepository;
    private Map<String, Pool> pools;
    private Map<String, Dolphin> dolphins;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.pools = new LinkedHashMap<>();
        this.dolphins = new HashMap<>();
    }

    @Override
    public String addPool(String poolType, String poolName) {
        if (this.pools.containsKey(poolName)) {
            throw new NullPointerException(ExceptionMessages.POOL_EXISTS);
        }

        Pool pool;
        switch (poolType) {
            case "DeepWaterPool":
                pool = new DeepWaterPool(poolName);
                break;
            case "ShallowWaterPool":
                pool = new ShallowWaterPool(poolName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_POOL_TYPE);
        }

        this.pools.put(poolName, pool);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_POOL_TYPE, poolType, poolName);
    }

    @Override
    public String buyFood(String foodType) {
        Food food;
        switch (foodType) {
            case "Squid":
                food = new Squid();
                break;
            case "Herring":
                food = new Herring();
                break;
            case "Mackerel":
                food = new Mackerel();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FOOD_TYPE);
        }

        this.foodRepository.add(food);
        return String.format(ConstantMessages.SUCCESSFULLY_BOUGHT_FOOD_TYPE, foodType);
    }

    @Override
    public String addFoodToPool(String poolName, String foodType) {
        Pool pool = this.pools.get(poolName);
        Food food = this.foodRepository.findByType(foodType);
        if (food == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_FOOD_FOUND, foodType));
        }

        pool.addFood(food);
        this.foodRepository.remove(food);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FOOD_IN_POOL, foodType, poolName);
    }

    @Override
    public String addDolphin(String poolName, String dolphinType, String dolphinName, int energy) {
        if (this.dolphins.containsKey(dolphinName)) {
            throw new IllegalArgumentException(ExceptionMessages.DOLPHIN_EXISTS);
        }

        Dolphin dolphin;
        switch (dolphinType) {
            case "BottleNoseDolphin":
                dolphin = new BottleNoseDolphin(dolphinName, energy);
                if (!(this.pools.get(poolName) instanceof DeepWaterPool)) {
                    return ConstantMessages.POOL_NOT_SUITABLE;
                }
                break;
            case "SpottedDolphin":
                dolphin = new SpottedDolphin(dolphinName, energy);
                break;
            case "SpinnerDolphin":
                dolphin = new SpinnerDolphin(dolphinName, energy);
                if (!(this.pools.get(poolName) instanceof ShallowWaterPool)) {
                    return ConstantMessages.POOL_NOT_SUITABLE;
                }
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_DOLPHIN_TYPE);
        }

        this.pools.get(poolName).addDolphin(dolphin);
        this.dolphins.put(dolphinName, dolphin);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DOLPHIN_IN_POOL, dolphinType, dolphinName, poolName);
    }

    @Override
    public String feedDolphins(String poolName, String foodType) {
        Pool pool = this.pools.get(poolName);
        Food food = pool.getFoods().stream()
                .filter(f -> f.getClass().getSimpleName().equals(foodType))
                .findFirst()
                .orElse(null);

        if (food == null) {
            throw new IllegalArgumentException(ExceptionMessages.NO_FOOD_OF_TYPE_ADDED_TO_POOL);
        }

        int fedDolphinsCount = 0;
        for (Dolphin dolphin : pool.getDolphins()) {
            dolphin.eat(food);
            fedDolphinsCount++;
        }

        pool.getFoods().remove(food);
        return String.format(ConstantMessages.DOLPHINS_FED, fedDolphinsCount, poolName);
    }

    @Override
    public String playWithDolphins(String poolName) {
        Pool pool = this.pools.get(poolName);
        if (pool.getDolphins().isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.NO_DOLPHINS);
        }

        int removedDolphinsCount = 0;
        Iterator<Dolphin> iterator = pool.getDolphins().iterator();

        while (iterator.hasNext()) {
            Dolphin dolphin = iterator.next();
            dolphin.jump();
            if (dolphin.getEnergy() <= 0) {
                iterator.remove();
                removedDolphinsCount++;
            }
        }

        return String.format(ConstantMessages.DOLPHINS_PLAY, poolName, removedDolphinsCount);
    }

    @Override
    public String getStatistics() {
        StringBuilder statistics = new StringBuilder();

        for (Pool pool : this.pools.values()) {
            statistics.append(String.format(ConstantMessages.DOLPHINS_FINAL, pool.getName()))
                    .append(System.lineSeparator());
            if (pool.getDolphins().isEmpty()) {
                statistics.append(ConstantMessages.NONE)
                        .append(System.lineSeparator());
            } else {
                pool.getDolphins().forEach(dolphin ->
                        statistics.append(String.format("%s - %d", dolphin.getName(), dolphin.getEnergy()))
                                .append(ConstantMessages.DELIMITER));
                statistics.setLength(statistics.length() - ConstantMessages.DELIMITER.length());
                statistics.append(System.lineSeparator());
            }
        }

        return statistics.toString().trim();
    }
}
