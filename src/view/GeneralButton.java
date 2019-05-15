package view;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.effect.MotionBlur;

/**
 * Button component.
 */
public class GeneralButton extends StackPane {
    private static final int FONT = 24;
    private Text text;

    /**
     * General Button costructor.
     * @param nameButton
     *         The button text.
     */
    public GeneralButton(final String nameButton) {
        super();
        updateBtn(nameButton);
    }

    /** Updates the button text. 
     * @param nameButton
     *         The button updated text.
     */
    public final void updateBtn(final String nameButton) {
        getChildren().clear();

        this.text = new Text("" + nameButton);
        this.text.setFont(Font.font(FONT));
        this.text.setFill(Color.BLACK);

        final Rectangle btn = new Rectangle(300, 100);
        btn.setOpacity(0.5);
        btn.setFill(Color.WHITE);
        btn.setEffect(new MotionBlur());

        setAlignment(Pos.CENTER_RIGHT);
        getChildren().addAll(btn, text);

        setOnMouseEntered(event -> {
        });

        setOnMouseExited(event -> {
        });

        setOnMousePressed(event -> {
        });

        setOnMouseReleased(event -> {
        });
    }
}
