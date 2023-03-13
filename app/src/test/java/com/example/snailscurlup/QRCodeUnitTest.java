package com.example.snailscurlup;

import static org.junit.Assert.assertTrue;

import com.example.snailscurlup.ui.scan.QrCode;

import org.junit.Test;

public class QRCodeUnitTest {
    /**
     * A test case for the QRcode class.
     * Verifies if it produces the correct results when it comes to
     * synthesizing the name/picture URL/points.
     */
    private QrCode mockQRCode() {
        // Create a fake QR code using some dummy data
        final String dummyData = "123456";
        QrCode newQR = new QrCode(dummyData, 0.0, 0.0);
        return newQR;
    }

    @Test
    public void testQRName() {
        /**
         * A test to make sure the QR code yields the expected name (Soggy).
         */
        QrCode qr = mockQRCode();
        String name = qr.getName();
        assertTrue(name == "Soggy");
    }

    @Test
    public void testQRPoints() {
        /**
         * A test to make sure that the QR code produces the expected number
         * of points (418).
         */
        QrCode qr = mockQRCode();
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
        QrCode qr = mockQRCode();
        String url = qr.getURL();
        assertTrue(url != null);
    }
}
