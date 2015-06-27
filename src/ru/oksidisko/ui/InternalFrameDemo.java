package ru.oksidisko.ui;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class InternalFrameDemo extends JFrame implements ActionListener {
    public static final String MANAGE_USERS = "Manage users";
    public static final String MANAGE_TOPICS = "Manage topics";

    public static final String ACTIONS = "Actions";
    public static final String QUIT = "Quit";
    JDesktopPane desktop;

    public InternalFrameDemo() {
        super("InternalFrameDemo");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        createFrame(MANAGE_USERS); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu(ACTIONS);
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //Set up the first menu item.
        createMenuItem(menu, MANAGE_USERS, KeyEvent.VK_U);

        // Set up the second menu item
        createMenuItem(menu, MANAGE_TOPICS, KeyEvent.VK_T);

        //Set up the last menu item.
        createMenuItem(menu, QUIT, KeyEvent.VK_Q);

        return menuBar;
    }

    private void createMenuItem(JMenu menu, String menuItemText, int mnemonic) {
        JMenuItem menuItem = new JMenuItem(menuItemText);
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, InputEvent.ALT_MASK));
        menuItem.setActionCommand(menuItemText);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.add(menuItem);
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (MANAGE_USERS.equals(actionCommand)) {
            createFrame(MANAGE_USERS);
        } else if (MANAGE_TOPICS.equals(actionCommand)) {
            createFrame(MANAGE_TOPICS);
        } else { //quit
            quit();
        }
    }

    //Create a new internal frame.
    protected void createFrame(String frameId) {
        JInternalFrame frame = null;
        if (frameId.equals(MANAGE_USERS)){
            frame = ManageUsersFrame.getInstance(this);
        } else if (frameId.equals(MANAGE_TOPICS)) {
            frame = ManageTopicsFrame.getInstance(this);
        }
        if (frame != null) {
            frame.setVisible(true); //necessary as of 1.3
            desktop.add(frame);
            try {
                frame.setSelected(true);
            } catch (java.beans.PropertyVetoException ignored) {
            }
        }
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        InternalFrameDemo frame = new InternalFrameDemo();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
