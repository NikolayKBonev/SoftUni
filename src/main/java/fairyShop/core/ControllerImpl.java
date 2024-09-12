package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.Collection;

public class ControllerImpl implements Controller {
    private HelperRepository helperRepository;
    private PresentRepository presentRepository;
    private Shop shop;

    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
        this.shop = new ShopImpl();
    }

    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        switch (type) {
            case "Happy":
                helper = new Happy(helperName);
                break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
        this.helperRepository.add(helper);
        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
        Helper helper = this.helperRepository.findByName(helperName);
        if (helper == null) {
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }
        Instrument instrument = new InstrumentImpl(power);
        helper.addInstrument(instrument);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power, helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName, energyRequired);
        this.presentRepository.add(present);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        Present present = this.presentRepository.findByName(presentName);
        Collection<Helper> helpers = this.helperRepository.getModels();

        int countBrokenInstruments = 0;
        boolean isDone = false;

        for (Helper helper : helpers) {
            if (helper.getEnergy() > 50) {
                this.shop.craft(present, helper);
                countBrokenInstruments += helper.getInstruments().stream().filter(Instrument::isBroken).count();
                if (present.isDone()) {
                    isDone = true;
                    break;
                }
            }
        }

        if (!isDone) {
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        }

        String done = present.isDone() ? "done" : "not done";
        return String.format(ConstantMessages.PRESENT_DONE, presentName, done) +
                String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, countBrokenInstruments);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        long countCraftedPresents = this.presentRepository.getModels().stream().filter(Present::isDone).count();

        sb.append(String.format("%d presents are done!", countCraftedPresents)).append(System.lineSeparator());
        sb.append("Helpers info:").append(System.lineSeparator());
        for (Helper helper : this.helperRepository.getModels()) {
            sb.append(String.format("Name: %s", helper.getName())).append(System.lineSeparator());
            sb.append(String.format("Energy: %d", helper.getEnergy())).append(System.lineSeparator());
            long notBrokenInstruments = helper.getInstruments().stream().filter(i -> !i.isBroken()).count();
            sb.append(String.format("Instruments: %d not broken left", notBrokenInstruments)).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
