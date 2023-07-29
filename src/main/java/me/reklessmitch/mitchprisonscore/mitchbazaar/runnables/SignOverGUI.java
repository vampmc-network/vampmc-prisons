package me.reklessmitch.mitchprisonscore.mitchbazaar.runnables;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import lombok.Getter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.mitchbazaar.guis.FinaliseBazaarPayment;
import me.reklessmitch.mitchprisonscore.mitchprofiles.utils.CurrencyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SignOverGUI extends BukkitRunnable {

    private final Player player;
    @Getter private long amount = 0;
    @Getter private final String itemToBeBrought;
    @Getter private final String currencyToBuyWith;
    private final long maxAmount;

    public SignOverGUI(Player player, String itemToBeBrought, String currencyToBuyWith, long maxAmount) {
        this.player = player;
        this.itemToBeBrought = itemToBeBrought;
        this.currencyToBuyWith = currencyToBuyWith;
        this.maxAmount = maxAmount;
    }


    @Override
    public void run() {
        try {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            // create sign
            PacketContainer blockUpdate = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
            blockUpdate.getModifier().writeDefaults();

            Location signLocation = player.getLocation();

            BlockPosition signPosition = new BlockPosition(signLocation.toVector());
            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(Material.OAK_SIGN);

            blockUpdate.getBlockPositionModifier().write(0, signPosition);
            blockUpdate.getBlockData().write(0, wrappedBlockData);
            protocolManager.sendServerPacket(player, blockUpdate);

            // edit sign
            player.sendBlockChange(signLocation, Material.OAK_SIGN.createBlockData());

            String[] lines = new String[4];
            lines[0] = "";
            lines[1] = "§a§lEnter the amount";
            lines[2] = "§a§lyou want to buy";
            lines[3] = "§a§l==========";
            player.sendSignChange(signLocation, lines);

            // open sign editor
            PacketContainer openSignPacket = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            openSignPacket.getModifier().writeDefaults();
            openSignPacket.getBlockPositionModifier().write(0, signPosition);
            protocolManager.sendServerPacket(player, openSignPacket);

            // change sign to air
            wrappedBlockData = WrappedBlockData.createData(Material.AIR);
            blockUpdate.getBlockData().write(0, wrappedBlockData);
            protocolManager.sendServerPacket(player, blockUpdate);

            //listen to packet update sign
            PacketListener packetListener = new PacketAdapter(MitchPrisonsCore.get(), PacketType.Play.Client.UPDATE_SIGN) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    Player ePlayer = event.getPlayer();
                    if (!ePlayer.getName().equals(player.getName())) return;

                    PacketContainer packet = event.getPacket();
                    String[] lines = packet.getStringArrays().read(0);

                    protocolManager.removePacketListener(this);

                    if (!lines[0].isEmpty()) {
                        amount = CurrencyUtils.parse(lines[0]);
                        if(amount > maxAmount){
                            player.sendMessage("§7This item only has §c" + maxAmount + " in stock");
                            return;
                        }
                        Bukkit.getScheduler().runTask(MitchPrisonsCore.get(), () -> {
                            FinaliseBazaarPayment gui = new FinaliseBazaarPayment(player, amount, itemToBeBrought, currencyToBuyWith);
                            gui.open();
                        });
                    }
                }
            };
            protocolManager.addPacketListener(packetListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
