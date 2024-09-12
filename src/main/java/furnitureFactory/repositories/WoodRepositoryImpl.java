package furnitureFactory.repositories;

import furnitureFactory.entities.wood.Wood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class WoodRepositoryImpl implements WoodRepository {
    private Collection<Wood> woodTypes;

    public WoodRepositoryImpl() {
        this.woodTypes = new ArrayList<>();
    }

    @Override
    public void add(Wood wood) {
        this.woodTypes.add(wood);
    }

    @Override
    public boolean remove(Wood wood) {
        return this.woodTypes.remove(wood);
    }

    @Override
    public Wood findByType(String type) {
        Optional<Wood> wood = this.woodTypes.stream()
                .filter(w -> w.getClass().getSimpleName().equals(type))
                .findFirst();
        return wood.orElse(null);
    }
}
