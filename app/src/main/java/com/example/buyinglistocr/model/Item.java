package com.example.buyinglistocr.model;

/**
 * Represent the Item object
 */
public class Item {

    // Different attributes of Item
    private long id;
    private String name;
    private int quantityDesired;
    private int quantityGot;
    private String note;
    private int status;
    private long idList;

    /**
     * The constructor of the class
     * @param id - The id
     * @param name - The name
     * @param quantityDesired - The quantityDesired
     * @param quantityGot - The quantity got
     * @param note - The note
     * @param status - The status
     * @param idList - The id of the list
     */
    public Item(long id, String name, int quantityDesired, int quantityGot, String note, int status, long idList) {

        this.id = id;
        this.name = name;
        this.quantityDesired = quantityDesired;
        this.quantityGot = quantityGot;
        this.note = note;
        this.status = status;
        this.idList = idList;

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
    public void setId(long id) {

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
    public void setName(String name) {

        this.name = name;

    }

    /**
     * Getter of the quantityDesired
     * @return - The quantityDesired
     */
    public int getQuantityDesired() {

        return this.quantityDesired;

    }

    /**
     * Setter of the quantityDesired attribute
     * @param quantityDesired - The quantityDesired
     */
    public void setQuantityDesired(int quantityDesired) {

        this.quantityDesired = quantityDesired;

    }

    /**
     * Getter of the quantityGot attribute
     * @return - The quantityGot
     */
    public int getQuantityGot() {

        return this.quantityGot;

    }

    /**
     * Setter of the quantityGot attribute
     * @param quantityGot - The quantityGot
     */
    public void setQuantityGot(int quantityGot) {

        this.quantityGot = quantityGot;

    }

    /**
     * Getter of the note attribute
     * @return - The note
     */
    public String getNote() {

        return this.note;

    }

    /**
     * Setter of the note attribute
     * @param note - The note
     */
    public void setNote(String note) {

        this.note = note;

    }

    /**
     * Getter of the status attribute
     * @return - The status
     */
    public int getStatus() {

        return this.status;

    }

    /**
     * Setter of the status attribute
     * @param status - The status
     */
    public void setStatus(int status) {

        this.status = status;

    }

    /**
     * Getter of the idList attribute
     * @return - The idList
     */
    public long getIdList() {

        return idList;

    }

    /**
     * Setter of the idList attribute
     * @param idList - The idList
     */
    public void setIdList(int idList) {

        this.idList = idList;

    }

}