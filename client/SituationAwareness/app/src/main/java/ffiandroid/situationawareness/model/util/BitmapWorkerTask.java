package ffiandroid.situationawareness.model.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;


/**
 * Created by Torgrim on 08/05/2015.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>
{

    private final WeakReference<ImageView> imageViewReference;
    public  String imagePath;



    public BitmapWorkerTask(ImageView imageView)
    {
        imageViewReference = new WeakReference<>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        imagePath = params[0];
        Bitmap bitmap = null;

        File imageFile = new File(imagePath);
        FileInputStream is = null;
        try
        {
            is = new FileInputStream(imageFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        byte[] bytes = new byte[(int) imageFile.length()];
        if (is != null && imagePath != null)
        {
            try
            {
                is.read(bytes, 0, (int) imageFile.length());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if (bytes.length != 0)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options), 256, 256, true);
        }
        return bitmap;
    }



    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (isCancelled())
        {
            bitmap = null;
        }
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }








}
