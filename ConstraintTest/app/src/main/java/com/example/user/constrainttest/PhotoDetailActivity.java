package com.example.user.constrainttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
/**
 * Created by user on 12/12/16.
 */

public class PhotoDetailActivity extends Activity {
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.ImageTextView) TextView imageTextView;
    @BindView(R.id.createdDateTextView) TextView createdDateTextView;
    @BindView(R.id.discriptionEditText) EditText discriptionEditText;
    @BindView(R.id.submitButton) Button submitButton;

    Entry entry;
    PhotoBook info;
    String userChoosenTask;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        Timber.tag("LiftCycles");
        Timber.d("Activity Created");

        Intent intent = getIntent();
        entry = (Entry) intent.getExtras().get("entry");

        if (entry == Entry.EDIT) {
            submitButton.setText("EDIT");
            imageTextView.setVisibility(View.INVISIBLE);
            info = (PhotoBook) intent.getExtras().get("info");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Picasso.with(this).load(ServiceGenerator.BASE_URL + info.getImageThumbFile()).into(imageView);
            createdDateTextView.setText(format.format(info.getCreatedAt()));
            discriptionEditText.setText(info.getDescription());

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                    Log.d("11111111", selectedImage.getPath());
                    Log.d("11111111", selectedImage.getPath());
                    Log.d("11111111", selectedImage.getPath());
                    Log.d("11111111", selectedImage.getPath());

//                    String str = selectedImage.getPath();

                    Log.d("11111111", getRealPathFromURI(selectedImage));
                    Log.d("11111111", getRealPathFromURI(selectedImage));
                    Log.d("11111111", getRealPathFromURI(selectedImage));
                    Log.d("11111111", getRealPathFromURI(selectedImage));


                    file = new File(getRealPathFromURI(selectedImage));

                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @OnClick(R.id.closeButton) void actionCloseButton() {
        finish();
    }

    @OnClick(R.id.imageView) void getPhotoImage() {
        selectImage();
    }

    @OnClick(R.id.submitButton) void submit() {
        if (entry == Entry.ADD) {
            postPhoto();
        } else {
//            putPhoto();
        }

    }


    @OnClick(R.id.rootConstraintLayout) void hideKeyboard(ConstraintLayout constraintLayout) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(discriptionEditText.getWindowToken(), 0);
    }

    private void postPhoto() {
        InterfaceAPI apiService = ServiceGenerator.getClient().create(InterfaceAPI.class);

        /*
        Bitmap bitmap = ((BitmapDrawable)imageView.getBackground()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byte_arr = stream.toByteArray();
        image = Base64.encodeToString(byte_arr, Base64.DEFAULT);*/

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);


        PhotoBook photoBook = new PhotoBook();
        photoBook.setDescription("ddddddddd");

        String descriptionString = "hello, description goes here";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        Call<Void> call = apiService.postPhoto(body, description);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("dddddddd", String.valueOf(response.code()));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("dddddddd", t.toString());
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoDetailActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(PhotoDetailActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
