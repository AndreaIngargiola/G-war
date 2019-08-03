package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * implement the logic interface using a list of list of integer where the value of the 
 * element represent an entity type (example 1=enemy, 2=platform etc).
 */
public class GridImpl implements Grid {

    private static final LevelGenerationEntity<?> VOID = new LevelGenerationEntity<>();
    private final List<List<LevelGenerationEntity<?>>> matrix = new ArrayList<>();

    /**
     * Initialize the matrix.
     * @param bounds is the width (and consequentially the height) of the squared matrix.
     */
    public GridImpl(final int bounds) {
        for (int i = 0; i < bounds; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < bounds; j++) {
                this.matrix.get(i).add(GridImpl.VOID);
            }
        }
    }

    /**
     * Get the absolute matrix coordinates given a certain block shape (list of coordinates) and a origin point on the matrix. 
     * @param b is the block to overlap.
     * @param mOriginPoint is the point in the matrix that corresponds to the spawn point of the block and in which 
     * the relative coordinates of the block will be converted in absolute coordinates of the matrix.
     * @return a list of matrix coordinates that are in matrix bounds and corresponds to the points 
     * occupied by that block if is placed with that point of origin.
     */
    public final List<Coordinate> getOverlap(final Coordinate mOriginPoint, final EntityBlock b) {
        return b.getRelativeCoordinates().stream()
                                         .map(p -> mOriginPoint.sum(p))
                                         .filter(p -> this.isInMatrixBounds(p))
                                         .collect(Collectors.toList());
    }

    @Override
    public final Boolean place(final Coordinate mOriginPoint, final EntityBlock b) {
        List<Coordinate> overlap = this.getOverlap(mOriginPoint, b);

        /*if part of the block is out of bounds (so the overlap size is lesser than the size of the block)
         * or not every requested tile in the matrix is free (VOID), return false */
        if (overlap.size() < b.getOccupation() || !(overlap.stream().map(c -> this.getElement(c)).allMatch(e -> e.equals(GridImpl.VOID)))) {
            return false;
        } else {
            for (Coordinate c : overlap) {
                this.setElement(c, b.getEntity());
            }
        }
            return true;
     }

    @Override
    public final void reset() {
        this.matrix.stream()
                   .forEach(row -> row.stream().forEach(element -> element = new LevelGenerationEntity<>()));
    }

    @Override
    public final LevelGenerationEntity<?> getElement(final Coordinate elemCoordinates) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            return this.matrix.get(elemCoordinates.getPoint().x).get(elemCoordinates.getPoint().y);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Boolean isInMatrixBounds(final Coordinate elemCoordinates) {
        return (elemCoordinates.getPoint().x >= 0 &&  elemCoordinates.getPoint().x < this.matrix.size() 
               && elemCoordinates.getPoint().y >= 0 && elemCoordinates.getPoint().y  < this.matrix.size());
    }

    private void setElement(final Coordinate elemCoordinates, final LevelGenerationEntity<?> value) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.get(elemCoordinates.getPoint().x).set(elemCoordinates.getPoint().y, value);
        }
    }
}
