package me.reklessmitch.mitchprisonscore.mitchmines.configs;

import com.fastasyncworldedit.core.FaweAPI;
import com.massivecraft.massivecore.store.SenderEntity;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Getter
public class PlayerMine extends SenderEntity<PlayerMine> {

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
        Bukkit.broadcastMessage("Mine created at " + spawnPoint.toBlockVector3().getBlockX());
        generateSchematic();
        Bukkit.getScheduler().runTaskLater(MitchPrisonsCore.get(), this::reset, 20L);
    }

    public void reset(){
        volumeMined = 0;
        World world = FaweAPI.getWorld("privatemines");
        Location l = getPlayer().getLocation();
        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
        BlockVector3 minV = min.toBlockVector3();
        BlockVector3 maxV = max.toBlockVector3();
        int minX = minV.getBlockX();
        int minY = minV.getBlockY();
        int minZ = minV.getBlockZ();
        int maxX = maxV.getBlockX();
        int maxY = maxV.getBlockY();
        int maxZ = maxV.getBlockZ();

        BlockStateHolder<BlockState> blockType = BlockTypes.get(block.name()).getDefaultState();    // Desired block type
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockVector3 location = BlockVector3.at(x, y, z);
                    editSession.setBlock(location, blockType);
                }
            }
        }
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
        File file = new File(WorldEdit.getInstance().getSchematicsFolderPath() + File.separator + "1.schematic");
        World world = FaweAPI.getWorld("privatemines");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        if (format == null) {
            System.out.println("Schematic not found");
            return;
        }
        Clipboard clipboard = null;
        try {
            ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()));
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clipboard == null) {
            System.out.println("Clipboard is null");
            return;
        }
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(spawnPoint.toBlockVector3())
                    .copyBiomes(false)
                    .copyEntities(false)
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
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
            if(blockPosition.getBlockType().equals(BlockTypes.AIR)){
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
                editSession.setBlock(vector, BlockTypes.AIR.getDefaultState());
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

    public void upgradeSize() {
        size += 1;
        min = new SerLoc(-size, -70, -size).addS(middleLocation);
        max = new SerLoc(size, 0, size).addS(middleLocation);
        reset();
        changed();
    }

    public void teleport() {
        Location l = spawnPoint.toLocation();
        l.setPitch(90);
        getPlayer().teleport(l);
    }
}
