package forgottenBattleships.core;

import forgottenBattleships.common.ConstantMessages;
import forgottenBattleships.common.ExceptionMessages;
import forgottenBattleships.entities.battlezone.BattleZone;
import forgottenBattleships.entities.battlezone.BattleZoneImpl;
import forgottenBattleships.entities.battleship.Battleship;
import forgottenBattleships.entities.battleship.PirateBattleship;
import forgottenBattleships.entities.battleship.RoyalBattleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Collection<BattleZone> battleZones;

    public ControllerImpl() {
        this.battleZones = new ArrayList<>();
    }

    @Override
    public String addBattleZone(String battleZoneName, int capacity) {
        if (battleZones.stream().anyMatch(bz -> bz.getName().equals(battleZoneName))) {
            throw new IllegalArgumentException(ExceptionMessages.BATTLE_ZONE_EXISTS);
        }

        BattleZone battleZone = new BattleZoneImpl(battleZoneName, capacity);
        battleZones.add(battleZone);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_BATTLE_ZONE, battleZoneName);
    }

    @Override
    public BattleZone getBattleZoneByName(String battleZoneName) {
        return battleZones.stream()
                .filter(bz -> bz.getName().equals(battleZoneName))
                .findFirst()
                .orElseThrow(() -> new NullPointerException(ExceptionMessages.BATTLE_ZONE_DOES_NOT_EXISTS));
    }

    @Override
    public String addBattleshipToBattleZone(String battleZoneName, String shipType, String shipName, int health) {
        BattleZone battleZone = getBattleZoneByName(battleZoneName);

        Battleship battleship;
        switch (shipType) {
            case "RoyalBattleship":
                battleship = new RoyalBattleship(shipName, health);
                break;
            case "PirateBattleship":
                battleship = new PirateBattleship(shipName, health);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_SHIP_TYPE);
        }

        battleZone.addBattleship(battleship);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SHIP, shipType, shipName, battleZoneName);
    }

    @Override
    public String startBattle(String battleZoneName, String attackingShip, String shipUnderAttack) {
        BattleZone battleZone = getBattleZoneByName(battleZoneName);

        Battleship attacker = battleZone.getBattleshipByName(attackingShip);
        Battleship defender = battleZone.getBattleshipByName(shipUnderAttack);

        if (attacker == null || defender == null) {
            throw new IllegalArgumentException(ExceptionMessages.INSUFFICIENT_COUNT);
        }

        attacker.attack(defender);

        if (defender.getHealth() <= 0) {
            battleZone.removeBattleShip(defender);
        }

        Collection<Battleship> remainingShips = battleZone.getShips();

        if (remainingShips.size() == 0) {
            return String.format(ConstantMessages.SHIP_WINS, remainingShips.iterator().next().getName());
        } else {
            String shipsNames = remainingShips.stream()
                    .map(Battleship::getName)
                    .collect(Collectors.joining(", "));
            return String.format(ConstantMessages.BATTLE_CONTINUES, battleZoneName) + shipsNames;
        }
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();

        for (BattleZone battleZone : battleZones) {
            Collection<Battleship> ships = battleZone.getShips();

            sb.append(String.format(ConstantMessages.SHIPS_IN_BATTLE_ZONE, battleZone.getName())).append(System.lineSeparator());

            if (ships.size() == 1) {
                Battleship winner = ships.iterator().next();
                sb.append(String.format(ConstantMessages.SHIP_WINS, winner.getName())).append(System.lineSeparator());
            } else {
                for (Battleship ship : ships) {
                    sb.append(String.format(ConstantMessages.SHIP_INFO, ship.getName(), ship.getHealth(), ship.getAmmunition()))
                            .append(System.lineSeparator());
                }
            }
        }

        return sb.toString().trim();
    }
}
