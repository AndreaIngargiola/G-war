package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * implement the logic interface using a list of list of integer where the value of the 
 * element represent an entity type (example 1=enemy, 2=platform etc).
 */
public class GridImpl implements Grid {

    private final List<List<Integer>> matrix = new ArrayList<>();

    /**
     * initialize the matrix.
     * @param bounds is the width (and consequentially the height) of the squared matrix.
     */
    public GridImpl(final int bounds) {
        for (int i = 0; i < bounds; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < bounds; j++) {
                this.matrix.get(i).add(0);
            }
        }
    }

    @Override
    public final Boolean tryToPlace(final Coordinate spawnPoint, final Block b) {
        if (this.checkOccupation(spawnPoint, b).equals(false)) {
            return false;
        } else {
            b.getRelativeCoordinates().stream()
            .map(c -> c.getPoint())
            .forEach(p -> this.setElement(new Coordinate(p.x + spawnPoint.getPoint().x, 
                                                         p.y + spawnPoint.getPoint().y), 1));
            return true;
        }
    }

    @Override
    public final void reset() {
        this.matrix.stream().forEach(row -> row.stream().forEach(element -> element = 0));
    }

    @Override
    public final Optional<Integer> getElement(final Coordinate elemCoordinates) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            return Optional.of(this.matrix.get(elemCoordinates.getPoint().x).get(elemCoordinates.getPoint().y));
        } else {
            return Optional.empty();
        }
    }

    private Boolean isInMatrixBounds(final Coordinate elemCoordinates) {
        return (elemCoordinates.getPoint().x >= 0 &&  elemCoordinates.getPoint().x < this.matrix.size() 
               && elemCoordinates.getPoint().y >= 0 && elemCoordinates.getPoint().y  < this.matrix.size());
    }

    private void setElement(final Coordinate elemCoordinates, final Integer value) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.get(elemCoordinates.getPoint().x).set(elemCoordinates.getPoint().y, value);
        }
    }

    /**
     * Given a certain Block and a spawn point on the matrix, check if the requested tiles that we'll trying to occupy are free. 
     * @param spawnPoint is the entering point on the matrix.
     * @param b is the block that i'm trying to place.
     * @return a boolean: true if all the tiles are free, false if the tiles are occupied.
     */
    private Boolean checkOccupation(final Coordinate spawnPoint, final Block b) {
        final Integer freeTilesOnMatrix = (int) b.getRelativeCoordinates().stream()
                                                 .map(c -> c.getPoint())
                                                 .filter(p -> this.getElement(new Coordinate(spawnPoint.getPoint().x + p.x, 
                                                                                             spawnPoint.getPoint().y + p.y))
                                                                  .equals(Optional.of(0)))
                                                 .count();
        return freeTilesOnMatrix.equals(b.getOccupation());
    }
}
