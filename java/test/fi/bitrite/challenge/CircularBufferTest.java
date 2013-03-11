package fi.bitrite.challenge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CircularBufferTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private CircularBuffer buffer1;
    private CircularBuffer buffer3;
    private CircularBuffer buffer10;

    @Before
    public void createBuffers() {
        buffer1 = new CircularBuffer(1);
        buffer3 = new CircularBuffer(3);
        buffer10 = new CircularBuffer(10);
    }

    @Test
    public void zeroSizeBufferIsNotAllowed() {
        exception.expect(IllegalArgumentException.class);
        CircularBuffer buffer = new CircularBuffer(0);
    }

    @Test
    public void addSingleItem() {
        buffer1.add("item");
        List expected = Arrays.asList(new String[] {"item"});
        assertEquals(expected, buffer1.getItems());
    }

    @Test
    public void addSingleItemAndOverwrite() {
        // the buffer can only hold one item, so adding a second item should
        // overwrite the first
        buffer1.add("item1");
        buffer1.add("item2");
        List expected = Arrays.asList(new String[] {"item2"});
        assertEquals(expected, buffer1.getItems());
    }

    @Test
    public void addThreeItems() {
        buffer3.add("item1");
        buffer3.add("item2");
        buffer3.add("item3");
        List expected = Arrays.asList(new String[] {"item1", "item2", "item3"});
        assertEquals(expected, buffer3.getItems());
    }

    @Test
    public void itemWrap() {
        buffer3.add("item1");
        buffer3.add("item2");
        buffer3.add("item3");
        buffer3.add("item4");
        List expected = Arrays.asList(new String[] {"item2", "item3", "item4"});
        assertEquals(expected, buffer3.getItems());
    }

    @Test
    public void partiallyFilledBuffer() {
        buffer10.add("item1");
        buffer10.add("item2");
        buffer10.add("item3");
        List expected = Arrays.asList(new String[] {"item1", "item2", "item3"});
        assertEquals(expected, buffer10.getItems());
    }

    @Test
    public void doubleWrap() {
        buffer3.add("item1");
        buffer3.add("item2");
        buffer3.add("item3");
        buffer3.add("item4");
        buffer3.add("item5");
        buffer3.add("item6");
        buffer3.add("item7");
        List expected = Arrays.asList(new String[] {"item5", "item6", "item7"});
        assertEquals(expected, buffer3.getItems());
    }

    @Test
    public void removeItemFromPartiallyFilledBuffer() {
        buffer10.add("item1");
        buffer10.add("item2");
        buffer10.add("item3");

        buffer10.remove(1);
        List expected = Arrays.asList(new String[] {"item2", "item3"});
        assertEquals(expected, buffer10.getItems());
    }

    @Test
    public void removingTooManyItemsIsNotAllowed() {
        exception.expect(IllegalArgumentException.class);
        buffer3.remove(4);
    }

    @Test
    public void removeAllItems() {
        buffer1.add("item1");
        buffer1.remove(1);
        assertTrue(buffer1.getItems().isEmpty());
    }

    @Test
    public void removeItemFromWrappedBuffer() {
        buffer3.add("item1");
        buffer3.add("item2");
        buffer3.add("item3");
        buffer3.add("item4");
        buffer3.remove(1);
        List expected = Arrays.asList(new String[] {"item3", "item4"});
        assertEquals(expected, buffer3.getItems());
    }

    @Test
    public void removeAndAddItems() {
        buffer3.add("item1");
        buffer3.add("item2");
        buffer3.add("item3");
        buffer3.add("item4");

        buffer3.remove(2);
        List expected = Arrays.asList(new String[] {"item4"});
        assertEquals(expected, buffer3.getItems());

        buffer3.add("item5");
        buffer3.add("item6");

        expected = Arrays.asList(new String[] {"item4", "item5", "item6"});
        assertEquals(expected, buffer3.getItems());

        buffer3.add("item7");
        expected = Arrays.asList(new String[] {"item5", "item6", "item7"});
        assertEquals(expected, buffer3.getItems());
    }
}
