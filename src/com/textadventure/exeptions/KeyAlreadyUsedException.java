package com.textadventure.exeptions;

public class KeyAlreadyUsedException extends Exception {
    private final String key;

    public KeyAlreadyUsedException(String key) {
        this.key = key;
    }

    @Override
    public String getMessage() {
        return "Der Key " + this.key + " ist bereits in der HashMap";
    }
}
