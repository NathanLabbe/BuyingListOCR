package com.example.buyinglistocr.model;

import org.w3c.dom.Text;

public class Product {

    private static long idProduit;
    private static String name;
    private static int quantityBase;
    private static int quantityAct;
    private static String note;
    private static int statut;
    private static int idList;


    /**
     * The constructor of the class
     * @param idProduit
     * @param name
     * @param quantityBase
     * @param quantityAct
     * @param note
     * @param statut
     * @param idList
     */
    public Product(long idProduit, String name, int quantityBase, int quantityAct, String note, int statut, int idList) {
        super();
        this.idProduit = idProduit;
        this.name = name;
        this.quantityBase = quantityBase;
        this.quantityAct = quantityAct;
        this.note = note;
        this.statut = statut;
        this.idList = idList;
    }

    /**
     * Getters and setters
     */

    public static long getIdProduit() {
        return idProduit;
    }

    public static void setIdProduit(long idProduit) {
        Product.idProduit = idProduit;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Product.name = name;
    }

    public static int getQuantityBase() {
        return quantityBase;
    }

    public static void setQuantityBase(int quantityBase) {
        Product.quantityBase = quantityBase;
    }

    public static int getQuantityAct() {
        return quantityAct;
    }

    public static void setQuantityAct(int quantityAct) {
        Product.quantityAct = quantityAct;
    }

    public static String getNote() {
        return note;
    }

    public static void setNote(String note) {
        Product.note = note;
    }

    public static int getStatut() {
        return statut;
    }

    public static void setStatut(int statut) {
        Product.statut = statut;
    }

    public static int getIdList() {
        return idList;
    }

    public static void setIdList(int idList) {
        Product.idList = idList;
    }
    /**END**/

}
