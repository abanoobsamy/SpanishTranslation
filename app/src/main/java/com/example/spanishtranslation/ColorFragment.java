package com.example.spanishtranslation;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    private AudioManager sAudioManager;

    private MediaPlayer.OnCompletionListener sOnCompletion = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {

            releasePlayMusic();
        }
    };

    private AudioManager.OnAudioFocusChangeListener sAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }

            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                mediaPlayer.start();
            }

            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                releasePlayMusic();
            }
        }
    };

    public ColorFragment()
    {
        //still empty
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.numbers_and_words_list, container, false);

        sAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("red", "rojo", R.drawable.color_red,
                R.raw.color_red));
        words.add(new Word("green", "verde", R.drawable.color_green,
                R.raw.color_green));
        words.add(new Word("brown", "marrón", R.drawable.color_brown,
                R.raw.color_brown));
        words.add(new Word("gray", "gris", R.drawable.color_gray,
                R.raw.color_gray));
        words.add(new Word("black", "negro", R.drawable.color_black,
                R.raw.color_black));
        words.add(new Word("white", "blanco", R.drawable.color_white,
                R.raw.color_white));
        words.add(new Word("dusty yellow", "polvo amarillo", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "mostaza amarilla", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordsAdapter wordsAdapter = new WordsAdapter(getActivity() , words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView)rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);

                releasePlayMusic();

                int result = sAudioManager.requestAudioFocus(sAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getsMediaPlayer());

                    mediaPlayer.setOnCompletionListener(sOnCompletion);

                    mediaPlayer.start();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        releasePlayMusic();
    }

    public void releasePlayMusic()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.release();

            mediaPlayer = null;

            sAudioManager.abandonAudioFocus(sAudioFocusChangeListener);
        }
    }
}
