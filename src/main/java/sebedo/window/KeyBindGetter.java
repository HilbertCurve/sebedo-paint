package sebedo.window;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Parses keybinding JSON file, manipulates key bind settings, and returns them.
 */
public class KeyBindGetter implements Actions {
    private static KeyBindGetter keyBindGetter;
    private static final JSONParser jsonParser = new JSONParser();
    // I have no idea why I have to use long here.
    private static final HashMap<Long, String[]> keyBinds = new HashMap<>();

    static {
        try (FileReader reader = new FileReader("src/main/java/sebedo/keybindings.json")) {
            // read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            // cast read JSON file into a JSONArray
            JSONArray jsonArray = (JSONArray) obj.get("keybindings");
            // put values into keyBinds field
            for (Object o : jsonArray) {
                JSONObject jsonO = (JSONObject) o;
                JSONArray jsonA = (JSONArray) jsonO.get("keys");
                String[] keys = new String[jsonA.size()];

                for (int i = 0; i < keys.length; i++) {
                    keys[i] = (String) jsonA.get(i);
                }

                keyBinds.put((Long) jsonO.get("action"), keys);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private KeyBindGetter() {

    }

    public KeyBindGetter get() {
        if (keyBindGetter == null) {
            keyBindGetter = new KeyBindGetter();
        }

        return keyBindGetter;
    }
}
