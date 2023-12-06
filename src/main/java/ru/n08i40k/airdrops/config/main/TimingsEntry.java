package ru.n08i40k.airdrops.config.main;

public class Timings {
    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public int getOpenDelay() {
        return openDelay;
    }

    public void setOpenDelay(int openDelay) {
        this.openDelay = openDelay;
    }

    public int getRemoveDelay() {
        return removeDelay;
    }

    public void setRemoveDelay(int removeDelay) {
        this.removeDelay = removeDelay;
    }

    int spawnDelay;
    int openDelay;
    int removeDelay;

    public Timings() {}
}