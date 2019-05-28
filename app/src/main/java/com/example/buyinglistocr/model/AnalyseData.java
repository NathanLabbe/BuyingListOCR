package com.example.buyinglistocr.model;

import android.content.Context;

import java.util.ArrayList;

public class AnalyseData {
    private String textBrut;
    public static ArrayList<Purchase> table = new ArrayList<>();
    private ArrayList<Purchase> correspondanceTable = new ArrayList<>();
    private int nbProduct;
    private long idList;
    private Context context;
    private ProductDAO productDao;
    private ItemDAO itemDAO;
    private ListDAO list;

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

    public AnalyseData(String text, long idList) {
        this.textBrut = text;
        this.idList = idList;
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
            else if (Hamming(tokens[i], "TOTAL") < 2){
                tokens[i] = "\nTOTAL";
                res = res + " "+ tokens[i];
            }
            else if (tokens[i].equals("A")||tokens[i].equals("B")||tokens[i].equals("C")){
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
        System.out.println("Nombre de product is "+ nbProduct);
        String[] tokens = text.split("\\n");
        int i = 0;
        while ( i < tokens.length && (Hamming(tokens[i].split(" ")[0].toLowerCase(),"tel") >= 2)){
            i++;
        }
        i= i+1;// cet ligne depend s'il ya "N° Sirit"
        //System.out.println(i);
        for (int j = i; j < (i+nbProduct); j++ ){
            String[] lignes = tokens[j].split(" ");
            String nom ="";
            double prix;
            if (lignes[lignes.length-1].length() == 1) {
                prix = Double.parseDouble(lignes[lignes.length - 3]);
                for (int k = 0; k < lignes.length - 3; k++) {
                    nom = nom + " " + lignes[k];
                }
            }else {
                for (int k = 0; k < lignes.length; k++) {
                    nom = nom + " " + lignes[k];
                }
                lignes = tokens[j+1].split(" ");
                prix = Double.parseDouble(lignes[lignes.length - 3]);
                j++;
                i++;
            }
            Purchase p = new Purchase(nom, 0.0, 1);// quantite defaut
            table.add(p);
        }

    }


    //Cherche le nombre de produit total
    public int findNbProduct(String text) {
        String[] tokens = text.split("\\n");
        for (int i = 0; i < tokens.length; i++){
            String[] words = tokens[i].split(" ");
            // for (int j = 0; j < words.length; j++ ){
            if(Hamming(words[0],"Nombre") < 2) {
                nbProduct = Integer.parseInt(words[words.length-1]);
                //System.out.println("nb Product is"+nbProduct);
                break;
            }
            //}
        }
        return nbProduct;

    }


    //Affichage la Table
    public String list (String text) {
        clean(text);
        //findNbProduct(text);
        String res ="";
        for (int i = 0; i < table.size(); i++) {
            res = res + table.get(i).getName() + "   " + table.get(i).getPrice() + "    " + table.get(i).getQuantite()+"\n";
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

    /**
     * From the arrayList of purchase we create a arrayList of purchase with the correspondance of each name of the first arrayList
     * @param table
     */
    public void tableToCorrespondenceTable (ArrayList<Purchase> table){
        ArrayList<Product> products = productDao.getAll(666);
        //System.out.println(products.get(0).getName());
        //System.out.println(products.get(0).getName());
        for (int k = 0; k < table.size(); k++){
            correspondanceTable.add(table.get(k));
        }
        int hamming = 50;
        int res = -1;
        for(int i = 0; i<table.size(); i++){
            for(int j = 0; j<products.size(); j++){
                int tmp = Hamming(table.get(i).getName(), products.get(j).getName());
                //System.out.println(table.get(i).getName());
                if (tmp < hamming && tmp < 5){
                    hamming = tmp;
                    res = j;

                }
            }
            if(res != -1){
                correspondanceTable.get(i).setName(products.get(res).getCorrespondence());
                correspondanceTable.get(i).setPrice(table.get(i).getPrice());
                System.out.println("Name is "+ products.get(res).getCorrespondence());
                res = -1;
            }
            hamming = 50;

        }
    }

    /**
     * We compare the correspondance table to the elements on our list and we delete
     * @param correspondenceTable
     */
    public void removePurchase (ArrayList<Purchase> correspondenceTable){
        double spent = 0;
        List lists =  list.getList(idList);
        ArrayList<Item> items = itemDAO.get(idList);
        for(int i = 0; i<correspondenceTable.size(); i++){
            for(int j = 0; j<items.size(); j++){
                if(correspondenceTable.get(i).getName().toLowerCase().equals(items.get(j).getName().toLowerCase())){
                    if(items.get(j).getQuantityGot()<items.get(j).getQuantityDesired()){
                        items.get(j).setQuantityGot(items.get(j).getQuantityGot()+1);
                        itemDAO.update(items.get(j));
                        System.out.println(items.get(j).getName() + " : " + items.get(j).getQuantityGot());
                        if(items.get(j).getQuantityGot()==items.get(j).getQuantityDesired()){

                            items.get(j).setStatus(1);
                            itemDAO.update(items.get(j));
                        }
                        spent = spent + correspondenceTable.get(i).getPrice();
                        System.out.println("Your spent is "+ spent);
                    } else if (items.get(j).getStatus()!=1){
                        items.get(j).setStatus(1);
                        itemDAO.update(items.get(j));
                    } else {
                        System.out.println(items.get(j).getName() + " STATUT : " + items.get(j).getStatus());
                    }
                }
            }
        }
        lists.setSpent(spent);
        list.update(lists);
    }



}
