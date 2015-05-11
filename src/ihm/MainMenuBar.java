
package ihm;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {

    // Properties

    private static final long serialVersionUID = 1L;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    // Initializers

    public MainMenuBar(GuiController guiController) {
        this.setupGameMenu(guiController);
        this.setupEditMenu(guiController);
    }

    private JMenuItem createMenuItem(Object target, String title, String action) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(new ActionPerformer(target, action));
        return menuItem;
    }

    protected void setupGameMenu(Object target) {
        JMenu fileMenu = new JMenu("Game");
        fileMenu.add(this.createMenuItem(target, "New Game", "newGame"));
        fileMenu.add(this.createMenuItem(target, "Load...", "loadGame"));
        fileMenu.add(this.createMenuItem(target, "Export...", "exportGame"));
        fileMenu.add(this.createMenuItem(target, "Quit", "saveAndQuit"));
        this.add(fileMenu);
    }

    protected void setupEditMenu(Object target) {
        JMenu editMenu = new JMenu("Game");
        this.undoMenuItem = this.createMenuItem(target, "Undo", "undo");
        editMenu.add(this.undoMenuItem);
        this.redoMenuItem = this.createMenuItem(target, "Redo", "redo");
        editMenu.add(this.redoMenuItem);
        this.add(editMenu);
    }

    // Manage states

    public void changeUndoItemState(boolean flag) {
        this.undoMenuItem.setEnabled(flag);
    }

    public void changeRedoItemState(boolean flag) {
        this.redoMenuItem.setEnabled(flag);
    }

}
