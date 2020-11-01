import annotations.MapKeyFail;
import annotations.MapValueFail;
import exceptions.PutMapKeyFailException;
import exceptions.PutMapValueFailException;

import java.util.HashMap;

public class ImprovedHashMap extends HashMap<Object, Object> {
    @Override
    public Object put(Object key, Object value) {
        if (key.getClass().isAnnotationPresent(MapKeyFail.class))
            throw new PutMapKeyFailException();
        if (value.getClass().isAnnotationPresent(MapValueFail.class))
            throw new PutMapValueFailException();
        return super.put(key, value);
    }
}
