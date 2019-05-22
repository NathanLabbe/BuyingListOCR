package com.example.buyinglistocr.model;

import android.content.Context;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AnalyseData {
    private String textBrut;
    private ArrayList<Purchase> table = new ArrayList<>();
    private ArrayList<Purchase> correspondanceTable = new ArrayList<>();
    private int nbProduct;
    private long idList;
    private Context context;
    private ProductDAO productDao;
    private ItemDAO itemDAO;

    public String getTextBrut() {
        return textBrut;
    }

    public void setTextBrut(String textBrut) {
        this.textBrut = textBrut;
    }

    public ArrayList<Purchase> getTable() {
        return table;
    }

    public void setTable(ArrayList<Purchase> table) {
        this.table = table;
    }

    public ArrayList<Purchase> getCorrespondanceTable() {
        return correspondanceTable;
    }

    public void setCorrespondanceTable(ArrayList<Purchase> correspondanceTable) {
        this.correspondanceTable = correspondanceTable;
    }

    public AnalyseData(String text, Context context, long idList) {
        this.textBrut = text;
        this.context = context;
        this.idList = idList;
        productDao = new ProductDAO(context);
        itemDAO = new ItemDAO(context);
    }

    // Corriger les mots cles comme TEL, MONTANT, Nombre
    public void correction(String text) {
        String[] tokens = text.split("[ \\n]+");
        String res = "";
        int count=0;
        boolean start = false;
        for (int i = 0; i < tokens.length; i++) {
            if (start) {count++;}
            if (count == 3){
                if (!(Hamming(tokens[i], "N°")==0)){
                    res = res + "\n";
                } else {
                    count = -1;
                }

            }
            if (Hamming(tokens[i], "TEL") < 2) {
                start = true;
                tokens[i] = "\nTEL";
                res = res +" "+ tokens[i];
            }
            else if (Hamming(tokens[i], "N°")==0){
                tokens[i] = "\nN°";
                res = res +" "+ tokens[i]+ tokens[i+1];
            }
            else if (Hamming(tokens[i], "MONTANT") < 2) {
                tokens[i] = "MONTANT";
                res = res +" "+ tokens[i];
            }
            else if (Hamming(tokens[i], "Nombre") < 2) {
                tokens[i] = "Nombre";
                res = res +" "+ tokens[i];
            }
            else if (tokens[i].equals("A")){
                res = res + " A\n";
            }
            else if (Hamming(tokens[i], "EUR")<2 && i<tokens.length-1 && !tokens[i+1].equals("A")){
                res = res + " EUR\n";
            }
            else if (i==0 || res.endsWith("\n")){
                res = res + tokens[i];
            }else {res = res +" "+ tokens[i];}
        }
        this.setTextBrut(res);

    }

    //Enleve les donnees inutiles et le mettre en Table
    public void clean(String text) {
        text= text.replace(',','.');
        int nbProduct = findNbProduct(text);
        String[] tokens = text.split("\\n");
        int i = 0;
        while ( i < tokens.length && Hamming(tokens[i].split(" ")[0],"TEL") < 2){
            i++;
        }
        i= i+3;// cet ligne depend s'il ya "N° Sirit"
        for (int j = i; j < (i+nbProduct); j++ ){
            String[] lignes = tokens[j].split(" ");
            String nom ="";
            //double prix = Double.parseDouble(lignes[lignes.length-3]);
            double prix = 0;
            for (int k = 0; k < lignes.length - 3; k++){
                nom = nom+ " "+ lignes[k];
            }
            Purchase p = new Purchase(nom, prix, 1);// quantite defaut
            table.add(p);
        }

    }


    //Cherche le nombre de produit total
    public int findNbProduct(String text) {
        String[] tokens = text.split("\\n");
        for (int i = 0; i < tokens.length; i++){
            String[] words = tokens[i].split(" ");
            // for (int j = 0; j < words.length; j++ ){
            if(Hamming(words[1].toLowerCase(),"nombre") < 2) {
                nbProduct = Integer.parseInt(words[words.length-1]);
                break;
            }
            //}
        }
        return nbProduct;

    }


    //Affichage la Table
    public String list (String text) {
        clean(text);
        findNbProduct(text);
        String res ="";
        for (int i = 0; i < table.size(); i++) {
            res = res + table.get(i).getName() + "   " + table.get(i).getPrice() + "    " + table.get(i).getQuantite();
        }
        return res;
    }

    // ajouter une lettre en n'importe position de mot pour le Hamming
    public String ajouteChar(String s, int i) {
        String n = "";
        for (int k = 0; k <= s.length(); k++) {
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
        int res = 50;
        switch (s.length() - m.length()) {

            case 1:
                int i = 0;
                while (i <= m.length()) {
                    dist = 0;
                    String n = ajouteChar(m, i);
                    for (int k = 0; k < n.length(); k++) {
                        if (n.charAt(k) != s.charAt(k)) {
                            dist = dist + 1;
                        }
                    }
                    if (dist < res) res = dist;
                    i = i + 1;
                }
                break;


            case -1:
                int j = 0;
                while (j <= s.length() ) {
                    dist = 0;
                    String n = ajouteChar(s, j);
                    for (int k = 0; k < n.length(); k++) {
                        if (n.charAt(k) != m.charAt(k)) {
                            dist = dist + 1;
                        }
                    }
                    if (dist < res) res = dist;
                    j = j + 1;
                }
                break;

            case 0:
                for (int k = 0; k < s.length(); k++) {
                    if (s.charAt(k) != m.charAt(k)) {
                        dist = dist + 1;
                    }
                }
                res = dist;
                break;

            default:
                res = 50;
                break;
        }
        return res;
    }


    public void tableToCorrespondenceTable (ArrayList<Purchase> table){
        ArrayList<Product> products = productDao.getAll(666);
        for (int k = 0; k < table.size(); k++){
            correspondanceTable.add(table.get(k));
        }
        int hamming = 50;
        int res = -1;
        for(int i = 0; i<table.size(); i++){
            for(int j = 0; j<products.size(); j++){
                int tmp = Hamming(table.get(i).getName(), products.get(j).getName());
                if (tmp < hamming && tmp < 3){
                    hamming = tmp;
                    res = j;

                }
            }
            if(res != -1){
                correspondanceTable.get(i).setName(products.get(res).getCorrespondence());
                res = -1;
            }
            hamming = 50;

        }
    }

    public void removePurchase (ArrayList<Purchase> correspondenceTable){
        ArrayList<Item> items = itemDAO.get(idList);
        for(int i = 0; i<correspondenceTable.size(); i++){
            for(int j = 0; j<items.size(); j++){
                if(correspondenceTable.get(i).getName().equals(items.get(j).getName())){
                    itemDAO.delete(items.get(j).getId());
                }
            }
        }
    }




}
