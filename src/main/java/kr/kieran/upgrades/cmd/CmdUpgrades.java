package kr.kieran.upgrades.cmd;

import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.Perm;
import kr.kieran.upgrades.action.ActionFishingRodGui;
import kr.kieran.upgrades.action.ActionHarvesterGui;
import kr.kieran.upgrades.action.ActionSellWandGui;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.util.InventoryUtil;
import kr.kieran.upgrades.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CmdUpgrades extends MassiveCommand {

    private static CmdUpgrades i = new CmdUpgrades();

    public CmdUpgrades() {
        this.addRequirements(RequirementIsPlayer.get());
        this.addRequirements(RequirementHasPerm.get(Perm.UPGRADES));
    }

    public static CmdUpgrades get() {
        return CmdUpgrades.i;
    }

    @Override
    public List<String> getAliases() {
        return MConf.get().cmdAliases;
    }

    @Override
    public void perform() {
        if (me.getItemInHand() == null
                || (me.getItemInHand().getType() != Tools.get().fishingRodMaterial && me.getItemInHand().getType() != Tools.get().harvesterHoeMaterial && me.getItemInHand().getType() != Tools.get().sellWandMaterial)
                || !me.getItemInHand().hasItemMeta()
                || !me.getItemInHand().getItemMeta().hasDisplayName()
                || !me.getItemInHand().getItemMeta().hasLore()
                || !me.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().fishingRodName)) && !me.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().harvesterHoeName)) && !me.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().sellWandName))) {
            MixinMessage.get().msgOne(me, MConf.get().mustHoldItem);
            return;
        }
        if (me.getItemInHand().getAmount() > 1) {
            MixinMessage.get().msgOne(me, MConf.get().tooManyItems);
            return;
        }
        // Upgrade GUI creation.
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, Txt.parse(MConf.get().inventoryName));
        ChestGui chestGui = ChestGui.getCreative(inventory);
        chestGui.setAutoclosing(true);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);
        chestGui.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_HOE).name(Txt.parse(MConf.get().simpleHarvesterHoe)));
        chestGui.getInventory().setItem(2, new ItemBuilder(Material.BLAZE_ROD).name(Txt.parse(MConf.get().simpleSellWand)));
        chestGui.getInventory().setItem(4, new ItemBuilder(Material.FISHING_ROD).name(Txt.parse(MConf.get().simpleFishingRod)));
        chestGui.setAction(0, new ActionHarvesterGui(me.getItemInHand()));
        chestGui.setAction(2, new ActionSellWandGui(me.getItemInHand()));
        chestGui.setAction(4, new ActionFishingRodGui(me.getItemInHand()));
        InventoryUtil.fillInventory(chestGui);
        // Open the GUI.
        me.openInventory(chestGui.getInventory());
        me.sendMessage(Txt.parse(MConf.get().openingUpgradeGui));
    }

}
