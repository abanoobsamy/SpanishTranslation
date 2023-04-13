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

public class FamilyFragment extends Fragment {

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

    public FamilyFragment()
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

        words.add(new Word("father", "padre", R.drawable.family_father,
                R.raw.family_father));
        words.add(new Word("mother", "madre", R.drawable.family_mother,
                R.raw.family_mother));
        words.add(new Word("son", "hijo", R.drawable.family_son,
                R.raw.family_son));
        words.add(new Word("daughter", "hija", R.drawable.family_daughter,
                R.raw.family_daughter));
        words.add(new Word("older brother", "hermano mayor", R.drawable.family_older_brother,
                R.raw.family_older_brother));
        words.add(new Word("younger brother", "hermano más joven", R.drawable.family_younger_brother,
                R.raw.family_younger_brother));
        words.add(new Word("older sister", "hermana mayor", R.drawable.family_older_sister,
                R.raw.family_older_sister));
        words.add(new Word("younger sister", "hermana menor", R.drawable.family_younger_sister,
                R.raw.family_younger_sister));
        words.add(new Word("grandmother", "abuela", R.drawable.family_grandmother,
                R.raw.family_grandmother));
        words.add(new Word("grandfather", "abuelo", R.drawable.family_grandfather,
                R.raw.family_grandfather));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordsAdapter wordsAdapter = new WordsAdapter(getActivity() , words, R.color.category_family);

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
