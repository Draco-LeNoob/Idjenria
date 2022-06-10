package fr.idjenria.game.job;

import fr.idjenria.game.player.Player;

public enum Job {
    MINER(0, "Mineur"),
    LUMBERJACK(1, "Bûcheron"),
    HUNTER(2, "Chasseur")
    ;

    private final long id;
    private final String name;

    Job(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public String getName() { return name; }

    static JobType fromId(long id){
        for(JobType job : JobType.values())
            if(job.getId() == id)
                return job;

        return null;
    }
}