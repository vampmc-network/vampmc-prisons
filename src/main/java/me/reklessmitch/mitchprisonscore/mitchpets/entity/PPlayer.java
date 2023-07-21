package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.PPlayerColl;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
public class PPlayer extends SenderEntity<PPlayer> {

    public static PPlayer get(Object oid) {
        return PPlayerColl.get().get(oid);
    }

    @Override
    public PPlayer load(@NotNull PPlayer that)
    {
        super.load(that);
        return this;
    }

    Set<Pet> pets = newPets();
    @Setter PetType activePet = PetType.TOKEN;

    private Set<Pet> newPets() {
        return Set.of(new Pet(PetType.CRATE), new Pet(PetType.MONEY), new Pet(PetType.SUPPLY_DROP),
                new Pet(PetType.JACKHAMMER_BOOST), new Pet(PetType.TOKEN));
    }

    public Pet getPet(PetType type){
        return pets.stream().filter(pet -> pet.getType() == type).findFirst().orElse(null);
    }


}
