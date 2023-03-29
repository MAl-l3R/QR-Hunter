ackage com.example.snailscurlup.ui.scan;


/*
import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class QRCodesManager {
    private static QRCodesManager instance = null;
    private List<QrCode> qrCodes = new ArrayList<>();

    private QRCodesManager() {
        // private constructor to prevent instantiation outside of the class
    }

    public static QRCodesManager getInstance() {
        if (instance == null) {
            instance = new  QRCodesManager();
        }
        return instance;
    }

    public QrCodeInstance getQRCodeInstance(String data, Double latitude, Double longitude, boolean imageLocation) {
        String newQrCodeHash = getHash256(data);
        // check if qr code with the same hash already exists
        QrCode qrCode = null;
        for (QrCode code : qrCodes) {
            if (code.getHash().equals(newQrCodeHash)) {
                qrCode = code;
                break;
            }
        }
        // if qr code with the same hash does not exist, create a new one
        if (qrCode == null) {
            qrCode = new QrCode(data, latitude, longitude);
            qrCodes.add(qrCode);
        }

        // create qr code instance object and assign it to the qr code object
        QrCodeInstance qrCodeInstance = new  QrCodeInstance(data, latitude, longitude, imageLocation);
        qrCode.addQRCodeInstance(qrCodeInstance);

        return qrCodeInstance;
    }

    public String getHash256(@NonNull String data) {
        String hash256;
        try {
            // From https://www.baeldung.com/sha-256-hashing-java
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashHex = new StringBuilder(2 * hashBytes.length);
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1) {
                    hashHex.append('0');
                }
                hashHex.append(hex);
            }
            hash256 = hashHex.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("Use SHA-256 message digest algorithm");
            hash256 = Integer.toHexString(data.hashCode());
        }
        return hash256;
    }
}
*/