package ru.n08i40k.customtnt.events;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;

import java.lang.invoke.MethodHandles;

public class EventBusManager {
    private static IEventBus eventBus;

    public static IEventBus initEventBus() {
        if (eventBus != null)
            throw new IllegalArgumentException("Bus already initialized");

        eventBus = new EventBus();

        eventBus.registerLambdaFactory("ru.n08i40k.customtnt", (lookupInMethod, klass) ->
                (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        return eventBus;
    }

    public static IEventBus getEventBus() {
        return eventBus;
    }
}
