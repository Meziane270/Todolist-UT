package com.todolist.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private User validUser;
    private User invalidUser;
    private User invalidUserShortPassword;
    private User invalidUserLongPassword;
    private User invalidUserBadMail;
    private User invalidUserBadFirstname;
    private User invalidUserBadLastname;
    private User invalidUserBadAge;

    @Before
    public void setUp() {
    }

    @Test
    public void isValidUser() {
        assertTrue(validUser.isValid());
    }

    @Test
    public void isInvalidUser() {
        assertFalse(invalidUser.isValid());
    }

    @Test
    public void isInvalidMail() {
        assertFalse(invalidUserBadMail.isValid());
    }

    @Test
    public void isTooShortPassword() {
        assertFalse(invalidUserShortPassword.isValid());
    }


    @Test
    public void isTooLongPassword() {
        assertFalse(invalidUserLongPassword.isValid());
    }

    @Test
    public void isInvalidFirstname() {
        assertFalse(invalidUserBadFirstname.isValid());
    }

    @Test
    public void isInvalidLastname() {
        assertFalse(invalidUserBadLastname.isValid());
    }

    @Test
    public void isInvalidAge() {
        assertFalse(invalidUserBadAge.isValid());
    }

}