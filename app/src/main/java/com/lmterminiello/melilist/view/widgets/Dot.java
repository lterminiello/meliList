package com.lmterminiello.melilist.view.widgets;

public class Dot {
    enum State {
        SMALL,
        MEDIUM,
        INACTIVE,
        ACTIVE
    }

    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
