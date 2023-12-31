package me.reklessmitch.mitchprisonscore.mitchprofiles.holograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.massivecraft.massivecore.store.SenderEntity;
import me.reklessmitch.mitchprisonscore.MitchPrisonsCore;
import me.reklessmitch.mitchprisonscore.colls.PlayerWardrobeColl;
import me.reklessmitch.mitchprisonscore.mitchprofiles.object.WardrobeItem;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerWardrobe extends SenderEntity<PlayerWardrobe> {

    public static PlayerWardrobe get(Object oid) {return PlayerWardrobeColl.get().get(oid);}

    @Override
    public PlayerWardrobe load(@NotNull PlayerWardrobe that) {
        super.load(that);
        return this;
    }

    private boolean active = true;
    private WardrobeItem chestplate = null;
    private WardrobeItem helmet = null;
    private WardrobeItem leggings = null;
    private WardrobeItem boots = null;
    private WardrobeItem offhand = null;

    // create an invisible armour stand in the location of the player and have it follow the player,
    // then have the armour stand have an item on its chestplate

    private void testing() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);
        // Equipment slot. 0: main hand, 1: off hand, 2–5: armor slot (2: boots,
        // 3: leggings, 4: chestplate, 5: helmet).
        // Also has the top bit set if another entry follows, and otherwise unset if this is the last item in the array.

        Location loc = getPlayer().getLocation();
        packet.getDoubles().write(0, loc.getX());
        packet.getDoubles().write(1, loc.getY());
        packet.getDoubles().write(2, loc.getZ());
        packet.getIntegers().write(3, 0);
    }


    public ArmorStand spawnWardrobe(){
        ArmorStand armorStand = getPlayer().getWorld().spawn(getPlayer().getLocation(), ArmorStand.class);
        armorStand.setInvisible(true);
        armorStand.setBasePlate(false);
        armorStand.setArms(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);

        // add the items to the armour stand
        armorStand.addPassenger(getPlayer());
        return armorStand;
    }

    public void updateArmourStand(){
        ArmorStand armorStand = MitchPrisonsCore.get().getPlayerWardrobes().get(getPlayer().getUniqueId());
        EntityEquipment equipment = armorStand.getEquipment();
        if(equipment == null){
            return;
        }
        if(chestplate != null){
            equipment.setChestplate(chestplate.getGuiItem());
        }
        if(helmet != null){
            equipment.setHelmet(helmet.getGuiItem());
        }
        if(leggings != null){
            equipment.setLeggings(leggings.getGuiItem());
        }
        if(boots != null){
            equipment.setBoots(boots.getGuiItem());
        }
        if(offhand != null){
            equipment.setItemInOffHand(offhand.getGuiItem());
        }
    }

    @Override
    public void changed(){
        super.changed();
        if(active){
            updateArmourStand();
        }
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = new WardrobeItem(chestplate);
        this.changed();
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = new WardrobeItem(helmet);
        this.changed();
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = new WardrobeItem(leggings);
        this.changed();
    }

    public void setBoots(ItemStack boots) {
        this.boots = new WardrobeItem(boots);
        this.changed();
    }

    public void setOffhand(ItemStack offhand) {
        this.offhand = new WardrobeItem(offhand);
        this.changed();
    }
}
