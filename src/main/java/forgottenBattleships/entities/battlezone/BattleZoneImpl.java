package forgottenBattleships.entities.battlezone;

import forgottenBattleships.entities.battleship.Battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BattleZoneImpl implements BattleZone {
    private String name;
    private int capacity;
    private Collection<Battleship> ships;

    public BattleZoneImpl(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        this.ships = new ArrayList<>();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Battle zone name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void addBattleship(Battleship ship) {
        if (ships.size() >= capacity) {
            throw new IllegalArgumentException("Not enough capacity.");
        }
        if (ship.getHealth() <= 0) {
            throw new IllegalArgumentException("Ship's health cannot be below or equal to 0.");
        }
        if (ships.stream().anyMatch(s -> s.getName().equals(ship.getName()))) {
            throw new IllegalArgumentException("This ship already exists!");
        }
        ships.add(ship);
    }

    @Override
    public Battleship getBattleshipByName(String battleshipName) {
        Optional<Battleship> ship = ships.stream().filter(s -> s.getName().equals(battleshipName)).findFirst();
        return ship.orElse(null);
    }

    @Override
    public void removeBattleShip(Battleship ship) {
        ships.remove(ship);
    }

    @Override
    public Collection<Battleship> getShips() {
        return Collections.unmodifiableCollection(ships);
    }
}
