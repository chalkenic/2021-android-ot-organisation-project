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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.dialogs.PatientBasicDetailsDialog;
import com.example.otorganisationapp.repository.OTDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import lombok.SneakyThrows;


public class PatientBasicDetailsFragment extends Fragment {

    // Int value for allowing camera access.
    String photoPath;
    String photoFileName;
    ImageView patientImage;
    Patient patient;
    EditText phoneEdit;




    OTDatabase db;
    Context theContext;

    public static final int CAMERA_PERM_CODE = 100;
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    public static final int READ_EXTERNAL_STORAGE_CODE = 107;

    public PatientBasicDetailsFragment(Patient patient) {
        this.patient = patient;
    }

    /**
     * Code adapted from Android.developers: Getting a result from an activity
     * Available at: https://developer.android.com/training/basics/intents/result
     */
    ActivityResultLauncher<Intent> photoContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Log.d("Test", "fkjnewfkwnvkr,sv ,smv sd,vdsv");
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Log.d("photo_creation", "photo creation 8");

                        File f = new File(photoPath);

                        Log.d("photo_creation", "photo creation 9");

                        // Holds location of image.
                        Uri contentUri;

                        Log.d("photo_creation", "photo creation 10");

                        contentUri = Uri.fromFile(f);

                        Log.d("photo_creation", contentUri.toString());

//                        if (Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) {
//                            contentUri = Uri.fromFile(f);
//
//                        } else {
//                            contentUri = FileProvider.getUriForFile(getContext(), "com.example.otorganisationapp.fileprovider", f);
//
//                        }
                            // Store image location into uri & add this into both fragment & patient object.

                            patientImage.setImageURI(contentUri);
                            patient.setImageUri(contentUri.toString());

                            // Scan the file saved & add it into the Media database in phone.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
//                            mediaScanIntent.setData(contentUri);
                            getContext().sendBroadcast(mediaScanIntent);
                        } else {
                            }
                        }

                            // Update database with Patient changes.
                            db.patientDAO().updatePatient(patient);

                        }

            });

    ActivityResultLauncher<Intent> galleryContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        Uri galleryUri = result.getData().getData();


                        String filePath;

                        Picasso.get().load(galleryUri).into(patientImage);


                        patient.setImageUri(galleryUri.toString());

                        Cursor cursor = getActivity().getContentResolver().query(galleryUri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                        cursor.moveToFirst();
                        filePath = cursor.getString(0);
                        cursor.close();

                        patient.setImageUri("file://" + filePath);

                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        mediaScanIntent.setData(Uri.parse(filePath));
                        getContext().sendBroadcast(mediaScanIntent);

                        db.patientDAO().updatePatient(patient);

                    } else {
                        Log.d("photo_creation", "Exited Gallery intent.");
                    }
                }
            });


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inner_patient_tab_basic, container, false);

        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},
                CAMERA_PERM_CODE);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_CODE);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_EXTERNAL_STORAGE_CODE);



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
            Log.d("photo_creation", "patient image uri: " + patient.getImageUri());
            Picasso.get().load(Uri.parse(patient.getImageUri())).into(patientImage);
