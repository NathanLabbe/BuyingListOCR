package com.example.buyinglistocr.model;

/**
 * Represent the Product object
 */
public class Product {

    // Different attributes of Product
    private long id;
    private String name;
    private String correspondence;
    private long idShop;

    /**
     * The constructor of the class
     * @param id - The id
     * @param name - The name
     * @param correspondence - The correspondence
     * @param idShop - The idShop
     */
    public Product(long id, String name, String correspondence,  long idShop) {

        this.id = id;
        this.name = name;
        this.correspondence = correspondence;
        this.idShop = idShop;

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
     * Getter of the correspondence attribute
     * @return - The correspondence
     */
    public String getCorrespondence() {

        return this.correspondence;

    }

    /**
     * Setter of the correspondence attribute
     * @param correspondence - The correspondence
     */
    public void setCorrespondence(String correspondence) {

        this.correspondence = correspondence;

    }



    /**
     * Getter of the idShop attribute
     * @return - The idShop
     */
    public long getIdShop() {

        return this.idShop;

    }

    /**
     * Setter of the idShop attribute
     * @param idShop - The idShop
     */
    public void setIdShop(int idShop) {

        this.idShop = idShop;

    }

}