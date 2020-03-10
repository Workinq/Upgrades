package kr.kieran.upgrades.action.inspect;

import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.entity.Levels;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.enums.ToolType;
import kr.kieran.upgrades.util.InventoryUtil;
import kr.kieran.upgrades.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ActionInspectSellWand extends ChestActionAbstract {

    private ItemStack item;

    public ActionInspectSellWand(ItemStack item)
    {
        this.item = item;
    }

    @Override
    public boolean onClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = Bukkit.createInventory(null, 45, Txt.parse(MConf.get().inventoryName));
        ChestGui chestGui = ChestGui.getCreative(inventory);
        chestGui.setAutoclosing(true);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);
        ItemStack item = new ItemBuilder(Material.STAINED_GLASS_PANE).data((byte) 5);
        chestGui.getInventory().setItem(29, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &610")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(0, 10, ToolType.SELL_WAND)) + " tokens", "&7Benefits:", "  &7+ &65% sell boost"))));
        chestGui.getInventory().setItem(30, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &620")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(10, 20, ToolType.SELL_WAND)) + " tokens", "&7Benefits:", "  &7+ &65% sell boost"))));
        chestGui.getInventory().setItem(31, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &630")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(20, 30, ToolType.SELL_WAND)) + " tokens", "&7Benefits:", "  &7+ &65% sell boost"))));
        chestGui.getInventory().setItem(32, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &640")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(30, 40, ToolType.SELL_WAND)) + " tokens", "&7Benefits:", "  &7+ &65% sell boost"))));
        chestGui.getInventory().setItem(33, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &650")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(40, 50, ToolType.SELL_WAND)) + " tokens", "&7Benefits:", "  &7+ &65% sell boost"))));
        chestGui.getInventory().setItem(13, this.item);
        InventoryUtil.fillInventory(chestGui);
        // Open the created GUI.
        player.openInventory(chestGui.getInventory());
        return true;
    }

}
