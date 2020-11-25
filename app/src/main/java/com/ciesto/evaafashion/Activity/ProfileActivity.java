package com.ciesto.evaafashion.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {
    protected CircularImageView imgUser;
    protected TextView tvEdit;
    protected EditText etName;
    protected EditText etEmail;
    protected CardView cardUpdate;
    protected ImageView imgEdit;
    Activity activity = ProfileActivity.this;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    private int PICK_IMAGE_REQUEST = 123;
    private int CAMERA = 111;
    Dialog builder;
    ProgressDialog progressDialog;
    String filepath = "aa";
    LoginPreferences loginPreferences;
    String s_name, s_email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_profile);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        imgUser = (CircularImageView) findViewById(R.id.img_user);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        cardUpdate = (CardView) findViewById(R.id.card_update);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        imgEdit = (ImageView) findViewById(R.id.img_edit);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        if (Constant.isNetworkAvailable(activity)) {
            GetProfile();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void SetListener() {
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(activity, permissions)) {
                    EasyPermissions.requestPermissions(activity, "Please allow app", 1, permissions);
                } else {
                    SelectImageDialog();
                }
            }
        });

        cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_name = etName.getText().toString();
                s_email = etEmail.getText().toString();
                if (TextUtils.isEmpty(s_name)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_name), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_email)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_email), Toast.LENGTH_SHORT).show();
                } else if (!s_email.matches(emailPattern)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_email), Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.isNetworkAvailable(activity)) {
                        EditProfile();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void GetProfile() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.GetProfile(Constant.ACCESSKEY, "1", loginPreferences.getUserID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Profile response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        // Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        JSONObject object1 = object.getJSONObject("Customer");
                        etName.setText(object1.getString("First_Name"));
                        etEmail.setText(object1.getString("Email_Id"));
                        Picasso.get()
                                .load(Constant.Profile_Images + object1.getString("Profile_Image"))
                                .error(R.drawable.ic_user)
                                .into(imgUser);

                    } else {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    private void EditProfile() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        File file, f;
        MultipartBody.Part f_path = null;
        if (filepath.equals("aa")) {
            file = new File(activity.getCacheDir(), "myimage");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(getResources().getColor(R.color.gray));

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = new File(String.valueOf(filepath));
            Log.e("File URL=", file.toString());

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            f_path = MultipartBody.Part.createFormData("Profile_Image", file.getPath(), requestFile);
        }


        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), Constant.ACCESSKEY);
        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody Id = RequestBody.create(MediaType.parse("text/plain"), loginPreferences.getUserID());
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), s_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), s_email);


        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.EditProfile(key, status, Id, name, email, f_path);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    //  Log.e("Login response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }


    public void SelectImageDialog() {
        builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.image_dialog, null);
        LinearLayout lnr_gallery = view1.findViewById(R.id.lnr_gallery);
        LinearLayout lnr_camara = view1.findViewById(R.id.lnr_camara);
        lnr_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

                Intent intent = new Intent();
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });
        lnr_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });

        builder.setContentView(view1);
        builder.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            Log.e("Request code==", String.valueOf(requestCode));
            // When an Image is picked

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {

                try {
                    filepath = getPath(activity, data.getData());
                    filepath = Constant.compressImage(filepath);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
                    imgUser.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Image Exception=", e.toString());
                }


            } else if (requestCode == CAMERA && resultCode == RESULT_OK && null != data) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgUser.setImageBitmap(thumbnail);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(activity.getApplicationContext(), thumbnail);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                String path1 = Environment.getExternalStorageDirectory()
                        .toString();
                File direct = new File(path1 + "/Tickfun");
                if (!direct.exists()) {
                    direct.mkdirs();
                }

                File destination = new File(direct.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");

                if (destination.exists()) {
                    destination.delete();
                }

                try {
                    FileOutputStream out = new FileOutputStream(destination);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
                Cursor imageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);

                if (imageCursor.moveToFirst()) {
                    filepath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.e("filepath==", filepath);
                }

            } else {
                Toast.makeText(activity, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Exception=", e.toString());
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (activity.getContentResolver() != null) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public static String getPath(Context context, Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{split[1]};
            return getDataColumn(context, contentUri, selection, selectionArgs);


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);

        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
    }

    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity, 4));
    }
}
