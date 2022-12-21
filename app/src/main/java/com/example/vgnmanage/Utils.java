package com.example.vgnmanage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vgnmanage.Database.MyfoodDb;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;

public class Utils {
    Food food = new Food();
    MyfoodDb myfoodDb;
    Context context;


    public static void requestMultiplePermissions(Activity activity) {
        Dexter.withActivity(activity)
                .withPermissions(//dastor gereftan v chek kardan permitions
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.INTERNET
                )
                .withListener(
                        new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {//agar movafeghat shod
                                    Toast.makeText(activity.getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {//agar mokhalefat shod
                                    // show alert dialog navigating to Settings
                                    Toast.makeText(activity, "sorry dont use this app", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {// agar eror dade shod
                        Toast.makeText(activity.getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()// dar thread asli ejra she
                .check();
    }

    public void savefoodtodatabase(Food food, boolean isitpath, Context context) {
        if (isitpath) {
            this.food = food;
            this.context = context;
            myfoodDb = new MyfoodDb(context);
            myfoodDb.insertFoodToDbwithoutid(food);

        } else {

        }
    }

    public static String saveimage_bitmaptofilepath(Bitmap myBitmap, String name, String IMAGE_DIRECTORY, Context context) {
        if (name == null || name.isEmpty()) {
            name = "noname";
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();// kode makhsos tabdil be str
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);// bitmap ya ax ro andaze va noe file v byte haii ke bayad tosh rikhte beshe ro dobare be bitmapemon midim
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);// bitmap ya ax ro andaze va noe file v byte haii ke bayad tosh rikhte beshe ro dobare be bitmapemon midim
//        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT); // bara vaghti mikhastim file nasazim
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);// yek file dar mohit khareji be esm posheye delkhahemon migirim
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {// age on file vojod nadasht misazimesh
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, name + "_" + Calendar.getInstance()// yek nemone az calender ya taghvime ke tsh tarikh v .. on rozo dare
                    .getTimeInMillis() + ".jpg");// ke ma inja zaman hal ro bara asas mili sanie mide // 1600032334344.jpg
            // yek file jadid be esm file ghablimon v pasvand ya chile ya / jadidemon migirim
            f.createNewFile();// ono misazim
            FileOutputStream fo = new FileOutputStream(f);// kode makhsos rikhtan file
            fo.write(bytes.toByteArray());// kode makhsos rikhtan file
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},//arayeye  masirhaye filehamon
                    new String[]{"image/jpeg"},//noe formati ke mikhaim
                    null);
            fo.close();// yek output baz mikonim va tosh masir filemon ya khode file ke mikhaimo midim
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();// masir file ro midim ke besorat string
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e("TAG", "File not Saved::--->");
        }
        return "";
    }

    // baraye khandan ax az file
    void readimagefromfile(ImageView myImage, File imgFile) {
//        File imgFile = new File("/sdcard/Images/test_image.jpg");
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
            myImage.setImageBitmap(myBitmap);
        }
// in paini ha ham mishe
        //        File imgFile = new  File(“filepath”);
//        if(imgFile.exists())
//        {
//            ImageView myImage = new ImageView(this);
//            myImage.setImageURI(Uri.fromFile(imgFile));
//    }
//        myImage.setImageURI(Uri.parse(imgFile.getAbsolutePath()));

    }

    //baraye tabdile imagebitmapbe file
    String pathofputimagebitmaptofile(Bitmap myBitmap, File wallpaperDirectory, String filechild) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, filechild + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"},
                    null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    //baraye gereftan ax az netdownload
    void downloadimagefromnet(ImageView myimage, String imageurlhavetowownload) {
        Picasso.get().load(imageurlhavetowownload).into(myimage);
    }

// bara ferestadan image be net ham ke kole kodaye inja az tarigh upload file

    // bara downloadfilebefile ham
    class Download_toFile_Task extends AsyncTask<String, String, String> {
        String DOWNLOAD_DIR = "";
        File outFile;

        Download_toFile_Task(File file) {
            outFile = file;
        }

        @Override
        protected void onPreExecute() {
//            if (!pdialog.isShowing()) {
//                pdialog.show();
//            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();
                Log.i("lengthOfFile :", String.valueOf(lengthOfFile));
                BufferedInputStream bufferinput = new BufferedInputStream(url.openStream(), 8 * 1024);
                String filename = params[0].substring(params[0].lastIndexOf('/') + 1);
//                File outFile = new File(
//                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DOWNLOAD_DIR,
//                        filename);

                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
//                if (outFile.exists()) {
//                    String ext = filename.contains(".") ?
//                            filename.substring(filename.lastIndexOf('.')) :
//                            "";
//                    String name = filename.contains(".") ?
//                            filename.substring(0, filename.lastIndexOf('.')) :
//                            filename;
//                    outFile = new File(
//                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DOWNLOAD_DIR,
//                            name + "-2" + ext);
//                }
                OutputStream output = new FileOutputStream(outFile);
                byte[] byts_buffer = new byte[1024];
                int count = 0;
                int downloaded = 0;
                while ((count = bufferinput.read(byts_buffer)) != -1) {
                    Log.i("count :", String.valueOf(count));
                    downloaded += count;
                    Log.i("download :", String.valueOf(downloaded));
                    publishProgress(String.valueOf(downloaded * 100 / lengthOfFile));
                    output.write(byts_buffer, 0, count);
                }
                output.flush();
                return outFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            pdialog.setProgress(Integer.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String result) {
//            pdialog.dismiss();
            if (result == null) {
                Toast.makeText(context, "download failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        }

    }

    void downloadfile(String urldownloadtofile, File filetodownloaded) {
        Download_toFile_Task download_toFile_task = new Download_toFile_Task(filetodownloaded);
        download_toFile_task.execute(urldownloadtofile);
    }

    void getbitmapfromnet(String url) {

        Picasso.get()
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }

    void getbitmapfromimageview(ImageView imageView) {
//        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
    }

    void putimagefromnettodrawble(String url, Drawable drawable) {
        Picasso.get().load(url).fit().centerCrop()
                .placeholder(drawable);
//                .placeholder(R.drawable.user_placeholder);
//                .error(R.drawable.user_placeholder_error)
//                .into(imageView);
    }
}
