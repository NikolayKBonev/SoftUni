package goldDigger.models.operation;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.Collection;
import java.util.Iterator;

public class OperationImpl implements Operation {
    @Override
    public void startOperation(Spot spot, Collection<Discoverer> discoverers) {
        Iterator<String> exhibitIterator = spot.getExhibits().iterator();
        for (Discoverer discoverer : discoverers) {
            while (discoverer.canDig() && exhibitIterator.hasNext()) {
                discoverer.dig();
                discoverer.getMuseum().addExhibit(exhibitIterator.next());
                exhibitIterator.remove();
            }
            if (!exhibitIterator.hasNext()) {
                break;
            }
        }
    }
}
