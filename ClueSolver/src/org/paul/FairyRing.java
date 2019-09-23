package org.paul;

import org.rspeer.runetek.adapter.Varpbit;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Varps;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.scene.SceneObjects;

public class FairyRing {

    private static final int FAIRY_RING_ROOT = 398;
    private static final int[][] TURN_INDICES = {{19, 20}, {21, 22}, {23, 24}};
    private static final int CONFIRM_INDEX = 26;

    private static final int[] VARPBITS = {3985, 3986, 3987};
    private static final char[][] CODES = {{'a', 'd', 'c', 'b'}, {'i', 'l', 'k', 'j'}, {'p', 's', 'r', 'q'}};

    /**
     * Finds a nearby fairy ring and opens the configure menu
     * @return true if the configure menu is opened
     */
    public static boolean configure() {
        if (isInputOpen()) {
            return true;
        }

        return interactWithRing("Configure") && Time.sleepUntil(FairyRing::isInputOpen, 1800);
    }

    /**
     * Presses the zanaris menu option
     * @return true if the interact was successful
     */
    public static boolean zanaris() {
        return interactWithRing("Zanaris");
    }

    /**
     * Presses the last destination menu option
     * @return true if the interact was successful
     */
    public static boolean lastDestination() {
        return interactWithRing("Last-destination");
    }

    /**
     * Checks if the fairy ring input menu is opened
     * @return whether or not the input menu is opened
     */
    public static boolean isInputOpen() {
        InterfaceComponent[] fairy = Interfaces.get(FAIRY_RING_ROOT);
        return fairy != null && fairy.length > 0 && Interfaces.validateComponent(fairy, 0);
    }


    /**
     * Presses the confirm button on the fairy ring configure interface
     * @return whether or not the interact went through
     */
    public static boolean confirm() {
        InterfaceComponent confirm = Interfaces.getComponent(FAIRY_RING_ROOT, CONFIRM_INDEX);
        return confirm != null && confirm.interact("Confirm");
    }


    /**
     * Enters in the given code and presses the confirm button to travel to the destination
     * @param code the code to enter
     * @return whether or not we traveled
     */
    public static boolean travel(String code) {
        return enterCode(code) && confirm();
    }

    /**
     * Gets the current code using the varp values
     * @return the current code
     */
    public static String getCurrentCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < VARPBITS.length; i++) {
            codeBuilder.append(getCharAt(i));
        }

        return codeBuilder.toString();
    }

    /**
     * Will enter the given code into the fairy ring input menu
     *
     * @param code the code to enter
     * @return Whether the code was successfully entered
     */
    public static boolean enterCode(String code) {
        char[] chars = code.toCharArray();
        if (chars.length != 3) {
            return false;
        }

        boolean result = true;
        for (int i = 0; i < chars.length; i++) {
            char current = getCharAt(i);
            char dest = chars[i];

            if (current != dest) {
                result &= setCharTo(i, dest);
            }
        }

        return result && getCurrentCode().equals(code);
    }

    private static boolean interactWithRing(String action) {
        SceneObject ring =  SceneObjects.getNearest("Fairy ring");
        return ring != null && ring.interact(e -> e.startsWith(action));
    }

    private static char getCharAt(int index) {
        int value = getVarpbitValue(index);
        if (value == -1) {
            return 0;
        }

        return CODES[index][value];
    }

    private static int getVarpbitValue(int index) {
        Varpbit bit = Varps.getBit(VARPBITS[index]);
        if (bit != null) {
            return bit.getValue();
        }

        return -1;
    }

    private static boolean setCharTo(int index, char dest) {
        if (!isCharValid(dest)) {
            return false;
        }

        int from = getVarpbitValue(index);
        int to = indexOf(index, dest);
        int rightDistance = distance(from, to, Move.RIGHT);
        int leftDistance = distance(from, to, Move.LEFT);

        int distance = Math.min(rightDistance, leftDistance);
        Move move = distance == rightDistance ? Move.RIGHT : Move.LEFT;
        InterfaceComponent moveComponent = Interfaces.getComponent(FAIRY_RING_ROOT, TURN_INDICES[index][move.getIndex()]);

        if (moveComponent == null) {
            return false;
        }

        boolean result = true;
        for (int i = 0; i < distance; i++) {
            int value = getVarpbitValue(index);
            result &= moveComponent.interact(move.getAction())
                    && Time.sleepUntil(() -> getVarpbitValue(index) != value, 1200);
            Time.sleep(150, 250);
        }

        return result && Time.sleepUntil(() -> getCharAt(index) == dest, 1200);
    }

    private static int distance(int from, int to, Move move) {
        int distance = 0;
        while (from != to) {
            from += (move == Move.RIGHT ? 1 : -1);
            from = Math.floorMod(from, 4);
            distance++;
        }

        return distance;
    }

    private static int indexOf(int rotator, char c) {
        for (int i = 0; i < CODES[rotator].length; i++) {
            if (c == CODES[rotator][i]) {
                return i;
            }
        }

        return -1;
    }

    private static boolean isCharValid(char check) {
        for (char[] chars : CODES) {
            for (char ch : chars) {
                if (check == ch) {
                    return true;
                }
            }
        }

        return false;
    }

    private enum Move {
        LEFT(1, "Rotate counter-clockwise"),
        RIGHT(0, "Rotate clockwise");

        private final int index;
        private final String action;

        Move(int index, String action) {
            this.index = index;
            this.action = action;
        }

        public int getIndex() {
            return index;
        }

        public String getAction() {
            return action;
        }
    }


}