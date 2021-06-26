package com.todolist.unit;

import com.todolist.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        item = mock(Item.class);
        when(item.getContentSize()).thenReturn(1001);
    }

    @Test
    public void contentLengthTooLong() {
        assertFalse(item.isValid());
    }

}