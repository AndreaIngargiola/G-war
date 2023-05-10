package model.levelsgenerator.geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enumerators.Faction;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;

/**
 * Implements the Grid interface using the Map<Coordinate, LevelGenerationEntity> as data structure.
 * The static private entity VOID represents a empty block of the grid. 
 */
public class GridImpl implements Grid {

    private static final LevelGenerationEntity VOID = new LevelGenerationEntity("Void", "null", new HashSet<>(), Faction.NEUTRAL_IMMORTAL);
    private final Map<Coordinate, LevelGenerationEntity> matrix;
    private final Coordinate size;

    /**
     * Initialize the matrix.
     * @param rows is the number of rows of the matrix.
     * @param columns is the number of columns of the matrix.
     */
    public GridImpl(final int rows, final int columns) {
        this.matrix = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix.put(new Coordinate(j, i), GridImpl.VOID);
            }
        }
        this.size = new Coordinate(columns, rows);
    }


    /**
     * A constructor used for the creation of copies between Grids.
     * @param matrix is the map with the matrix map entries of the original Grid.
     * @param size is the size of the original Grid.
     */
    public GridImpl(final Map<Coordinate, LevelGenerationEntity> matrix, final Coordinate size) {
        this.matrix = matrix;
        this.size = size;
    }


    @Override
    public final void reset() {
        this.matrix.keySet().stream().forEach(k -> this.matrix.put(k, GridImpl.VOID));
    }

    @Override
    public final List<Coordinate> getOverlap(final Coordinate mOriginPoint, final Block b) {
        return b.getRelativeCoordinates().stream()
                                         .map(p -> mOriginPoint.sum(p))
                                         .filter(p -> this.isInMatrixBounds(p))
                                         .collect(Collectors.toList());
    }

    @Override
    public final void place(final Coordinate mOriginPoint, final EntityBlock b) {
        final List<Coordinate> overlap = this.getOverlap(mOriginPoint, b);
        overlap.forEach(c -> this.setElement(c, b.getEntity()));
     }

    @Override
    public final LevelGenerationEntity getElement(final Coordinate elemCoordinates) throws IllegalArgumentException {
        if (this.isInMatrixBounds(elemCoordinates)) {
            return this.matrix.get(elemCoordinates);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public final Coordinate getSize() {
        return this.size.getSafeCopy();
    }


    @Override
    public final Map<Coordinate, LevelGenerationEntity> getSnapshot() {
        final Map<Coordinate, LevelGenerationEntity> snapshot = new HashMap<>();
        snapshot.putAll(this.matrix);
        return snapshot;
    }

    @Override
    public final Boolean isInMatrixBounds(final Coordinate elemCoordinates) {
        return (elemCoordinates.getPoint().x >= 0 && elemCoordinates.getPoint().x < this.getSize().getPoint().x 
                && elemCoordinates.getPoint().y >= 0 && elemCoordinates.getPoint().y < this.getSize().getPoint().y);
    }

    @Override
    public final LevelGenerationEntity getVoid() {
        return GridImpl.VOID;
    }

    @Override
    public final void setElement(final Coordinate elemCoordinates, final LevelGenerationEntity value) throws IllegalArgumentException {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.put(elemCoordinates, value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public final Grid getCopy() {
        return new GridImpl(this.getSnapshot(), this.size.getSafeCopy());
    }
    /**
     * Print on system.out the inner matrix with a cartesian representation 
     * (the (0,0) is the bottom left and the (size x,size y) is in the upper right).
     */
    public final void printAsCartesianPlane() {
        final List<List<String>> mat = new ArrayList<>();

        for (int i = 0; i < this.getSize().getPoint().y; i++) {
            mat.add(new ArrayList<>());
            for (int j = 0; j < this.getSize().getPoint().x; j++) {
                mat.get(i).add(this.matrix.get(new Coordinate(j, i)).getCanonicalName());
            }
        }

        for (int i = this.getSize().getPoint().y - 1; i >= 0; i--) {
            System.out.println(mat.get(i).toString());
        }
    }
}
