package view;

import java.util.LinkedList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Implementation of {@link StatisticsView}.
 */
public final class StatisticsViewImpl implements StatisticsView {
    private static final int PADDING = 20;
    private static final Image IMG_HEART_FULL = new Image("img/heartFull.png");
    private static final Image IMG_HEART_EMPTY = new Image("img/hud_heartEmpty.png");
    private static final List<Image> IMG_POINTS;
    static {
        IMG_POINTS = IntStream.range(0, 10)
                .mapToObj(n -> "img/hud_" + n + ".png")
                .map(Image::new)
                .collect(Collectors.toList());
    }
    private final List<ImageView> hearts = new LinkedList<>();
    private final HBox lifeBar = new HBox();
    private final HBox pointsBar = new HBox();
    private final HBox root = new HBox(PADDING, lifeBar, pointsBar);
    private final List<ImageView> pointsDigit = new LinkedList<>();

    /**
     * Initializes HUD images.
     */
    public StatisticsViewImpl() {

        root.setPadding(new Insets(PADDING));
        pointsBar.setAlignment(Pos.CENTER_LEFT);
        this.setMaxHealth(10);
        this.setPoints(0);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void setCurrentHealth(final int life) {
        for (int i = 0; i < life; i++) {
            hearts.get(i).setImage(IMG_HEART_FULL);
        }
        for (int i = life; i < hearts.size(); i++) {
            hearts.get(i).setImage(IMG_HEART_EMPTY);
        }
        if (life > hearts.size()) {
            setMaxHealth(life);
        }
    }

    @Override
    public void setPoints(final int points) {
        pointsDigit.forEach(view -> pointsBar.getChildren().remove(view));
        pointsDigit.clear();
        for (final String ch : Integer.toString(points).split("")) {
            pointsDigit.add(new ImageView(IMG_POINTS.get(Integer.parseInt(ch))));
        }
        pointsDigit.forEach(view -> pointsBar.getChildren().add(view));
    }

    private void setMaxHealth(final int max) {
        hearts.forEach(view -> lifeBar.getChildren().remove(view));
        hearts.clear();
        for (int i = 0; i < max; i++) {
            hearts.add(new ImageView(IMG_HEART_FULL));
        }
        hearts.forEach(view -> lifeBar.getChildren().add(view));
        System.out.println("f");
    }
}
