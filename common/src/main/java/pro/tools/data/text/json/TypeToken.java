package pro.tools.data.text.json;

import pro.tools.data.text.json.exception.TypeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author SeanDragon
 */
public final class TypeToken {
    private final Type type;

    public TypeToken() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new TypeException("No generics found!");
        }
        ParameterizedType type = (ParameterizedType) superclass;
        this.type = type.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}