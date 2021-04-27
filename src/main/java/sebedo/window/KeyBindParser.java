package sebedo.window;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Parses keybindings JSON file, manipulates key bind settings, and returns them.
 */
public abstract class KeyBindParser implements Actions {
    private static final JSONParser jsonParser = new JSONParser();

    // I have no idea why I have to use long here.
    private static final HashMap<Long, String[]> keyBinds = new HashMap<>();

    public static void parseKeyBinds() {
        try (FileReader reader = new FileReader("src/main/java/sebedo/keybindings.json")) {
            // read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            // cast read JSON file into a JSONArray
            JSONArray jsonArray = (JSONArray) obj.get("keybindings");
            // put values into keyBinds field
            for (Object o : jsonArray) {
                // cast entry to a JSONObject
                JSONObject jsonO = (JSONObject) o;
                // cast JSONObject to a JSONArray
                JSONArray jsonA = (JSONArray) jsonO.get("keys");
                // put each value into keybindings array
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

    public static Collection<String> getKeyBind(long action) {
        return new HashSet<>(Arrays.asList(keyBinds.get(action)));
    }

    //TODO: make key bind editor method
}
