package ffiandroid.situationawareness.model.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import ffiandroid.situationawareness.model.PhotoReport;

/**
 * Created by Torgrim on 08/05/2015.
 */
public class BitmapWorkerTask extends AsyncTask<PhotoReport, Void, Bitmap>
{

    private final WeakReference<ImageView> imageViewReference;
    public  PhotoReport report;



    public BitmapWorkerTask(ImageView imageView)
    {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(PhotoReport... params) {
        report = params[0];
        Bitmap bitmap = null;
        long startTime = System.currentTimeMillis();

        File imageFile = new File(report.getPath());
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
        if (is != null && report != null)
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
            System.out.println("Time it took to decode a image file from path with BitmapFactory " +
                    (System.currentTimeMillis() - startTime) + " ms with extension " + report.getExtension() );
            System.out.println("Size of the created bitmap in memory: " + bitmap.getAllocationByteCount() + " bytes");
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