//            patientImage.setImageURI(Uri.parse(patient.getImageUri()));

        // Show button to add new image if none saved in Object.
        } else {
            Log.d("all_pat", "image doesn't exist...");
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
        TextView titleView = (TextView) v.findViewById(R.id.patient_tab_basic_title_result);
        titleView.setText(patient.getTitle());

        // Add Patient gender into TextView.
        TextView genderView = (TextView) v.findViewById(R.id.patient_tab_basic_gender_result);
        genderView.setText(patient.getGender());

        // Add Patient's date of birth into TextView.
        TextView dobView = (TextView) v.findViewById(R.id.patient_tab_basic_age_result);
        dobView.setText(StaticMethods.getFormattedDate(patient.getDob()));

        // Add Patient's NHS number into TextView.
        TextView nhsNumber = (TextView) v.findViewById(R.id.patient_tab_basic_nhs_number_result);
        nhsNumber.setText(patient.getNHSNumber());

        // Add Patient's phone number into TextView.
        TextView phoneNumber = (TextView) v.findViewById(R.id.patient_tab_basic_phone_no_result);
        phoneNumber.setText(patient.getPhoneNo());

        // Source phone number container to allow click attribute.
        Log.d("phone_box", "Creating phone box...");
        LinearLayoutCompat phoneContainer = (LinearLayoutCompat) v.findViewById(R.id.patient_tab_basic_phone_container);
        Log.d("phone_box", "Phone box created!");

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

                Intent callNumberIntent = new Intent(Intent.ACTION_DIAL);
                callNumberIntent.setData(Uri.parse("tel:%s" + patient.getPhoneNo()));

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            imgOptions = new CharSequence[]{"Take photo", "Select from gallery", "Cancel"};
        } else {
            imgOptions = new CharSequence[]{"Select from gallery", "Cancel"};
        }

        // Dialog contains options that will navigate to specific intents.
        AlertDialog.Builder b = new AlertDialog.Builder(theContext);
        b.setTitle("Choose image source");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            b.setItems(imgOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent d;
                    if (imgOptions[which].equals("Take photo")) {
                        dialog.dismiss();
                        d = takePhoto();
                        // Launch intent.
                        openPhotoActivityResult(d);
                    } else if (imgOptions[which].equals("Select from gallery")) {
                        dialog.dismiss();
                        d = chooseGalleryImage();
                        openPhotoActivityResult(d);
                    } else if (imgOptions[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            b.setItems(imgOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent d;
                    if (imgOptions[which].equals("Select from gallery")) {
                        dialog.dismiss();
                        d = chooseGalleryImage();
                        openPhotoActivityResult(d);
                    } else if (imgOptions[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
        }
        b.show();
    }


    public Intent takePhoto() {

        Intent takePhotoIntent;

            // Intent to open up phone camera.
            takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile;

            try {
            // Create File method.
            Log.d("photo_creation", "photo creation 0");
            photoFile = createImageFileFromPhoto();
            Log.d("photo_creation", "photo creation 7");
            } catch (IOException fileError) {
                photoFile = null;
                Log.d("photo_creation", Arrays.toString(fileError.getStackTrace()));
                Toast.makeText(getContext(), "Photo error: process not completed.", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Uri photoUri = FileProvider.getUriForFile(getContext(), "com.example.otorganisationapp.provider", photoFile);

                    // Signal location for image to be saved into.
                    //                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    // Store request code of camera.
                    takePhotoIntent.putExtra("RESULT_CODE", CAMERA_REQUEST_CODE);

            }


        return takePhotoIntent;


    }

    public Intent chooseGalleryImage() {

        // Grab intent for picking image within external folder of phone.
        Intent galleryChoice = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //Store request code within intent.
        galleryChoice.putExtra("RESULT_CODE",  GALLERY_REQUEST_CODE);

        return galleryChoice;
    }

    /**
     * Create File from camera image.
     * @return
     * @throws IOException
     */
    public File createImageFileFromPhoto() throws IOException {

        File photoImage;

        Log.d("photo_creation", "photo creation 1");

        String photoTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        Log.d("photo_creation", "photo creation 2: " + photoTimeStamp);
        photoFileName = "JPEG_" + photoTimeStamp + "_";
        Log.d("photo_creation", "photo creation 3: " + photoFileName);


        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        Log.d("photo_creation", "photo creation 4: " + storageDirectory);

        // If Android 30, use External file storage location.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            photoImage = File.createTempFile(
                    photoFileName,
                    ".jpeg",
//                getContext().getExternalCacheDir() );
                    storageDirectory);
        } else {
            // If android 27, use Cache directory for temporary storing the URI.
            photoImage = File.createTempFile(
                    photoFileName,
                    ".jpeg",
                    getContext().getExternalCacheDir() );
//                    storageDirectory);
        }

        Log.d("photo_creation", "photo creation 5");


        photoPath = photoImage.getAbsolutePath();

        Log.d("photo_creation", "photo creation 6");

        return photoImage;
    }

    public void openPhotoActivityResult(Intent intent) {

        Log.d("photo_Creation", "Intent for activity result opened");

        // Launch Camera app.
        if (intent.getIntExtra("RESULT_CODE", 0) == CAMERA_REQUEST_CODE) {

            photoContent.launch(intent);

//        // launch gallery app.
        } else if (intent.getIntExtra("RESULT_CODE", 0) == GALLERY_REQUEST_CODE) {
            galleryContent.launch(intent);
        }
    }

}


