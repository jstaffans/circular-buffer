package fi.bitrite.challenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CircularBuffer {

    private final Object[] buffer;

    private int insertionIndex = 0;
    private int numItems = 0;

    /**
     * Creates a new circular buffer of constant size.
     * @throws IllegalArgumentException If size is less than one.
     */
    public CircularBuffer(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least one");
        }

        buffer = new Object[size];
    }

    /**
     * @return Size of the buffer.
     */
    public int getSize() {
        return buffer.length;
    }

    /**
     * @return The number of items currently in the buffer.
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Adds an item to the buffer. Wraps if end of buffer is reached, overwriting the oldest
     * item in the buffer.
     */
    public void add(Object item) {
        insertItemAtCurrentInsertionIndex(item);
        updateNumItems();
        updateInsertionIndex();
    }

    private void updateNumItems() {
        if (numItems < buffer.length) {
            numItems++;
        }
    }

    private void insertItemAtCurrentInsertionIndex(Object item) {
        buffer[insertionIndex] = item;
    }

    private void updateInsertionIndex() {
        insertionIndex++;
        if (insertionIndex >= buffer.length) {
            insertionIndex = 0;
        }
    }

    /**
     * Removes n items, starting from the oldest item in the buffer.
     * @throws IllegalArgumentException If the number of items to remove exceeds the size of the buffer.
     */
    public void remove(int n) {
        if (n > buffer.length) {
            throw new IllegalArgumentException("Cannot remove more items than the size of the buffer");
        }

        if (n >= numItems) {
            emptyBuffer();
        } else {
            removeItemsByRotatingAndNulling(n);
            numItems -= n;
        }
    }

    private void emptyBuffer() {
        Arrays.fill(buffer, null);
        numItems = 0;
    }

    private void removeItemsByRotatingAndNulling(int numItemsToRemove) {
        // Algorithm with example buffer [5, 6, 2, 3, 4] and n=2:
        // 1) rotate left until the new oldest item is the first element: [4, 5, 6, 2, 3]
        // 2) set n right-most elements to null: [4, 5, 6, null, null]
        // 3) update insertionIndex to point to first null element

        int rotationOffset = bufferIsInOrder() ? 0 : insertionIndex;
        Collections.rotate(Arrays.asList(buffer), -(rotationOffset + numItemsToRemove));

        int nullFillOffset = numItems - numItemsToRemove;
        Arrays.fill(buffer, nullFillOffset, numItems, null);

        insertionIndex = nullFillOffset;
    }

    /**
     * @return The contents of the buffer starting from the oldest item.
     */
    public List<Object> getItems() {
        Object[] wrappedItems = Arrays.copyOf(buffer, numItems);

        if (!bufferIsInOrder()) {
            // if the buffer has wrapped, the insertion index points to the oldest item in
            // the buffer. This item should be returned as the first item in the list.
            Collections.rotate(Arrays.asList(wrappedItems), -insertionIndex);
        }

        return Arrays.asList(wrappedItems);
    }

    /**
     * @return True if the oldest item is the first element in the buffer.
     */
    private boolean bufferIsInOrder() {
        return numItems < buffer.length || insertionIndex == 0;
    }


}
