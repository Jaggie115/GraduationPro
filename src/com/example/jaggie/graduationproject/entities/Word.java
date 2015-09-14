package com.example.jaggie.graduationproject.entities;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Word extends BmobObject implements Serializable {

    private String content;
    private String enDef;
    private String cnDef;
    private String definition;
    private String pronunciation;
    private String audioUrl;
    private String ukAudioUrl;
    private String usAudioUrl;
    private String type;

    public Word(String json) throws JSONException {
        JSONTokener jsonToken = new JSONTokener(json);
        JSONObject result = (JSONObject) jsonToken.nextValue();
        JSONObject wordInfo = result.getJSONObject("data");
        content = wordInfo.getString("content");
 //       id = wordInfo.getInt("id");
//		retention = wordInfo.getInt("retention");
        definition = wordInfo.getString("definition");
//		targetRetention = wordInfo.getInt("target_retention");
        pronunciation = wordInfo.getString("pronunciation");
        JSONObject endefObject = wordInfo.getJSONObject("en_definitions");

        enDef = wordInfo.getJSONObject("en_definition").getString("defn");
        cnDef = wordInfo.getJSONObject("cn_definition").getString("defn");
        audioUrl = wordInfo.getString("audio");
        ukAudioUrl = wordInfo.getString("uk_audio");
        usAudioUrl = wordInfo.getString("us_audio");

    }

    /**
     * en_definitions array 返回英文释义的数组 en_definition object 英文释义 cn_definition
     * object 中文释义 id int 单词的id retention int 单词的熟悉度 definition string 中文释义
     * target_retention int 用户自定义的目标学习度 learning_id long learing
     * id，如果未返回，在表明用户没学过该单词 content string 查询的单词 pronunciation string 单词的音标
     */
    public String getContent() {
        return content;
    }




    public String getEnDef() {
        return enDef;
    }

    public String getCnDef() {
        return cnDef;
    }


    public String getDefinition() {
        return definition;
    }


    public String getPronunciation() {
        return pronunciation;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getUkAudioUrl() {
        return ukAudioUrl;
    }

    public String getUsAudioUrl() {
        return usAudioUrl;
    }


    /**
     * 判断list中两个word对象需要重写该方法
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word))
            return false;
        return getContent().equals(((Word) o).getContent());

    }

    public void setContent(String content) {
        this.content = content;
    }



    public void setEnDef(String enDef) {
        this.enDef = enDef;
    }

    public void setCnDef(String cnDef) {
        this.cnDef = cnDef;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setUkAudioUrl(String ukAudioUrl) {
        this.ukAudioUrl = ukAudioUrl;
    }

    public void setUsAudioUrl(String usAudioUrl) {
        this.usAudioUrl = usAudioUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
