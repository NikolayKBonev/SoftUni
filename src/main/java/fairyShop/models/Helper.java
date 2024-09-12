package fairyShop.models;

import java.util.Collection;

public interface Helper {
    void work();

    void addInstrument(fairyShop.models.Instrument instrument);

    boolean canWork();

    String getName();

    int getEnergy();

    Collection<Instrument> getInstruments();
}
