
package waffle.application;

import javax.swing.SwingUtilities;
import waffle.engine.Engine;
import waffle.gui.GuiController;
import waffle.util.Logger;

public class Main implements Runnable {

    // Properties

    private static final int defaultXDim = 10;
    private static final int defaultYDim = 10;
    private static final int defaultNbPlayers = 2;

    // Runnable

    @Override
    public void run() {
        Logger.logApp("App will launch");

        Engine engine = new Engine(defaultXDim, defaultYDim, defaultNbPlayers);
        GuiController guiController = new GuiController(engine);
        engine.setIHM(guiController);

        Logger.logApp("App did launch");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

}
