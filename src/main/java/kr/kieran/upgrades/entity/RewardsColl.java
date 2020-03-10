package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.store.Coll;

public class RewardsColl extends Coll<Rewards> {

    private static RewardsColl i = new RewardsColl();

    public static RewardsColl get()
    {
        return RewardsColl.i;
    }

    @Override
    public void onTick()
    {
        super.onTick();
    }

    @Override
    public void setActive(boolean active)
    {
        try
        {
            super.setActive(active);
        }
        catch (IllegalStateException ignored)
        {
        }
        if (!active)
        {
            return;
        }
        Rewards.i = get("instance", true);
    }

}
