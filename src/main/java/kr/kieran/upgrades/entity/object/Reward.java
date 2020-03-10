package kr.kieran.upgrades.entity.object;

import com.massivecraft.massivecore.store.EntityInternal;

import java.util.List;

public class Reward extends EntityInternal<Reward> {

    private List<String> rewards;
    private double chance;

    public Reward(List<String> rewards, double chance)
    {
        this.rewards = rewards;
        this.chance = chance;
    }

    public List<String> getRewards()
    {
        return rewards;
    }

    public void setRewards(List<String> rewards)
    {
        this.rewards = rewards;
    }

    public double getChance()
    {
        return chance;
    }

    public void setChance(double chance)
    {
        this.chance = chance;
    }

}
