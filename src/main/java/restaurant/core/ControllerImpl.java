package restaurant.core;

import restaurant.common.ConstantMessages;
import restaurant.common.ExceptionMessages;
import restaurant.models.client.Client;
import restaurant.models.client.ClientImpl;
import restaurant.models.waiter.FullTimeWaiter;
import restaurant.models.waiter.HalfTimeWaiter;
import restaurant.models.waiter.Waiter;
import restaurant.models.working.WorkingImpl;
import restaurant.repositories.ClientRepository;
import restaurant.repositories.WaiterRepository;

import java.util.Collection;

public class ControllerImpl implements Controller {
    private WaiterRepository waiterRepository;
    private ClientRepository clientRepository;
    private WorkingImpl working;
    private int servedClientsCount;

    public ControllerImpl() {
        this.waiterRepository = new WaiterRepository();
        this.clientRepository = new ClientRepository();
        this.working = new WorkingImpl();
        this.servedClientsCount = 0;
    }

    @Override
    public String addWaiter(String type, String waiterName) {
        Waiter waiter;
        if (type.equals("FullTimeWaiter")) {
            waiter = new FullTimeWaiter(waiterName);
        } else if (type.equals("HalfTimeWaiter")) {
            waiter = new HalfTimeWaiter(waiterName);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.WAITER_INVALID_TYPE);
        }

        waiterRepository.add(waiter);
        return String.format(ConstantMessages.WAITER_ADDED, type, waiterName);
    }

    @Override
    public String addClient(String clientName, String... orders) {
        Client client = new ClientImpl(clientName);
        Collection<String> clientOrders = client.getClientOrders();
        for (String order : orders) {
            clientOrders.add(order);
        }
        clientRepository.add(client);
        return String.format(ConstantMessages.CLIENT_ADDED, clientName);
    }

    @Override
    public String removeWaiter(String waiterName) {
        Waiter waiter = waiterRepository.byName(waiterName);
        if (waiter == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.WAITER_DOES_NOT_EXIST, waiterName));
        }

        waiterRepository.remove(waiter);
        return String.format(ConstantMessages.WAITER_REMOVE, waiterName);
    }

    @Override
    public String removeClient(String clientName) {
        Client client = clientRepository.byName(clientName);
        if (client == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CLIENT_DOES_NOT_EXIST, clientName));
        }

        clientRepository.remove(client);
        return String.format(ConstantMessages.CLIENT_REMOVE, clientName);
    }

    @Override
    public String startWorking(String clientName) {
        Client client = clientRepository.byName(clientName);
        if (client == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CLIENT_DOES_NOT_EXIST, clientName));
        }

        Collection<Waiter> waiters = waiterRepository.getCollection();
        if (waiters.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.THERE_ARE_NO_WAITERS);
        }

        working.takingOrders(client, waiters);

        if (client.getClientOrders().isEmpty()) {
            servedClientsCount++;
        }

        return String.format(ConstantMessages.ORDERS_SERVING, clientName);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(ConstantMessages.FINAL_CLIENTS_COUNT, servedClientsCount)).append(System.lineSeparator());
        sb.append(ConstantMessages.FINAL_WAITERS_STATISTICS).append(System.lineSeparator());

        for (Waiter waiter : waiterRepository.getCollection()) {
            sb.append(String.format(ConstantMessages.FINAL_WAITER_NAME, waiter.getName())).append(System.lineSeparator());
            sb.append(String.format(ConstantMessages.FINAL_WAITER_EFFICIENCY, waiter.getEfficiency())).append(System.lineSeparator());
            Collection<String> orders = waiter.takenOrders().getOrdersList();
            if (orders.isEmpty()) {
                sb.append(String.format(ConstantMessages.FINAL_WAITER_ORDERS, "None")).append(System.lineSeparator());
            } else {
                sb.append(String.format(ConstantMessages.FINAL_WAITER_ORDERS, String.join(ConstantMessages.FINAL_WAITER_ORDERS_DELIMITER, orders))).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
