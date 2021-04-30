package sebedo.window;

public enum Tools {
    FREEHAND,
    ELLIPSE,
    RECTANGLE,
    LINE,
    ARC,
    SHAPE,
    SELECT;

    private static String[] toolNames() {
        String[] names = new String[Tools.values().length];

        for (Tools t : Tools.values()) {
            names[t.ordinal()] = t.toString();
        }
        return names;
    }

    public static String[] toolNames = toolNames();
}