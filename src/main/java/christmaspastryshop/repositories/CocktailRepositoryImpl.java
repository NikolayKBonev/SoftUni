package christmaspastryshop.repositories;

import christmaspastryshop.entities.interfaces.Cocktail;
import christmaspastryshop.repositories.interfaces.CocktailRepository;

public class CocktailRepositoryImpl extends RepositoryImpl<Cocktail> implements CocktailRepository<Cocktail> {
    @Override
    public Cocktail getByName(String name) {
        return this.getAll()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
