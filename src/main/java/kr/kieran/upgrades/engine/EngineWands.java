package kr.kieran.upgrades.engine;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.UpgradesPlugin;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.integration.EngineVault;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

public class EngineWands extends Engine {

    private static EngineWands i = new EngineWands();

    public static EngineWands get()
    {
        return i;
    }

    @EventHandler
    public void onUseCraftWand(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK)
        {
            return;
        }
        if (event.getClickedBlock().getType() != Material.CHEST && event.getClickedBlock().getType() != Material.TRAPPED_CHEST)
        {
            return;
        }
        if (event.getItem() == null || event.getItem().getType() != Tools.get().craftWandMaterial)
        {
            return;
        }
        if (!event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() || !event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().craftWandName)))
        {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (!Board.getInstance().getFactionAt(new FLocation(block.getLocation())).getId().equals(FPlayers.getInstance().getByPlayer(player).getId()))
        {
            MixinMessage.get().msgOne(player, MConf.get().notAllowedHere);
            return;
        }
        if (!(block.getState() instanceof Chest))
        {
            return;
        }
        Chest chest = (Chest) block.getState();
        if (isInventoryEmpty(chest.getInventory()))
        {
            MixinMessage.get().msgOne(player, MConf.get().emptyChest);
            return;
        }
        try
        {
            int diamonds = 0, emeralds = 0, iron = 0, gold = 0, goldNugget = 0, glowstone = 0, coal = 0, redstone = 0, lapis = 0;
            int itemsChanged;
            for (ItemStack item : chest.getInventory().getContents())
            {
                if (item == null)
                {
                    continue;
                }
                if (item.getType() == Material.DIAMOND)
                {
                    chest.getInventory().remove(item);
                    diamonds += item.getAmount();
                }
                if (item.getType() == Material.EMERALD)
                {
                    emeralds += item.getAmount();
                    chest.getInventory().remove(item);
                }
                if (item.getType() == Material.IRON_INGOT)
                {
                    chest.getInventory().remove(item);
                    iron += item.getAmount();
                }
                if (item.getType() == Material.GLOWSTONE_DUST)
                {
                    glowstone += item.getAmount();
                    chest.getInventory().remove(item);
                }
                if (item.getType() == Material.GOLD_INGOT)
                {
                    chest.getInventory().remove(item);
                    gold += item.getAmount();
                }
                if (item.getType() == Material.GOLD_NUGGET)
                {
                    chest.getInventory().remove(item);
                    goldNugget += item.getAmount();
                }
                if (item.getType() == Material.COAL)
                {
                    chest.getInventory().remove(item);
                    coal += item.getAmount();
                }
                if (item.getType() == Material.REDSTONE)
                {
                    redstone += item.getAmount();
                    chest.getInventory().remove(item);
                }
                if (item.getType() == Material.INK_SACK && ((Dye) item.getData()).getColor() == DyeColor.BLUE)
                {
                    chest.getInventory().remove(item);
                    lapis += item.getAmount();
                }
            }
            chest.update();
            itemsChanged = diamonds + emeralds + glowstone + gold + goldNugget + iron + coal + redstone + lapis;
            int diamondBlocks = diamonds / 9, diamondOverflow = diamonds % 9;
            int emeraldBlocks = emeralds / 9, emeraldOverflow = emeralds % 9;
            int ironBlocks = iron / 9, ironOverflow = iron % 9;
            int goldBlocks = gold / 9, goldOverflow = gold % 9;
            int goldIngots = goldNugget / 9, nuggetOverflow = goldNugget % 9;
            int glowstoneBlocks = glowstone / 9, glowstoneOverflow = glowstone % 9;
            int redstoneBlocks = redstone / 9, redstoneOverflow = redstone % 9;
            int lapisBlocks = lapis / 9, lapisOverflow = lapis % 9;
            int coalBlocks = coal / 9, coalOverflow = coal % 9;
            itemsChanged -= diamondOverflow + emeraldOverflow + ironOverflow + goldOverflow + nuggetOverflow + glowstoneOverflow + redstoneOverflow + coalOverflow + lapisOverflow;
            chest.getInventory().addItem(
                    new ItemStack((diamondBlocks > 0) ? Material.DIAMOND_BLOCK : Material.AIR, diamondBlocks),
                    new ItemStack((diamondOverflow > 0) ? Material.DIAMOND : Material.AIR, diamondOverflow),
                    new ItemStack((emeraldBlocks > 0) ? Material.EMERALD_BLOCK : Material.AIR, emeraldBlocks),
                    new ItemStack((emeraldOverflow > 0) ? Material.EMERALD : Material.AIR, emeraldOverflow),
                    new ItemStack((ironBlocks > 0) ? Material.IRON_BLOCK : Material.AIR, ironBlocks),
                    new ItemStack((ironOverflow > 0) ? Material.IRON_INGOT : Material.AIR, ironOverflow),
                    new ItemStack((goldBlocks > 0) ? Material.GOLD_BLOCK : Material.AIR, goldBlocks),
                    new ItemStack((goldOverflow > 0) ? Material.GOLD_INGOT : Material.AIR, goldOverflow),
                    new ItemStack((goldIngots > 0) ? Material.GOLD_INGOT : Material.AIR, goldIngots),
                    new ItemStack((nuggetOverflow > 0) ? Material.GOLD_NUGGET : Material.AIR, nuggetOverflow),
                    new ItemStack((glowstoneBlocks > 0) ? Material.GLOWSTONE : Material.AIR, glowstoneBlocks),
                    new ItemStack((glowstoneOverflow > 0) ? Material.GLOWSTONE_DUST : Material.AIR, glowstoneOverflow),
                    new ItemStack((redstoneBlocks > 0) ? Material.REDSTONE_BLOCK : Material.AIR, redstoneBlocks),
                    new ItemStack((redstoneOverflow > 0) ? Material.REDSTONE : Material.AIR, redstoneOverflow),
                    new ItemStack((lapisBlocks > 0) ? Material.LAPIS_BLOCK : Material.AIR, lapisBlocks),
                    new ItemStack((lapisOverflow > 0) ? Material.INK_SACK : Material.AIR, lapisOverflow, (byte) 4),
                    new ItemStack((coalBlocks > 0) ? Material.COAL_BLOCK : Material.AIR, coalBlocks),
                    new ItemStack((coalOverflow > 0) ? Material.COAL : Material.AIR, coalOverflow));
            MixinMessage.get().msgOne(player, MConf.get().craftedItems, itemsChanged, itemsChanged / 9);
            chest.update();
        }
        catch (Exception ignored)
        {
        }
    }

    @EventHandler
    public void onUseLightningWand(PlayerInteractAtEntityEvent event)
    {
        if (!(event.getRightClicked() instanceof Creeper))
        {
            return;
        }
        Player player = event.getPlayer();
        if (player.getItemInHand() == null || player.getItemInHand().getType() != Tools.get().lightningWandMaterial)
        {
            return;
        }
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().hasLore() || !player.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().lightningWandName)))
        {
            return;
        }
        event.setCancelled(true);
        Creeper creeper = (Creeper) event.getRightClicked();
        if (UpgradesPlugin.get().hasLightningWandCooldown(player.getUniqueId()))
        {
            MixinMessage.get().msgOne(player, MConf.get().lightningWandCooldown, new DecimalFormat("0.0").format(UpgradesPlugin.get().getMillisecondsLeft(player.getUniqueId()) / 1000.0));
        }
        else
        {
            UpgradesPlugin.get().addLightningWandCooldown(player.getUniqueId());
            creeper.setPowered(true);
        }
    }

    @EventHandler
    public void onUseSandWand(PlayerInteractEvent event)
    {
        if (event.getClickedBlock() == null)
        {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() != Tools.get().sandWandMaterial)
        {
            return;
        }
        if (!event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() || !event.getItem().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().sandWandName)))
        {
            return;
        }
        event.setCancelled(true);
        Block clickedBlock = event.getClickedBlock();
        if (!Board.getInstance().getFactionAt(new FLocation(clickedBlock)).getId().equals(FPlayers.getInstance().getByPlayer(player).getFaction().getId()))
        {
            MixinMessage.get().msgOne(player, MConf.get().notAllowedHere);
            return;
        }
        List<Block> blocks = new LinkedList<>();
        for (int y = clickedBlock.getLocation().getBlockY(); y > 0; y--)
        {
            Block current = new Location(clickedBlock.getWorld(), clickedBlock.getX(), y, clickedBlock.getZ()).getBlock();
            if (current.getType() != Material.SAND)
            {
                break;
            }
            blocks.add(current);
        }
        if (blocks.isEmpty())
        {
            return;
        }
        if (EngineVault.get().getEconomy().getBalance(player) - MConf.get().sandWandCost < 0)
        {
            MixinMessage.get().msgOne(player, MConf.get().notEnoughMoney, MConf.get().sandWandCost - EngineVault.get().getEconomy().getBalance(player));
        }
        EngineVault.get().getEconomy().withdrawPlayer(player, MConf.get().sandWandCost);
        MixinMessage.get().msgOne(player, MConf.get().sandWandCharged, MConf.get().sandWandCost);
        new BukkitRunnable()
        {
            Iterator<Block> iterator = blocks.iterator();

            @Override
            public void run()
            {
                if (!iterator.hasNext())
                {
                    cancel();
                    return;
                }
                iterator.next().setType(Material.AIR);
                iterator.remove();
            }
        }.runTaskTimer(UpgradesPlugin.get(), 0L, 2L);
    }

    @EventHandler
    public void onSellChest(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }
        Player player = event.getPlayer();
        if (player.getItemInHand() == null)
        {
            return;
        }
        if (player.getItemInHand().getType() != Tools.get().sellWandMaterial)
        {
            return;
        }
        if (!player.getItemInHand().hasItemMeta())
        {
            return;
        }
        if (!player.getItemInHand().getItemMeta().hasDisplayName())
        {
            return;
        }
        if (!player.getItemInHand().getItemMeta().hasLore())
        {
            return;
        }
        if (!player.getItemInHand().getItemMeta().getDisplayName().equals(Txt.parse(Tools.get().sellWandName)))
        {
            return;
        }
        Block clicked = event.getClickedBlock();
        if (clicked.getType() != Material.CHEST && clicked.getType() != Material.TRAPPED_CHEST)
        {
            return;
        }
        event.setCancelled(true);
        if (!Board.getInstance().getFactionAt(new FLocation(clicked.getLocation())).getId().equals(FPlayers.getInstance().getByPlayer(player).getFaction().getId()))
        {
            MixinMessage.get().msgOne(player, MConf.get().notAllowedHere);
            return;
        }
        Chest chest = (Chest) clicked.getState();
        Inventory inventory = chest.getInventory();
        if (isInventoryEmpty(inventory))
        {
            MixinMessage.get().msgOne(player, MConf.get().emptyChest);
            return;
        }
        Map<ItemStack, Integer> sellItems = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++)
        {
            ItemStack item = inventory.getItem(i);
            if (item == null)
            {
                continue;
            }
            if (ShopGuiPlusApi.getItemStackPriceSell(player, item) == -1.0)
            {
                continue;
            }
            sellItems.putIfAbsent(item, 0);
            sellItems.put(item, sellItems.get(item) + item.getAmount());
        }
        if (sellItems.isEmpty())
        {
            MixinMessage.get().msgOne(player, MConf.get().noItemsToSell);
            return;
        }
        double totalMoney = 0;
        int totalItems = 0;
        double sellMultiplier;
        if (Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", "")) == 100)
        {
            sellMultiplier = 1.0D;
        }
        else
        {
            sellMultiplier = Double.parseDouble("0." + Integer.parseInt(ChatColor.stripColor(player.getItemInHand().getItemMeta().getLore().get(6)).replaceAll("[^0-9]", "")));
        }
        for (Map.Entry<ItemStack, Integer> map : sellItems.entrySet())
        {
            ItemStack itemToDelete = map.getKey().clone();
            itemToDelete.setAmount(map.getValue());
            double money = ShopGuiPlusApi.getItemStackPriceSell(player, itemToDelete) * sellMultiplier;
            totalMoney += money;
            totalItems += map.getValue();
            EngineVault.get().getEconomy().depositPlayer(player, money);
            inventory.removeItem(itemToDelete);
        }
        handleStats(player.getItemInHand(), totalMoney);
        MixinMessage.get().msgOne(player, MConf.get().soldItems, totalItems, totalMoney);
        sellItems.clear();
    }

    private void handleStats(ItemStack item, double sold)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        double totalValue = Double.parseDouble(ChatColor.stripColor(lore.get(5)).replaceAll("\\.(.*)", "").replaceAll("[^0-9]", ""));
        lore.set(5, Txt.parse("&7Total value sold: &6$" + String.format("%,.2f", (totalValue + sold))));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private boolean isInventoryEmpty(Inventory inventory)
    {
        for (ItemStack item : inventory.getContents())
        {
            if (item != null)
            {
                return false;
            }
        }
        return true;
    }

}
