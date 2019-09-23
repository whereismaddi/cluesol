package org.paul;

import org.rspeer.runetek.api.component.Bank;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;




public class BuyClueItems extends Task {
    private int[] itemListBank = {6328, 1267};
    public static boolean buyingSupplies = false;
    public boolean validate() {

        return (Bank.isOpen() && !Bank.containsAll(itemListBank)) || buyingSupplies;

    }

    public int execute() {
        Log.info("Buyingclueitems");
        buyingSupplies = true;
        Log.info("Buying sups;");

        return 300;
    }
}
