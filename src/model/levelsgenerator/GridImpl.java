package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * implement the logic interface using a list of list of integer where the value of the 
 * element represent an entity type (example 1=enemy, 2=platform etc).
 */
public class GridImpl implements Grid {

    private List<List<Integer>> matrix = new ArrayList<>();

    /**
     * initialize the matrix.
     * @param bounds is the width (and consequentially the height) of the squared matrix.
     */
    public LogicsImpl(final int bounds) {
        for (int i = 0; i < bounds; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < bounds; j++) {
                this.matrix.get(i).add(0);
            }
        }
    }

    @Override
    public final Boolean tryToPlace(final Point spawnPoint, final Block b) {
        if (this.checkOccupation(spawnPoint, b).equals(false)) {
            return false;
        } else {
            b.getAbsoluteCoordinates().stream()
            .forEach(p -> this.setElement(new Point(spawnPoint.getX() + p.getX(), 
                                          spawnPoint.getY() + p.getY()), 1));
            return true;
        }
    }

    @Override
    public final void reset() {
        this.matrix.stream().forEach(row -> row.stream().forEach(element -> element = 0));
    }

    @Override
    public final Optional<Integer> getElement(final Point elemCoordinates) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            return Optional.of(this.matrix.get(elemCoordinates.getX()).get(elemCoordinates.getY()));
        } else {
            return Optional.empty();
        }
    }

    private Boolean isInMatrixBounds(final Point elemCoordinates) {
        return (elemCoordinates.getX() >= 0 &&  elemCoordinates.getX() < this.matrix.size() 
               && elemCoordinates.getY() >= 0 && elemCoordinates.getY() < this.matrix.size());
    }

    private void setElement(final Point elemCoordinates, final Integer value) {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.get(elemCoordinates.getX()).set(elemCoordinates.getY(), value);
        }
    }

    private Boolean checkOccupation(final Point spawnPoint, final Block b) {
        final Integer freeTilesOnMatrix = (int) b.getAbsoluteCoordinates().stream()
                                                 .filter(p -> this.getElement(new Point(spawnPoint.getX() + p.getX(), 
                                                                                        spawnPoint.getY() + p.getY()))
                                                                  .equals(Optional.of(0)))
                                                 .count();
        return freeTilesOnMatrix.equals(b.getOccupation());
    }
}
