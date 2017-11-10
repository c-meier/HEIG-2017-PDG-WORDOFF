package ch.heigvd.wordoff.client.Util;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class TokenManager {

    private static final String tokenFileName = "./token.json";
    private static final String tokenKey = "token";

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

    public static void saveToken(String token) {
        JSONObject obj = new JSONObject();
        obj.put(tokenKey, token);

        try (FileWriter file = new FileWriter(tokenFileName)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
