package org.paul;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class EastOfUzerDig extends Task {

    @Override
    public boolean validate() {

        return Main.digInUzer;

    }

    public int execute() {
        Log.info("the boolean is true");
        if (Main.uzerDigPosition.getPosition().distance(Players.getLocal()) > 200) {
            Log.info("Going to use fairy ring");
            if (FairyRing.isInputOpen()) {
                FairyRing.travel("dlq");
            } else {
                FairyRing.configure();
            }
        }
        if (Main.uzerDigPosition.getPosition().distance(Players.getLocal()) < 200) {
            if (!Main.isAtUzerDigPosition()) {
                Log.info("Walking to dig spot");
                Movement.walkTo(Main.uzerDigPosition);
                Time.sleepUntil(Main::isAtUzerDigPosition, 4000);
            }
        }

        if (Main.isAtUzerDigPosition()) {
            Log.info("Digging");
            Inventory.getFirst("Spade").interact("Dig");
            Time.sleep(800);
        }


        return 300;
    }
}
