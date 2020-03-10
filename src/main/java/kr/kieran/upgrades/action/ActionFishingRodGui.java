package kr.kieran.upgrades.action;

import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.action.confirm.ActionConfirmFishingRod;
import kr.kieran.upgrades.action.inspect.ActionInspectFishingRod;
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

import java.util.ArrayList;
import java.util.List;

public class ActionFishingRodGui extends ChestActionAbstract {

    private ItemStack item;
    private int level;

    public ActionFishingRodGui(ItemStack item)
    {
        this.item = item;
        this.level = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(7)).replaceAll("[^0-9]", ""));
    }

    @Override
    public boolean onClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        if (!ItemUtil.isItemValid(player.getItemInHand(), Tools.get().fishingRodMaterial, Tools.get().fishingRodName))
        {
            player.closeInventory();
            MixinMessage.get().msgOne(player, MConf.get().invalidFishingRod);
            return true;
        }
        // Sell wand upgrade GUI creation.
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, Txt.parse(MConf.get().inventoryName));
        ChestGui chestGui = ChestGui.getCreative(inventory);
        chestGui.setAutoclosing(true);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);
        chestGui.getInventory().setItem(0, item);
        List<String> lore = new ArrayList<>();
        for (String string : MConf.get().upgradeLore)
        {
            lore.add(Txt.parse(string.replace("%tool%", "fishing rod").replace("%levels%", "1").replace("%tokens%", String.format("%,d", LevelUtil.calculateCost(level, 1, ToolType.FISHING_ROD)))));
        }
        chestGui.getInventory().setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).data((byte) 5).name(Txt.parse(MConf.get().upgradeByOne)).setLore(lore));
        lore = new ArrayList<>();
        for (String string : MConf.get().upgradeLore)
        {
            lore.add(Txt.parse(string.replace("%tool%", "fishing rod").replace("%levels%", "10").replace("%tokens%", String.format("%,d", LevelUtil.calculateCost(level, 10, ToolType.FISHING_ROD)))));
        }
        chestGui.getInventory().setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).data((byte) 5).name(Txt.parse(MConf.get().upgradeByTen)).setLore(lore));
        chestGui.setAction(0, new ActionInspectFishingRod(item));
        chestGui.setAction(3, new ActionConfirmFishingRod(level, 1, item));
        chestGui.setAction(4, new ActionConfirmFishingRod(level, 10, item));
        InventoryUtil.fillInventory(chestGui);
        // Open the created GUI.
        player.openInventory(chestGui.getInventory());
        return true;
    }

}
