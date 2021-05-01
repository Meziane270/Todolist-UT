package com.todolist.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
        String validMail = "validMail@gmail.com";
        String invalidMail = "invalidMail@.com";
        String validPassword = "PasswordValid";
        String invalidTooLongPassword = "invalid";
        String invalidTooSmallPassword = "TooBigPasswordToBeAValidUser123456789";
        String validFirstname = "Firstname";
        String invalidFirstname = "";
        String validLastname = "Lastname";
        String invalidLastname = "";
        LocalDate validAge = LocalDate.of(2000, 1, 1);
        LocalDate invalidAge = LocalDate.of(2020, 1, 1);

        validUser = new User(validMail, validFirstname, validLastname, validPassword, validAge);
        invalidUser = new User(invalidMail, invalidFirstname, invalidLastname, invalidTooSmallPassword, invalidAge);
        invalidUserLongPassword = new User(validMail, validFirstname, validLastname, invalidTooLongPassword, validAge);
        invalidUserShortPassword = new User(validMail, validFirstname, validLastname, invalidTooSmallPassword, validAge);
        invalidUserBadMail = new User(invalidMail, validFirstname, validLastname, validPassword, validAge);
        invalidUserBadFirstname = new User(validMail, invalidFirstname, validLastname, validPassword, validAge);
        invalidUserBadLastname = new User(validMail, validFirstname, invalidLastname, validPassword, validAge);
        invalidUserBadAge = new User(validMail, validFirstname, validLastname, validPassword, invalidAge);
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