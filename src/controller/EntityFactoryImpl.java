package controller;

import org.jbox2d.common.Vec2;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import model.entities.Coward;
import model.entities.Entity;
import model.entities.Floor;
import model.entities.Grill;
import model.entities.Platform;
import model.entities.Player;
import model.physics.BodyBuilderImpl;
import viewGame.CowardView;
import viewGame.FloorView;
import viewGame.GameViewImpl;
import viewGame.GrillView;
import viewGame.ImmortalEntityView;
import viewGame.MortalEntityView;
import viewGame.PlatformView;
import viewGame.PlayerKeyboardInput;
import viewGame.PlayerView;

/**
 * Implementation of {@link EntityFactory}.
 *
 */
public class EntityFactoryImpl implements EntityFactory {

    private final Group root = GameViewImpl.getRoot();


    @Override
    public final EntityController createPlayer(final Vec2 position) {
        Entity playerModel = new Player(new BodyBuilderImpl(), position);
        PlayerView playerView = new PlayerView(root, GameViewImpl.getStatistics());
        PlayerController playerController = new PlayerController(playerModel, playerView, new PlayerKeyboardInput(GameViewImpl.getScene()));
       // PlayerKeyboardInput keyboard = new PlayerKeyboardInput(GameViewImpl.getScene());
       // keyboard.setListener(playerController);
        return playerController;
    }

    @Override
    public final EntityController createCoward(final Vec2 position) {
        Entity cowardModel = new Coward(new BodyBuilderImpl(), position);
        MortalEntityView cowardView = new CowardView(root);
        MortalEntityController cowardController = new MortalEntityController(cowardModel, cowardView);
        return cowardController;
    }

    @Override
    public final EntityController createPlatform(final Vec2 position) {
        Entity platformModel = new Platform(new BodyBuilderImpl(), position);
        ImmortalEntityView platformView = new PlatformView(root);
        platformView.setPosition(new Point2D(position.x, position.y));
        ImmortalEntityController platformController = new ImmortalEntityController(platformModel, platformView);
        return platformController;
    }

    @Override
    public final EntityController createGrill(final Vec2 position) {
        Entity grillModel = new Grill(new BodyBuilderImpl(), position);
        GrillView grillView = new GrillView(root);
        grillView.setPosition(new Point2D(position.x, position.y));
        GrillController grillController = new GrillController(grillModel, grillView);
        return grillController;
    }

    @Override
    public final EntityController createFloor(final Vec2 position) {
        Entity floorModel = new Floor(new BodyBuilderImpl(), position);
        ImmortalEntityView floorView = new FloorView(root);
        floorView.setPosition(new Point2D(position.x, position.y));
        ImmortalEntityController floorController = new ImmortalEntityController(floorModel, floorView);
        return floorController;
    }

}
