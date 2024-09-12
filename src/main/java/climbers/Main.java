package climbers;

import climbers.core.Controller;
import climbers.core.ControllerImpl;
import climbers.core.Engine;
import climbers.core.EngineImpl;

public class Main {

    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        Engine engine = new EngineImpl(controller);
        engine.run();
    }
}
