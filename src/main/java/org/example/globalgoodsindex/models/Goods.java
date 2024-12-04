package org.example.globalgoodsindex.models;

public enum Goods {

    APPLES(110),
    BANANA(118),
    BEEF_ROUND(121),
    WINE(14),
    CHICKEN_FILLET(19),
    CIGARETTES(17),
    BEER_DOMESTIC(15),
    EGGS(11),
    BEER_IMPORTED(16),
    LETTUCE(113),
    BREAD(9),
    CHEESE_LOCAL(12),
    MILK(8),
    ONION(119),
    ORANGES(111),
    POTATO(112),
    RICE(115),
    TOMATO(116),
    WATER(13),
    //TRANSPORT
    TAXI_START(107),
    ONE_WAY_TICKET(18),
    MONTHLY_PASS(20),
    GASOLINE(24),
    //CLOTHES
    JEANS(60),
    NIKE(64),
    DRESS(62),
    //ACTIVITIES
    FITNESS_CLUB(40),
    CINEMA(44),
    //RESTAURANTS
    MEAL(1),
    MC_MEAL(3),
    COKE_OR_PEPSI(6),
    CAPPUCCINO(114),
    //HOME
    BASIC(30),
    INTERNET_MOBILE(34),
    INTERNET_CABLE(33);

    private final int itemId;

    Goods(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
