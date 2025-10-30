package com.emissions.app.constants;

public enum CarSize {
    SMALL,
    MEDIUM,
    LARGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
