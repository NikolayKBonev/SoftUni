package goldDigger.models.museum;

import java.util.Collection;

public interface Museum {
    Collection<String> getExhibits();

    void addExhibit(String exhibit); // Добавяме този метод
}
