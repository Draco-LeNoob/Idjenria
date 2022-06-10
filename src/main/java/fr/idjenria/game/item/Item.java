package fr.idjenria.game.item;

import java.util.Optional;

public enum Item {
    // Liste des items
    GOLD_COIN(0, "Pièce d'Or", 75, false),
    SILVER_COIN(1, "Pièce d'Argent", 25, false),
    BRONZE_COIN(2, "Pièce de Bronze", 5, true),
    LEATHER(10, "Cuire", 50, true),
    LOG(20, "Bûche", 5, true),
    STONE(30, "Pierre", 5, false),
    ROPE(40, "Corde", 60, true),
    ;

    // Paramètres
    private final long id;
    private final String name;
    private final int rarity;
    private final boolean canObtainDiscussing;

    // Constructeur
    Item(long id, String name, int rarity, boolean canObtainDiscussing){
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.canObtainDiscussing = canObtainDiscussing;
    }

    // Getters
    public long getId(){ return id; }
    public String getName(){ return name; }
    public int getRarity(){ return rarity; }
    public boolean canObtainDiscussing(){ return canObtainDiscussing; }

    public static Optional<Item> fromId(long id){
        for(Item material : Item.values()){
            if(material.getId() == id) return Optional.of(material);
        }

        return Optional.empty();
    }
}