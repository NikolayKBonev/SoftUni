package handball.entities.team;

public class Germany extends BaseTeam {
    private static final int ADVANTAGE_INCREMENT = 145;

    public Germany(String name, String country, int advantage) {
        super(name, country, advantage);
    }

    @Override
    public void play() {
        increaseAdvantage(ADVANTAGE_INCREMENT);
    }
}