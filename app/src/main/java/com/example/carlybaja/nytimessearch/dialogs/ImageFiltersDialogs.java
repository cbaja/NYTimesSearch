package com.example.carlybaja.nytimessearch.dialogs;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carlybaja.nytimessearch.R;
import com.example.carlybaja.nytimessearch.models.SearchOptions;
import com.example.carlybaja.nytimessearch.utils.DatePickerFragment;

import java.util.Calendar;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.activity_second);

        EditText etBeginDate =(EditText) view.findViewById(R.id.etBeginDate);
        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();



            }
        });
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
      //  android.support.v4.app.DialogFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "datePicker");


    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            EditText etBeginDate =(EditText) view.findViewById(R.id.etBeginDate);

            etBeginDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));

        }
    };

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = (String)parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.tvBeginDate:
                searchOptions.beginDate = selectedValue;
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
