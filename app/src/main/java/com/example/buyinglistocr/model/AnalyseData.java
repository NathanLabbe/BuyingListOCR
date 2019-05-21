package com.example.buyinglistocr.model;

import android.content.Context;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AnalyseData {
    private String textBrut;
    private ArrayList<Purchase> Table = new ArrayList<>();
    private int nbProduct;
    private Context context;

    public AnalyseData(String text, Context context) {
        this.textBrut = text;
        this.context = context;
    }

    // Corriger les mots cles comme TEL, MONTANT, Nombre
    public String correction(String text) {
        String[] tokens = text.split("[ \\n]+");
        String res = "";
        for (int i = 0; i < tokens.length; i++) {
            if (Hamming(tokens[i], "TEL") < 2) {
                //System.out.println(tokens[i]);
                tokens[i] = "TEL";
            }
            if (Hamming(tokens[i], "MONTANT") < 2) {
                // System.out.println(tokens[i]);
                tokens[i] = "MONTANT";
            }
            if (Hamming(tokens[i], "Nombre") < 2) {
                tokens[i] = "Nombre";
            }
            res = res +" "+ tokens[i];
        }
        return res;

    }

    //Enleve les donnees inutiles et le mettre en BBD
    public void clean(String text) {
       text= text.replace(',','.');


        int nbProduct = findNbProduct(text);
        String[] tokens = text.split("\\n");
        int i = 0;
        while ( i < tokens.length && Hamming(tokens[i].split(" ")[0],"TEL") < 2){
            i++;
        }
        i= i+2;// cet ligne depend s'il ya "N° Sirit"
        for (int j = i; j < (i+nbProduct); j++ ){
            String[] lignes = tokens[j].split(" ");
            String nom ="";
            double prix = Double.parseDouble(lignes[lignes.length-3]);
            for (int k = 0; k < lignes.length - 3; k++){
                nom = nom+ " "+ lignes[k];
            }
            Purchase p = new Purchase(nom, prix, 1);
            Table.add(p);
        }

    }

    public String list (String text) {
        clean(text);
        findNbProduct(text);
        String res ="";
        System.out.println(Table.size());
        for (int i = 0; i < Table.size(); i++) {
            res = res + Table.get(i).getName() + "   " + Table.get(i).getPrice() + "    " + Table.get(i).getQuantite();
        }
        return res;
    }

    // ajouter une lettre en n'importe position de mots pour le Hamming
    public String ajouteChar(String s, int i) {
        String n = "";
        for (int k = 0; k < s.length(); k++) {
            if (k < i) {
                n = n + s.charAt(k);
            } else if (k == i) {
                n = n + '*';
            } else {
                n = n + s.charAt(k - 1);
            }
        }
        return n;
    }

    // Comparer et corriger les mots
    public int Hamming(String s, String m) {
        int dist = 0;
        switch (s.length() - m.length()) {

            case 1:
                int i = 0;
                while (i <= m.length() && dist != 1) {
                    dist = 0;
                    String n = ajouteChar(m, i);
                    for (int k = 0; k < m.length(); k++) {
                        if (n.charAt(k) != s.charAt(k)) {
                            dist = dist + 1;
                        }
                    }
                    i = i + 1;
                }
                break;


            case -1:
                int j = 0;
                while (j <= s.length() && dist != 1) {
                    dist = 0;
                    String n = ajouteChar(s, j);
                    for (int k = 0; k < s.length(); k++) {
                        if (n.charAt(k) != m.charAt(k)) {
                            dist = dist + 1;
                        }
                    }
                    j = j + 1;
                }
                break;

            case 0:
                for (int k = 0; k < s.length(); k++) {
                    if (s.charAt(k) != m.charAt(k)) {
                        dist = dist + 1;
                    }
                }
                break;

            default:
                dist = 5;
                break;
        }
        return dist;
    }

    //Corriger les fautes d'analyses
    private String toleranceMot(String text) {

        return null;
    }

    //Cherche le nombre de produit total
    public int findNbProduct(String text) {
        String[] tokens = text.split("\\n");
        for (int i = 0; i < tokens.length; i++){
            String[] words = tokens[i].split(" ");
           // for (int j = 0; j < words.length; j++ ){

                if(Hamming(words[0],"Nombre") < 2) {
                    nbProduct = Integer.parseInt(words[words.length-1]);
                    break;
                }
            //}
        }
        return nbProduct;

    }

    // return le nbProduct
    public int getNbProduct() {
        return nbProduct;
    }

    public static void main(String[] args) throws FileNotFoundException {

        String c = " TEL ; 02.22.51.00.01 \n N ' r R4498809700011 \n PAT VRT FRUIT PEUle 1,28 EUR A \n PATUMES. ENN.,w,. R 1.48 EUR A \n MONTANT \nNombre de produit est 2\n";
        AnalyseData a = new AnalyseData(c);
        System.out.println(a.Hamming("MONTIN", "MONTANT"));
        System.out.println(a.correction(a.textBrut));
        System.out.println(a.findNbProduct(a.textBrut));
        //a.clean(c);
        System.out.println(a.Table.size());
        System.out.println("sdrtdyddrt"+ a.list(a.textBrut));
        //System.out.println("misss");

    }
}
