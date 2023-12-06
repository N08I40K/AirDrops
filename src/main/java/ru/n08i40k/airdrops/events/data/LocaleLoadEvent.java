package ru.n08i40k.airdrops.events.data;

import ru.n08i40k.customtnt.events.DataReloadEvent;
import ru.n08i40k.customtnt.events.EventType;

public class LocaleReloadEvent extends DataReloadEvent {
    protected LocaleReloadEvent(EventType eventType) {
        super(DataType.LOCALE, eventType);
    }

    public static class Pre extends LocaleReloadEvent {
        public Pre() {
            super(EventType.PRE);
        }
    }

    public static class Post extends LocaleReloadEvent {
        public Post() {
            super(EventType.POST);
        }
    }
}
