package furnitureFactory.core;

import furnitureFactory.common.ConstantMessages;
import furnitureFactory.common.ExceptionMessages;
import furnitureFactory.entities.factories.*;
import furnitureFactory.entities.workshops.*;
import furnitureFactory.entities.wood.*;
import furnitureFactory.repositories.*;
import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private WoodRepository woodRepository;
    private WorkshopRepository workshopRepository;
    private Collection<Factory> factories;

    public ControllerImpl() {
        this.woodRepository = new WoodRepositoryImpl();
        this.workshopRepository = new WorkshopRepositoryImpl();
        this.factories = new ArrayList<>();
    }

    @Override
    public String buildFactory(String factoryType, String factoryName) {
        Factory factory;

        switch (factoryType) {
            case "OrdinaryFactory":
                factory = new OrdinaryFactory(factoryName);
                break;
            case "AdvancedFactory":
                factory = new AdvancedFactory(factoryName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FACTORY_TYPE);
        }

        if (this.factories.stream().anyMatch(f -> f.getName().equals(factoryName))) {
            throw new NullPointerException(ExceptionMessages.FACTORY_EXISTS);
        }

        this.factories.add(factory);
        return String.format(ConstantMessages.SUCCESSFULLY_BUILD_FACTORY_TYPE, factoryType, factoryName);
    }

    @Override
    public Factory getFactoryByName(String factoryName) {
        return this.factories.stream()
                .filter(f -> f.getName().equals(factoryName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String buildWorkshop(String workshopType, int woodCapacity) {
        Workshop workshop;

        switch (workshopType) {
            case "TableWorkshop":
                workshop = new TableWorkshop(woodCapacity);
                break;
            case "DeckingWorkshop":
                workshop = new DeckingWorkshop(woodCapacity);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_WORKSHOP_TYPE);
        }

        this.workshopRepository.add(workshop);
        return String.format(ConstantMessages.SUCCESSFULLY_BUILD_WORKSHOP_TYPE, workshopType);
    }

    @Override
    public String addWorkshopToFactory(String factoryName, String workshopType) {
        Factory factory = this.getFactoryByName(factoryName);
        Workshop workshop = this.workshopRepository.findByType(workshopType);

        if (factory == null) {
            throw new NullPointerException(String.format(ExceptionMessages.NO_WORKSHOP_FOUND, workshopType));
        }

        if (workshop == null) {
            throw new NullPointerException(String.format(ExceptionMessages.NO_WORKSHOP_FOUND, workshopType));
        }

        if (factory.getWorkshops().stream().anyMatch(w -> w.getClass().getSimpleName().equals(workshopType))) {
            throw new IllegalArgumentException(ExceptionMessages.WORKSHOP_EXISTS);
        }

        if ((factory instanceof OrdinaryFactory && workshop instanceof DeckingWorkshop) ||
                (factory instanceof AdvancedFactory && workshop instanceof TableWorkshop)) {
            return ExceptionMessages.NON_SUPPORTED_WORKSHOP;
        }

        factory.addWorkshop(workshop);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_WORKSHOP_IN_FACTORY, workshopType, factoryName);
    }

    @Override
    public String buyWoodForFactory(String woodType) {
        Wood wood;

        switch (woodType) {
            case "OakWood":
                wood = new OakWood();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_WOOD_TYPE);
        }

        this.woodRepository.add(wood);
        return String.format(ConstantMessages.SUCCESSFULLY_BOUGHT_WOOD_FOR_FACTORY, woodType);
    }

    @Override
    public String addWoodToWorkshop(String factoryName, String workshopType, String woodType) {
        Factory factory = this.getFactoryByName(factoryName);

        if (factory == null || factory.getWorkshops().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.NO_WORKSHOP_ADDED);
        }

        Wood wood = this.woodRepository.findByType(woodType);

        if (wood == null) {
            throw new NullPointerException(String.format(ExceptionMessages.NO_WOOD_FOUND, woodType));
        }

        Workshop workshop = factory.getWorkshops().stream()
                .filter(w -> w.getClass().getSimpleName().equals(workshopType))
                .findFirst()
                .orElse(null);

        if (workshop == null) {
            throw new NullPointerException(String.format(ExceptionMessages.NO_WORKSHOP_FOUND, workshopType));
        }

        workshop.addWood(wood);
        this.woodRepository.remove(wood);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_WOOD_IN_WORKSHOP, woodType, workshopType);
    }

    @Override
    public String produceFurniture(String factoryName) {
        Factory factory = this.getFactoryByName(factoryName);

        if (factory == null || factory.getWorkshops().isEmpty()) {
            throw new NullPointerException(String.format(ExceptionMessages.THERE_ARE_NO_WORKSHOPS, factoryName));
        }

        int producedCount = 0;
        Collection<Workshop> workshopsToRemove = new ArrayList<>();

        for (Workshop workshop : factory.getWorkshops()) {
            if (workshop.getWoodQuantity() < workshop.getWoodQuantityReduceFactor()) {
                System.out.println(ExceptionMessages.INSUFFICIENT_WOOD);
            } else {
                workshop.produce();
                producedCount++;
            }

            if (workshop.getWoodQuantity() <= 0) {
                System.out.println(String.format(ExceptionMessages.WORKSHOP_STOPPED_WORKING, workshop.getClass().getSimpleName()));
                workshopsToRemove.add(workshop);
            }
        }

        for (Workshop workshop : workshopsToRemove) {
            factory.getWorkshops().remove(workshop);
            ((WorkshopRepositoryImpl) this.workshopRepository).remove(workshop);
            factory.getRemovedWorkshops().add(workshop);
        }

        if (producedCount == 0) {
            return String.format(ExceptionMessages.FACTORY_DO_NOT_PRODUCED, factoryName);
        } else {
            return String.format(ConstantMessages.SUCCESSFUL_PRODUCTION, producedCount, factoryName);
        }
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();

        for (Factory factory : this.factories) {
            report.append(String.format("Production by %s factory:\n", factory.getName()));

            if (factory.getWorkshops().isEmpty()) {
                report.append("  No workshops were added to produce furniture.\n");
            } else {
                for (Workshop workshop : factory.getWorkshops()) {
                    report.append(String.format("  %s: %d furniture produced\n",
                            workshop.getClass().getSimpleName(), workshop.getProducedFurnitureCount()));
                }
            }
        }

        return report.toString().trim();
    }
}
