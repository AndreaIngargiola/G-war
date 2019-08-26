package model.math;

/**
 * A functional interface for general purpose.
 * @param <I> is the input object type.
 * @param <O> is the output object type.
 */
public interface Function<I, O> {
    /**
     * Execute the function.
     * @param i is the input of the function.
     * @return an output o that is f(i).
     */
    O apply(I i);
}
