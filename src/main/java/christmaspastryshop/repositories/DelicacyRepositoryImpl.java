package christmaspastryshop.repositories;

import christmaspastryshop.entities.interfaces.Delicacy;
import christmaspastryshop.repositories.interfaces.DelicacyRepository;

public class DelicacyRepositoryImpl extends RepositoryImpl<Delicacy> implements DelicacyRepository<Delicacy> {
    @Override
    public Delicacy getByName(String name) {
        return this.getAll()
                .stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
