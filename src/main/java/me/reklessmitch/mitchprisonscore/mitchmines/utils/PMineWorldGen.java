package me.reklessmitch.mitchprisonscore.mitchmines.utils;

import java.util.Random;

import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class PMineWorldGen extends ChunkGenerator {
    private final String worldName;

    public PMineWorldGen(String worldName) {
        this.worldName = worldName;
    }

    public void createWorld() {
        World world = MitchPrisonsCore.get().getServer().createWorld((new WorldCreator(this.worldName))
                .generator(this).generateStructures(false));
        world.setDifficulty(Difficulty.EASY);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000L);
    }

    @Override
    public final ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++)
                biome.setBiome(x, z, Biome.THE_VOID);
        }
        return chunkData;
    }

    @Override
    public Location getFixedSpawnLocation(@NotNull World world, @NotNull Random random) {
        return new Location(world, 0.0D, 90.0D, 0.0D);
    }
}