package fr.idjenria.game.item;

import java.util.Optional;

public enum Material {
    // Liste des items
    GOLD_COIN(0, "Pièce d'Or"),
    SILVER_COIN(1, "Pièce d'Argent"),
    BRONZE_COIN(2, "Pièce de Bronze"),
    LEATHER(10, "Cuire"),
    LOG(20, "Bûche"),
    STONE(30, "Roche"),
    ROPE(40, "Corde"),
    ;

    // Paramètres
    private final long id;
    private final String name;

    // Constructeur
    Material(long id, String name){
        this.id = id;
        this.name = name;
    }

    // Getters
    public long getId(){ return id; }
    public String getName(){ return name; }

    public static Optional<Material> fromId(long id){
        for(Material material : Material.values()){
            if(material.getId() == id) return Optional.of(material);
        }

        return Optional.empty();
    }
}