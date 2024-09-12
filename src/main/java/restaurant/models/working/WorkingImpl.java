package restaurant.models.working;

import restaurant.models.client.Client;
import restaurant.models.waiter.Waiter;

import java.util.Collection;

public class WorkingImpl implements Working {

    @Override
    public void takingOrders(Client client, Collection<Waiter> waiters) {
        for (Waiter waiter : waiters) {
            while (waiter.canWork() && !client.getClientOrders().isEmpty()) {
                String order = client.getClientOrders().iterator().next();
                client.getClientOrders().remove(order);
                waiter.takenOrders().getOrdersList().add(order);
                waiter.work();
            }

            if (client.getClientOrders().isEmpty()) {
                break;
            }
        }
    }
}
