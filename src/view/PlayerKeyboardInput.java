package view;


import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.W;
import static javafx.scene.input.KeyCode.SPACE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import controller.PlayerInputListener;
//import controller.PlayerInputListener;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Manages key presses.
 */
public final class PlayerKeyboardInput {
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private Optional<PlayerInputListener> listener = Optional.empty();

    /**
     * @param scene
     *            The scene from which to capture key presses.
     */
    public PlayerKeyboardInput(final Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::onKeyEvent);
    }

    /**
     * Sets a {@link PlayerInputListener} instance.
     * 
     * @param listener
     *            The listener
     */
    public void setListener(final PlayerInputListener listener) {
        this.listener = Optional.of(listener);
    }

    /**
     * Clear the listener.
     */
    public void clearListener() {
        this.listener = Optional.empty();
    }

    private void onKeyEvent(final KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            pressedKeys.add(event.getCode());
            notifyDirectionChange();
    } else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            pressedKeys.remove(event.getCode());
            if (!event.getCode().equals(W) && !event.getCode().equals(UP)) {
                notify(listener -> listener.stop());
                if (event.getCode().equals(SPACE)) {
                    notify(listener -> listener.stopPunch());
                }
            }
       }
    }

    private void notifyDirectionChange() {
        notify(listener -> listener.move(computeDirection()));
    }

    private Point2D computeDirection() {
      Point2D direction = Point2D.ZERO;

      if (pressedKeys.contains(SPACE)) {
          notify(listener -> listener.punch());
      }
      if (pressedKeys.contains(D) || pressedKeys.contains(RIGHT)) {
          direction = direction.add(1, 0);
      }
      if (pressedKeys.contains(A) || pressedKeys.contains(LEFT)) {
          direction = direction.add(-1, 0);
      }
      if (pressedKeys.contains(W) || pressedKeys.contains(UP)) {
          direction = direction.add(0, 1);
      }
      return direction;
  }

    private void notify(final Consumer<PlayerInputListener> action) {
        listener.ifPresent(action::accept);
    }
}
