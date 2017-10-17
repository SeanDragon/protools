package pro.tools.data.text.json.typeimpl;

import pro.tools.data.text.json.exception.TypeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * @author SeanDragon
 */
@SuppressWarnings("SpellCheckingInspection")
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;
    private final Type owner;

    public ParameterizedTypeImpl(Class raw, Type[] args, Type owner) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
        this.owner = owner;
        checkArgs();
    }

    private void checkArgs() {
        if (raw == null) {
            throw new TypeException("raw class can't be null");
        }
        TypeVariable[] typeParameters = raw.getTypeParameters();
        if (args.length != 0 && typeParameters.length != args.length) {
            throw new TypeException(raw.getName() + " expect " + typeParameters.length + " arg(s), got " + args.length);
        }
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return owner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(raw.getName());
        if (args.length != 0) {
            sb.append('<');
            for (int i = 0; i < args.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                Type type = args[i];
                if (type instanceof Class) {
                    Class clazz = (Class) type;

                    if (clazz.isArray()) {
                        int count = 0;
                        do {
                            count++;
                            clazz = clazz.getComponentType();
                        } while (clazz.isArray());

                        sb.append(clazz.getName());

                        for (int j = count; j > 0; j--) {
                            sb.append("[]");
                        }
                    } else {
                        sb.append(clazz.getName());
                    }
                } else {
                    sb.append(args[i].toString());
                }
            }
            sb.append('>');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;

        if (!raw.equals(that.raw)) {
            return false;
        }
        return Arrays.equals(args, that.args) && (owner != null ? owner.equals(that.owner) : that.owner == null);

    }

    @Override
    public int hashCode() {
        int result = raw.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}