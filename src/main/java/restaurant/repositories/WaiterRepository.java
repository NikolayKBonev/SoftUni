package restaurant.repositories;

import restaurant.models.waiter.Waiter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WaiterRepository implements Repository<Waiter> {
    private List<Waiter> waiters;

    public WaiterRepository() {
        this.waiters = new ArrayList<>();
    }

    @Override
    public Collection<Waiter> getCollection() {
        return Collections.unmodifiableList(waiters);
    }

    @Override
    public void add(Waiter waiter) {
        waiters.add(waiter);
    }

    @Override
    public boolean remove(Waiter waiter) {
        return waiters.remove(waiter);
    }

    @Override
    public Waiter byName(String name) {
        return waiters.stream().filter(w -> w.getName().equals(name)).findFirst().orElse(null);
    }
}
