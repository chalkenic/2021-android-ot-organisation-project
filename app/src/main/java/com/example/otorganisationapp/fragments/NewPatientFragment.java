package com.example.otorganisationapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.repository.OTDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewPatientFragment extends Fragment {

    OTDatabase db;

    MainActivity activity;


    public NewPatientFragment() {}

    /*
    Stores currently-selected gender on form.
     */
    Gender theGender;

//    /*
//    Contains date of birth.
//     */
    TextView patientDOBField;


//    private FragmentActivity theContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_patient, container, false);

        db = OTDatabase.getDatabase(getContext());
        activity = (MainActivity)getActivity();

        AppCompatButton dateButton = v.findViewById(R.id.patient_form_choose_date);

        /**
         * Code adapted from Abhi Android DatePicker tutorial: DatePicker Tutorial With Example In Android Studio
         * Available at: https://abhiandroid.com/ui/datepicker
         */
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar tempCalendar = Calendar.getInstance();
                int year = tempCalendar.get(Calendar.YEAR);
                int month = tempCalendar.get(Calendar.MONTH);
                int day = tempCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), datePickerListener,
                        year, month, day);

                datePickerDialog.show();
            }
        });

        /**
         *  Sourcing patient date of birth method.
         */
        RadioGroup genderRadio = (RadioGroup) v.findViewById(R.id.patient_form_gender_radio);

        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.radio_gender_male:
                        theGender = Gender.Male;
                        break;

                    case R.id.radio_gender_female:
                        theGender = Gender.Female;
                        break;

                    case R.id.radio_gender_undefined:
                        theGender = Gender.Undefined;
                        break;
                }
            }
        });

        AppCompatButton submitButton = (AppCompatButton) v.findViewById(R.id.patient_form_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Stores error dependant on which field is empty.
                String error = "";

                // Define all local fields that'll be added into Patient object.
                String patientTitle;
                String patientName = null;
                String patientGender;
                String patientNHSNumber = null;
                Date patientDob;
                String patientPhoneNo;

                try {

                    // Source title from EditText box & insert into String.
                    EditText title = (EditText) getActivity().findViewById(R.id.patient_form_title);



                    patientTitle = title.getText().toString();
                    Log.d("new_pat", "1: " + title.getText().toString());

                    // Source patient name from EditText box & insert into String.
                    EditText name = (EditText) getActivity().findViewById(R.id.patient_form_name);
                    Log.d("new_pat", "2: " + name.getText().toString());
                    patientName = name.getText().toString();

                    if (patientTitle.equals("")) {
                        error = "title";
                        throw new NullPointerException();

                    } else if (patientName.equals("")) {
                        error = "name";
                        throw new NullPointerException();
                    }

                    if (theGender != null) {
                        Log.d("new_pat", "3: " + theGender.toString());
                        patientGender = theGender.toString();
                    } else {
                        error = "gender";
                        throw new NullPointerException();
                    }


                    EditText nhsNumber = (EditText) getActivity().findViewById(R.id.patient_form_nhs_number);

                    if (nhsNumber.getText().length() != 10) {
                        throw new NumberFormatException();
                    } else {
                        Log.d("new_pat", "4: " + nhsNumber.getText().toString());
                        patientNHSNumber = nhsNumber.getText().toString();
                    }


                    TextView dob = (TextView) getActivity().findViewById(R.id.patient_form_dob);


                    if (dob.getText().length() > 5) {
                        Log.d("new_pat", "5: " + dob.getText().toString());

                        patientDob = StaticMethods.setFormattedDate(dob.getText().toString());
                        Log.d("new_pat", "5.5: " + patientDob.toString());
                    } else {
                        error = "dob";
                        throw new NullPointerException();
                    }

                    EditText phoneNo = (EditText) getActivity().findViewById(R.id.patient_phone_no);

                    Log.d("new_pat", "phone number length: " + phoneNo.getText().length());


                    if (phoneNo.getText().length() > 4) {
                        Log.d("new_pat", "6: " + phoneNo.getText().toString());
                        patientPhoneNo = phoneNo.getText().toString();
                    } else {
                        throw new NumberFormatException();
                    }


                    Log.d("new_pat", "creating patient " + patientName + "...");


                    db.patientDAO().insertPatient(
                            new Patient(patientTitle, patientName, patientGender,
                                    patientNHSNumber, patientDob, patientPhoneNo)
                    );

                    db.patientDAO().getPatientsByName(patientName);

                    Log.d("new_pat", "7: patient created:" + patientName);

                    Toast.makeText(getContext(), ("Patient: " + patientName + " added to database."), Toast.LENGTH_SHORT)
                            .show();
                    activity.changeCurrentFragment(new MainMenuFragment(), "Main Menu");
                    
                // Exceptions if required fields are empty.
                } catch (NullPointerException n) {
                    // Method call that prints toast depending on arg provided.
                    if (error.equals("title")) {
                        nullFormEntryToast("title");
                    } else if (error.equals("name")) {
                        nullFormEntryToast("name");
                    } else if (error.equals("gender")) {
                        nullFormEntryToast("gender");
                    } else if (error.equals("dob")) {
                        nullFormEntryToast("date of birth");
                    }
                } catch (NumberFormatException e) {
                    
                    if (patientNHSNumber == null) {
                        nullFormEntryToast("NHS number (10 digits)");
                    } else {
                        Log.d("new_pat", "WHY AM I HERE");
                        nullFormEntryToast("phone number");
                    }
                }


//                catch (ParseException e) {
//                    Log.d("what_am_i", "null pointer gender");
//                    nullFormEntryToast("date of birth");
//                }

            }

        });
        return v;
    }

    public void nullFormEntryToast(String field) {
        Toast.makeText(getContext(), ("Please enter valid patient " + field), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        theContext = (FragmentActivity) context;
//        super.onAttach(context);
//    }

    public final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String dateChosen = dayOfMonth + "/" + (month + 1) + "/" + year;
            patientDOBField = (TextView) getActivity().findViewById(R.id.patient_form_dob);
            patientDOBField.setText(dateChosen);
        }
    };

    enum Gender {
        Male,
        Female,
        Undefined
    }
}
