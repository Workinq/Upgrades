package kr.kieran.upgrades.util;

import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class LicenseUtil {

    private String licenseKey;
    private Plugin plugin;

    public LicenseUtil(String licenseKey, Plugin plugin) {
        this.licenseKey = licenseKey;
        this.plugin = plugin;
    }

    public boolean register() {
        ValidationType validation = isValid();
        return validation == ValidationType.VALID;
    }

    private ValidationType isValid() {
        String rand = toBinary(UUID.randomUUID().toString());
        String sKey = toBinary("YeRoF0I6n05thxLeTkoHuW8dUhTdyUInLkfF");
        String key = toBinary(licenseKey);
        try {
            URL url = new URL("https://license.kieraaaan.me/verify.php" + "?v1=" + xor(rand, sKey) + "&v2=" + xor(rand, key) + "&pl=" + plugin.getName());
            Scanner scanner = new Scanner(url.openStream());
            if (scanner.hasNext()) {
                String response = scanner.next();
                scanner.close();
                try {
                    return ValidationType.valueOf(response);
                } catch (IllegalArgumentException e) {
                    String respRand = xor(xor(response, key), sKey);
                    if (rand.substring(0, respRand.length()).equals(respRand)) return ValidationType.VALID;
                    else return ValidationType.WRONG_RESPONSE;
                }
            } else {
                scanner.close();
                return ValidationType.PAGE_ERROR;
            }
        } catch (IOException e) {
            return ValidationType.URL_ERROR;
        }
    }

    private static String xor(String s1, String s2) {
        StringBuilder s0 = new StringBuilder();
        for (int i = 0; i < (Math.min(s1.length(), s2.length())); i++) {
            s0.append(Byte.parseByte("" + s1.charAt(i)) ^ Byte.parseByte("" + s2.charAt(i)));
        }
        return s0.toString();
    }

    public enum ValidationType {
        WRONG_RESPONSE, PAGE_ERROR, URL_ERROR, KEY_OUTDATED, KEY_NOT_FOUND, NOT_VALID_IP, INVALID_PLUGIN, VALID
    }

    private String toBinary(String string) {
        byte[] bytes = string.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

}
