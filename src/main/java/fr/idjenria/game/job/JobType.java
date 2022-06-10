package fr.idjenria.game.job;

public enum JobType {
    MINER(0, "Mineur"),
    LUMBERJACK(1, "Bûcheron"),
    HUNTER(2, "Chasseur")
    ;

    private final long id;
    private final String name;

    JobType(long id, String name){
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