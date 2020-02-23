package kr.kieran.upgrades.enums;

public enum ToolType {

    CRAFT_WAND("craft wand", "craftwand", -1),
    FISHING_ROD("fishing rod", "fishingrod", 70),
    HARVESTER_HOE("harvester hoe", "harvesterhoe", 70),
    LIGHTNING_WAND("lightning wand", "lightningwand", -1),
    SAND_WAND("sand wand", "sandwand", -1),
    SELL_WAND("sell wand", "sellwand", 50),
    TRAY_PICKAXE("tray pickaxe", "traypickaxe", -1),
    TRENCH_PICKAXE("trench pickaxe", "trenchpickaxe", -1);

    private String name;
    private String alias;
    private int maxLevel;

    ToolType(String name, String alias, int maxLevel) {
        this.name = name;
        this.alias = alias;
        this.maxLevel = maxLevel;
    }

    public static ToolType getByAlias(String alias) {
        for (ToolType type : ToolType.values()) if (type.getAlias().equals(alias)) return type;
        return null;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}
