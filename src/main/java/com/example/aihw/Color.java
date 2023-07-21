package com.example.aihw;

public enum Color {

    SOURCE("#6ac46a"),
    TARGET("#ff8889"),
    BLOCKED("#6b6b6b"),
    UNBLOCKED("#f5f3f6"),
    Path("#bfe0ff"),
    TESTED("#ddf5dd"),
    EXPANDED("#f5dcd8")

    ;

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
