package model.levelsgenerator;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import model.entities.AbstractEntity;

public class LevelGeneratorImpl implements LevelGenerator {

    private List<LevelGenerationEntity<? extends AbstractEntity>> entityList;
    private Integer iteration;

    @Override
    public Map<Point, ? extends AbstractEntity> getNewLevel() {
        // TODO Auto-generated method stub
        return null;
    }

    private void importEntities() {
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().whitelistPackages("model.entities").scan()) {

        ClassInfoList filtered = scanResult.getAllClasses().filter(
                classInfo -> (classInfo.extendsSuperclass("AbstractEntity") 
                           && classInfo.getSubclasses().isEmpty()));

        Class<? extends AbstractEntity> type = AbstractEntity.class;
        filtered.stream()
                .map(c -> c.loadClass(type))
                .forEach(e -> entityList.add(new LevelGenerationEntity<>(type.cast(e))));
        }
    }
    
    

}
