package kr.kieran.upgrades.util;

import com.massivecraft.massivecore.chestgui.ChestGui;
import kr.kieran.upgrades.entity.MConf;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static void fillInventory(ChestGui gui)
    {
        ItemStack filler = new ItemBuilder(MConf.get().fillerMaterial).durability(MConf.get().fillerData).name(MConf.get().fillerName);
        for (int i = 0; i < gui.getInventory().getSize(); i++)
        {
            if (gui.getInventory().getItem(i) != null)
            {
                continue;
            }
            gui.getInventory().setItem(i, filler);
        }
    }

}
