package christmaspastryshop;

import christmaspastryshop.core.ControllerImpl;
import christmaspastryshop.core.EngineImpl;
import christmaspastryshop.core.interfaces.Controller;
import christmaspastryshop.core.interfaces.Engine;
import christmaspastryshop.entities.interfaces.Delicacy;
import christmaspastryshop.entities.interfaces.Cocktail;
import christmaspastryshop.entities.interfaces.Booth;
import christmaspastryshop.repositories.BoothRepositoryImpl;
import christmaspastryshop.repositories.CocktailRepositoryImpl;
import christmaspastryshop.repositories.DelicacyRepositoryImpl;
import christmaspastryshop.repositories.interfaces.BoothRepository;
import christmaspastryshop.repositories.interfaces.CocktailRepository;
import christmaspastryshop.repositories.interfaces.DelicacyRepository;
import christmaspastryshop.io.ConsoleReader;
import christmaspastryshop.io.ConsoleWriter;

public class Main {
    public static void main(String[] args) {
        DelicacyRepository<Delicacy> delicacyRepository = new DelicacyRepositoryImpl();
        CocktailRepository<Cocktail> cocktailRepository = new CocktailRepositoryImpl();
        BoothRepository<Booth> boothRepository = new BoothRepositoryImpl();

        Controller controller = new ControllerImpl(delicacyRepository, cocktailRepository, boothRepository);

        ConsoleReader reader = new ConsoleReader();
        ConsoleWriter writer = new ConsoleWriter();

        Engine engine = new EngineImpl(reader, writer, controller);
        engine.run();
    }
}
