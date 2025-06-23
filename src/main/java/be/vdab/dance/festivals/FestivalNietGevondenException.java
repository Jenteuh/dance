package be.vdab.dance.festivals;

public class FestivalNietGevondenException extends RuntimeException {
    private final long id;
    public FestivalNietGevondenException(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
