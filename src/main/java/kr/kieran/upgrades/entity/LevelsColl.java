package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.store.Coll;

public class LevelsColl extends Coll<Levels> {

    private static LevelsColl i = new LevelsColl();

    public static LevelsColl get()
    {
        return LevelsColl.i;
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
        Levels.i = get("instance", true);
    }

}
