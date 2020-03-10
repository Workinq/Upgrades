package kr.kieran.upgrades.action.confirm;

import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import com.massivecraft.massivecore.mixin.MixinMessage;
import kr.kieran.upgrades.entity.Levels;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.enums.ToolType;
import kr.kieran.upgrades.integration.EngineCoins;
import kr.kieran.upgrades.util.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ActionConfirmHarvesterHoe extends ChestActionAbstract {

    private int level;
    private int levelAmount;
    private ItemStack item;

    public ActionConfirmHarvesterHoe(int level, int levelAmount, ItemStack item)
    {
        this.level = level;
        this.levelAmount = levelAmount;
        this.item = item;
    }

    @Override
    public boolean onClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        int maxLevel = ToolType.HARVESTER_HOE.getMaxLevel();
        if (level == maxLevel)
        {
            MixinMessage.get().msgOne(player, MConf.get().maximumLevel);
            player.closeInventory();
            return false;
        }
        int newLevel = level + levelAmount;
        if (newLevel > maxLevel)
        {
            newLevel = maxLevel;
        }
        int price = 0;
        for (int i = level; i <= newLevel; i++)
        {
            price += Levels.get().getPriceByLevel(i, ToolType.HARVESTER_HOE);
        }
        if (EngineCoins.get().canAfford(player, price))
        {
            EngineCoins.get().withdraw(player, price);
            LevelUtil.increaseHarvesterHoe(item, newLevel);
            MixinMessage.get().msgOne(player, MConf.get().toolUpgraded, levelAmount, price);
            player.closeInventory();
        }
        else
        {
            MixinMessage.get().msgOne(player, MConf.get().cannotAfford, price);
            return false;
        }
        return true;
    }

}
