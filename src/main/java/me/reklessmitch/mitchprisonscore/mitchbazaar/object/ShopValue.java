package me.reklessmitch.mitchprisonscore.mitchbazaar.object;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShopValue {

    private UUID owner;
    private long amount;
    private long price;

    private double pricePerItem;


    // TODO: Fix this when a player buys an item the price per item updates
    public ShopValue(UUID owner, long amount, long price) {
        this.owner = owner;
        this.amount = amount;
        this.price = price;
        this.pricePerItem = getPricePerItem();
    }

    public Double getPricePerItem() {
        return (double) price / amount;
    }

}
