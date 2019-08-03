package model.levelsgenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *BlockImpl is the implementation of the block using the List data structure.
 */
public class BlockImpl implements Block {

    private final Set<Coordinate> vertex;
    private final Coordinate spawnPoint;

    /**
     * the block cannot be empty, so when the block is created the first point will be the "center" of the block with absolute coordinates 0,0.
     */
    public BlockImpl() {
        vertex = new HashSet<>();
        this.vertex.add(new Coordinate(0, 0));
        this.spawnPoint = new Coordinate(0, 0);
    }

    @Override
    public final void addPoint(final Coordinate p) {
        this.vertex.add(p);
    }

    @Override
    public final List<Coordinate> getRelativeCoordinates() {
       return this.vertex.stream()
                         .map(c -> c.getPoint())
                         .map(p -> new Coordinate(p.x - this.spawnPoint.getPoint().x, 
                                                  p.y - this.spawnPoint.getPoint().y))
                         .collect(Collectors.toList());
    }

    @Override
    public final Integer getOccupation() {
        return this.vertex.size();
    }
}
