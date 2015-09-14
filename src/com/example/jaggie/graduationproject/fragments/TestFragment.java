package com.example.jaggie.graduationproject.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Word;

/**
 * Created by jaggie on 2015/3/4.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private static final String FORGOT_WORDS = "forgotwords";
    private static final String REMEMBERED_WORDS = "rememberedwords";

    private TextView wordContent_tv;
    private TextView pronunciation_tv;
    private TextView audio_tv;
    private MediaPlayer audioPlayer;
    private TextView a_cn_definition_tv;
    private TextView b_cn_definition_tv;
    private TextView c_cn_definition_tv;
    private TextView d_cn_definition_tv;

    private List<Word> incompleteWords;
    private List<Word> allWords = new ArrayList<Word>();
    private Word curWord;
    private String curUrl;
    private List<Word> passWords = new ArrayList<Word>();
    private HashSet<Word> choices = new HashSet<Word>(4);
    private List<Word> selectWords = new ArrayList<Word>(4);


    public static TestFragment newInstance(ArrayList<Word> forgotWords, ArrayList<Word> rememberedWords) {
        TestFragment tf = new TestFragment();
        Bundle args = new Bundle();
        args.putSerializable(TestFragment.FORGOT_WORDS, forgotWords);
        args.putSerializable(TestFragment.REMEMBERED_WORDS, rememberedWords);
        tf.setArguments(args);
        return tf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_test, container, false);

        this.wordContent_tv = (TextView) v.findViewById(R.id.wordcontent_tv);
        this.pronunciation_tv = (TextView) v.findViewById(R.id.pronunciation_tv);
        Typeface mFace = Typeface.createFromAsset(getActivity().getAssets(), "font/segoeui.ttf");

        this.audio_tv = (TextView) v.findViewById(R.id.audio_tv);
        this.a_cn_definition_tv = (TextView) v.findViewById(R.id.a_cn_definition_tv);
        this.b_cn_definition_tv = (TextView) v.findViewById(R.id.b_cn_definition_tv);
        this.c_cn_definition_tv = (TextView) v.findViewById(R.id.c_cn_definition_tv);
        this.d_cn_definition_tv = (TextView) v.findViewById(R.id.d_cn_definition_tv);
        this.a_cn_definition_tv.setOnClickListener(this);
        this.b_cn_definition_tv.setOnClickListener(this);
        this.c_cn_definition_tv.setOnClickListener(this);
        this.d_cn_definition_tv.setOnClickListener(this);
        this.audio_tv.setOnClickListener(this);
        this.incompleteWords = (ArrayList<Word>) getArguments().getSerializable(TestFragment.FORGOT_WORDS);
        copyToAllWords();
        this.curWord = this.incompleteWords.get(0);
        loadChoicesToScreen(this.curWord);
        return v;
    }

    private void copyToAllWords() {
        for (Word w : this.incompleteWords) {
            this.allWords.add(w);
        }
    }

    private void loadWordContentToScreen(Word word) {
        this.wordContent_tv.setText(word.getContent());
        this.pronunciation_tv.setText("[" + word.getPronunciation() + "]");
        // audio_tv.setText(word.getAudioUrl());
        // playAudio(word.getAudioUrl());
    }

    private void loadChoicesToScreen(Word word) {

        loadWordContentToScreen(word);

        int count;
        if (this.allWords.size() <= 3) {
            count = this.allWords.size() - 1;
        } else {
            count = 3;
        }
        Random r = new Random();
        this.choices.clear();
        this.selectWords.clear();
        this.choices.add(word);
        while (count > 0) {
            Word w = this.allWords.get(r.nextInt(this.allWords.size()));
            if (this.choices.add(w)) {
                count--;
            }
        }

        Iterator<Word> iterator = this.choices.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            Word w = iterator.next();
            this.selectWords.add(w);
            switch (i) {
            case 0:
                this.a_cn_definition_tv.setText("A. " + w.getCnDef());
                break;
            case 1:
                this.b_cn_definition_tv.setText("B. " + w.getCnDef());
                break;
            case 2:
                this.c_cn_definition_tv.setText("C. " + w.getCnDef());
                break;
            case 3:
                this.d_cn_definition_tv.setText("D. " + w.getCnDef());
                break;

            }
            i++;
        }

    }

    private void playAudio(final String url) {
        if (this.audioPlayer == null) {
            this.audioPlayer = new MediaPlayer();
            this.audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (!mp.isPlaying()) {
                        mp.start();
                    }
                }
            });
            try {
                this.curUrl = url;
                this.audioPlayer.reset();
                this.audioPlayer.setDataSource(url);
                this.audioPlayer.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!url.equals(this.curUrl)) {
                try {

                    this.curUrl = url;
                    this.audioPlayer.reset();
                    this.audioPlayer.setDataSource(url);
                    this.audioPlayer.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                if (this.audioPlayer.isPlaying()) {
                    this.audioPlayer.seekTo(0);
                } else {
                    this.audioPlayer.start();
                }

            }

        }


    }


    private void judgeWordDefinition(Word word, int choice) {


        if (word.equals(this.selectWords.get(choice))) {
            this.passWords.add(this.curWord);
            this.incompleteWords.remove(this.curWord);
        }

        int i = this.allWords.indexOf(this.curWord);
        if (i + 1 >= this.allWords.size()) {
            if (this.incompleteWords.size() == 0) {
                getActivity().finish();
            } else {
                RememberFragment tf = RememberFragment.newInstance((ArrayList<Word>) this.incompleteWords, new ArrayList<Word>());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_container, tf).commit();
            }
        } else {
            this.curWord = this.allWords.get(i + 1);
            loadChoicesToScreen(this.curWord);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.a_cn_definition_tv:
            judgeWordDefinition(this.curWord, 0);
            break;
        case R.id.b_cn_definition_tv:
            judgeWordDefinition(this.curWord, 1);
            break;
        case R.id.c_cn_definition_tv:
            judgeWordDefinition(this.curWord, 2);
            break;
        case R.id.d_cn_definition_tv:
            judgeWordDefinition(this.curWord, 3);
            break;
        case R.id.audio_tv:
            playAudio(this.curUrl);
            break;


        }
    }
}
