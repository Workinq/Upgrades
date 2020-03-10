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

public class ActionInspectFishingRod extends ChestActionAbstract {

    private ItemStack item;

    public ActionInspectFishingRod(ItemStack item)
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
        chestGui.getInventory().setItem(28, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &610")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(0, 10, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &7+ &65% token chance"))));
        chestGui.getInventory().setItem(29, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &620")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(10, 20, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &7+ &65% token chance"))));
        chestGui.getInventory().setItem(30, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &630")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(20, 30, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &6Fishing reward level 1"))));
        chestGui.getInventory().setItem(31, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &640")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(30, 40, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &7+ &65% token chance"))));
        chestGui.getInventory().setItem(32, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &650")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(40, 50, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &7+ &65% token chance"))));
        chestGui.getInventory().setItem(33, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &660")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(50, 60, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &7+ &65% token chance"))));
        chestGui.getInventory().setItem(34, new ItemBuilder(item.clone()).name(Txt.parse("&7Level &670")).setLore(Txt.parse(MUtil.list("&7Cost: &6" + String.format("%,d", Levels.get().getPriceByLevelRange(60, 70, ToolType.FISHING_ROD)) + " tokens", "&7Benefits:", "  &6Fishing reward level 2"))));
        chestGui.getInventory().setItem(13, this.item);
        InventoryUtil.fillInventory(chestGui);
        // Open the created GUI.
        player.openInventory(chestGui.getInventory());
        return true;
    }

}
