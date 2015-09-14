package com.example.jaggie.graduationproject.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Word;

/**
 * Created by jaggie on 2015/2/4.
 */
public class RememberFragment extends Fragment implements View.OnClickListener {

    private static final String FORGOT_WORDS = "forgotwords";
    private static final String REMEMBERED_WORDS = "rememberedwords";


    private Word curWord;
    private String curUrl;
    private List<Word> planWords = new ArrayList<Word>();
    private List<Word> forgotWords = new ArrayList<Word>();
    private List<Word> rememberedWords = new ArrayList<Word>();
    private TextView wordContent_tv;
    private TextView pronunciation_tv;
    private TextView audio_tv;
    private TextView en_definition_tv;
    private TextView cn_definition_tv;
    private TextView forgot_tv;
    private TextView remembered_tv;
    private MediaPlayer audioPlayer;

    public static RememberFragment newInstance(ArrayList<Word> forgotWords, ArrayList<Word> rememberedWords) {
        RememberFragment rf = new RememberFragment();
        Bundle args = new Bundle();
        args.putSerializable(RememberFragment.FORGOT_WORDS, forgotWords);
        args.putSerializable(RememberFragment.REMEMBERED_WORDS, rememberedWords);
        rf.setArguments(args);
        return rf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remember, container, false);
        this.wordContent_tv = (TextView) v.findViewById(R.id.wordcontent_tv);
        this.pronunciation_tv = (TextView) v.findViewById(R.id.pronunciation_tv);
        Typeface mFace = Typeface.createFromAsset(getActivity().getAssets(), "font/segoeui.ttf");
        this.pronunciation_tv.setTypeface(mFace);
        this.audio_tv = (TextView) v.findViewById(R.id.audio_tv);
        this.en_definition_tv = (TextView) v.findViewById(R.id.en_definition_tv);
        this.cn_definition_tv = (TextView) v.findViewById(R.id.cn_definition_tv);
        this.forgot_tv = (TextView) v.findViewById(R.id.forgot_tv);
        this.remembered_tv = (TextView) v.findViewById(R.id.remembered_tv);
        this.remembered_tv.setOnClickListener(this);
        this.forgot_tv.setOnClickListener(this);
        this.audio_tv.setOnClickListener(this);
        this.forgotWords = (ArrayList<Word>) getArguments().getSerializable(RememberFragment.FORGOT_WORDS);
        this.rememberedWords = (ArrayList<Word>) getArguments().getSerializable(RememberFragment.REMEMBERED_WORDS);
        loadWordToScreen(this.forgotWords.get(0));
        return v;
    }

    private void copyToPlanWords() {
        for (Word word : this.forgotWords) {
            this.planWords.add(word);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // super.onActivityResult(requestCode, resultCode, data);
        //
        // switch (requestCode) {
        // case 200:
        // forgotWords = (ArrayList<Word>)
        // data.getSerializableExtra("incompleteWords");
        // rememberedWords = (ArrayList<Word>)
        // data.getSerializableExtra("rememberedWords");
        // if (forgotWords == null || forgotWords.size() == 0) {
        // Intent testIntent = new Intent(this, TestActivity.class);
        // testIntent.putExtra("testWords", (ArrayList<Word>) planWords);
        // startActivityForResult(testIntent, 300);
        // // Toast.makeText(this, "该考试了==" + rememberedWords.size(),
        // Toast.LENGTH_SHORT).show();
        // } else {
        // rememberedWords.clear();
        // loadWordToScreen(forgotWords.get(0));
        // }
        // break;
        // case 300:
        // forgotWords = (ArrayList<Word>)
        // data.getSerializableExtra("incompleteWords");
        // rememberedWords = (ArrayList<Word>)
        // data.getSerializableExtra("rememberedWords");
        // if (forgotWords == null || forgotWords.size() == 0) {
        // finish();
        // Toast.makeText(this, "任务结束" + rememberedWords.size(),
        // Toast.LENGTH_SHORT).show();
        // } else {
        // rememberedWords.clear();
        // loadWordToScreen(forgotWords.get(0));
        // }
        // break;
        //
        // }
    }

    private void remember() {
        if (this.forgotWords.size() != 0) {
            Word word = this.forgotWords.remove(0);
            if (!this.rememberedWords.contains(word)) {
                this.rememberedWords.add(word);
            }
            if (this.forgotWords.size() == 0) {
                // 传递给下一个activity
                // Intent intent = new Intent(this, AffirmActivity.class);
                // intent.putExtra("words", (ArrayList<Word>) rememberedWords);
                // startActivityForResult(intent, 200);
                AffirmFragment af = AffirmFragment.newInstance((ArrayList<Word>) this.rememberedWords,
                    (ArrayList<Word>) this.forgotWords);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_container, af).commit();
                Toast.makeText(getActivity(), "下一组=" + this.rememberedWords.size(), Toast.LENGTH_SHORT).show();

            } else {
                loadWordToScreen(this.forgotWords.get(0));
            }
        }
    }

    private void forget() {
        if (this.forgotWords.size() != 0) {
            Word word = this.forgotWords.remove(0);
            this.forgotWords.add(word);
            loadWordToScreen(this.forgotWords.get(0));
        }

    }

    private void loadWordToScreen(Word word) {
        this.curWord = word;
        this.wordContent_tv.setText(word.getContent());
        this.pronunciation_tv.setText("[" + word.getPronunciation() + "]");
        // audio_tv.setText(word.getAudioUrl());
        this.en_definition_tv.setText(word.getEnDef());
        this.cn_definition_tv.setText(word.getCnDef());
        // playAudio(word.getAudioUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.remembered_tv:
            remember();
            break;

        case R.id.forgot_tv:
            forget();
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
