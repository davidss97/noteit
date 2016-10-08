
package com.noteitapp.hack.noteit;

        import android.app.Activity;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;

        import java.io.File;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Date;


public class MainActivity extends Activity {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    Button find;
    AutoCompleteTextView autoComplete;

    String[] CLAUS = new String[]{
            "AA", "AB", "BA", "BB"
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_enter_code);


        find = (Button) findViewById(R.id.button2);


        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(),
                android.R.layout.simple_dropdown_item_1line, CLAUS);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(adapter);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botopremut();
            }
        });

        autoComplete.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    botopremut();
                    return true;
                }
                return false;
            }
        });

        Button cam = (Button)findViewById(R.id.button_camara);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void botopremut() {
        String codi = autoComplete.getText().toString();
        int esta = 0;
        for (int i = 0; i < CLAUS.length; i++) {
            if (codi.equals(CLAUS[i])) {
                esta = 1;
                Toast toastWIN = Toast.makeText(this.getBaseContext(), codi, Toast.LENGTH_LONG);
                toastWIN.show();
                break;
            }
        }
        if (esta == 0) {
            Toast toastLOOSE = Toast.makeText(this.getBaseContext(), "Image not found. Please enter a valid key", Toast.LENGTH_LONG);
            toastLOOSE.show();
        }
    }


    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    public void takePhoto() { //View view
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
////////////////////////////////
        createImageFolder();
        try {
            imageUri = Uri.fromFile( createImageFileName() );
        } catch (IOException e) {
            e.printStackTrace();
        }
  ////////////////////////////
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageUri);
                //Uri.fromFile(photo));
        //imageUri = Uri.fromFile(photo);

        startActivityForResult(intent, TAKE_PICTURE);
    }
///////////////////////////////////////////////////////////////////////////
    File mImageFolder;
    private void createImageFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageFolder = new File(imageFile, "NoteIt");
        if(!mImageFolder.exists()) {
            mImageFolder.mkdirs();
        }
    }
    private File createImageFileName() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "IMAGE_" + timestamp + "_";
        File imageFile = File.createTempFile(prepend, ".jpg", mImageFolder);
        //imageUri = Uri.fromFile(imageFile);
        String mImageFileName = imageFile.getAbsolutePath();
        return imageFile;
    }////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;

                    try {
                        Toast.makeText(this, selectedImage.toString(),Toast.LENGTH_LONG).show();
                        bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);


                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}
