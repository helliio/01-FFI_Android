package ffiandroid.situationawareness.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.util.AdapterContentHolder;
import ffiandroid.situationawareness.model.util.AsyncDrawable;
import ffiandroid.situationawareness.model.util.BitmapWorkerTask;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 23, 2015.
 */
public class ImageAdapter extends ArrayAdapter<AdapterContentHolder> {
    private final int THUMBSIZE = 96;

    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster item loading by caching view in A
     * ViewHolder object
     */
    private static class ViewHolder {
        ImageView imgIcon;
        TextView description;
    }
    private static class TextViewHolder
    {
        TextView textView;
    }

    public ImageAdapter(Context context, ArrayList<AdapterContentHolder> reports) {
        super(context, 0, reports);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag
        ViewHolder viewHolder;
        TextViewHolder textViewHolder;
        AdapterContentHolder report = getItem(position);
        if(report.photoReport != null) {
            // Check if an existing view is being reused, otherwise inflate the
            // item view
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_photo_view_item, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.custom_item_img_des);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.custom_item_img_icon);
            convertView.setTag(viewHolder);
            viewHolder = (ViewHolder) convertView.getTag();

            // Get the data item for this position
            PhotoReport photoReport = report.photoReport;
            // set description text
            viewHolder.description.setText(photoReport.toString());


            // TODO(Torgrim): Work on improving the efficiency when creating a bitmap
            if (photoReport.getPath() != null) {
                Bitmap pl = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
                Bitmap placeHolderBitmap = Bitmap.createScaledBitmap(pl, 256, 256, true);
                if (AsyncDrawable.cancelPotentialWork(photoReport.getPath(), viewHolder.imgIcon)) {
                    final BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.imgIcon);
                    final AsyncDrawable asyncDrawable =
                            new AsyncDrawable(getContext().getResources(), placeHolderBitmap, task);
                    viewHolder.imgIcon.setImageDrawable(asyncDrawable);
                    task.execute(photoReport.getPath());
                }

            }
        }
        else
        {
            textViewHolder = new TextViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            textViewHolder.textView = ((TextView) convertView.findViewById(android.R.id.text1));
            convertView.setTag(textViewHolder);
            textViewHolder.textView.setText(report.report);


        }
        return convertView;
    }
}
