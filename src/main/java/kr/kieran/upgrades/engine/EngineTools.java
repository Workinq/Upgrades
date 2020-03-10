package kr.kieran.upgrades.engine;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.convictcoins.event.EventCoinsChange;
import kr.kieran.upgrades.UpgradesPlugin;
import kr.kieran.upgrades.entity.CaneTop;
import kr.kieran.upgrades.entity.Rewards;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.entity.object.Reward;
import kr.kieran.upgrades.integration.EngineCoins;
import kr.kieran.upgrades.integration.EngineVault;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EngineTools extends Engine {

    private static EngineTools i = new EngineTools();

    public static EngineTools get()
    {
        return i;
    }

    @EventHandler
    public void onUseFishingRod(PlayerFishEvent event)
    {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH)
        {
            return;
        }
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() != Tools.get().fishingRodMaterial)
        {
            return;
        }
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().hasLore() || !player.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().fishingRodName)))
        {
            return;
        }
        int rewardLevel = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(5)).replaceAll("[^0-9]", ""));
        int tokenChance = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", ""));
        handleRewards(player, rewardLevel);
        handleTokens(player, tokenChance);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakCane(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.SUGAR_CANE_BLOCK)
        {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Tools.get().harvesterHoeMaterial)
        {
            return;
        }
        if (!event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() || !event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().harvesterHoeName)))
        {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        int explosiveLevel = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(5)).replaceAll("[^0-9]", ""));
        int tokenChance = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", ""));
        explosive(event.getClickedBlock(), player, explosiveLevel, tokenChance);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMineTray(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Tools.get().trayPickaxeMaterial)
        {
            return;
        }
        if (!event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() || !event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().trayPickaxeName)))
        {
            return;
        }
        event.setCancelled(true);
        Location location = event.getClickedBlock().getLocation();
        int radius = Tools.get().trayPickaxeRadius;
        int minX = location.getBlockX() - radius / 2;
        int minZ = location.getBlockZ() - radius / 2;
        for (int x = minX; x < minX + radius; x++)
        {
            for (int z = minZ; z < minZ + radius; z++)
            {
                Block block = event.getClickedBlock().getWorld().getBlockAt(x, event.getClickedBlock().getY(), z);
                if (block.getType() != Material.DIRT && block.getType() != Material.NETHERRACK)
                {
                    continue;
                }
                if (!canBreak(FPlayers.getInstance().getByPlayer(event.getPlayer()).getFaction(), block))
                {
                    continue;
                }
                block.setTypeIdAndData(Material.AIR.getId(), (byte) 0 , true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMineTrench(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Tools.get().trenchPickaxeMaterial)
        {
            return;
        }
        if (!event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() || !event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().trenchPickaxeName)))
        {
            return;
        }
        event.setCancelled(true);
        Location location = event.getClickedBlock().getLocation();
        int radius = Integer.parseInt(ChatColor.stripColor(event.getItem().getItemMeta().getLore().get(0)).replaceAll("[^0-9]", ""));
        int minX = location.getBlockX() - radius / 2, minY = location.getBlockY() - radius / 2, minZ = location.getBlockZ() - radius / 2;
        for (int x = minX; x < minX + radius; x++)
        {
            for (int y = minY; y < minY + radius; y++)
            {
                for (int z = minZ; z < minZ + radius; z++)
                {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR)
                    {
                        continue;
                    }
                    if (Tools.get().trenchBlacklistedBlocks.contains(block.getType().name()))
                    {
                        continue;
                    }
                    if (!canBreak(FPlayers.getInstance().getByPlayer(event.getPlayer()).getFaction(), block))
                    {
                        continue;
                    }
                    block.setTypeIdAndData(Material.AIR.getId(), (byte) 0 , false);
                }
            }
        }
    }

    private boolean canBreak(Faction faction, Block block)
    {
        Faction at = Board.getInstance().getFactionAt(new FLocation(block));
        return at.getId().equals(faction.getId()) || at.isNone();
    }

    private void handleTokens(Player player, int tokenChance)
    {
        if (ThreadLocalRandom.current().nextInt(100) > tokenChance)
        {
            return;
        }
        double coinsBalance = EngineCoins.get().getBalance(player);
        EventCoinsChange event = new EventCoinsChange(player, coinsBalance, coinsBalance + ThreadLocalRandom.current().nextInt(1, 3));
        UpgradesPlugin.get().getServer().getPluginManager().callEvent(event);
        if (event.isCancelled())
        {
            return;
        }
        EngineCoins.get().add(player, event.getAfter());
    }

    private void handleRewards(Player player, int rewardLevel)
    {
        for (Reward reward : Rewards.get().getRewardsByLevel(rewardLevel))
        {
            if (ThreadLocalRandom.current().nextDouble() > reward.getChance())
            {
                continue;
            }
            for (String command : reward.getRewards())
            {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
            }
            return;
        }
    }

    private void explosive(Block start, Player player, int radius, int tokenChance)
    {
        if (radius > 0) player.getWorld().playEffect(start.getLocation(), Effect.EXPLOSION_LARGE, 1);
        for (int x = start.getLocation().getBlockX() - radius; x <= start.getLocation().getBlockX() + radius; x++)
        {
            for (int z = start.getLocation().getBlockZ() - radius; z <= start.getLocation().getBlockZ() + radius; z++)
            {
                Location location = new Location(start.getWorld(), x, start.getLocation().getBlockY(), z);
                if (!canBreak(FPlayers.getInstance().getByPlayer(player).getFaction(), location.getBlock()))
                {
                    continue;
                }
                handleCane(start, location, player, tokenChance);
            }
        }
    }

    private void handleCane(Block block, Location blockLocation, Player player, int tokenChance)
    {
        Location location = blockLocation;
        while (location.getBlock().getType() == Material.SUGAR_CANE_BLOCK)
        {
            location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() + 1), location.getBlockZ());
        }
        for (location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()); location.getBlockY() >= block.getY(); location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()))
        {
            if (location.getBlock().getType() != Material.SUGAR_CANE_BLOCK)
            {
                continue;
            }
            block.setTypeIdAndData(Material.AIR.getId(), (byte) 0 , false);
            double sellPrice = ShopGuiPlusApi.getItemStackPriceSell(player, new ItemStack(Material.SUGAR_CANE));
            EngineVault.get().getEconomy().depositPlayer(player, sellPrice);
//            ExperienceAPI.addXP(player, "Herbalism", 30, "UNKNOWN", false);
            CaneTop.get().increaseStats(player.getUniqueId(), 1);
            handleTokens(player, tokenChance);
        }
    }

}
