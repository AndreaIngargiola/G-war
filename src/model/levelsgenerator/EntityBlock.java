package model.levelsgenerator;
import java.util.List;
import java.util.Set;
import model.components.Component;
import model.entities.Entity;

public class EntityBlock<X extends Entity> extends BlockImpl {

    private List<Condition<? extends Component>> placingConditions;

    public EntityBlock(Coordinate spawnPoint, X e) {
        super(spawnPoint);
        this.BuildConditions(e.getComponents());
    }

    private void BuildConditions(Set<Component> c) {

    }

}
