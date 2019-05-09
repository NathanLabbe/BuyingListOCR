package com.example.buyinglistocr.controller;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseData {
    private String textBrut;
    private HashMap<String, ArrayList<Float>> Table;

    public AnalyseData(String text) {
        this.textBrut = text;
    }


    //Enleve les donnees inutiles
    public  String clean(String text){
        String res="";
        Boolean keep = false;
        String[] tokens = text.split(" ");

        for (String t : tokens){
            if( keep ==false && t.equals("TEL") ){
                keep=true;

            }else if(keep==true && !t.equals("MONTANT")){
                res+=" "+t;
            }else if(keep==true && t.equals("MONTANT")){
                keep=false;
            }

        }

        return res;

    }

    //Corriger les fautes d'analyses
    private String toleranceMot(String text){

        return null;
    }

    //Ajoute les noms de produit, qte, prix ..Etc  à la HashTable
    private void ajoutTable(String text){


    }
}
