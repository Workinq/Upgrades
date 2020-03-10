package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.collections.MassiveMapDef;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import kr.kieran.upgrades.entity.object.Reward;

import java.util.ArrayList;
import java.util.List;

@EditorName("config")
public class Rewards extends Entity<Rewards> {

    protected static transient Rewards i;
    public MassiveMapDef<Integer, List<Reward>> rewards = new MassiveMapDef<>();

    public Rewards()
    {
        initRewards();
    }

    public static Rewards get()
    {
        return Rewards.i;
    }

    public List<Reward> getRewardsByLevel(int level)
    {
        return rewards.get(level);
    }

    private void initRewards()
    {
        List<Reward> rewardList = new ArrayList<>();
        rewardList.add(new Reward(MUtil.list("ss give %player% skeleton 1", "rawmsg %player% &8(&6!&8) &7You received a skeleton spawner from fishing!"), 0.010D));
        rewardList.add(new Reward(MUtil.list("ss give %player% zombie 1", "rawmsg %player% &8(&6!&8) &7You received a zombie spawner from fishing!"), 0.025D));
        rewardList.add(new Reward(MUtil.list("ss give %player% spider 1", "rawmsg %player% &8(&6!&8) &7You received a spider spawner from fishing!"), 0.050D));
        rewardList.add(new Reward(MUtil.list("tokens add %player% 1000", "rawmsg %player% &8(&6!&8) &7You received 1000 tokens from fishing!"), 0.10D));
        rewardList.add(new Reward(MUtil.list("mobcoins give %player% 1000", "rawmsg %player% &8(&6!&8) &7You received 1000 mobcoins from fishing!"), 0.10D));
        rewards.put(0, rewardList);
        rewardList = new ArrayList<>();
        rewardList.add(new Reward(MUtil.list("ss give %player% enderman 1", "rawmsg %player% &8(&6!&8) &7You received an enderman spawner from fishing!"), 0.010D));
        rewardList.add(new Reward(MUtil.list("ss give %player% pigman 1", "rawmsg %player% &8(&6!&8) &7You received a pigman spawner from fishing!"), 0.025D));
        rewardList.add(new Reward(MUtil.list("ss give %player% blaze 1", "rawmsg %player% &8(&6!&8) &7You received a blaze spawner from fishing!"), 0.050D));
        rewardList.add(new Reward(MUtil.list("tokens add %player% 2500", "rawmsg %player% &8(&6!&8) &7You received 2500 tokens from fishing!"), 0.10D));
        rewardList.add(new Reward(MUtil.list("mobcoins give %player% 2500", "rawmsg %player% &8(&6!&8) &7You received 2500 mobcoins from fishing!"), 0.10D));
        rewards.put(1, rewardList);
        rewardList = new ArrayList<>();
        rewardList.add(new Reward(MUtil.list("ss give %player% silverfish 1", "rawmsg %player% &8(&6!&8) &7You received a silverfish spawner from fishing!"), 0.010D));
        rewardList.add(new Reward(MUtil.list("ss give %player% villager 1", "rawmsg %player% &8(&6!&8) &7You received a villager spawner from fishing!"), 0.025D));
        rewardList.add(new Reward(MUtil.list("ss give %player% creeper 1", "rawmsg %player% &8(&6!&8) &7You received a creeper spawner from fishing!"), 0.050D));
        rewardList.add(new Reward(MUtil.list("tokens add %player% 5000", "rawmsg %player% &8(&6!&8) &7You received 5000 tokens from fishing!"), 0.10D));
        rewardList.add(new Reward(MUtil.list("mobcoins give %player% 5000", "rawmsg %player% &8(&6!&8) &7You received 5000 mobcoins from fishing!"), 0.10D));
        rewards.put(2, rewardList);
        this.changed();
    }

    public void setRewards(MassiveMapDef<Integer, List<Reward>> rewards)
    {
        this.rewards = rewards;
    }

    @Override
    public Rewards load(Rewards that)
    {
        super.load(that);
        this.setRewards(that.rewards);
        return this;
    }

}
