package com.example.buyinglistocr.model;

/**
 * Created by Chi Linh on 2019--2019-05-15--12:06.
 */
public class ProductOfMagasin {


        private static long idProductOM;
        private static String nameOM;
        private static int quantity;
        private static float prix;
        private static long idMagasin;


        public ProductOfMagasin(long idProductOM, String nameOM, int quantity, float prix,  long idMagasin) {

            this.idProductOM = idProductOM;
            this.nameOM = nameOM;
            this.quantity = quantity;
            this.prix = prix;
            this.idMagasin = idMagasin;
        }



        public static long getIdProductOM() {
            return idProductOM;
        }

        public void setIdProductOM(long idProductOM) {
            this.idProductOM = idProductOM;
        }

        public static String getNameOM() {
            return nameOM;
        }

        public void setNameOM(String nameOM)
        {
            this.nameOM = nameOM;
        }

        public static int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public static float getPrix() {
            return prix;
        }

        public void setPrix(int prix) {
            this.prix = prix;
        }

        public static long getIdMagasin() {
            return idMagasin;
        }

        public void setIdMagasin(int idMagasin) {
            this.idMagasin = idMagasin;
        }


}
