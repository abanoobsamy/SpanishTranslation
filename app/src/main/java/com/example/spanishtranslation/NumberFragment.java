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
import android.widget.TextView;

import java.util.ArrayList;

public class NumberFragment extends Fragment {

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

    public NumberFragment()
    {
        //still empty
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.numbers_and_words_list, container, false);

        sAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("one", "uno", R.drawable.number_one,
                R.raw.number_one));
        words.add(new Word("two", "dos", R.drawable.number_two,
                R.raw.number_two));
        words.add(new Word("three", "Tres", R.drawable.number_three,
                R.raw.number_three));
        words.add(new Word("four", "cuatro", R.drawable.number_four,
                R.raw.number_four));
        words.add(new Word("five", "cinco", R.drawable.number_five,
                R.raw.number_five));
        words.add(new Word("six", "seis", R.drawable.number_six,
                R.raw.number_six));
        words.add(new Word("seven", "siete", R.drawable.number_seven,
                R.raw.number_seven));
        words.add(new Word("eight", "ocho", R.drawable.number_eight,
                R.raw.number_eight));
        words.add(new Word("nine", "nueve", R.drawable.number_nine,
                R.raw.number_nine));
        words.add(new Word("ten", "diez", R.drawable.number_ten,
                R.raw.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordsAdapter itemsAdapter = new WordsAdapter(getActivity(), words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView)rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(itemsAdapter);

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



//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.numbers_and_words_list, container, false);
//
////        TextView textView = new TextView(getActivity());
////        textView.setText("Numbers");
////        return textView;
//        return rootView;
//    }
}
