package com.example.snailscurlup;

import static org.junit.Assert.assertTrue;

import com.example.snailscurlup.ui.scan.AbstractQR;

import org.junit.Test;

public class AbstractQRUnitTest {
    /**
     * Verifies the name and point generation of the AbstractQR class.
     */
    private AbstractQR mockAbstract() {
        String data = "123ABC456DEF";
        AbstractQR code = new AbstractQR(data);
        return code;
    }

    @Test
    public void testName() {
        /**
         * Tests to see if the name generates correctly and produces the correct result.
         * This is based on the hashing algorithm used under the hood by the AbstractQR class.
         */
        AbstractQR code = mockAbstract();
        assertTrue(code.getName().equals("Funny"));
    }

    @Test
    public void testPoints() {
        /**
         * Tests to see if the points value generates correctly and produces the correct result.
         * This is based on the hashing algorithm used under the hood by the AbstractQR class.
         */
        AbstractQR code = mockAbstract();
        assertTrue(Integer.toString(code.getPointsInt()).equals("232"));
    }
}
