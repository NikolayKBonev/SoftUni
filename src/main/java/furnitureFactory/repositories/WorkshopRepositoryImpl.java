package furnitureFactory.repositories;

import furnitureFactory.entities.workshops.Workshop;
import furnitureFactory.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class WorkshopRepositoryImpl implements WorkshopRepository {
    private Collection<Workshop> workshops;

    public WorkshopRepositoryImpl() {
        this.workshops = new ArrayList<>();
    }

    @Override
    public void add(Workshop workshop) {
        if (workshop.getWoodQuantity() <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.WORKSHOP_WOOD_QUANTITY_BELOW_OR_EQUAL_ZERO);
        }

        Optional<Workshop> existingWorkshop = this.workshops.stream()
                .filter(w -> w.getClass().getSimpleName().equals(workshop.getClass().getSimpleName()))
                .findFirst();

        if (existingWorkshop.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessages.WORKSHOP_EXISTS_IN_REPOSITORY);
        }

        this.workshops.add(workshop);
    }

    @Override
    public boolean remove(Workshop workshop) {
        return this.workshops.remove(workshop);
    }

    @Override
    public Workshop findByType(String type) {
        Optional<Workshop> workshop = this.workshops.stream()
                .filter(w -> w.getClass().getSimpleName().equals(type))
                .findFirst();
        return workshop.orElse(null);
    }
}
