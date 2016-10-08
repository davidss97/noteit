
package com.noteitapp.hack.noteit;

        import android.app.Activity;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
        import android.support.v7.widget.Toolbar;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_enter_code);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        find = (Button) findViewById(R.id.button2);
        final TextView textView = (TextView) findViewById(R.id.section_label);


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
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    botopremut();
                    return true;
                }
                return false;
            }
        });

    }

    public void botopremut() {
        String codi = autoComplete.getText().toString();
        int esta = 0;
        for (int i = 0; i < CLAUS.length; i++) {
            if (codi.equals(CLAUS[i])) {
                esta = 1;
                Toast toastWIN = Toast.makeText(this.getBaseContext(), codi, 5);
                toastWIN.show();
                break;
            }
        }
        if (esta == 0) {
            Toast toastLOOSE = Toast.makeText(this.getBaseContext(), "Image not found. Please enter a valid key", 7);
            toastLOOSE.show();
        }
    }
}