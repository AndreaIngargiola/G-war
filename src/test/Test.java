package test;

/**
 * The main class tests.
 */
public final class Test {

    private Test() {
        //nothing
    }

    /**
     * 
     * @param args
     *        automatic main parameter
     * @throws java.io.IOException
     *        when invalid arguments are passed
     */
    public static void main(final String... args) throws java.io.IOException {
        new TestGrid(10);
    }
}

