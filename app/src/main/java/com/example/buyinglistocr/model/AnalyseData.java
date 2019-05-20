package com.example.buyinglistocr.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseData {
    private String textBrut;
    private HashMap<String, ArrayList<Float>> Table;

    public AnalyseData(String text) {
        this.textBrut = text;
    }

    public String[] correction(String text) {
        String[] tokens = text.split("[ \\n]+");
        for (int i = 0; i < tokens.length; i++) {
            if (Hamming(tokens[i], "TEL") < 2) {
                //System.out.println(tokens[i]);
                tokens[i] = "TEL";
            }
            if (Hamming(tokens[i], "MONTANT") < 2) {
                // System.out.println(tokens[i]);
                tokens[i] = "MONTANT";
            }
        }
        return tokens;

    }

    //Enleve les donnees inutiles
    public String clean(String text) {
        String res = "";
        Boolean start = false;
        String[] tokens = correction(text);
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("TEL")) {
                start = true;
            }
            if (start) {
                res = res + " " + tokens[i];
            }
            if (tokens[i].equals("MONTANT")) {
                start = false;
                //res = res + " " + tokens[i];
            }
        }

        return res;

    }


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

    //Ajoute les noms de produit, qte, prix ..Etc  à la HashTable
    private void ajoutTable(String text) {


    }

    public static void main(String[] args) throws FileNotFoundException {
        AnalyseData a = new AnalyseData("go gog go gog ");
        String[] b = new String[6];
        b[0] = "TEN";
        b[1] = "A";
        b[2] = "B";
        b[3] = "CCCC";
        b[4] = "MOOTANT";
        b[5] = "TENnnn";
        String c = " JEEEE SUIS SUN TEN LOL LILY MONNANT MIS";
        System.out.println(a.Hamming("MONTIN", "MONTANT"));
        System.out.println(a.clean(c));
        //System.out.println("misss");

    }
}
