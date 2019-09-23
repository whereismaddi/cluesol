package org.paul;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.ChatMessageType;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Paul", desc = "Solve clues", name = "clue solver")
public class Main extends TaskScript implements ChatMessageListener {

    public static final Task[] TASKS = {new BuyClueItems(), new EastOfUzerDig(), new CheckClue(), new Digsite()};

    public static boolean digInUzer = false;
    public static boolean beckonAtDigsite = false;




    @Override
    public void notify(ChatMessageEvent e) {
        if (e.getType().equals(ChatMessageType.SERVER)) {
            e.getMessage().contains("a new clue!");
            {
                digInUzer = false;
            }
        }
    }




    public static final Position uzerDigPosition = new Position(3510, 3074);
    public static boolean isAtUzerDigPosition() { return uzerDigPosition.getPosition().distance(Players.getLocal()) < 1;}

    @Override
    public void onStart() {
        Log.info("Script starting");
        submit (TASKS);
    }
    public void onStop() {
        Log.info("Script stopped.");
    }

    public static InterfaceComponent openClueScrollText() {
        return Interfaces.getComponent(203, 2);
    }

   }



