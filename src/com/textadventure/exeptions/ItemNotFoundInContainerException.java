package com.textadventure.exeptions;

public class ItemNotFoundInContainerException extends Exception {
    private final String item;
    private final String container1;
    private final String container2;

    public ItemNotFoundInContainerException(String item, String container1, String container2) {
        this.item = item;
        this.container1 = container1;
        this.container2 = container2;
    }

    @Override
    public String getMessage() {
        return "Weder in " + container1 + " noch in " + container2 + " befindet sich " + item;
    }
}
