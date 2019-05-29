package model.levelsgenerator;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *BlockImpl is the implementation of the block using the List data structure.
 */
public class BlockImpl implements Block {

    private final Set<Point> vertex;
    private final Point spawnPoint;

    /**
     * the block cannot be empty, so when the block is created the first point will be the "center" of the block or, in other
     * words, the spawn point.
     * @param spawnPoint is the initial point and the point in which the relative coordinates of the other points 
     * will be calculated
     */
    public BlockImpl(final Point spawnPoint) {
        vertex = new HashSet<>();
        this.vertex.add(spawnPoint);
        this.spawnPoint = spawnPoint;
    }

    @Override
    public final void addPoint(final Point p) {
        this.vertex.add(p);
    }

    @Override
    public final List<Point> getRelativeCoordinates() {
       return this.vertex.stream()
                         .map(p -> new Point(p.x - this.spawnPoint.x, 
                                             p.y - this.spawnPoint.y))
                         .collect(Collectors.toList());
    }

    @Override
    public final Integer getOccupation() {
        return this.vertex.size();
    }
}
