package sebedo.window;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

/**
 * Parses keybindings JSON file, manipulates key bind settings, and returns them.
 */
public class KeyBindParser {
    private final JSONParser jsonParser = new JSONParser();
    // I have no idea why I have to use long here.
    private final HashMap<Long, String[]> keyBinds = new HashMap<>();

    private final String keyBindingFilePath;

    public KeyBindParser(String filePath) {
        this.keyBindingFilePath = filePath;
        this.parseKeyBinds();
    }

    public void parseKeyBinds() {
        try (FileReader reader = new FileReader(keyBindingFilePath)) {
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

    public Collection<String> getKeyBind(long action) {
        return new HashSet<>(Arrays.asList(keyBinds.get(action)));
    }

    public void setKeyBind(long action, String[] keyStrokes) {
        try {
            // set up reader
            FileReader reader = new FileReader(keyBindingFilePath);
            // set up string builder; this is what will be written to the keybindings file
            StringBuilder builder = new StringBuilder();
            // formatted keyStrokes
            StringBuilder sb = new StringBuilder("[\"");
            for (String ks : keyStrokes) {
                if (ks.equals(keyStrokes[keyStrokes.length - 1])) {
                    sb.append(ks).append("\"]");
                } else {
                    sb.append(ks).append("\",\"");
                }
            }

            // read keyBindingFilePath
            while (reader.ready()) {
                builder.append((char) reader.read());
            }

            // set up writer
            FileWriter writer = new FileWriter(keyBindingFilePath);
            // find the action prior to what is to be edited
            int targetIndex = action != 0 ? builder.indexOf("\"action\": " + (action - 1)) : builder.indexOf("\n");
            // find the next '['
            targetIndex = builder.indexOf("[", targetIndex);
            // set the new keybinding at the target location
            builder.replace(targetIndex, targetIndex + sb.length(), sb.toString());
            // write the edited builder to keybindings file
            writer.append(builder.toString());

            reader.close();
            writer.close();

            System.out.println(targetIndex);
            System.out.println(builder.charAt(targetIndex));
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
