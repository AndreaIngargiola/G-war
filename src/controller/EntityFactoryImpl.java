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
import view.entities.CowardView;
import view.entities.FloorView;
import view.world.GameViewImpl;
import view.entities.GrillView;
import view.entities.ImmortalEntityView;
import view.entities.MortalEntityView;
import view.entities.PlatformView;
import view.entities.PlayerKeyboardInput;
import view.entities.PlayerView;

/**
 * Implementation of {@link EntityFactory}.
 *
 */
public class EntityFactoryImpl implements EntityFactory {

    private final Group root = GameViewImpl.getRoot();


    @Override
    public final EntityController createPlayer(final Vec2 position) {
        final Entity playerModel = new Player(new BodyBuilderImpl(), position);
        final PlayerView playerView = new PlayerView(root, GameViewImpl.getStatistics());
        return new PlayerController(playerModel, playerView, new PlayerKeyboardInput(GameViewImpl.getScene()));
    }

    @Override
    public final EntityController createCoward(final Vec2 position) {
        final Entity cowardModel = new Coward(new BodyBuilderImpl(), position);
        final MortalEntityView cowardView = new CowardView(root);
        return new MortalEntityController(cowardModel, cowardView);
    }

    @Override
    public final EntityController createPlatform(final Vec2 position) {
        final Entity platformModel = new Platform(new BodyBuilderImpl(), position);
        final ImmortalEntityView platformView = new PlatformView(root);
        platformView.setPosition(new Point2D(position.x, position.y));
        return new ImmortalEntityController(platformModel, platformView);
    }

    @Override
    public final EntityController createGrill(final Vec2 position) {
        final Entity grillModel = new Grill(new BodyBuilderImpl(), position);
        final GrillView grillView = new GrillView(root);
        grillView.setPosition(new Point2D(position.x, position.y));
        return new GrillController(grillModel, grillView);
    }

    @Override
    public final EntityController createFloor(final Vec2 position) {
        final Entity floorModel = new Floor(new BodyBuilderImpl(), position);
        final ImmortalEntityView floorView = new FloorView(root);
        floorView.setPosition(new Point2D(position.x, position.y));
        return new ImmortalEntityController(floorModel, floorView);
    }

}
