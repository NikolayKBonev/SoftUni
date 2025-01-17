package goldDigger.models.museum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BaseMuseum implements Museum {
    private Collection<String> exhibits;

    public BaseMuseum() {
        this.exhibits = new ArrayList<>();
    }

    @Override
    public Collection<String> getExhibits() {
        return Collections.unmodifiableCollection(this.exhibits);
    }

    @Override
    public void addExhibit(String exhibit) {
        this.exhibits.add(exhibit);
    }
}
