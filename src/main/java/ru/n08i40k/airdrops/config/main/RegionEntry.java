package ru.n08i40k.airdrops.config.main;

import java.util.Map;

public class Region {
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Map<String, Boolean> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, Boolean> flags) {
        this.flags = flags;
    }

    boolean enabled;
    int radius;
    Map<String, Boolean> flags;

    public Region() {}
}