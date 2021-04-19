package sebedo.window;

/**
 * Both here and in the keybindings json file, each action has a specified integer to go along with it (to make parsing the JSON file a little easier). This interface serves to be the bindings between the two.
 *
 */
public interface Actions {
    long COPY = 0;
    long PASTE = 1;
    long UNDO = 2;
    long REDO = 3;
    long CLEAR = 4;
    long SWITCH_TOOL = 5;

    long[] actions = {
            COPY,
            PASTE,
            UNDO,
            REDO,
            CLEAR,
            SWITCH_TOOL
    };
}
