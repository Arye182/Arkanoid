package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.General;

import java.awt.Color;
import java.awt.Image;


import java.util.List;
import java.util.ArrayList;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private String gameName;
    private T status;
    private List<String> keys;
    private List<String> headers;
    private List<T> tasks;
    private KeyboardSensor keyboardSensor;
    private boolean stop;
    private boolean isMain;
    private List<Menu<T>> subMenus;
    private AnimationRunner runner;
    private List<Boolean> isSubMenu;
    private Image menuBackground;
    private Image subMenuBackground;


    /**
     * Instantiates a new Menu animation.
     *
     * @param gameName       the game name
     * @param keyboardSensor the keyboard sensor
     * @param runner         the runner
     * @param isMainMenu     the is main menu
     */
    public MenuAnimation(String gameName, KeyboardSensor keyboardSensor,  AnimationRunner runner, boolean isMainMenu) {
        this.gameName = gameName;
        this.tasks = new ArrayList<T>();
        this.headers = new ArrayList<String>();
        this.keys = new ArrayList<String>();
        this.status = null;
        this.keyboardSensor = keyboardSensor;
        this.stop = false;
        this.runner = runner;
        this.subMenus = new ArrayList<Menu<T>>();
        this.isSubMenu = new ArrayList<Boolean>();
        this.isMain = isMainMenu;
        this.menuBackground = General.loadImage("background_images/menu.png");
        this.subMenuBackground = General.loadImage("background_images/submenu.png");
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.headers.add(message);
        this.tasks.add(returnVal);
        this.isSubMenu.add(false);
        this.subMenus.add(null);

    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(subMenu);
        this.keys.add(key);
        this.headers.add(message);
        this.tasks.add(null);
        this.isSubMenu.add(true);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.isMain) {
            d.drawImage(0, 0,  menuBackground);
        } else {
            d.drawImage(0, 0, subMenuBackground);
            int i;
            for (i = 0; i < this.headers.size(); ++i) {
                int optionX = 300;
                int optionY = 300 + i * 40;
                String optionText = "(" + (String) this.keys.get(i) + ") " + (String) this.headers.get(i);
                d.setColor(Color.BLACK);
                d.drawText(optionX + 1, optionY, optionText, 50);
                d.drawText(optionX - 1, optionY, optionText, 50);
                d.drawText(optionX, optionY + 1, optionText, 50);
                d.drawText(optionX, optionY - 1, optionText, 50);
                d.setColor(Color.GREEN);
                d.drawText(optionX, optionY, optionText, 50);
            }
        }
        int i;
        for (i = 0; i < this.tasks.size(); ++i) {
            if (this.keyboardSensor.isPressed(this.keys.get(i))) {
                if (!this.isSubMenu.get(i)) {
                    this.status = this.tasks.get(i);
                    this.stop = true;

                } else {
                    Menu<T> subMenu = this.subMenus.get(i);
                    this.runner.run(subMenu);
                    this.status = subMenu.getStatus();
                    this.stop = true;
                    ((MenuAnimation) subMenu).reset();
                }
                break;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return status != null;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.status = null;
        this.stop = false;
    }
}
