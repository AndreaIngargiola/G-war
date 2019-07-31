package utils;


import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;


/**
* A collection of <T> objects.
* It works as a Map that translate an Interface to its Implementation where Interface is a child of the interface specified in
* the generic type T.
* 
* @param <T>
*            Parent interface of all the interfaces this collection can contain.
*/
public interface Translator<T> {
    /**
     * Gets an element by its Interface.
     * 
     * @param <C>
     *            the Interface type.
     * @param interf
     *            the interface class
     * @throws IllegalArgumentException
     *             if interf is not an interface
     * @return the element found
     */
    <C extends T> C get(Class<C> interf) throws IllegalArgumentException;

    /**
     * 
     * @return a list of elements' name
     */
    List<String> getNames();

    /**
     * Puts an element in the collection.
     * 
     * @param element
     *            the element
     * @throws IllegalArgumentException
     *             - if the element or its interfaces are already present in the collection
     *             - if you try to insert an element implementing the parent interface only
     */ 
    void put(T element) throws IllegalArgumentException;

    /**
     * Removes an element by its type.
     * 
     * @param <C>
     *            the type
     * @param type
     *            the type
     * @return The removed component
     */
    <C extends T> C remove(Class<C> type);

    //T???
    /**
     * Removes the element from the collection.
     * 
     * @param element
     *            the element.
     */
    void remove(T element);

    /**
     * Removes all elements from the collection.
     */
    void clear();

    /**
     * @return A stream of elements.
     */
    Stream<T> stream();

    /**
     * Convenience method.
     * 
     * @param action
     *            an action to be executed on each element.
     */
    default void forEach(final Consumer<T> action) {
        stream().forEach(action);
    }
}
