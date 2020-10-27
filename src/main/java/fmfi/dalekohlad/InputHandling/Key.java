package fmfi.dalekohlad.InputHandling;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

import java.util.HashMap;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Key<T extends KeyEvent> implements EventHandler {
    private HashMap<Pair<Boolean, KeyCode>, Runnable> shortcuts;

    public Key(HashMap<Pair<Boolean, KeyCode>, Runnable> shortcuts) {
        this.shortcuts = shortcuts;
    }

    @Override
    public void handle(Event event) {
        if(event.getEventType() != KEY_PRESSED)
            return;
        KeyEvent key = (KeyEvent) event;
        Pair<Boolean, KeyCode> shortcut_id = new Pair<>(key.isShiftDown(), key.getCode());
        shortcuts.getOrDefault(shortcut_id, () -> {}).run();
    }
}

