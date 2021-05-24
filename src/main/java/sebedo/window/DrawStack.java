package sebedo.window;

import sebedo.shape.SebedoRectangle;

import java.awt.*;
import java.util.Stack;

public final class DrawStack extends Stack<Object> {
    private static DrawStack drawStack;
    private static final Stack<Object> undoStack = new Stack<>();

    private DrawStack() {

    }

    public static DrawStack get() {
        if (drawStack == null) {
            drawStack = new DrawStack();
        }

        return drawStack;
    }

    public void undo() {
        PaintPanel.isPainting = false;
        if (!DrawStack.get().empty()) {
            Object tmp = DrawStack.get().pop();
            DrawStack.undoStack.add(tmp);
        }
        PaintPanel.get().update();
    }

    public void redo() {
        PaintPanel.isPainting = false;
        if (!DrawStack.undoStack.empty()) {
            Object tmp = DrawStack.undoStack.pop();
            DrawStack.drawStack.add(tmp);
        }
        PaintPanel.get().update();
    }

    @Override
    public void clear() {
        super.clear();
        PaintPanel.get().update();
    }
}
