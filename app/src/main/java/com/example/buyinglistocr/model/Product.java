package com.example.buyinglistocr.model;

/**
 * Represent the Product object
 */
public class Product {

    // Different attributes of Product
    private long id;
    private String name;
    private long idShop;

    /**
     * The constructor of the class
     * @param id - The id
     * @param name - The name
     * @param idShop - The idShop
     */
    public Product(long id, String name,  long idShop) {

        this.id = id;
        this.name = name;
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