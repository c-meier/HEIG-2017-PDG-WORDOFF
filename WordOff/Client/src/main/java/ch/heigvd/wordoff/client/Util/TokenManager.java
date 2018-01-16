/*
 * File: TokenManager.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Util;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

/**
 * Management class of the token. Load, save and delete a token.
 */
public class TokenManager {

    // Token
    private static final String tokenFileName = "./token.json";
    private static final String tokenKey = "token";

    /**
     * Load the token from disk
     * @return The String representation of the token
     * @throws TokenNotFoundException If the file is not found
     */
    public static String loadToken() throws TokenNotFoundException {
        JSONParser parser = new JSONParser();
        String token = "";

        try (FileReader file = new FileReader(tokenFileName)) {
            Object obj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) obj;
            token = (String) jsonObject.get(tokenKey);
        } catch (FileNotFoundException e) {
            // File not found, user needs to sign-in first
            throw new TokenNotFoundException();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Save the String representation of the token to disk
     * @param token The token
     */
    public static void saveToken(String token) {
        JSONObject obj = new JSONObject();
        obj.put(tokenKey, token);

        try (FileWriter file = new FileWriter(tokenFileName)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the file containing the token
     */
    public static void deleteToken() {
        try {
            Path path = FileSystems.getDefault().getPath(tokenFileName);
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", tokenFileName);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", tokenFileName);
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
