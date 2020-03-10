package kr.kieran.upgrades.util;

import com.massivecraft.massivecore.util.Txt;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static boolean isItemValid(ItemStack item, Material material, String name)
    {
        if (item == null || item.getType() != material)
        {
            return false;
        }
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore())
        {
            return false;
        }
        return item.getItemMeta().getDisplayName().equals(Txt.parse(name));
    }

}
