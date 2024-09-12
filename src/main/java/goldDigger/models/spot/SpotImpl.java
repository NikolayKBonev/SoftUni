package goldDigger.models.spot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import goldDigger.common.ExceptionMessages;

public class SpotImpl implements Spot {
    private String name;
    private Collection<String> exhibits;

    public SpotImpl(String name, String... exhibits) {
        this.setName(name);
        this.exhibits = new ArrayList<>();
        Collections.addAll(this.exhibits, exhibits);
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.SPOT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<String> getExhibits() {
        return Collections.unmodifiableCollection(this.exhibits);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addExhibit(String exhibit) {
        this.exhibits.add(exhibit);
    }
}
