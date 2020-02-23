package kr.kieran.upgrades.util;

import com.massivecraft.massivecore.chestgui.ChestGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static void fillInventory(ChestGui gui) {
        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).durability((byte) 7).name(" ");
        for (int i = 0; i < gui.getInventory().getSize(); i++) {
            if (gui.getInventory().getItem(i) != null) continue;
            gui.getInventory().setItem(i, filler);
        }
    }

}
