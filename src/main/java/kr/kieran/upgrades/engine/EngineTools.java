package kr.kieran.upgrades.engine;

import com.gmail.nossr50.api.ExperienceAPI;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.stoneagetokens.api.TokensAPI;
import kr.kieran.stoneagetokens.event.EventTokensChange;
import kr.kieran.upgrades.UpgradesPlugin;
import kr.kieran.upgrades.entity.CaneTop;
import kr.kieran.upgrades.entity.Rewards;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.entity.object.Reward;
import kr.kieran.upgrades.integration.EngineVault;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
    private final static IBlockData AIR = Blocks.AIR.getBlockData();

    private static IBlockData get(String var0) {
        return net.minecraft.server.v1_8_R3.Block.REGISTRY.get(new MinecraftKey(var0)).getBlockData();
    }

    public static EngineTools get() {
        return i;
    }

    @EventHandler
    public void onUseFishingRod(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Player player = event.getPlayer();
        if (player.getItemInHand() == null) return;
        if (player.getItemInHand().getType() != Tools.get().fishingRodMaterial) return;
        if (!player.getItemInHand().hasItemMeta()) return;
        if (!player.getItemInHand().getItemMeta().hasDisplayName()) return;
        if (!player.getItemInHand().getItemMeta().hasLore()) return;
        if (!player.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().fishingRodName))) return;
        int rewardLevel = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(5)).replaceAll("[^0-9]", ""));
        int tokenChance = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", ""));
        handleRewards(player, rewardLevel);
        handleTokens(player, tokenChance);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakCane(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.SUGAR_CANE_BLOCK) return;
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Tools.get().harvesterHoeMaterial) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;
        if (!event.getItem().getItemMeta().hasLore()) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().harvesterHoeName))) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        int explosiveLevel = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(5)).replaceAll("[^0-9]", ""));
        int tokenChance = Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", ""));
        explosive(event.getClickedBlock(), player, explosiveLevel, tokenChance);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMineTray(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Tools.get().trayPickaxeMaterial) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;
        if (!event.getItem().getItemMeta().hasLore()) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().trayPickaxeName))) return;
        event.setCancelled(true);
        Location location = event.getClickedBlock().getLocation();
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) location.getWorld()).getHandle();
        int radius = Tools.get().trayPickaxeRadius;
        int minX = location.getBlockX() - radius / 2;
        int minZ = location.getBlockZ() - radius / 2;
        for (int x = minX; x < minX + radius; x++) {
            for (int z = minZ; z < minZ + radius; z++) {
                Block block = event.getClickedBlock().getWorld().getBlockAt(x, event.getClickedBlock().getY(), z);
                if (block.getType() != Material.DIRT && block.getType() != Material.NETHERRACK) continue;
                Faction at = BoardColl.get().getFactionAt(PS.valueOf(block));
                Faction mine = MPlayer.get(player).getFaction();
                if (!at.getId().equals(mine.getId()) && !at.isNone()) continue;
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMineTrench(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Tools.get().trenchPickaxeMaterial) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;
        if (!event.getItem().getItemMeta().hasLore()) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().trenchPickaxeName))) return;
        event.setCancelled(true);
        Location location = event.getClickedBlock().getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        int radius = Integer.parseInt(ChatColor.stripColor(event.getItem().getItemMeta().getLore().get(0)).replaceAll("[^0-9]", ""));
        int minX = location.getBlockX() - radius / 2, minY = location.getBlockY() - radius / 2, minZ = location.getBlockZ() - radius / 2;
        for (int x = minX; x < minX + radius; x++) {
            for (int y = minY; y < minY + radius; y++) {
                for (int z = minZ; z < minZ + radius; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR) continue;
                    if (Tools.get().trenchBlacklistedBlocks.contains(block.getType().name())) continue;
                    Faction at = BoardColl.get().getFactionAt(PS.valueOf(block));
                    Faction mine = MPlayerColl.get().get(player).getFaction();
                    if (!at.getId().equals(mine.getId()) && !at.isNone()) continue;
                    breakBlock(world, block.getLocation());
                }
            }
        }
    }

    private void breakBlock(World world, Location location) {
        world.setTypeAndData(new BlockPosition(location.getX(), location.getY(), location.getZ()), AIR, 2);
    }

    private void handleTokens(Player player, int tokenChance) {
        if (ThreadLocalRandom.current().nextInt(100) > tokenChance) return;
        EventTokensChange event = new EventTokensChange(player, ThreadLocalRandom.current().nextInt(1, 3));
        UpgradesPlugin.get().getServer().getPluginManager().callEvent(event);
        TokensAPI.addPlayer(player, event.getAmount());
    }

    private void handleRewards(Player player, int rewardLevel) {
        for (Reward reward : Rewards.get().getRewardsByLevel(rewardLevel)) {
            if (ThreadLocalRandom.current().nextDouble() > reward.getChance()) continue;
            for (String command : reward.getRewards()) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
            }
            return;
        }
    }

    private void explosive(Block start, Player player, int radius, int tokenChance) {
        if (radius > 0) player.getWorld().playEffect(start.getLocation(), Effect.EXPLOSION_LARGE, 1);
        double total = 0.0;
        for (int x = start.getLocation().getBlockX() - radius; x <= start.getLocation().getBlockX() + radius; x++) {
            for (int z = start.getLocation().getBlockZ() - radius; z <= start.getLocation().getBlockZ() + radius; z++) {
                Location location = new Location(start.getWorld(), x, start.getLocation().getBlockY(), z);
                Faction at = BoardColl.get().getFactionAt(PS.valueOf(location.getBlock()));
                Faction mine = MPlayer.get(player).getFaction();
                if (!at.getId().equals(mine.getId()) && !at.isNone()) continue;
                total += handleCane(start, location, player, tokenChance);
            }
        }
        if (total > 0.0) sendActionBar(player, Txt.parse("&6&l+ $" + total));
    }

    private double handleCane(Block block, Location blockLocation, Player player, int tokenChance) {
        double totalMoney = 0;
        Location location;
        for (location = blockLocation; location.getBlock().getType() == Material.SUGAR_CANE_BLOCK; location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() + 1), location.getBlockZ())) {
        }
        World world = ((CraftWorld) location.getWorld()).getHandle();
        for (location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()); location.getBlockY() >= block.getY(); location = new Location(location.getWorld(), location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ())) {
            if (location.getBlock().getType() != Material.SUGAR_CANE_BLOCK) continue;
            breakBlock(world, location);
            double sellPrice = ShopGuiPlusApi.getItemStackPriceSell(player, new ItemStack(Material.SUGAR_CANE));
            EngineVault.get().getEconomy().depositPlayer(player, sellPrice);
            ExperienceAPI.addXP(player, "Herbalism", 30, "UNKNOWN", false);
            CaneTop.get().increaseStats(player.getUniqueId(), 1);
            handleTokens(player, tokenChance);
            totalMoney += sellPrice;
        }
        return totalMoney;
    }

    private void sendActionBar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
