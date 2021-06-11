package com.todolist.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;

public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        item = Mockito.mock(Item.class);
        Mockito.when(item.getContentSize()).thenReturn(1001);
    }

    @Test
    public void contentLengthTooLong() {
        assertFalse(item.isValid());
    }

}