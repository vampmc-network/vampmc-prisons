package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import java.util.Comparator;
import java.util.List;

public class CmdCellTop extends CellCommands {

    public CmdCellTop(){
        this.addAliases("top");
    }

    @Override
    public void perform() {
        msg("§b§l+--- Top Cells ---+");
        List<Cell> cells = CellConf.get().getCells().values().stream()
                .limit(10)
                .sorted(Comparator.comparing(Cell::getBeacons, Comparator.reverseOrder()))
                .toList();
        for(int i = 0; i < cells.size(); i++){
            Cell cell = cells.get(i);
            msg("§b" + (i + 1) + ". §f" + cell.getName() + " §7- §b" + cell.getBeacons() + " Beacons");
        }
    }
}
