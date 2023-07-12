package me.reklessmitch.mitchprisonscore.mitchmines.configs;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.SerLoc;

@Getter
@EditorName("config")
public class MineConf extends Entity<MineConf> {
    public static MineConf i;
    public static MineConf get() { return i; }

    private int mineSizeRadius = 40;
    private int mineYLevel = 107;
    private SerLoc mineOffset = new SerLoc(-135, 19, -48);

}
