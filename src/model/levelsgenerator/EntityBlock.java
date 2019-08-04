package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EntityBlock extends BlockImpl {

    private List<Condition> placingConditions;
    private final LevelGenerationEntity<?> entity;

    public EntityBlock(final LevelGenerationEntity<?> e) {
        super();
        this.entity = e;
        this.BuildConditions(e.getComponentsSet());
        /*this.BuildConditions(this.entity.getComponentsSet());*/
    }

    private void BuildConditions(final Set<String> componentNames) {
        this.placingConditions = new ArrayList<>();
    }

    public LevelGenerationEntity getEntity() {
        return this.entity;
    }

}
