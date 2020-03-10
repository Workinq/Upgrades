package kr.kieran.upgrades.engine;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.util.ItemBuilder;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EngineFix extends Engine {

    private static EngineFix i = new EngineFix();

    public static EngineFix get()
    {
        return i;
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName())
        {
            return;
        }
        String displayName = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
        if (displayName.contains("Wand") || (displayName.contains("Hoe") && displayName.contains("Harvester")))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHoeGrass(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        if (event.getClickedBlock().getType() != Material.DIRT && event.getClickedBlock().getType() != Material.GRASS)
        {
            return;
        }
        if (event.getItem() == null || !event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName())
        {
            return;
        }
        String displayName = event.getItem().getItemMeta().getDisplayName();
        if (displayName.contains("Wand") || (displayName.contains("Hoe") && displayName.contains("Harvester")))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event)
    {
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() != Tools.get().fishingRodMaterial)
        {
            return;
        }
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().hasLore() || !player.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().fishingRodName)))
        {
            return;
        }
        if (Board.getInstance().getFactionAt(new FLocation(player.getLocation())).isWarZone())
        {
            return;
        }
        event.setCancelled(true);
        MixinMessage.get().msgOne(player, MConf.get().warzoneFishing);
    }

    @EventHandler
    public void onSellItems(ShopPreTransactionEvent event)
    {
        if (event.getShopAction() != ShopManager.ShopAction.SELL && event.getShopAction() != ShopManager.ShopAction.SELL_ALL)
        {
            return;
        }
        if (event.getShopItem().getItem() == null || event.getShopItem().getItem().getType() != Tools.get().sellWandMaterial)
        {
            return;
        }
        if (!event.getShopItem().getItem().hasItemMeta() || !event.getShopItem().getItem().getItemMeta().hasDisplayName() || !event.getShopItem().getItem().getItemMeta().hasLore() || !event.getShopItem().getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().sellWandName)))
        {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore())
        {
            return;
        }
        player.getInventory().addItem(
                new ItemBuilder(Tools.get().fishingRodMaterial).name(Txt.parse(Tools.get().fishingRodName)).setLore(Txt.parse(Tools.get().fishingRodLore)).amount(1).unbreakable(true),
                new ItemBuilder(Tools.get().harvesterHoeMaterial).name(Txt.parse(Tools.get().harvesterHoeName)).setLore(Txt.parse(Tools.get().harvesterHoeLore)).amount(1).unbreakable(true),
                new ItemBuilder(Tools.get().sellWandMaterial).name(Txt.parse(Tools.get().sellWandName)).setLore(Txt.parse(Tools.get().sellWandLore)).amount(1).unbreakable(true)
        );
    }

}
