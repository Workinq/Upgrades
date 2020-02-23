package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.store.Coll;

public class CaneTopColl extends Coll<CaneTop> {

    private static CaneTopColl i = new CaneTopColl();

    public static CaneTopColl get() {
        return CaneTopColl.i;
    }

    @Override
    public void onTick() {
        super.onTick();
    }

    @Override
    public void setActive(boolean active) {
        try { super.setActive(active); } catch (IllegalStateException ignored) {}
        if (!active) {
            return;
        }
        CaneTop.i = get("instance", true);
    }

}
