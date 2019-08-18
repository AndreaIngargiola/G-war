package controller;

import org.jbox2d.common.Vec2;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import model.entities.Coward;
import model.entities.Entity;
import model.entities.Floor;
import model.entities.Grill;
import model.entities.Platform;
import model.entities.Player;
import model.physics.BodyBuilderImpl;
import view.PlayerKeyboardInput;
import view.PlayerView;
import view.StatisticsView;
import view.CowardView;
import view.FloorView;
import view.GrillView;
import view.ImmortalEntityView;
import view.MortalEntityView;
import view.PlatformView;

/**
 * Implementation of {@link EntityFactory}.
 *
 */
public class EntityFactoryImpl implements EntityFactory {

    private final Group root;
    private final Vec2 position;

    /**
     * 
     * @param root
     *            the root
     * @param position
     *            the position of the entity 
     */
    public EntityFactoryImpl(final Group root, final Vec2 position) {
        this.root = root;
        this.position = position;
    }

    @Override
    public final EntityController createPlayer(final Scene scene, final StatisticsView statistics) {
        Entity playerModel = new Player(new BodyBuilderImpl(), position);
        PlayerView playerView = new PlayerView(root, statistics);
        PlayerController playerController = new PlayerController(playerModel, playerView);
        PlayerKeyboardInput keyboard = new PlayerKeyboardInput(scene);
        keyboard.setListener(playerController);
        return playerController;
    }

    @Override
    public final EntityController createCoward() {
        Entity cowardModel = new Coward(new BodyBuilderImpl(), position);
        MortalEntityView cowardView = new CowardView(root);
        MortalEntityController cowardController = new MortalEntityController(cowardModel, cowardView);
        return cowardController;
    }

    @Override
    public final EntityController createPlatform() {
        Entity platformModel = new Platform(new BodyBuilderImpl(), position);
        ImmortalEntityView platformView = new PlatformView(root);
        platformView.setPosition(new Point2D(position.x, position.y));
        ImmortalEntityController platformController = new ImmortalEntityController(platformModel, platformView);
        return platformController;
    }

    @Override
    public final EntityController createGrill() {
        Entity grillModel = new Grill(new BodyBuilderImpl(), position);
        GrillView grillView = new GrillView(root);
        grillView.setPosition(new Point2D(position.x, position.y));
        GrillController grillController = new GrillController(grillModel, grillView);
        return grillController;
    }

    @Override
    public final EntityController createFloor() {
        Entity floorModel = new Floor(new BodyBuilderImpl(), position);
        ImmortalEntityView floorView = new FloorView(root);
        floorView.setPosition(new Point2D(position.x, position.y));
        ImmortalEntityController floorController = new ImmortalEntityController(floorModel, floorView);
        return floorController;
    }

}
