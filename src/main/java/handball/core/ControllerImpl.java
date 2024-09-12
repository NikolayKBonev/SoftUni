package handball.core;

import handball.common.ConstantMessages;
import handball.common.ExceptionMessages;
import handball.entities.equipment.ElbowPad;
import handball.entities.equipment.Equipment;
import handball.entities.equipment.Kneepad;
import handball.entities.gameplay.Gameplay;
import handball.entities.gameplay.Indoor;
import handball.entities.gameplay.Outdoor;
import handball.entities.team.Bulgaria;
import handball.entities.team.Germany;
import handball.entities.team.Team;
import handball.repositories.EquipmentRepository;
import handball.repositories.Repository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private Repository equipmentRepository;
    private Collection<Gameplay> gameplays;

    public ControllerImpl() {
        this.equipmentRepository = new EquipmentRepository();
        this.gameplays = new ArrayList<>();
    }

    @Override
    public String addGameplay(String gameplayType, String gameplayName) {
        Gameplay gameplay;
        switch (gameplayType) {
            case "Outdoor":
                gameplay = new Outdoor(gameplayName);
                break;
            case "Indoor":
                gameplay = new Indoor(gameplayName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_GAMEPLAY_TYPE);
        }
        this.gameplays.add(gameplay);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_GAMEPLAY_TYPE, gameplayType);
    }

    @Override
    public String addEquipment(String equipmentType) {
        Equipment equipment;
        switch (equipmentType) {
            case "Kneepad":
                equipment = new Kneepad();
                break;
            case "ElbowPad":
                equipment = new ElbowPad();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_EQUIPMENT_TYPE);
        }
        this.equipmentRepository.add(equipment);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_EQUIPMENT_TYPE, equipmentType);
    }

    @Override
    public String equipmentRequirement(String gameplayName, String equipmentType) {
        Gameplay gameplay = this.gameplays.stream()
                .filter(g -> g.getName().equals(gameplayName))
                .findFirst()
                .orElse(null);

        Equipment equipment = this.equipmentRepository.findByType(equipmentType);
        if (equipment == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_EQUIPMENT_FOUND, equipmentType));
        }

        gameplay.addEquipment(equipment);
        this.equipmentRepository.remove(equipment);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_EQUIPMENT_IN_GAMEPLAY, equipmentType, gameplayName);
    }

    @Override
    public String addTeam(String gameplayName, String teamType, String teamName, String country, int advantage) {
        Team team;
        switch (teamType) {
            case "Bulgaria":
                team = new Bulgaria(teamName, country, advantage);
                break;
            case "Germany":
                team = new Germany(teamName, country, advantage);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_TEAM_TYPE);
        }
        Gameplay gameplay = this.gameplays.stream()
                .filter(g -> g.getName().equals(gameplayName))
                .findFirst()
                .orElse(null);

        if ((team instanceof Bulgaria && !(gameplay instanceof Outdoor)) || (team instanceof Germany && !(gameplay instanceof Indoor))) {
            return ConstantMessages.GAMEPLAY_NOT_SUITABLE;
        }

        gameplay.addTeam(team);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TEAM_IN_GAMEPLAY, teamType, gameplayName);
    }

    @Override
    public String playInGameplay(String gameplayName) {
        Gameplay gameplay = this.gameplays.stream()
                .filter(g -> g.getName().equals(gameplayName))
                .findFirst()
                .orElse(null);
        gameplay.teamsInGameplay();
        int playedCount = gameplay.getTeam().size();
        return String.format(ConstantMessages.TEAMS_PLAYED, playedCount);
    }

    @Override
    public String percentAdvantage(String gameplayName) {
        Gameplay gameplay = this.gameplays.stream()
                .filter(g -> g.getName().equals(gameplayName))
                .findFirst()
                .orElse(null);
        int totalAdvantage = gameplay.getTeam().stream().mapToInt(Team::getAdvantage).sum();
        return String.format(ConstantMessages.ADVANTAGE_GAMEPLAY, gameplayName, totalAdvantage);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Gameplay gameplay : gameplays) {
            sb.append(gameplay.toString()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
