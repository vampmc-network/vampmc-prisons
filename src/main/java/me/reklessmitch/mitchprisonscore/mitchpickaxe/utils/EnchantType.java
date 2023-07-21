package me.reklessmitch.mitchprisonscore.mitchpickaxe.utils;

public enum EnchantType {
    BEACON("Beacon"),
    FORTUNE("Fortune"),
    GREED("Greed"),
    JACKHAMMER("Jackhammer"),
    KEY_FINDER("Key Finder"),
    LOOT_FINDER("Loot Finder"),
    SPEED("Speed"),
    APOCALYPSE("Apocalypse"),
    SUPPLY_DROP("Supply Drop"),
    EFFICIENCY("Efficiency"),
    TOKEN_POUCH("Token Pouch"),
    SCAVENGER("Scavenger"),
    BOOST("Boost"),
    HASTE("Haste"),
    NUKE("Nuke"),
    EXPLOSIVE("Explosive");

    private final String displayName;

    EnchantType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
