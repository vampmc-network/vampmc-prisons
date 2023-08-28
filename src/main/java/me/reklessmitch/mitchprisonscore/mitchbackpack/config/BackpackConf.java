package me.reklessmitch.mitchprisonscore.mitchbackpack.config;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;

@Getter
@EditorName("config")
public class BackpackConf extends Entity<BackpackConf> {
    public static BackpackConf i;
    public static BackpackConf get() { return i; }

    private int startBackPackSize = 200;
    private int slotPriceIncreasePerSize = 5;
    private int autoSellCost = 1000000;
    private String guiTitle = ":backpack:";

}
