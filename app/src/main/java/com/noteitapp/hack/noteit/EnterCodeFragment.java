

package com.noteitapp.hack.noteit;

        import android.content.Context;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;


public class EnterCodeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public EnterCodeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EnterCodeFragment newInstance(int sectionNumber) {
        EnterCodeFragment fragment = new EnterCodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    Button find ;

    String[] CLAUS = new String[]{
            "AA", "AB", "BA", "BB"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_code, container, false);
        find = (Button) rootView.findViewById(R.id.button2);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        final AutoCompleteTextView autoComplete = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, CLAUS);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(adapter);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codi = autoComplete.getText().toString();
                int esta = 0;
                for (int i = 0; i < CLAUS.length; i++) {
                    if (codi.equals(CLAUS[i])){
                        esta = 1;
                        Toast toastWIN = Toast.makeText(getContext(), codi, 15);
                        toastWIN.show();
                        break;
                    }
                }
                if (esta == 0){
                    Toast toastLOOSE = Toast.makeText(getContext(), "Image not found. Please enter a valid key", 7);
                    toastLOOSE.show();
                }
            }
        });
        return rootView;
    }
}
