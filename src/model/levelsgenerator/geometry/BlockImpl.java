package model.levelsgenerator.geometry;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *BlockImpl is the implementation of the block using the Set data structure.
 */
public class BlockImpl implements Block {

    private final Set<Coordinate> vertex;
    private final Coordinate spawnPoint;

    /**
     * The block cannot be empty, so when the block is created the first point will be the "center" of the block with absolute coordinates 0,0.
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
                         .map(p -> p.sub(this.spawnPoint))
                         .collect(Collectors.toList());
    }

    @Override
    public final int getOccupation() {
        return this.vertex.size();
    }

    /**
     * @allowed extension that respects the safe copies paradigm and the accuracy of the fields.
     */
    @Override
    public Block getCopy() {
        final Block copy = new BlockImpl();
        this.vertex.stream().forEach(c -> copy.addPoint(c.getSafeCopy()));
        return copy;
    }
}
