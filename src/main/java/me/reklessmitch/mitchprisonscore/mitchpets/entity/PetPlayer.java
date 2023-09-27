package me.reklessmitch.mitchprisonscore.mitchpets.entity;

import com.massivecraft.massivecore.store.SenderEntity;
import lombok.Getter;
import lombok.Setter;
import me.reklessmitch.mitchprisonscore.colls.PPlayerColl;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

@Getter
public class PetPlayer extends SenderEntity<PetPlayer> {

    public static PetPlayer get(Object oid) {
        return PPlayerColl.get().get(oid);
    }

    @Override
    public PetPlayer load(@NotNull PetPlayer that)
    {
        super.load(that);
        return this;
    }

    private Map<PetType, Pet> pets = initializePets();

    @Setter PetType activePet = PetType.TOKEN;

    private Map<PetType, Pet> initializePets() {
        Map<PetType, Pet> petsCreate = new EnumMap<>(PetType.class);
        petsCreate.put(PetType.CRATE, new Pet(PetType.CRATE));
        petsCreate.put(PetType.MONEY, new Pet(PetType.MONEY));
        petsCreate.put(PetType.SUPPLY_DROP, new Pet(PetType.SUPPLY_DROP));
        petsCreate.put(PetType.JACKHAMMER_BOOST, new Pet(PetType.JACKHAMMER_BOOST));
        petsCreate.put(PetType.TOKEN, new Pet(PetType.TOKEN));
        return petsCreate;
    }

    public Pet getPet(PetType type) {
        return pets.getOrDefault(type, null);
    }


}
