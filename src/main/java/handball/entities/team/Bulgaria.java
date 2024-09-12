package handball.entities.team;

public class Bulgaria extends BaseTeam {
    private static final int ADVANTAGE_INCREMENT = 115;

    public Bulgaria(String name, String country, int advantage) {
        super(name, country, advantage);
    }

    @Override
    public void play() {
        increaseAdvantage(ADVANTAGE_INCREMENT);
    }
}