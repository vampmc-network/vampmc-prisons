package me.reklessmitch.mitchprisonscore.mitchmines.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.IdUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.PlayerMineColl;
import me.reklessmitch.mitchprisonscore.mitchboosters.configs.BoosterPlayer;
import me.reklessmitch.mitchprisonscore.mitchboosters.objects.Booster;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.SerLoc;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import me.reklessmitch.mitchprisonscore.utils.LangConf;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Getter
public class PlayerMine extends SenderEntity<PlayerMine> {

    private int size = 10;
    private int offSetX = 300 * PlayerMineColl.get().getAll().size();
    private SerLoc spawnPoint = new SerLoc(offSetX, 99, 0);
    private SerLoc middleLocation = new SerLoc(offSetX -57, 97, 0);
    private SerLoc min = new SerLoc(-size, -70, -size).addS(middleLocation);
    private SerLoc max = new SerLoc(size, 0, size).addS(middleLocation);
    private long volume = (long) (size + 1) * (size + 1) * 100; // 50 * 2 for size radius
    private long volumeMined = 0;
    @Setter private Material block = Material.STONE;
    private int booster = 1;

    public void reset(){
        volumeMined = 0;
        Location l = getPlayer().getLocation();
        try(EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld("privatemines"))){
            Region cub = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3());
            editSession.setBlocks(cub, BlockTypes.get(block.name().toLowerCase()));
        }
        if(isInMine(BlockVector3.at(l.getX(), l.getY(), l.getZ()))){
            getPlayer().teleport(middleLocation.toLocation().add(0, 1, 0));
            getPlayer().sendMessage(LangConf.get().getMineReset());
        }
    }


    public void addBooster(int amount){
        booster += amount;
        getPlayer().sendMessage(LangConf.get().getMineBoosterAdded());
        changed();
    }

    public void generateSchematic(){
        File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + "mine.schem");

        try {Clipboard clip = FaweAPI.load(file);
            MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () ->
                    clip.paste(FaweAPI.getWorld("privatemines"), spawnPoint.toBlockVector3(), false,
                            false, false, null));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static PlayerMine get(Object oid) {
        return PlayerMineColl.get().get(oid);
    }

    @Override
    public PlayerMine load(@NotNull PlayerMine that)
    {
        super.load(that);
        return this;
    }

    public boolean isInMine(BlockVector3 blockVector3) {
        return blockVector3.containedWithin(min.toBlockVector3(), max.toBlockVector3());
    }

    public int getBlocksOnYLayer(int y){

        BlockVector3 minV = min.toBlockVector3().withY(y);
        BlockVector3 maxV = max.toBlockVector3().withY(y);

        return getBeaconsAndBlocksInRegion(new CuboidRegion(minV, maxV));
    }

    public int getExplosiveBlocks(Location location, int radius){
        World world = FaweAPI.getWorld("privatemines");
        return getBeaconsAndBlocksInRegion(new EllipsoidRegion(world, BlockVector3.at(location.getX(), location.getY(), location.getZ()), Vector3.at(radius, radius, radius)));
    }

    private int multiplyBeaconBooster(int beacons){
        Booster beaconBooster = BoosterPlayer.get(getPlayer()).getActiveBeaconBooster();
        if(beaconBooster != null){
            beacons *= beaconBooster.getMultiplier();
        }
        return beacons;
    }
    private void volumeMinedCheck(){
        if(volumeMined >= volume * 0.7){
            reset();
        }
    }

    public void upgradeSize(int amount, boolean set) {
        if(set){
            size = amount;
        }else{
            getPlayer().sendMessage("§a§lYour Mine has been upgraded to size: " + size);
            size += amount;
        }
        min = new SerLoc(-size, -70, -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
        volume = (long) (size + 1) * (size + 1) * 100;
        reset();
        changed();
    }

    public void teleport() {
        reset();
        Location l = spawnPoint.toLocation().add(0, 1, 0);
        l.setYaw(90);
        if (IdUtil.getOfflinePlayer(id).isOnline()){
            getPlayer().teleport(l);
        }
    }

    public int getBeaconsAndBlocksInRegion(Region region){
        int beacons = 0;
        int blocks = 0;

        try(EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld("privatemines"))) {
            for (BlockVector3 vector : region) {
                if (!isInMine(vector)) continue;
                BlockType type = editSession.getBlock(vector).getBlockType();
                if (type.equals(BlockTypes.BEACON)) {
                    beacons++;
                }
                if (!type.equals(BlockTypes.AIR)) {
                    blocks++;
                    editSession.setBlock(vector, BlockTypes.AIR);
                }
            }
        }
        volumeMined += blocks;
        ProfilePlayer.get(getPlayer()).getCurrency("beacon").add(multiplyBeaconBooster(beacons));
        volumeMinedCheck();
        return blocks;
    }

    public int apocalypse() {
        BlockVector3 minV = min.toBlockVector3().withY(97);
        BlockVector3 maxV = max.toBlockVector3().withY(97);

        Random random = MitchPrisonsCore.get().getRandom();

        int totalBlocks = 0;
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(maxV.getBlockX() - minV.getBlockX() + 1) + minV.getBlockX();
            int z = random.nextInt(maxV.getBlockZ() - minV.getBlockZ() + 1) + minV.getBlockZ();
            totalBlocks += getBeaconsAndBlocksInRegion(new CuboidRegion(BlockVector3.at(x, 47, z), BlockVector3.at(x, 97, z)));
        }

        return totalBlocks;
    }
}
