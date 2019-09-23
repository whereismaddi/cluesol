package org.paul;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class CheckClue extends Task {

    @Override
    public boolean validate() {

        return !Main.digInUzer && !Main.beckonAtDigsite && Bank.isClosed();


    }

    public int execute() {

        if (Main.openClueScrollText() != null) {
            Main.openClueScrollText().getText();
        }

        if (Inventory.contains("Clue scroll (medium)")) {
            if (Main.openClueScrollText() == null)
                Log.info("Opening clue");
            Inventory.getFirst("Clue scroll (medium)").interact("Read");
            Time.sleep(600);
        }

        if (Inventory.contains("Clue scroll (medium)")) {
            if (Main.openClueScrollText().getText().contains("02 degrees 43 minutes south")) {
                Log.info("I have the uzer clue");
                Main.digInUzer = true;
            }

            if (Inventory.contains("Clue scroll (medium)")) {
                if (Main.openClueScrollText().getText().contains("Beckon in the Digsite")) {
                    Log.info("I have the digsite clue");
                    Main.beckonAtDigsite = true;
                }

            }

        }
        return 300;
    }
}
