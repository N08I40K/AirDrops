package ru.n08i40k.airdrops.events;

public class DataReloadEvent implements IEvent {
    public enum DataType {
        CONFIG,
        LOCALE
    }

    private final DataType dataType;
    private EventType eventType;

    public DataReloadEvent(DataType dataType) {
        this(dataType, EventType.PRE);
    }

    public DataReloadEvent(DataType dataType, EventType eventType) {
        this.dataType = dataType;
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    protected void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public DataType getDataType() {
        return dataType;
    }
}
