package com.example.buyinglistocr.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyinglistocr.BuildConfig;
import com.example.buyinglistocr.R;
import com.example.buyinglistocr.model.AnalyseData;
import com.example.buyinglistocr.model.Item;
import com.example.buyinglistocr.model.ItemDAO;
import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.model.ListDAO;
import com.example.buyinglistocr.model.ProductDAO;
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

    // access to the database
    private ItemDAO itemDAO;
    private ListDAO listDAO;

    // reference
    FloatingActionButton addElementBtn;
    android.widget.ListView listView;

    // attribute
    long idList;
    String listName;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        itemDAO = new ItemDAO(ListView.this);
        listDAO = new ListDAO(ListView.this);

        // get the idList from our current list
        Intent intent = getIntent();
        idList = intent.getLongExtra("idList", 0);
        listName = intent.getStringExtra("listName");
        System.out.println("ListView : " + idList);

        // gestion toolbar
        Toolbar toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(listName);

        // display reference on the activity view
        listView = (android.widget.ListView) findViewById(R.id.activity_list_view_list);

        // display products of our current list
        viewData(idList);

        // TODO
        FloatingActionButton addElementBtn = findViewById(R.id.activity_list_view_add_new_elt);
        addElementBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                addItemsView(view);

            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // item informations
                Item item = (Item) listView.getItemAtPosition(position);
                // TEST
                System.out.println("NAME : " + item.getName());
                System.out.println("ID : " + item.getId());

                Intent ModifyElementIntent = new Intent(ListView.this, ModifyElement.class);
                ModifyElementIntent.putExtra("idItem", item.getId());
                ModifyElementIntent.putExtra("idList", idList);
                startActivity(ModifyElementIntent);
            }
        });

        final Activity activity = this;
        checkPermission();
        this.findViewById(R.id.buttonTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                dispatchTakePictureIntent();
            }
        });
    }


    //Affichage du menu dans la barre d'action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions liées à chaque items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()){
            /**BOUTON DELETE*/
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Boite de dialogue
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listDAO.delete(idList);
                                        Intent MainIntent = new Intent(ListView.this, MainActivity.class);
                                        startActivity(MainIntent);
                                    }
                                }
                        )
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_list, null);
                builder2.setView(customLayout);

                // Define the positive button
                builder2.setPositiveButton("Rename", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText editText = customLayout.findViewById(R.id.name);

                        List list = new List(listDAO.getList(idList).getId(), editText.getText().toString(), listDAO.getList(idList).getSpent());

                        listDAO.update(list);
                        recreate();

                    }

                });

                // Create and show the alert dialog
                AlertDialog dialog = builder2.create();
                dialog.show();

                break;


        }
        //Toast.makeText(this, msg+" Checked", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display all products of a list
     * @param id
     */
    private void viewData(long id) {

        ArrayList<Item> items = itemDAO.get(idList);
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            listItems.add(it.next().getName());
        }
        // TODO
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);

    }

    /**
     * Launch AddProduct activity
     * @param v
     */
    public void addItemsView(View v) {

        Intent AddElementIntent = new Intent(ListView.this, AddElement.class);
        // send the idList to the AddProduct activity
        AddElementIntent.putExtra("idList", idList);
        startActivity(AddElementIntent);

    }

    /**
     * PARTIE PHOTO
     */
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
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "aàAbBcçCdDeEéèêfFgGhHiIjJkKlLmMnNoôOpPqQrRsStTuùUvVwWxXyYzZ1234567890\',.;+-_%/ ");
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
        AnalyseData test = new AnalyseData("a");
        retStr = test.clean(retStr);
        return retStr;
    }
}