package com.example.spanishtranslation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Word> {

    private int colorCategory;

    public WordsAdapter(Activity context, ArrayList<Word> words, int colorCategory2)
    {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        this.colorCategory = colorCategory2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        Word word = getItem(position);

        TextView textView = (TextView)view.findViewById(R.id.textView2);
        textView.setText(word.getsDefaultTranslation());

        TextView textView0 = (TextView)view.findViewById(R.id.textView);
        textView0.setText(word.getsSpanishTranslation());

        ImageView imageView = (ImageView)view.findViewById(R.id.all_image);

        if (word.hasImage())
        {
            imageView.setImageResource(word.getsImageIdResource());
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }

//        ImageView imageView1 = (ImageView)view.findViewById(R.id.imageView);
//        imageView1.setImageResource(word.getsImageSound());

        //عشان نعمل الوان لل activities
        View textCategory = view.findViewById(R.id.text_category);
        int color = ContextCompat.getColor(getContext(), colorCategory);
        textCategory.setBackgroundColor(color);

        return view;
    }
}
