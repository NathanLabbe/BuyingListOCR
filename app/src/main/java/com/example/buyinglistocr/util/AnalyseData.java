package com.example.buyinglistocr.util;

import android.content.Context;

import com.example.buyinglistocr.model.CorrespondenceManager;
import com.example.buyinglistocr.model.Item;
import com.example.buyinglistocr.model.ItemManager;
import com.example.buyinglistocr.model.Product;
import com.example.buyinglistocr.model.ProductManager;
import com.example.buyinglistocr.model.Purchase;

import java.util.ArrayList;

public class AnalyseData {
    private String textBrut;
    public static ArrayList<Purchase> table = new ArrayList<>();
    private ArrayList<Purchase> correspondanceTable = new ArrayList<>();
    private long idList;
    private Context context;

    private ProductManager productManager;
    private ItemManager itemManager;
    private CorrespondenceManager correspondenceManager;

    public String getTextBrut() {
        return textBrut;
    }

    public ArrayList<Purchase> getTable() {
        return table;
    }

    public ArrayList<Purchase> getCorrespondanceTable() {
        return correspondanceTable;
    }

    public AnalyseData(String text, Context context, long idList) {
        this.textBrut = text;
        this.context = context;
        this.idList = idList;
        productManager = new ProductManager(context);
        itemManager = new ItemManager(context);
    }


    //Choisit les donnees utiles et le mettre en Table de purchase
    public void clean(String text) {
        text= text.replace(',','.');
        String[] tokens = text.split("\\n");

        for (int j = 0; j < tokens.length-1; j++) {
            String[] ligne = tokens[j].split(" ");
            String nom = "";
            double prix = 0.0;
            if (ligne.length > 1 && ligne[ligne.length - 2].equals("EUR")) {
                prix = Double.parseDouble(ligne[ligne.length - 3]);
                for (int k = 0; k < ligne.length - 3; k++) {
                    nom = nom + " " + ligne[k];
                }
                Purchase p = new Purchase(nom, prix, 1);// quantite defaut
                table.add(p);
            }else {
                for (int k = 0; k < ligne.length; k++) {
                    nom = nom + " " + ligne[k];
                }
                String[] ligne2 = tokens[j+1].split(" ");
                if (ligne2.length > 2 && ligne2[1].equals("kg")) {
                        prix = Double.parseDouble(ligne2[ligne2.length - 3]);
                        j++;
                        Purchase p = new Purchase(nom, prix, 1);// quantite defaut
                        table.add(p);
                }
            }
        }
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

    // Hamming pour une list des mots
    public boolean HammList(String s1, String s2){
        int res = 0;
        int longeur = s1.split(" ").length;
        String h1 = "";
        if (s1.charAt(0) == ' '){
            for (int k = 1; k< s1.length(); k++){
                h1 = h1 + s1.charAt(k);
            }
        }
        String[] token1 = h1.split(" ");
        String[] token2 = s2.split(" ");
        if (token1.length <= token2.length) {
            for (int i = 0; i < token1.length - 1; i++) {
                res = res + Hamming(token1[i], token2[i]);
            }
        } else {
            for (int j = 0; j < token2.length -1; j++){
                res = res + Hamming(token1[j], token2[j]);
            }
        }
        return res <= longeur;
    }

    /**
     * From the arrayList of purchase we create a arrayList of purchase with the correspondance of each name of the first arrayList
     * @param table
     */
    public void tableToCorrespondenceTable (ArrayList<Purchase> table){
        ArrayList<Product> products = productManager.getAll(666);
        for (int k = 0; k < table.size(); k++){
            correspondanceTable.add(table.get(k));
        }
        int res = -1;
        for(int i = 0; i<table.size(); i++){
            for(int j = 0; j<products.size(); j++){
                if (HammList(table.get(i).getName(), products.get(j).getName())){
                    res = j;
                }
            }
            if(res != -1){

                //correspondanceTable.get(i).setName(products.get(res).getCorrespondence());
                //System.out.println("Name is "+ products.get(res).getCorrespondence());
                res = -1;
            }
        }
    }

    /**
     * We compare the correspondance table to the elements on our list and we delete
     * @param correspondenceTable
     */
    public double removePurchase (ArrayList<Purchase> correspondenceTable){
        double spent = 0.0;
        ArrayList<Item> items = itemManager.get(idList);
        for(int i = 0; i<correspondenceTable.size(); i++){
            for(int j = 0; j<items.size(); j++){
                if(correspondenceTable.get(i).getName().toLowerCase().equals(items.get(j).getName().toLowerCase()) ){
                    if(items.get(j).getQuantityGot()<items.get(j).getQuantityDesired()){
                        items.get(j).setQuantityGot(items.get(j).getQuantityGot()+1);
                        itemManager.update(items.get(j));
                        //System.out.println(items.get(j).getName() + " : " + items.get(j).getQuantityGot());
                        if(items.get(j).getQuantityGot()==items.get(j).getQuantityDesired()){

                            items.get(j).setStatus(1);
                            itemManager.update(items.get(j));
                        }
                        spent = spent + correspondenceTable.get(i).getPrice();
                        //System.out.printf("Your spent is %s%n", spent);
                    } else if (items.get(j).getStatus()!=1){
                        items.get(j).setStatus(1);
                        itemManager.update(items.get(j));
                    } else {
                        //System.out.println(items.get(j).getName() + " STATUT : " + items.get(j).getStatus());
                    }
                }
            }
        }
        return spent;
    }



}
