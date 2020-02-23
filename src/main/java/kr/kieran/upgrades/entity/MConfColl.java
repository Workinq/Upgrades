package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.store.Coll;

public class MConfColl extends Coll<MConf> {

    private static MConfColl i = new MConfColl();

    public static MConfColl get() {
        return MConfColl.i;
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
        MConf.i = get("instance", true);
    }

}
