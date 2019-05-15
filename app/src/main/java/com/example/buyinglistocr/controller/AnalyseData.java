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
        String[] tokens = text.split("[ \\n]+");

        for (int i =0; i<tokens.length ; i++){
            if( keep ==false && tokens[i].startsWith("TEL") && !tokens[i].equals("TEL")){
                keep=true;
            }if( keep ==false && tokens[i].equals("TEL") ) {
                keep = true;
                i++;
            }
            if(keep == true && tokens[i].equals("SIRET")){
                keep = true;
                i++;
            }else if(keep==true && !tokens[i].equals("MONTANT")){
                res+=" "+tokens[i];
                if(keep==true && (tokens[i].equals("A") || tokens[i].equals("B"))){
                    res+="\n";
                }
                System.out.println(tokens[i]);
            }else if(keep==true && tokens[i].equals("MONTANT")) {
                keep = false;
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
