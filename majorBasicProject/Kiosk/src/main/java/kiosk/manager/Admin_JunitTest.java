package kiosk.manager;

import static org.junit.jupiter.api.Assertions.*;

/*
 Only For Testing
 */
class Admin_JunitTest {

    @org.junit.jupiter.api.Test
    void isRecipieSyntaxValid() {

        {

            assertEquals(true, Admin.isRecipieSyntaxValid("딸기(g):0"));
            assertEquals(true, Admin.isRecipieSyntaxValid("딸기(g):123"));
            assertEquals(true, Admin.isRecipieSyntaxValid("      딸기(g):123   "));
            assertEquals(true, Admin.isRecipieSyntaxValid("   딸sadfsdasadfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffsdfsadfsadfasdfasdfsdf기(g):123      "));
            assertEquals(true, Admin.isRecipieSyntaxValid("딸기(g):123"));
        }

        //FALSE
        {
            assertEquals(false, Admin.isRecipieSyntaxValid(" "));
            assertEquals(false, Admin.isRecipieSyntaxValid(": "));
            assertEquals(false, Admin.isRecipieSyntaxValid("::"));
            assertEquals(false, Admin.isRecipieSyntaxValid(":::"));
        }


        {
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):0.123"));
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):0."));
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):12.12"));
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):.12"));
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):0.0"));

            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):-12"));
            assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):+123"));

        }

        assertEquals(false, Admin.isRecipieSyntaxValid("딸기(g):123123123213"));
    }


    @org.junit.jupiter.api.Test
    void isRecipieSemanticsValid() {
        assertEquals(true, Admin.isRecipieSemanticsValid("딸기(g):1"));
        assertEquals(true, Admin.isRecipieSemanticsValid("딸기(g):" + Integer.toString(Integer.MAX_VALUE)));

        assertEquals(false, Admin.isRecipieSemanticsValid("딸기(g):0"));
        assertEquals(false, Admin.isRecipieSemanticsValid("딸기(g):-1"));

    }

    @org.junit.jupiter.api.Test
    void isMenuPriceSyntaxValid() {
    }

    @org.junit.jupiter.api.Test
    void isMenuPriceSemanticsValid() {
    }

    @org.junit.jupiter.api.Test
    void isMenuOptionSyntaxValid() {
    }

    @org.junit.jupiter.api.Test
    void isStockSyntaxValid() {
    }

    @org.junit.jupiter.api.Test
    void isStockSemanticsValid() {
    }

    @org.junit.jupiter.api.Test
    void isAdminSyntaxValid() {
    }

    @org.junit.jupiter.api.Test
    void isMenuNameSynaxValid() {
    }
}