package kr.kieran.upgrades.action.confirm;

import com.massivecraft.massivecore.chestgui.ChestActionAbstract;
import com.massivecraft.massivecore.mixin.MixinMessage;
import kr.kieran.stoneagetokens.api.TokensAPI;
import kr.kieran.upgrades.entity.Levels;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.enums.ToolType;
import kr.kieran.upgrades.util.LevelUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ActionConfirmSellWand extends ChestActionAbstract {

    private int level;
    private int levelAmount;
    private ItemStack item;

    public ActionConfirmSellWand(int level, int levelAmount, ItemStack item) {
        this.level = level;
        this.levelAmount = levelAmount;
        this.item = item;
    }

    @Override
    public boolean onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (level == Levels.get().maxSellWandLevel) {
            MixinMessage.get().msgOne(player, MConf.get().maximumLevel);
            player.closeInventory();
            return false;
        }
        int newLevel = level + levelAmount;
        if (newLevel > Levels.get().maxSellWandLevel) newLevel = Levels.get().maxSellWandLevel;
        int price = 0;
        for (int i = level; i <= newLevel; i++) price += Levels.get().getPriceByLevel(i, ToolType.SELL_WAND);
        if (TokensAPI.canAffordPlayer(player, price)) {
            TokensAPI.removePlayer(player, price);
            LevelUtil.increaseSellWand(item, newLevel);
            MixinMessage.get().msgOne(player, MConf.get().toolUpgraded, levelAmount, price);
            player.closeInventory();
        } else {
            MixinMessage.get().msgOne(player, MConf.get().cannotAfford, price);
            return false;
        }
        return true;
    }

}
