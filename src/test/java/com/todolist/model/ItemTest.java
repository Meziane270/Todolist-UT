package com.todolist.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ItemTest extends TestCase {

    private User user;

    @Before
    public void setUp() {
        StringBuffer toDoListcontentTooLong = new StringBuffer(1500);
        for(int i=0;i<1500;i++){
            toDoListcontentTooLong.append("a");
        }
    }

    @Test
    public void contentLengthTooLong(){
    }

}