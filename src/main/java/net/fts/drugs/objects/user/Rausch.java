package net.fts.drugs.objects.user;

public class Rausch {

    String name;
    int secounds;
    long startedAt;

    public Rausch(long startedAt, int secounds, String name) {
        this.startedAt = startedAt;
        this.secounds = secounds;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return secounds;
    }
    public void setDurations(int secounds) {
        this.secounds = secounds;
    }

    public long getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }
}
