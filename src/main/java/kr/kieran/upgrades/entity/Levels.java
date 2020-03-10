package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import kr.kieran.upgrades.enums.ToolType;

import java.util.List;
import java.util.Map;

@EditorName("config")
public class Levels extends Entity<Levels> {

    protected static transient Levels i;

    // Prices
    public List<Map<Integer, Integer>> fishingRodPrices;
    public List<Map<Integer, Integer>> harvesterHoePrices;
    public List<Map<Integer, Integer>> sellWandPrices;

    // Max levels
    public int maxFishingRodLevel;
    public int maxHarvesterHoeLevel;
    public int maxSellWandLevel;

    public Levels()
    {
        this.fishingRodPrices = new MassiveList<>();
        this.harvesterHoePrices = new MassiveList<>();
        this.sellWandPrices = new MassiveList<>();
        this.maxFishingRodLevel = 70;
        this.maxHarvesterHoeLevel = 70;
        this.maxSellWandLevel = 50;
        initLevels();
    }

    public static Levels get()
    {
        return Levels.i;
    }

    private void initLevels()
    {
        for (int i = 0; i <= 70; i++)
        {
            fishingRodPrices.add(MUtil.map(i, i * 10000));
            harvesterHoePrices.add(MUtil.map(i, i * 10000));
        }
        for (int i = 0; i <= 50; i++)
        {
            sellWandPrices.add(MUtil.map(i, i * 10000));
        }
        this.changed();
    }

    public int getPriceByLevel(int level, ToolType type)
    {
        switch (type)
        {
            case FISHING_ROD:
                for (Map<Integer, Integer> fishingRodPrice : fishingRodPrices)
                {
                    for (Integer lvl : fishingRodPrice.keySet())
                    {
                        if (level == lvl)
                        {
                            return fishingRodPrice.get(lvl);
                        }
                    }
                }
                break;
            case HARVESTER_HOE:
                for (Map<Integer, Integer> harvesterHoePrice : harvesterHoePrices)
                {
                    for (Integer lvl : harvesterHoePrice.keySet())
                    {
                        if (level == lvl)
                        {
                            return harvesterHoePrice.get(lvl);
                        }
                    }
                }
                break;
            case SELL_WAND:
                for (Map<Integer, Integer> sellWandPrice : sellWandPrices)
                {
                    for (Integer lvl : sellWandPrice.keySet())
                    {
                        if (level == lvl)
                        {
                            return sellWandPrice.get(lvl);
                        }
                    }
                }
                break;
        }
        return -1;
    }

    public int getPriceByLevelRange(int from, int to, ToolType type)
    {
        int total = 0;
        for (int i = from; i <= to; i++)
        {
            total += getPriceByLevel(i, type);
        }
        return total;
    }

    public int getMaxLevel(ToolType type)
    {
        switch (type)
        {
            case FISHING_ROD:
                return maxFishingRodLevel;
            case HARVESTER_HOE:
                return maxHarvesterHoeLevel;
            case SELL_WAND:
                return maxSellWandLevel;
            default:
                return -1;
        }
    }

    @Override
    public Levels load(Levels that)
    {
        super.load(that);
        return this;
    }

}
