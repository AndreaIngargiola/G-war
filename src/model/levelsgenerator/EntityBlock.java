package model.levelsgenerator;
import java.util.List;
import java.util.Set;
import model.components.Component;
import model.entities.Entity;

public class EntityBlock extends BlockImpl {

    private List<Condition> placingConditions;
    private LevelGenerationEntity entity;

    public EntityBlock(Coordinate spawnPoint,  e) {
        super(spawnPoint);
        this.BuildConditions(this.entity.getComponentsSet());
    }

    private void BuildConditions(Set<String> componentNames) {

    }

    public LevelGenerationEntity getEntity() {
        
        return null;
    }

}
