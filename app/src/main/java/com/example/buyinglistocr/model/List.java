package com.example.buyinglistocr.model;

/**
 * Represent the List object
 */
public class List {

    // Different attributes of List
    private long idList;
    private String name;
    private float spent;

    /**
     * The constructor of the class
     * @param idList - The id of the list
     * @param name - The name of the list
     * @param spent - The spent for the list
     */
    public List(long idList, String name, float spent) {

        super();
        this.idList = idList;
        this.name = name;
        this.spent = spent;

    }

    /**
     * Getter of the idList attribute
     * @return - The idList
     */
    public long getIdList() {

        return this.idList;

    }

    /**
     * Setter of the idList attribute
     * @param idList - The idList
     */
    public void setIdList(long idList) {

        this.idList = idList;

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
    public void setName(String name) {

        this.name = name;

    }

    /**
     * Getter of the spent attribute
     * @return - The spent
     */
    public float getSpent() {

        return this.spent;

    }

    /**
     * Setter of the spent attribute
     * @param spent - The spent
     */
    public void setSpent(float spent) {

        this.spent = spent;

    }

}



