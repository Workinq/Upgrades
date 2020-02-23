package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.store.Coll;

public class ToolsColl extends Coll<Tools> {

    private static ToolsColl i = new ToolsColl();

    public static ToolsColl get() {
        return ToolsColl.i;
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
        Tools.i = get("instance", true);
    }

}
