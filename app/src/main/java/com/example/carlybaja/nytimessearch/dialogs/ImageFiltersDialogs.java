package com.example.carlybaja.nytimessearch.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carlybaja.nytimessearch.R;
import com.example.carlybaja.nytimessearch.models.SearchOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFiltersDialogs extends DialogFragment implements AdapterView.OnItemSelectedListener {


    private SearchOptions searchOptions;

    public interface ImageFiltersDialogListener {
        void onFinishImageFiltersDialog(SearchOptions searchOptions);
    }

    public ImageFiltersDialogs() {}

    public static ImageFiltersDialogs newInstance(String title, SearchOptions searchOptions) {
        ImageFiltersDialogs frag = new ImageFiltersDialogs();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_ImageFilters);
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelable("searchOptions", searchOptions);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_filters, container, false);

        String title = getArguments().getString("title", "Advanced Filters");
        TextView tvImageTitle = (TextView)view.findViewById(R.id.tvImageFilterDialogTitle);
        tvImageTitle.setText(title);

        searchOptions = getArguments().getParcelable("searchOptions");

        // set up spinners and their default selected values
        Spinner spSortOrder = (Spinner)view.findViewById(R.id.spSortOrder);
        ArrayAdapter<CharSequence> sortOrderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_order, android.R.layout.simple_spinner_item);
        sortOrderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(sortOrderAdapter);
        spSortOrder.setSelection(sortOrderAdapter.getPosition(searchOptions.sortOrder));
        spSortOrder.setOnItemSelectedListener(this);

        Spinner spDeskValues = (Spinner)view.findViewById(R.id.spDeskValues);
        ArrayAdapter<CharSequence> DeskValuesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.desk_values, android.R.layout.simple_spinner_item);
        DeskValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeskValues.setAdapter(DeskValuesAdapter);
        spDeskValues.setSelection(DeskValuesAdapter.getPosition(searchOptions.deskValues));
        spDeskValues.setOnItemSelectedListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // set up button click listeners
        Button btnSave = (Button)view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFiltersDialogListener listener = (ImageFiltersDialogListener) getActivity();
                listener.onFinishImageFiltersDialog(searchOptions);
                dismiss();
            }
        });

        Button btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = (String)parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.tvBeginDate:
              //  searchOptions.beginDate = selectedValue;
                break;
            case R.id.spSortOrder:
                searchOptions.sortOrder = selectedValue;
                break;
            case R.id.spDeskValues:
                searchOptions.deskValues = selectedValue;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }







}
