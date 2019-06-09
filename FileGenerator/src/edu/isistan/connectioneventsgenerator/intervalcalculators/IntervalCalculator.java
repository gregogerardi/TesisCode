package edu.isistan.connectioneventsgenerator.intervalcalculators;


public abstract class IntervalCalculator {

    private TypeOfNextEvent typeOfNextEvent;

    public IntervalCalculator() {
        typeOfNextEvent = TypeOfNextEvent.CONNECTION;
    }

    public TypeOfNextEvent getTypeOfNextEvent() {
        return typeOfNextEvent;
    }

    public long getNewInterval() {
        long newEventTime = typeOfNextEvent == TypeOfNextEvent.CONNECTION ? getNextConnectionEvent() : getNextDisconnectionEvent();
        typeOfNextEvent = typeOfNextEvent.swap();
        return newEventTime;
    }

    abstract long getNextConnectionEvent();

    abstract long getNextDisconnectionEvent();

    public enum TypeOfNextEvent {
        CONNECTION, DISCONNECTION;

        public TypeOfNextEvent swap() {
            return this == CONNECTION ? DISCONNECTION : CONNECTION;
        }
    }
}
