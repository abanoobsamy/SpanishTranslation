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

public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment()
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

        words.add(new Word("Where are you going?", "¿A dónde vas?",
                R.raw.phrases_where_are_you_going));
        words.add(new Word("What is your name?", "¿Cuál es su nombre?",
                R.raw.phrases_what_is_your_name));
        words.add(new Word("My name is...", "Me llamo...",
                R.raw.phrases_my_name_is));
        words.add(new Word("How are you feeling?", "¿Como te sientes?",
                R.raw.phrases_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "Me siento bien.",
                R.raw.phrases_im_feeling_good));
        words.add(new Word("Are you coming?", "¿Vienes?",
                R.raw.phrases_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "Si, voy para allá.",
                R.raw.phrases_yes_im_coming));
        words.add(new Word("I’m coming.", "Ya voy.",
                R.raw.phrases_im_coming));
        words.add(new Word("Let’s go.", "Vamonos.",
                R.raw.phrases_lets_go));
        words.add(new Word("Come here.", "Ven aca.",
                R.raw.phrase_come_here));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordsAdapter wordsAdapter = new WordsAdapter(getActivity(), words, R.color.category_phrases);

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
