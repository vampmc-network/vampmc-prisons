package me.reklessmitch.mitchprisonscore.mitchbazaar.object;


import lombok.Getter;

import java.util.UUID;

@Getter
public class ShopValue {

    UUID owner;
    int amount;
    long price;

    public ShopValue(UUID owner, int amount, long price) {
        this.owner = owner;
        this.amount = amount;
        this.price = price;
    }

    public Double getPricePerItem() {
        return (double) price / amount;
    }


}
