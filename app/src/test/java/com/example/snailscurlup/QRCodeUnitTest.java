package com.example.snailscurlup;

import static org.junit.Assert.assertTrue;

import com.example.snailscurlup.ui.scan.QRCode;

import org.junit.Test;

public class QRCodeUnitTest {
    /**
     * A test case for the QRcode class.
     * Verifies if it produces the correct results when it comes to
     * synthesizing the name/picture URL/points.
     */
    private QRCode mockQRCode() {
        // Create a fake QR code using some dummy data
        final String dummyData = "123456";
        QRCode newQR = new QRCode(dummyData, 0.0, 0.0);
        return newQR;
    }

    @Test
    public void testQRName() {
        /**
         * A test to make sure the QR code yields the expected name (Soggy).
         */
        QRCode qr = mockQRCode();
        String name = qr.getName();
        assertTrue(name == "Soggy");
    }

    @Test
    public void testQRPoints() {
        /**
         * A test to make sure that the QR code produces the expected number
         * of points (418).
         */
        QRCode qr = mockQRCode();
        int points = qr.getPointsInt();
        assertTrue(points == 418);
    }

    @Test
    public void testQRURL() {
        /**
         * Tests if a URL is generated.
         * URL is random, it cannot be predicted ahead of time.
         * So, we test for whether or not it generated correctly.
         */
        QRCode qr = mockQRCode();
        String url = qr.getURL();
        assertTrue(url != null);
    }

    @Test
    public void testQRHash() {
        /**
         * Tests whether the QR code gets a hash when its generated
         * each time creating a QrCode object it will gets a hash from method getHash256()
         * */
        QRCode qr = mockQRCode();
        String hash = qr.getHash256("123456");
        assertTrue(hash != null);



    }

    @Test
    public void testQRGeoPoint() {
        /**
         * Tests whether the Qr code gets a geolocation when its generated
         * this function is not implemented yet
         * */

    }
}



