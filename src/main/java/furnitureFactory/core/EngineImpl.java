package furnitureFactory.core;

import furnitureFactory.common.Command;
import furnitureFactory.entities.factories.Factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private Controller controller;
    private BufferedReader reader;

    public EngineImpl() {
        this.controller = new ControllerImpl();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result;
            try {
                result = processInput();

                if (result.equals("Exit")) {
                    break;
                }
            } catch (NullPointerException | IllegalArgumentException | IllegalStateException | IOException e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s+");

        Command command = Command.valueOf(tokens[0]);
        String result = null;
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        switch (command) {
            case BuildFactory:
                result = buildFactory(data);
                break;
            case GetFactoryByName:
                result = getFactoryByName(data);
                break;
            case BuildWorkshop:
                result = buildWorkshop(data);
                break;
            case AddWorkshopToFactory:
                result = addWorkshopToFactory(data);
                break;
            case ProduceFurniture:
                result = produceFurniture(data);
                break;
            case BuyWoodForFactory:
                result = buyWoodForFactory(data);
                break;
            case AddWoodToWorkshop:
                result = addWoodToWorkshop(data);
                break;
            case GetReport:
                result = getReport();
                break;
            case Exit:
                result = "Exit";
                break;
        }
        return result;
    }

    private String buildFactory(String[] data) {
        try {
            String factoryType = data[0];
            String factoryName = data[1];
            return this.controller.buildFactory(factoryType, factoryName);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getFactoryByName(String[] data) {
        try {
            String factoryName = data[0];
            Factory factory = this.controller.getFactoryByName(factoryName);
            return factory != null ? factory.getName() : "Factory not found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String buildWorkshop(String[] data) {
        try {
            String workshopType = data[0];
            int woodCapacity = Integer.parseInt(data[1]);
            return this.controller.buildWorkshop(workshopType, woodCapacity);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String addWorkshopToFactory(String[] data) {
        try {
            String factoryName = data[0];
            String workshopType = data[1];
            return this.controller.addWorkshopToFactory(factoryName, workshopType);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String produceFurniture(String[] data) {
        try {
            String factoryName = data[0];
            return this.controller.produceFurniture(factoryName);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String buyWoodForFactory(String[] data) {
        try {
            String woodType = data[0];
            return this.controller.buyWoodForFactory(woodType);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String addWoodToWorkshop(String[] data) {
        try {
            String factoryName = data[0];
            String workshopType = data[1];
            String woodType = data[2];
            return this.controller.addWoodToWorkshop(factoryName, workshopType, woodType);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getReport() {
        try {
            return this.controller.getReport();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
