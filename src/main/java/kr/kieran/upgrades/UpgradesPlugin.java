package kr.kieran.upgrades;

import com.massivecraft.massivecore.MassivePlugin;
import kr.kieran.upgrades.entity.MConf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpgradesPlugin extends MassivePlugin {

    private static UpgradesPlugin i;
    private Map<UUID, Long> lightningWandCooldown;

    public UpgradesPlugin() {
        UpgradesPlugin.i = this;
        lightningWandCooldown = new HashMap<>();
    }

    public static UpgradesPlugin get() {
        return i;
    }

    public boolean hasLightningWandCooldown(UUID uuid) {
        if (!lightningWandCooldown.containsKey(uuid)) return false;
        long value = lightningWandCooldown.get(uuid);
        return value > System.currentTimeMillis();
    }

    public void addLightningWandCooldown(UUID uuid) {
        long value = System.currentTimeMillis() + MConf.get().lightningWandCooldownTime * 1000L;
        lightningWandCooldown.put(uuid, value);
    }

    public long getMillisecondsLeft(UUID uuid) {
        if (!lightningWandCooldown.containsKey(uuid)) return -1L;
        return lightningWandCooldown.get(uuid) - System.currentTimeMillis();
    }

    @Override
    public boolean isVersionSynchronized() {
        return false;
    }

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }

}
