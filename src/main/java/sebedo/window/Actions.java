package sebedo.window;

/**
 * Both here and in the keybindings json file, each action has a specified integer to go along with it (to make parsing the JSON file a little easier). This interface serves to be the bindings between the two.
 *
 */
public interface Actions {
    int COPY = 0;
    int PASTE = 1;
    int UNDO = 2;
    int REDO = 3;
    int CLEAR = 4;
    int SWITCH_TOOL = 5;

    int[] actions = {
            COPY,
            PASTE,
            UNDO,
            REDO,
            CLEAR,
            SWITCH_TOOL
    };
}
