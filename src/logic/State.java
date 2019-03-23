package logic;

import models.Clock;
import models.Guard;
import models.Update;

import java.util.List;

public class State {
    private final SymbolicLocation location;
    private Zone zone, arrivalZone;

    public State(SymbolicLocation location, Zone zone) {
        this.location = location;
        this.zone = zone;
        this.arrivalZone = new Zone(zone.getSize());
    }

    public State(SymbolicLocation location, Zone zone, Zone arrivalZone) {
        this.location = location;
        this.zone = zone;
        this.arrivalZone = arrivalZone;
    }

    public State(State oldState) {
        this.location = oldState.getLocation();
        this.zone = new Zone(oldState.getZone());
        this.arrivalZone = new Zone(oldState.getArrivalZone());
    }

    public SymbolicLocation getLocation() {
        return location;
    }

    public Zone getZone() {
        return zone;
    }

    public Zone getArrivalZone() {
        return arrivalZone;
    }

    private int getIndexOfClock(Clock clock, List<Clock> clocks) {
        return clocks.indexOf(clock) + 1;
    }

    private List<Guard> getInvariants() {
        return location.getInvariants();
    }

    public void applyGuards(List<Guard> guards, List<Clock> clocks) {
        for (Guard guard : guards)
            zone.buildConstraintsForGuard(guard, getIndexOfClock(guard.getClock(), clocks));
    }

    public void applyInvariants(List<Clock> clocks) {
        for (Guard invariant : getInvariants())
            zone.buildConstraintsForGuard(invariant, getIndexOfClock(invariant.getClock(), clocks));
    }

    public void applyResets(List<Update> resets, List<Clock> clocks) {
        for (Update reset : resets)
            zone.updateValue(getIndexOfClock(reset.getClock(), clocks), reset.getValue());
    }

    public void setArrivalZone(Zone arrivalZone) {
        this.arrivalZone = new Zone(arrivalZone);
    }

    @Override
    public String toString() {
        return "{" + location + ", " + zone + '}';
    }
}