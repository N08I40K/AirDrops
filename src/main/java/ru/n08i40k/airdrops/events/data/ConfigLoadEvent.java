package ru.n08i40k.airdrops.events.data;

import ru.n08i40k.customtnt.events.DataReloadEvent;
import ru.n08i40k.customtnt.events.EventType;

public class ConfigReloadEvent extends DataReloadEvent {
    public enum ConfigType {
        MAIN
    }

    private final ConfigType configType;

    protected ConfigReloadEvent(ConfigType configType, EventType type) {
        super(DataType.CONFIG, type);

        this.configType = configType;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public static class Pre extends ConfigReloadEvent {
        public Pre(ConfigType configType) {
            super(configType, EventType.PRE);
        }
    }

    public static class Post extends ConfigReloadEvent {
        public Post(ConfigType configType) {
            super(configType, EventType.POST);
        }
    }
}
