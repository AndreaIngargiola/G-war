package model.levelsgenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import model.utility.Pair;

/**
 *BlockImpl is the implementation of the block using the List data structure.
 */
public class BlockImpl implements Block {

    private final Set<Coordinate> vertex;
    private final Coordinate spawnPoint;
    private Integer xMax;
    private Integer xMin;
    private Integer yMax;
    private Integer yMin;

    /**
     * the block cannot be empty, so when the block is created the first point will be the "center" of the block or, in other
     * words, the spawn point.
     * @param spawnPoint is the initial point and the point in which the relative coordinates of the other points 
     * will be calculated
     */
    public BlockImpl(final Coordinate spawnPoint) {
        vertex = new HashSet<>();
        this.vertex.add(spawnPoint);
        this.spawnPoint = spawnPoint;
        this.xMax = spawnPoint.getPoint().x;
        this.xMin = spawnPoint.getPoint().x;
        this.yMax = spawnPoint.getPoint().y;
        this.yMin = spawnPoint.getPoint().y;
    }

    @Override
    public final void addPoint(final Coordinate p) {
        this.vertex.add(p);
        this.updateMinMax();
    }
    
    private final void updateMinMax() {
        for(Coordinate p : this.getRelativeCoordinates()){
            this.xMax = (p.getPoint().x > this.xMax) ?  p.getPoint().x : this.xMax;
            this.xMin = (p.getPoint().x < this.xMin) ?  p.getPoint().x : this.xMin;
            this.yMax = (p.getPoint().y > this.yMax) ?  p.getPoint().y : this.yMax;
            this.yMin = (p.getPoint().y < this.yMin) ?  p.getPoint().y : this.yMin;       
        }
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

    public Integer getXmax() {
        return xMax;
    }

    public Integer getXmin() {
        return xMin;
    }

    public Integer getYmax() {
        return yMax;
    }

    public Integer getYmin() {
        return yMin;
    }

}
