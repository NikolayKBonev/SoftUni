package christmaspastryshop.repositories;

import christmaspastryshop.entities.interfaces.Booth;
import christmaspastryshop.repositories.interfaces.BoothRepository;

public class BoothRepositoryImpl extends RepositoryImpl<Booth> implements BoothRepository<Booth> {
    @Override
    public Booth getByNumber(int number) {
        return this.getAll()
                .stream()
                .filter(b -> b.getBoothNumber() == number)
                .findFirst()
                .orElse(null);
    }
}
