package com.example.otorganisationapp.fragments.patientTabFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.otorganisationapp.R;
import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.dialogs.PatientBasicDetailsDialog;
import com.example.otorganisationapp.repository.OTDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;



public class PatientBasicDetailsFragment extends Fragment {

    // Int value for allowing camera access.
    String photoPath;
    String photoFileName;
    ImageView patientImage;
    Patient patient;
    EditText phoneEdit;

    // Db access for patient updates.
    OTDatabase db;
    Context theContext;

    // Permission codes applied to Intents in order to access specific areas within phone.
    public static final int CAMERA_PERM_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    public PatientBasicDetailsFragment(Patient patient) {
        this.patient = patient;
    }

    /**
     * Code adapted from Android.developers: Getting a result from an activity
     * Available at: https://developer.android.com/training/basics/intents/result
     */
    //Apply photo data into patient object, & into ImageView within fragment.
    ActivityResultLauncher<Intent> photoContent = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // Create file from photo's absolute path.
                        File f = new File(photoPath);

                        // field to hold location of image.
                        Uri contentUri;

                        contentUri = Uri.fromFile(f);

                        // Apply image to ImageView on fragment.
                        patientImage.setImageURI(contentUri);

                        // Apply Uri to patient object for insertion into database.
                        patient.setImageUri(contentUri.toString());

