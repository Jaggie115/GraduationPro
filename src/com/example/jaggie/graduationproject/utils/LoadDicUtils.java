package com.example.jaggie.graduationproject.utils;

import android.content.Context;
import com.example.jaggie.graduationproject.entities.Word;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;


/**
 * Created by jaggie on 2015/2/6.
 */
public class LoadDicUtils {

    /*

    public static HashSet<Word> allCET6Words = new HashSet<Word>();

    public static List<String> loadDic(String dicName) {
        return null;
    }

    public static List<String> loadWordsFromLocal(final Context context, String dicName) {
        File dicsPath = new File(PathUtils.getDicsPath());
        if (!dicsPath.exists()) {
            dicsPath.mkdir();
        }
        String path = PathUtils.getDicsPath() + "/" + dicName + ".txt";
        File wordFile = new File(path);
        List<String> words = null;
        if (wordFile.exists()) {
            BufferedReader br = null;
            words = new ArrayList<String>();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile)));
                if (br != null) {
                    //

                    String line = null;


                    HashSet<String> luanWords = new HashSet<String>();
                    while ((line = br.readLine()) != null) {
                        String[] strings = line.split("\\s+");
                        //第一个字符是个方框
                        words.add(strings[1]);

                        luanWords.add(strings[1]);

                    }

                    final RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest;
                    Iterator<String> iterator = luanWords.iterator();
                    while (iterator.hasNext()){
                        String word = iterator.next();

                        Response.Listener listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    final Word word = new Word(response);
                                    word.setType("CET6");
                                    allCET6Words.add(word);
                                    word.save(context,new SaveListener() {
                                        @Override
                                        public void onSuccess() {

                                            System.out.println("loading--" + word.getContent()+"保存成功！");
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {

                                            System.out.println("loading--" + word.getContent()+"保存失败！"+s);
                                        }
                                    });

                                            System.out.println("loading--" + allCET6Words.size());
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        };

                        System.out.println("loading--" + word);

                        stringRequest = new StringRequest(Request.Method.GET, "https://api.shanbay.com/bdc/search/?word=" + word, listener, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error--" + allCET6Words.size());
                            }
                        });
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2500, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(stringRequest);
                    }







                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return words;
    }




    public static String[] loadDicNames() {
        File dicsDirectory = new File(PathUtils.getDicsPath());


        if (!dicsDirectory.exists()) {
            dicsDirectory.mkdir();
        }
        String[] dicsNames = null;
        if (dicsDirectory.isDirectory()) {
            dicsNames = dicsDirectory.list();
            for (int i = 0; i < dicsNames.length; i++) {
                if (dicsNames[i] != null && dicsNames.length > 0) {
                    int j = dicsNames[i].lastIndexOf(".");
                    if (j > -1)
                        dicsNames[i] = dicsNames[i].substring(0, j);
                }
            }
        }
        return dicsNames;
    }

    public static List<String> loadWordsByNum(Context context, String dicName, int num) {
        List<String> allWords = loadWordsFromLocal(context, dicName);
//
//        HashSet<String> randomWords = new HashSet<String>();
//        Random r = new Random();
//        while (randomWords.size() < num) {
//            int index = r.nextInt(allWords.size());
//            if (!randomWords.contains(allWords.get(index))) {
//                randomWords.add(allWords.get(index));
//            }
//
//        }
        List<String> wordList = new ArrayList<String>();
//        Iterator<String> iterator = randomWords.iterator();
//        while (iterator.hasNext()) {
//            wordList.add(iterator.next());
//        }
        return wordList;
    }

    */
}
