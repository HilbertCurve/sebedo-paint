package sebedo.window;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses keybinding JSON file, manipulates key bind settings, and returns them.<br>
 * TODO: implement JSON reading.
 */
public class KeyBindGetter {
    private static KeyBindGetter kbg;
    private static Object obj;

    static {
        try {
            obj = new JSONParser().parse(new FileReader("src/main/java/sebedo/keybindings.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static final JSONObject jo = (JSONObject) obj;

    static class KeyBind {
        static HashMap<ArrayList<Integer>, String> h;

    }

    private KeyBindGetter() {

    }

    public KeyBindGetter get() {
        if (kbg == null) {
            kbg = new KeyBindGetter();
        }

        return kbg;
    }

    public HashMap<ArrayList<Integer>, String> getKeyBind(Actions a) {


        return null;
    }
}
