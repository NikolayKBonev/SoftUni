// FoodRepositoryImpl.java
package dolphinarium.repositories;

import dolphinarium.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class FoodRepositoryImpl implements FoodRepository {
    private Collection<Food> foods;

    public FoodRepositoryImpl() {
        this.foods = new ArrayList<>();
    }

    @Override
    public void add(Food food) {
        this.foods.add(food);
    }

    @Override
    public boolean remove(Food food) {
        return this.foods.remove(food);
    }

    @Override
    public Food findByType(String type) {
        Optional<Food> food = this.foods.stream().filter(f -> f.getClass().getSimpleName().equals(type)).findFirst();
        return food.orElse(null);
    }
}
