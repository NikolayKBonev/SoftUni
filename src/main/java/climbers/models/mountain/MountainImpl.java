package climbers.models.mountain;

import climbers.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;

public class MountainImpl implements Mountain{

    private String name;
    private Collection<String> peaksList;
    public MountainImpl(String name) {
        setName(name);
        this.peaksList = new ArrayList<>();
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.MOUNTAIN_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public Collection<String> getPeaksList() {
        return peaksList;
    }

    @Override
    public String getName() {
        return name;
    }
}
