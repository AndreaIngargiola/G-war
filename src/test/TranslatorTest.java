package test;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.Translator;
import utils.TranslatorImpl;



/**
 * Test for {@link TranslatorImpl}.
 */
public class TranslatorTest {

    /**
     * It must not be possible to add the parent interface to the collection.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotAddParent() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);

        bag.put(new Parent() {
        });
    }

    /**
     * Basic test for put and get.
     */
    @Test
    public void testPut() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final Parent a = new ChildA() {
        };

        bag.put(a);

        assertEquals("error if ChildA is not the interface of 'a' ", a, bag.get(ChildA.class));

    }


    /**
     * Must not be possible since this is a collection of Interfaces.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotGetByConcreteClass() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final Parent a = new ConcreteChildA();

        bag.put(a);
        bag.get(ConcreteChildA.class);
    }

    /**
     * It must not be possible to get elements by the parent interface.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParentNotPresent() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);

        bag.put(new ChildA() {
        });

        bag.get(Parent.class);
    }

    /**
     * Remove a specific element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveByElement() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final ChildA a = new ChildA() {
        };

        bag.put(a);
        bag.remove(a);

        bag.get(ChildA.class);
    }

    /**
     * Remove elements by the interface.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveByInterface() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final ChildA a = new ChildA() {
        };

        bag.put(a);
        bag.remove(ChildA.class);

        bag.get(ChildA.class);
    }

    /**
     * Adding an element implementing multiple interfaces.
     */
    @Test
    public void testSubInterfaces() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final ChildofAB ab = new ChildofAB() {
        };

        bag.put(ab);

        assertEquals("error if ChildA is not the interface of 'ab' ", ab, bag.get(ChildA.class));
        assertEquals("error if ChildB is not the interface of 'ab' ", ab, bag.get(ChildB.class));
        assertEquals("error if ChildofAB is not the interface of 'ab' ", ab, bag.get(ChildofAB.class));
    }

    /**
     * Cannot add the same interface more than once.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInterfacesClash() {
        final Translator<Parent> bag = new TranslatorImpl<>(Parent.class);
        final ChildA a = new ChildA() {
        };
        final ChildofAB ab = new ChildofAB() {
        };

        bag.put(ab);
        bag.put(a);
    }
}

interface Parent {
}

interface ChildA extends Parent {
}

interface ChildB extends Parent {
}

interface ChildofAB extends ChildA, ChildB {
}

@SuppressWarnings("all")  //"This class name ends with Test but contains no test cases" PMD warning suppressed: this is a fake class used in the tests.
class ConcreteChildA implements ChildA {
}
