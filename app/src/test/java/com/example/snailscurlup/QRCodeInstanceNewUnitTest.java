package com.example.snailscurlup;

import static org.junit.Assert.assertTrue;

import com.example.snailscurlup.model.User;
import com.example.snailscurlup.ui.scan.AbstractQR;
import com.example.snailscurlup.ui.scan.QRCodeInstanceNew;

import org.junit.Test;

public class QRCodeInstanceNewUnitTest {
    /**
     * A test case for the "QRCodeInstanceNew" class.
     * Verifies if it can fill in the data correctly using the hash.
     */
    private QRCodeInstanceNew mockQR() {
        String data = "123ABC456DEF";
        AbstractQR abs = new AbstractQR(data);
        User dummyUser = new User("testuser", "asdf@gmail.com", "1234567890", "7310");
        QRCodeInstanceNew qr = new QRCodeInstanceNew(abs, dummyUser, null, null, null);
        return qr;
    }


    @Test
    public void testGetHash() {
        /**
         * Tests to see if the hash is correctly inherited from the abstract QR type.
         */
        QRCodeInstanceNew qr = mockQR();
        assertTrue(qr.AbstractQRHash().equals(qr.getAbstractQR().getHash()));
    }

    @Test
    public void testName() {
        /**
         * Tests to see if the name is consistent between AbstractQR and QR instance.
         */
        QRCodeInstanceNew qr = mockQR();
        assertTrue(qr.getName().equals("Funny"));
    }

    @Test
    public void testPoints() {
        /**
         * Tests to see if the points are consistent between AbstractQR and QR instance.
         */
        QRCodeInstanceNew qr = mockQR();
        assertTrue(Integer.toString(qr.getPointsInt()).equals("232"));
    }

}
