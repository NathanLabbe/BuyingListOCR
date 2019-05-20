package com.example.buyinglistocr.model;

/**
 * Created by Chi Linh on 2019--2019-05-15--11:55.
 */
public class Magasin {

    // Attributes of Magasin
    private long idMagasin;
    private String nameMagasin;

    public Magasin (long idMagasin, String nameMagasin) {

        super();
        this.idMagasin = idMagasin;
        this.nameMagasin = nameMagasin;

    }

    public long getIdMagasin() {

        return this.idMagasin;

    }

    public void setIdMagasin( long idMagasin) {

        this.idMagasin = idMagasin;

    }

    public String getNameMagasin() {

        return this.nameMagasin;

    }

    public void setNameMagasin( String nameMagasin) {

        this.nameMagasin = nameMagasin;

    }


}
