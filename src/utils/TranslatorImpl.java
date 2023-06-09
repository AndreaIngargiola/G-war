package utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.reflect.TypeToken;



/**
 * Base implementation of {@link Translator}.
 * 
 * @param <T>
 *            Elements super type.
 */
public final class TranslatorImpl<T> implements Translator<T> {
    private final Map<Class<?>, T> elements = new LinkedHashMap<>();
    private final Class<T> elementsInterface;

    /**
     * @param elementsInterface
     *            The super / parent interface of elements.
     */
    public TranslatorImpl(final Class<T> elementsInterface) {
        assertInterface(elementsInterface);
        this.elementsInterface = elementsInterface;
    }

    @Override
    public <C extends T> C get(final Class<C> interf) {
        assertInterface(interf);

        if (elements.containsKey(interf)) {
            return interf.cast(elements.get(interf));
        } else {
            throw new IllegalArgumentException("This class is not included into the set: " + interf.toString());
        }
    }

    @Override
    public Set<Class<?>> getInterfaces() {
        return elements.keySet();
    }

    @Override
    public void put(final T element) throws IllegalArgumentException {
        final Set<Class<?>> interfaces = allInterfaces(element.getClass());
        if (interfaces.isEmpty()) {
            throw new IllegalArgumentException("Element does not implement any suitable child interface");
        }

        interfaces.stream()
                .filter(in -> elements.get(in) != null)
                .findAny()
                .ifPresent(in -> {
                    throw new IllegalArgumentException(
                            "Element implemented interface " + in.getSimpleName() + " already present in the bag.");
                });

        interfaces.forEach(in -> elements.put(in, element));
    }

    @Override
    public <C extends T> C remove(final Class<C> type) {
        if (elements.containsKey(type))  {          //devo controllare se Ã¨ giusto
            return type.cast(elements.remove(type));

        } else {
            throw new IllegalArgumentException("This class is not included into the set: " + type.toString());
        }
    }

    @Override
    public void remove(final T element) {
        elements.values().remove(element);
    }

    @Override
    public Stream<T> stream() {
        return elements.values().stream();
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public String toString() {
        return "Translator<" + elementsInterface.getSimpleName() + ">[elements=" + elements + "]";
    }

    private Set<Class<?>> allInterfaces(final Class<?> type) {
        return TypeToken.of(type).getTypes().interfaces()
                .stream()
                .map(TypeToken::getRawType)
                .filter(t -> !t.equals(elementsInterface))
                .filter(elementsInterface::isAssignableFrom)
                .collect(Collectors.toSet());
    }

    private void assertInterface(final Class<?> type) {
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Type " + type.getSimpleName() + " is not an interface.");
        }
    }
}

