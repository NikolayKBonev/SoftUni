package fairyShop.models;

public class ShopImpl implements Shop {
    @Override
    public void craft(Present present, Helper helper) {
        while (helper.canWork() && !present.isDone()) {
            for (Instrument instrument : helper.getInstruments()) {
                while (!instrument.isBroken() && helper.canWork() && !present.isDone()) {
                    helper.work();
                    instrument.use();
                    present.getCrafted();
                }
                if (present.isDone() || !helper.canWork()) {
                    break;
                }
            }
            if (present.isDone()) {
                break;
            }
        }
    }
}
