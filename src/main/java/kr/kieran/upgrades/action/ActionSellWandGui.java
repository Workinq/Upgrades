package kr.kieran.upgrades.action;

import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.action.confirm.ActionConfirmSellWand;
import kr.kieran.upgrades.action.inspect.ActionInspectSellWand;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.enums.ToolType;
import kr.kieran.upgrades.util.InventoryUtil;
import kr.kieran.upgrades.util.ItemBuilder;
import kr.kieran.upgrades.util.ItemUtil;
import kr.kieran.upgrades.util.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ActionSellWandGui extends ChestActionAbstract {

    private ItemStack item;
    private int level;

    public ActionSellWandGui(ItemStack item) {
        this.item = item;
        this.level = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(7)).replaceAll("[^0-9]", ""));
    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!ItemUtil.isItemValid(player.getItemInHand(), Tools.get().sellWandMaterial, Tools.get().sellWandName)) {
            player.closeInventory();
            MixinMessage.get().msgOne(player, MConf.get().invalidSellWand);
            return false;
        }
        // Sell wand upgrade GUI creation.
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, Txt.parse(MConf.get().inventoryName));
        ChestGui chestGui = ChestGui.getCreative(inventory);
        chestGui.setAutoclosing(true);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);
        chestGui.getInventory().setItem(0, item);
        chestGui.getInventory().setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).data((byte) 5).name(Txt.parse("&7Increase level by &61")).setLore(MUtil.list(" ", Txt.parse("&7Level up your sell wand"), Txt.parse("&7by 1 level, costing: &6" + String.format("%,d", LevelUtil.calculateCost(level, 1, ToolType.SELL_WAND)) + " tokens&7."))));
        chestGui.getInventory().setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).data((byte) 5).name(Txt.parse("&7Increase level by &610")).setLore(MUtil.list(" ", Txt.parse("&7Level up your sell wand"), Txt.parse("&7by 10 levels, costing: &6" + String.format("%,d", LevelUtil.calculateCost(level, 10, ToolType.SELL_WAND)) + " tokens&7."))));
        chestGui.setAction(0, new ActionInspectSellWand(item));
        chestGui.setAction(3, new ActionConfirmSellWand(level, 1, item));
        chestGui.setAction(4, new ActionConfirmSellWand(level, 10, item));
        InventoryUtil.fillInventory(chestGui);
        // Open the created GUI.
        player.openInventory(chestGui.getInventory());
        return true;
    }

}