                        // Scan the file saved & add it into the Media database in phone.
                        // Ensure version is greater than 24.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            try {
                                MediaStore.Images.Media.insertImage(
                                        getContext().getContentResolver(),
                                        f.toString(),
                                        f.getName(),
                                        "photo");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            };

                            }
                        // Update database with Patient changes.
                        db.patientDAO().updatePatient(patient);
                        }
                }
            });

    //Apply sourced gallery image into patient object, & into ImageView within fragment.
    ActivityResultLauncher<Intent> galleryContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        Uri galleryUri = result.getData().getData();

                        String filePath;

                        // External image caching library for loading image into views.
                        Picasso.get().load(galleryUri).into(patientImage);

                        // Apply Uri to patient object for insertion into database.


                        // Apply interactive cursor into gallery folder, specific to item choice made.
                        Cursor cursor = getActivity().getContentResolver().query(galleryUri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                        // Grab first entry (gallery choice made_
                        cursor.moveToFirst();
                        // Get file path of cursor's position within phone.
                        filePath = cursor.getString(0);
                        // Close cursor.
                        cursor.close();

                        patient.setImageUri("file://" + filePath);

                        // Apply file to user & ensure file exists within gallery thereafter.

                        db.patientDAO().updatePatient(patient);

                    } else {

                    }
                }
            });


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inner_patient_tab_basic, container, false);

        // Ensure permissions for camera.
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},
                CAMERA_PERM_CODE);

        // Ensure access to storage for sourcing info.
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);

        theContext = getActivity();

        // Allow access to room database for patient data updates.
        db = OTDatabase.getDatabase(getContext());
        FragmentManager fm =getActivity().getSupportFragmentManager();


        // Confirm whether camera exists on phone.
        PackageManager packageManager = theContext.getPackageManager();
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getActivity(), "Device has no camera.", Toast.LENGTH_SHORT)
                    .show();
        }

        // source ImageView within layout & add existing picture saved into patient.
        patientImage = (ImageView) v.findViewById(R.id.patient_tab_basic_pic);
        if (patient.getImageUri() != null) {

            Picasso.get().load(Uri.parse(patient.getImageUri())).into(patientImage);

        // Show button to add new image if none saved in Object.
        } else {

            v.findViewById(R.id.patient_tab_basic_add_photo_button).setVisibility(View.VISIBLE);
        }

        // Clicking photo allows user to change image.
        CardView patientPhoto = (CardView) v.findViewById(R.id.patient_tab_basic_photo_frame);

        patientPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImageType();
                v.findViewById(R.id.patient_tab_basic_add_photo_button).setVisibility(View.GONE);

            }
        });

        // Add Patient's preferred title into TextView.
        AppCompatTextView titleView = (AppCompatTextView) v.findViewById(R.id.patient_tab_basic_title_result);
        titleView.setText(patient.getTitle());

        // Add Patient gender into TextView.
        AppCompatTextView genderView = (AppCompatTextView) v.findViewById(R.id.patient_tab_basic_gender_result);
        genderView.setText(patient.getGender());

        // Add Patient's date of birth into TextView.
        AppCompatTextView dobView = (AppCompatTextView) v.findViewById(R.id.patient_tab_basic_age_result);
        dobView.setText(StaticMethods.getFormattedDate(patient.getDob()));

        // Add Patient's NHS number into TextView.
        AppCompatTextView nhsNumber = (AppCompatTextView) v.findViewById(R.id.patient_tab_basic_nhs_number_result);
        nhsNumber.setText(patient.getNHSNumber());

        // Add Patient's phone number into TextView.
        AppCompatTextView phoneNumber = (AppCompatTextView) v.findViewById(R.id.patient_tab_basic_phone_no_result);
        phoneNumber.setText(patient.getPhoneNo());

        // Source phone number container to allow click attribute.
        LinearLayoutCompat phoneContainer = (LinearLayoutCompat) v.findViewById(R.id.patient_tab_basic_phone_container);

        phoneEdit = (EditText) v.findViewById(R.id.patient_tab_basic_phone_no_edit);


        // Configure dialog to change number opening on button click.
        AppCompatButton changeNoButton = (AppCompatButton) v.findViewById(R.id.patient_tab_basic_phone_no_explain_button);
        changeNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PatientBasicDetailsDialog numberDialog = new PatientBasicDetailsDialog(patient);

                numberDialog.show(fm, "Number Dialog");

            }
        });

        // Configure opening phone app when long-clicking phone box.
        phoneContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Open up intent for dialing values into phone application.
                Intent callNumberIntent = new Intent(Intent.ACTION_DIAL);
                // Apply values from patient's phone number into app.
                callNumberIntent.setData(Uri.parse("tel:%s" + patient.getPhoneNo()));

                // Launch phone application with patient phone number data.
                startActivity(callNumberIntent);

                return true;
            }
        });
        return v;
    }

    /**
     * Method sources choice by user regarding whether to take photo or source form gallery.
     */
    private void chooseImageType() {

        final CharSequence[] imgOptions;

        // Older android version has compatibility issues with camera, so option is removed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            imgOptions = new CharSequence[]{"Take photo", "Select from gallery", "Cancel"};
        } else {
            imgOptions = new CharSequence[]{"Select from gallery", "Cancel"};
        }

        // Dialog contains options that will navigate to specific intents.
        AlertDialog.Builder b = new AlertDialog.Builder(theContext);
        b.setTitle("Choose image source");

        // Alert dialog has a photo entry option if android version above API 19.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            b.setItems(imgOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent imageSourceIntent;
                    if (imgOptions[which].equals("Take photo")) {
                        dialog.dismiss();
                        // Create an ACTION_IMAGE_CAPTURE intent, for navigating to camera.
                        imageSourceIntent = takePhoto();
                        // Launch chosen intent..
                        openPhotoActivityResult(imageSourceIntent);

                    } else if (imgOptions[which].equals("Select from gallery")) {
                        dialog.dismiss();
                        // Create an ACTION_PICK intent, for gallery navigation to select a picture.
                        imageSourceIntent = chooseGalleryImage();
                        openPhotoActivityResult(imageSourceIntent);
                    } else if (imgOptions[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            // API 18 and below can only select from gallery.
        } else {
            b.setItems(imgOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent imageSourceIntent;
                    if (imgOptions[which].equals("Select from gallery")) {
                        dialog.dismiss();
                        imageSourceIntent = chooseGalleryImage();
                        openPhotoActivityResult(imageSourceIntent);

                    } else if (imgOptions[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
        }
        b.show();
    }

    /**
     * Create image capture intent via creation of file within external directory (via method call)
     * and store both file & request code into intent for future logic.
     * @return takePhotoIntent - intent containing basic file data.
     */
    public Intent takePhoto() {

        Intent takePhotoIntent;

            // Intent to open up phone camera.
            takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile;

            try {
            // Create File method.
            photoFile = createImageFileFromPhoto();

            } catch (IOException fileError) {
                photoFile = null;
                Log.d("photo_creation", Arrays.toString(fileError.getStackTrace()));
                Toast.makeText(getContext(), "Photo error: process not completed.", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {

                    // Generate URI from file created, apply provider authority.
                    Uri photoUri = FileProvider.getUriForFile(getContext(), "com.example.otorganisationapp.provider", photoFile);

                    // Store Uri within intent for activityResult logic.
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    // Store request code of camera to confirm permission accepted.
                    takePhotoIntent.putExtra("RESULT_CODE", CAMERA_REQUEST_CODE);
            }
        return takePhotoIntent;

    }

    /**
     * Create Intent for choosing picture within gallery, alongside request code defined prior in
     * order to allow reading of phone's external SD card.
     * @return galleryChoice - intent containing action to complete, location for action to commence
     * in and request code to allow access to said location.
     */
    public Intent chooseGalleryImage() {

        // Grab intent for picking image within external folder of phone.
        Intent galleryChoice = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //Store request code within intent.
        galleryChoice.putExtra("RESULT_CODE",  GALLERY_REQUEST_CODE);

        return galleryChoice;
    }

    /**
     * Create new default File for camera image to be applied to later.
     * @return photoImage - file data which will have Uri applied against later.
     * @throws IOException - throw exception if file cannot be created within defined constraints.
     */
    public File createImageFileFromPhoto() throws IOException {

        File photoImage;

        // Create a date & time for appending onto file name.
        String photoTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());

        // Add to object field data created, allowing access for later ActivityResult logic.
        photoFileName = "JPEG_" + photoTimeStamp + "_";

        // Source directory for image to be saved into.
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        // Create temporary file with defined data (data, location and file type).
        // If Android 30, use External file storage location.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            photoImage = File.createTempFile(
                    photoFileName,
                    ".jpeg",
//                    ".jpeg",
                    storageDirectory);
        } else {
            // If android 27, use Cache directory for temporary storing the URI.
            photoImage = File.createTempFile(
                    photoFileName,
//                    ".jpeg",
                    ".jpeg",
                    getContext().getExternalCacheDir() );
        }

        // Save into object field the absolute path, for Uri saving onto patient later.
        photoPath = photoImage.getAbsolutePath();

        return photoImage;
    }

    /**
     * Choose which intent to launch depending on intent type.
     * @param intent - used to launch activity (can be either open gallery or camera).
     */
    public void openPhotoActivityResult(Intent intent) {


        // Launch Camera app.
        if (intent.getIntExtra("RESULT_CODE", 0) == CAMERA_REQUEST_CODE) {

            photoContent.launch(intent);

//        // launch gallery app.
        } else if (intent.getIntExtra("RESULT_CODE", 0) == GALLERY_REQUEST_CODE) {
            galleryContent.launch(intent);
        }
    }

}


