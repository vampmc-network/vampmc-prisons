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
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.PlayerMineColl;
import me.reklessmitch.mitchprisonscore.mitchmines.utils.SerLoc;
import me.reklessmitch.mitchprisonscore.mitchprofiles.configs.ProfilePlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class PlayerMine extends SenderEntity<PlayerMine> {

    private int rank = 1;
    private int size = 10;
    private int offSetX = 400 * PlayerMineColl.get().getAll().size();
    private SerLoc spawnPoint = new SerLoc(offSetX, 107, 0);
    private SerLoc middleLocation = new SerLoc(offSetX -87, 97, 0);
    private SerLoc min = new SerLoc(-size, -50, -size).addS(middleLocation);
    private SerLoc max = new SerLoc(size, 0, size).addS(middleLocation);
    private long volume = (long) (size + 1) * (size + 1) * 100; // 50 * 2 for size radius
    private long volumeMined = 0;
    @Setter private Material block = Material.STONE;
    private int booster = 1;


    public void createMine(){
        generateSchematic();
    }

    public void reset(){
        volumeMined = 0;
        World world = FaweAPI.getWorld("privatemines");
        Location l = getPlayer().getLocation().subtract(0, 2, 0);
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
        Region cub = new CuboidRegion(min.toBlockVector3(), max.toBlockVector3());
        editSession.setBlocks(cub, BlockTypes.get(block.name().toLowerCase()));
        editSession.flushQueue();
        editSession.close();
        if(isInMine(BlockVector3.at(l.getX(), l.getY(), l.getZ()))){
            getPlayer().teleport(middleLocation.toLocation());
        }
        getPlayer().sendMessage("Mine Reset");
    }


    public void addBooster(int amount){
        booster += amount;
        getPlayer().sendMessage("Booster added");
    }

    private void generateSchematic(){
        File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + "3.schem");
        World world = FaweAPI.getWorld("privatemines");

        try {Clipboard clip = FaweAPI.load(file);
            MitchPrisonsCore.get().getServer().getScheduler().runTaskAsynchronously(MitchPrisonsCore.get(), () ->
                    clip.paste(world, spawnPoint.toBlockVector3(), false,
                            false, false, null));
        }catch (IOException ignored){}

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
        World world = FaweAPI.getWorld("privatemines");
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);

        BlockVector3 minV = min.toBlockVector3().withY(y);
        BlockVector3 maxV = max.toBlockVector3().withY(y);

        int blocks = 0;
        int beacons = 0;

        for(BlockVector3 vector : new CuboidRegion(minV, maxV)){
            BlockState blockPosition = world.getBlock(vector);
            if(blockPosition.getBlockType().equals(BlockTypes.BEACON)){
                beacons++;
            }
            if(!blockPosition.getBlockType().equals(BlockTypes.AIR)){
                blocks++;
            }
            editSession.setBlock(vector, BlockTypes.AIR);
        }
        editSession.flushQueue();
        editSession.close();
        volumeMined += blocks;
        ProfilePlayer.get(getPlayer()).getCurrency("beacon").add(beacons);
        volumeMinedCheck();
        return blocks;
    }

    public int getExplosiveBlocks(Location location, int radius){
        World world = FaweAPI.getWorld("privatemines");
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);

        int blocks = 0;
        int beacons = 0;

        BlockVector3 center = BlockVector3.at(location.getX(), location.getY(), location.getZ());

        // Get all the block positions within the ellipsoid region
        for(BlockVector3 vector : new EllipsoidRegion(world, center, Vector3.at(radius, radius, radius))){
            BlockStateHolder<BlockState> blockState = editSession.getBlock(vector);
            if(!isInMine(vector)) continue;
            if(blockState.getBlockType().equals(BlockTypes.BEACON)){
                beacons++;
            }
            if(!blockState.getBlockType().equals(BlockTypes.AIR)){
                editSession.setBlock(vector, BlockTypes.AIR);
                blocks++;
            }
        }
        editSession.flushQueue();
        editSession.close();
        volumeMined += blocks;
        ProfilePlayer.get(getPlayer()).getCurrency("beacon").add(beacons);
        volumeMinedCheck();
        return blocks;
    }

    private void volumeMinedCheck(){
        if(volumeMined >= volume * 0.7){
            reset();
        }
    }

    public void upgradeSize(int amount) {
        size += amount;
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

    public int apocolypse() {
        World world = FaweAPI.getWorld("privatemines");
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);

        BlockVector3 minV = min.toBlockVector3().withY(97);
        BlockVector3 maxV = max.toBlockVector3().withY(97);

        Random random = MitchPrisonsCore.get().getRandom();
        List<BlockVector3> randomBlocks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(maxV.getBlockX() - minV.getBlockX() + 1) + minV.getBlockX();
            int z = random.nextInt(maxV.getBlockZ() - minV.getBlockZ() + 1) + minV.getBlockZ();
            randomBlocks.add(BlockVector3.at(x, 97, z));
        }

        int beacons = 0;
        int blocks = 0;

        for(BlockVector3 v3 : randomBlocks){
            BlockVector3 minM = BlockVector3.at(v3.getX(), 47, v3.getZ());
            BlockVector3 maxM = BlockVector3.at(v3.getX(), 97, v3.getZ());
            CuboidRegion cuboidRegion = new CuboidRegion(minM, maxM);
            for(BlockVector3 vector : cuboidRegion){
                BlockState blockPosition = world.getBlock(vector);
                if(blockPosition.getBlockType().equals(BlockTypes.BEACON)){
                    beacons++;
                }
                if(!blockPosition.getBlockType().equals(BlockTypes.AIR)){
                    blocks++;
                }
                editSession.setBlock(vector, BlockTypes.AIR);
            }
        }
        editSession.flushQueue();
        editSession.close();
        volumeMined += blocks;
        ProfilePlayer.get(getPlayer()).getCurrency("beacon").add(beacons);
        volumeMinedCheck();
        return blocks;
    }

    public void addRankLevel() {
        rank++;
        upgradeSize(1);
    }
}
