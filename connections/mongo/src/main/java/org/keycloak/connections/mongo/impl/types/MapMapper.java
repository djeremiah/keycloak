package org.keycloak.connections.mongo.impl.types;

import com.mongodb.BasicDBObject;
import org.keycloak.connections.mongo.api.types.Mapper;
import org.keycloak.connections.mongo.api.types.MapperContext;
import org.keycloak.connections.mongo.api.types.MapperRegistry;

import java.util.Map;
import java.util.Set;

/**
 * For now, we support just convert from Map<String, simpleType>
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class MapMapper<T extends Map> implements Mapper<T, BasicDBObject> {

    // Just some dummy way of encoding . character as it's not allowed by mongo in key fields
    static final String DOT_PLACEHOLDER = "###";

    private final MapperRegistry mapperRegistry;
    private final Class<T> mapType;

    public MapMapper(MapperRegistry mapperRegistry, Class<T> mapType) {
        this.mapperRegistry = mapperRegistry;
        this.mapType = mapType;
    }

    @Override
    public BasicDBObject convertObject(MapperContext<T, BasicDBObject> context) {
        T mapToConvert = context.getObjectToConvert();
        return convertMap(mapToConvert, mapperRegistry);
    }

    public static BasicDBObject convertMap(Map mapToConvert, MapperRegistry mapperRegistry) {
        BasicDBObject dbObject = new BasicDBObject();
        Set<Map.Entry> entries = mapToConvert.entrySet();
        for (Map.Entry entry : entries) {
            String key = (String)entry.getKey();
            Object value = entry.getValue();

            Object dbValue = mapperRegistry==null ? entry.getValue() : mapperRegistry.convertApplicationObjectToDBObject(value, Object.class);

            if (key.contains(".")) {
                key = key.replaceAll("\\.", DOT_PLACEHOLDER);
            }

            dbObject.put(key, dbValue);
        }
        return dbObject;
    }

    @Override
    public Class<? extends T> getTypeOfObjectToConvert() {
        return mapType;
    }

    @Override
    public Class<BasicDBObject> getExpectedReturnType() {
        return BasicDBObject.class;
    }
}
