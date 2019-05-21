package com.example.buyinglistocr.model;

/**
 * Represent the Shop project
 */
public class Shop {

    // Different attributes of Shop
    private long id;
    private String name;

    /**
     * The constructor of the class
     * @param id - The id
     * @param name - The name
     */
    public Shop (long id, String name) {

        super();

        this.id = id;
        this.name = name;

    }

    /**
     * Getter of the id attribute
     * @return - The id
     */
    public long getId() {

        return this.id;

    }

    /**
     * Setter of the id attribute
     * @param id - The id
     */
    public void setId( long id) {

        this.id = id;

    }

    /**
     * Getter of the name attribute
     * @return - The name
     */
    public String getName() {

        return this.name;

    }

    /**
     * Setter of the name attribute
     * @param name - The name
     */
    public void setName( String name) {

        this.name = name;

    }


}
