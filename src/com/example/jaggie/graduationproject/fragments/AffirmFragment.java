package com.example.jaggie.graduationproject.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Word;

/**
 * Created by jaggie on 2015/3/4.
 */
public class AffirmFragment extends Fragment implements View.OnClickListener {

    private static final String FORGOT_WORDS = "forgotwords";
    private static final String REMEMBERED_WORDS = "rememberedwords";

    private MediaPlayer audioPlayer;
    private EditText wordContent_et;
    private TextView pronunciation_tv;
    private TextView audio_tv;
    private TextView en_definition_tv;
    private TextView cn_definition_tv;
    private TextView ignore_tv;
    private TextView nextone_tv;
    private List<Word> forgotWords;
    private List<Word> rememberedWords = new ArrayList<Word>();
    private List<Word> allWords = new ArrayList<Word>();
    private Word curWord;
    private String curUrl;

    private int remain;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String wordContent = AffirmFragment.this.curWord.getContent();

            if (wordContent.equals(s.toString())) {
                AffirmFragment.this.wordContent_et.setTextColor(getResources().getColor(R.color.green));
                AffirmFragment.this.nextone_tv.setEnabled(true);
            } else if (wordContent.startsWith(s.toString())) {
                AffirmFragment.this.wordContent_et.setTextColor(getResources().getColor(R.color.green));
                AffirmFragment.this.nextone_tv.setEnabled(false);
            } else {
                AffirmFragment.this.wordContent_et.setTextColor(getResources().getColor(R.color.red));
                AffirmFragment.this.nextone_tv.setEnabled(false);
            }
        }
    };

    public static AffirmFragment newInstance(ArrayList<Word> forgotWords, ArrayList<Word> rememberedWords) {
        AffirmFragment rf = new AffirmFragment();
        Bundle args = new Bundle();
        args.putSerializable(AffirmFragment.FORGOT_WORDS, forgotWords);
        args.putSerializable(AffirmFragment.REMEMBERED_WORDS, rememberedWords);
        rf.setArguments(args);
        return rf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_affirm, container, false);

        this.wordContent_et = (EditText) v.findViewById(R.id.wordcontent_et);
        this.wordContent_et.addTextChangedListener(this.mTextWatcher);
        this.pronunciation_tv = (TextView) v.findViewById(R.id.pronunciation_tv);
        Typeface mFace = Typeface.createFromAsset(getActivity().getAssets(), "font/segoeui.ttf");
        this.audio_tv = (TextView) v.findViewById(R.id.audio_tv);
        this.en_definition_tv = (TextView) v.findViewById(R.id.en_definition_tv);
        this.cn_definition_tv = (TextView) v.findViewById(R.id.cn_definition_tv);
        this.ignore_tv = (TextView) v.findViewById(R.id.ignore_tv);
        this.nextone_tv = (TextView) v.findViewById(R.id.nextone_tv);
        this.ignore_tv.setOnClickListener(this);
        this.nextone_tv.setOnClickListener(this);
        this.audio_tv.setOnClickListener(this);
        this.forgotWords = (ArrayList<Word>) getArguments().getSerializable(AffirmFragment.FORGOT_WORDS);
        this.rememberedWords = (ArrayList<Word>) getArguments().getSerializable(AffirmFragment.REMEMBERED_WORDS);
        this.curWord = this.forgotWords.get(0);
        this.remain = this.forgotWords.size();
        copyToAllWords();
        loadWordContentToScreen(this.curWord);
        return v;
    }

    private void copyToAllWords() {
        for (Word w : this.forgotWords) {
            this.allWords.add(w);
        }
    }

    private void ignore() {

        int i = this.allWords.indexOf(this.curWord);
        if (i + 1 >= this.allWords.size()) {
            Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
            RememberFragment rf = RememberFragment.newInstance((ArrayList<Word>) this.forgotWords, new ArrayList<Word>());
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_container, rf).commit();
        } else {
            this.curWord = this.allWords.get(i + 1);
            loadWordContentToScreen(this.curWord);
        }
        this.wordContent_et.setText("");

    }


    private void nextone() {

        this.rememberedWords.add(this.curWord);
        this.forgotWords.remove(this.curWord);


        int i = this.allWords.indexOf(this.curWord);
        if (i + 1 >= this.allWords.size()) {
            Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
            TestFragment tf = TestFragment.newInstance((ArrayList<Word>) this.allWords, (ArrayList<Word>) this.forgotWords);
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_container, tf).commit();
        } else {
            this.curWord = this.allWords.get(i + 1);
            loadWordContentToScreen(this.curWord);
        }
        this.nextone_tv.setEnabled(false);
        this.wordContent_et.setText("");

    }

    private void loadWordContentToScreen(Word word) {
        this.pronunciation_tv.setText("[" + word.getPronunciation() + "]");
        // audio_tv.setText(word.getUkAudioUrl());
        this.wordContent_et.setEms(word.getContent().length());
        this.en_definition_tv.setText(word.getEnDef());
        this.cn_definition_tv.setText(word.getCnDef());
        // playAudio(word.getAudioUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ignore_tv:
            ignore();
            break;
        case R.id.nextone_tv:
            nextone();
            break;
        case R.id.audio_tv:
            playAudio(this.curWord.getAudioUrl());
            break;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.audioPlayer != null) {
            this.audioPlayer.stop();
            this.audioPlayer.release();
        }
    }
}
