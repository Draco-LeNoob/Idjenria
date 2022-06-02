package fr.idjenria.game.item;

// Classe représentant un objet
public class Item {
    // Paramètres
    private final long id;
    private final String name;
    private final Material type;
    private final int amount;

    // Constructeurs
    public Item(Material type, int amount){
        this.id = type.getId();
        this.name = type.getName();
        this.type = type;
        this.amount = amount;
    }

    // Getters
    public long getId() { return id; }
    public Material getType() { return type; }
    public int getAmount() { return amount; }

    @Override
    public String toString() {
        return this.name + " * " + this.amount;
    }
}