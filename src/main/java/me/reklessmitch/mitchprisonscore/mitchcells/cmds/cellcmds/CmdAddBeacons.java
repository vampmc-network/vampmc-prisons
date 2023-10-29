package me.reklessmitch.mitchprisonscore.mitchcells.cmds.cellcmds;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import me.reklessmitch.mitchprisonscore.mitchcells.cmds.CellCommands;
import me.reklessmitch.mitchprisonscore.mitchcells.configs.CellConf;
import me.reklessmitch.mitchprisonscore.mitchcells.object.Cell;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;

import java.math.BigInteger;

public class CmdAddBeacons extends CellCommands {

    public CmdAddBeacons(){
        this.addAliases("addbeacons");
        this.addParameter(TypeLong.get(), "amount");
    }

    @Override
    public void perform() throws MassiveException {
        Cell cell = CellConf.get().getCellByMember(me.getUniqueId());
        if(cell == null){
            msg("§cYou are not in a cell");
            return;
        }
        BigInteger amount = BigInteger.valueOf(this.readArg());
        ProfilePlayer profile = ProfilePlayer.get(me.getUniqueId());

        BigInteger beaconAmount = profile.getCurrency("beacon").getAmount(); // Assuming getAmount() returns a BigInteger

        if (amount.compareTo(BigInteger.ZERO) > 0 && beaconAmount.compareTo(amount) > 0) {
            profile.getCurrency("beacon").take(amount); // Assuming subtract() is a method to subtract a BigInteger
            profile.changed();
            cell.addBeacons(amount);
            msg("§aAdded " + amount + " beacons to your cell");
        } else {
            msg("§cYou do not have enough beacons");
        }
    }
}
