package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.mitchprofiles.currency.MitchCurrency;

public class CmdAddBeacons extends CellCommands {

    public CmdAddBeacons(){
        this.addAliases("addbeacons");
        this.addParameter(TypeLong.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("<b>You are not in a cell");
            return;
        }
        long amount = this.readArg();
        ProfilePlayer profile = ProfilePlayer.get(me.getUniqueId());
        if(amount > 0 && profile.getCurrency("beacon").getAmount() > amount){
            cell.addBeacons(amount);
            msg("<g>Added " + amount + " beacons to your cell");
        }else{
            msg("<b>You do not have enough beacons");
        }
    }
}
