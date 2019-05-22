package com.example.buyinglistocr.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyinglistocr.BuildConfig;
import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.AdapterItems;
import com.example.buyinglistocr.model.AdapterLists;
import com.example.buyinglistocr.model.AnalyseData;
import com.example.buyinglistocr.model.Item;
import com.example.buyinglistocr.model.ItemDAO;
import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.model.ListDAO;
import com.example.buyinglistocr.model.Product;
import com.example.buyinglistocr.model.ProductDAO;
import com.example.buyinglistocr.model.Shop;
import com.example.buyinglistocr.model.ShopDAO;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class ListView extends AppCompatActivity {

    // The recycler view
    private RecyclerView rv;

    // access to the database
    private ListDAO listDAO;
    private ItemDAO itemDAO;
    private ProductDAO productDAO;
    private ShopDAO shopDAO;

    // The current list
    private List list;

    // The ArrayList of Item
    private ArrayList<Item> items;

    // The List id
    long idList;
    String listName;
    ArrayList<String> listItems = new ArrayList<String>();

    /**
     * Partie photo
     */

    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Tess";
    private TessBaseAPI tessBaseAPI;
    private Uri outputFileDir;
    private String mCurrentPhotoPath;
    HashMap<String, ArrayList<String>> listProduit;

    /**
     * Partie photo
     */

    /**
     * Method that be executed during the creation of the activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Get the parameter
        Intent intent = getIntent();
        list = intent.getParcelableExtra("list");
        idList = list.getId();
        listName = list.getName();

        // Get the list and the idem DAO
        itemDAO = new ItemDAO(ListView.this);
        listDAO = new ListDAO(ListView.this);
        productDAO = new ProductDAO(ListView.this);
        shopDAO = new ShopDAO(ListView.this);

        Shop intermarche = new Shop(666,"intermarche");
        shopDAO.add(intermarche);

        Product p = new Product(0, "SUZI WAN NOUILLES AU", "pâtes",666);
        productDAO.add(p);
        System.out.println("PUTAIN DE TAILLE DE PRODUCTS : "+productDAO.getAll(666).size());



        // Get the data
        items = itemDAO.get(list.getId());

        // Define the recycler view
        rv = findViewById(R.id.items);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new AdapterItems(this, items, rv));

        // Define the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(list.getName());

        // Define the buttonAddItem
        FloatingActionButton buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Launch the alert dialog
                showAlertDialogButtonClicked(view);

            }

        });

        /*******************************************************************************************
         * Photo
         *******************************************************************************************/

        final Activity activity = this;

        checkPermission();

        this.findViewById(R.id.buttonTakePhoto).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkPermission();
                dispatchTakePictureIntent();

            }

        });

        /*******************************************************************************************
         * Photo
         *******************************************************************************************/

    }

    /**
     * Method that be executed during the resume of the activity
     */
    @Override
    public void onResume(){

        super.onResume();

        // Notify the data set changed
        rv.getAdapter().notifyDataSetChanged();

    }

    /**
     * Allow to display the menu on the toolbar
     * @param menu - The menu
     * @return - A boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_listview,menu);

        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Allow to define the action for each item
     * @param item - The item
     * @return - A boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Suppression")
                    .setMessage("Etes vous sur de vouloir supprimer cette liste ?")
                    .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            listDAO.delete(list.getId());

                            Intent MainIntent = new Intent(ListView.this, MainActivity.class);
                            startActivity(MainIntent);

                        }

                    })

                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            closeContextMenu();

                        }

                    })

                    .create()
                    .show();

                break;

            case R.id.modify:

                // Create an alert builder
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

                // Set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_item, null);
                builder2.setView(customLayout);

                // Define the positive button
                builder2.setTitle("Modifier")

                    .setPositiveButton("Renommer", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText editText = customLayout.findViewById(R.id.name);

                            list.setName(editText.getText().toString());

                            listDAO.update(list);

                            recreate();

                        }

                    })

                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            closeContextMenu();

                        }

                    });

                // Create and show the alert dialog
                AlertDialog dialog = builder2.create();
                dialog.show();

                break;


        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Allow to define the alert dialog
     * @param view - The view
     */
    public void showAlertDialogButtonClicked(View view) {

        final Context context = this;

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_item, null);
        builder.setView(customLayout);

        // Define the positive button
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText editText = customLayout.findViewById(R.id.name);

                if(isPresent(editText.getText().toString(), list.getId())) {

                    Toast toast = Toast.makeText(context, "Ce nom existe déjà", Toast.LENGTH_SHORT);
                    toast.show();

                } else {

                    // Create the new item with the data of the edit text
                    Item item = new Item(editText.getText().toString(), 0, 0, new String(), 0, list.getId());

                    // Add this item to the database and get it id
                    long idItem = itemDAO.add(item);

                    item.setId(idItem);

                    // Add this item to the ArrayList
                    items.add(item);

                    // Notify the recycler view that a data is inserted
                    rv.getAdapter().notifyItemInserted(items.size() - 1);

                }

            }

        });

        // Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Allow to know if an item exist with the same name in this list
     * @param name - The name
     * @param idList - The list id
     * @return - True if the name exist, false else
     */
    public boolean isPresent(String name, long idList) {

        // The return value
        Boolean ret = false;

        // Get all items of our list
        ArrayList<Item> items = itemDAO.get(idList);

        for(Item item : items) {

            if(item.getName().equals(name)) {

                ret = true;

            }

        }

        return ret;

    }

    /*******************************************************************************************
     * Photo
     *******************************************************************************************/

    //Gestion Des Permissions
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 122);
        }


    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1024);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1024) {
            if (resultCode == Activity.RESULT_OK) {
                prepareTessData();
                startOCR(outputFileDir);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Result canceled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Activity result failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void prepareTessData() {
        try {
            File dir = getExternalFilesDir(TESS_DATA);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
                }
            }
            String fileList[] = getAssets().list("");
            for (String fileName : fileList) {
                String pathToDataFile = dir + "/" + fileName;
                if (!(new File(pathToDataFile)).exists()) {
                    InputStream in = getAssets().open(fileName);
                    OutputStream out = new FileOutputStream(pathToDataFile);
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) > 0) {
                        out.write(buff, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void startOCR(Uri imageUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 6;
            System.out.println(options.toString());

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
            String result = this.getText(bitmap);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        recreate();
    }

    //ORIENTATION BITMAP
    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        Bitmap bOutput;
        float degreees = 90;//rotation degree
        Matrix matrix = new Matrix();
        matrix.setRotate(degreees);
        bOutput = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
        return bOutput;
    }

    private String getText(Bitmap bitmap) {
        try {
            tessBaseAPI = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        String dataPath = getExternalFilesDir("/").getPath() + "/";
        tessBaseAPI.init(dataPath, "fra",TessBaseAPI.OEM_TESSERACT_ONLY);
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "aàAbBcçCdDeEéèêfFgGhHiIjJkKlLmMnNoôOpPqQrRsStTuùUvVwWxXyYzZ1234567890°\',.;+*-_%/ ");
        /**
         * Selon le téléphone commentez
         */
        //tessBaseAPI.setImage(rotateBitmap(bitmap, 90));
        tessBaseAPI.setImage(bitmap);


        String retStr = "No result";
        try {
            retStr = tessBaseAPI.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        Bitmap bitmapfinal = WriteFile.writeBitmap(tessBaseAPI.getThresholdedImage());

        tessBaseAPI.end();
        /**Analyse Data*/
        AnalyseData test = new AnalyseData(retStr, ListView.this, idList);
        test.correction(test.getTextBrut());
        test.clean(test.getTextBrut());
        test.tableToCorrespondenceTable(test.getTable());
        test.removePurchase(test.getCorrespondanceTable());
        for(int i = 0; i<test.getCorrespondanceTable().size(); i++) {
            System.out.println("Element TABLE CORRES numéro"+i+" "+test.getCorrespondanceTable().get(i).getName());
        }
        return retStr;
    }
}