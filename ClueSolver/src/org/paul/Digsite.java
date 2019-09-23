package org.paul;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.GrandExchangeSetup;
import org.rspeer.runetek.api.component.tab.EquipmentSlot;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.providers.RSGrandExchangeOffer;
import org.rspeer.script.task.Task;
import org.rspeer.runetek.api.component.GrandExchange;

public class Digsite extends Task {


    @Override
    public boolean validate() {

        return Main.beckonAtDigsite;

    }

    public int execute() {
        if (EquipmentSlot.HEAD.getItemName().contains("Green hat")
                && EquipmentSlot.MAINHAND.getItemName().contains("Iron pickaxe")
                && EquipmentSlot.FEET.getItemName().contains("Snakeskin boots")) {
            //GO DO CLUE
        }
        if (Inventory.contains("Green hat")) {
            Inventory.getFirst("Green hat").interact("Wear");
            Time.sleep(600);
        }
        if (Inventory.contains("Iron pickaxe")) {
            Inventory.getFirst("Iron pickaxe").interact("Wield");
            Time.sleep(600);
        }
        if (Inventory.contains("Snakeskin boots")) {
            Inventory.getFirst("Snakeskin boots").interact("Wear");
            Time.sleep(600);
        }

        if (!Inventory.contains("Green hat") && !EquipmentSlot.HEAD.getItemName().contains("Green hat") ||
                !Inventory.contains("Iron pickaxe") && !EquipmentSlot.MAINHAND.getItemName().contains("Iron pickaxe")
                || !EquipmentSlot.FEET.getItemName().contains("Snakeskin boots") && !Inventory.contains("Snakeskin boots")) {
            if (!Bank.isOpen()) {
                Bank.open();
                Time.sleepUntil(Bank::isOpen, 1000);
            }
        }
        if (Bank.isOpen()) {
            if (Bank.contains("Green hat") && !Inventory.contains("Green hat")) {
                Bank.withdraw("Green hat", 1);
                Time.sleepUntil(() -> Inventory.contains("Green hat"), 1000);
            }
            if (Bank.contains("Iron pickaxe") && !Inventory.contains("Iron pickaxe")) {
                Bank.withdraw("Iron pickaxe", 1);
                Time.sleepUntil(() -> Inventory.contains("Green hat"), 1000);
            }
            if (Bank.contains("Snakeskin boots") && !Inventory.contains("Snakeskin boots")) {
                Bank.withdraw("Snakeskin boots", 1);
                Time.sleepUntil(() -> Inventory.contains("Green hat"), 1000);
            }


        }
        return 300;
    }
}



