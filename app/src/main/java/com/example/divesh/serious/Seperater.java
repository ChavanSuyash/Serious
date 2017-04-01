package com.example.divesh.serious;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Suyash on 26-03-2017.
 */

public class Seperater{

    private Set<String> wordsSet;

    // Booleans
    boolean isWord;
    boolean isName;
    boolean isJob;
    boolean isCompany;
    boolean isEmail;
    boolean isPhone;
    boolean isAdrs;
    boolean isWebsite;
    boolean checkForaddress;
    boolean addressCheckerValue;

    // Strings
    String name;
    String companyName;
    String job;
    String email;
    String website;
    String phone;
    String address;

    StringBuilder addressBuilder;

    Pattern mPattern;
    Matcher matcher;

    SparseArray<TextBlock> textBlocks;
    Text line,element;

    List<Text> lines = new ArrayList<>();
    Text currentText;
    TextBlock currentTextBlock;


    int currrentLineCount,tempLineCount;




    public Seperater(Context context, SparseArray<TextBlock> textBlocks)
    {
        /*byte[] readBytes = null ;
        final Resources resources = context.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.jobs);
        try {
            readBytes = new byte[inputStream.available()];
        }catch (Exception e){}
        String wordListContents = new String(readBytes, "UTF-8");
        String[] words = wordListContents.split("\n");
        wordsSet = new HashSet<>();
        Collections.addAll(wordsSet, words);*/

        try{
            this.textBlocks = textBlocks;
            isAdrs =false;

            //Get rid of the textblocks, extract the components (Lines)
            for (int i = 0; i < textBlocks.size(); ++i) {
                currentTextBlock = textBlocks.valueAt(i);
                if (currentTextBlock != null && currentTextBlock.getValue() != null) {
                    addressBuilder = new StringBuilder();
                    onSeperate();
                }

            }
        } catch (Exception e){
            Toast.makeText(context,""+e.toString(),Toast.LENGTH_LONG).show();
            Log.d("Error",e.toString()+"");
        }


    }


    private static boolean checkForAddress(){
        return false;
    }

    public static boolean check_for_word(String word) {
        // System.out.println(word);
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    "/usr/share/dict/american-english"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.indexOf(word) != -1) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {}
        return false;
    }



    private void onSeperate(){
        List<? extends Text> l = currentTextBlock.getComponents();
        currrentLineCount = 0;
        do{
            currentText = l.get(currrentLineCount);

            /*if (isWord== false && currentText.getValue().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                isWord = true;
            }

            if (isJob == false && isWord == true && currentText.getValue().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {

                job = "";
                job = currentText.getValue();
                isJob = true;

            }

            if (isCompany== false && currentText.getValue().matches(".[A-Z].[^@$#/-<>!]+")) {

                companyName = "";
                companyName = currentText.getValue();
                isCompany = true;
                checkForaddress = true;
            }

            if (isEmail == false && currentText.getValue().matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +
                    "\t\t+ \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")
                    || currentText.getValue().contains("@")
                    || currentText.getValue().contains("Email")
                    || currentText.getValue().contains("email")) {

                email = "";
                email = currentText.getValue();
                isEmail = true;
                checkForaddress = true;

            }

            if (isWebsite == false && currentText.getValue().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                    || currentText.getValue().startsWith("www")
                    || currentText.getValue().contains("Website")
                    || currentText.getValue().contains("www")) {

                website = "";
                website = currentText.getValue();
                isWebsite = true;
                checkForaddress = true;


            }

            if (isName== false && isWord == false && !currentText.getValue().contains("+-0123456789/-#,!()") && currentText.getValue().length() > 2) {

                name = "";
                name = currentText.getValue();
                isName = true;
                checkForaddress = false;

            }

            if (isPhone == false && !currentText.getValue().contains("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
                    || currentText.getValue().startsWith("0")
                    && currentText.getValue().contains("+-0123456789/-#")
                    && currentText.getValue().matches("^[0-9]{2,7}$")) {

                phone = "";
                phone = currentText.getValue();
                isPhone = true;
                checkForaddress =true;

            }*/




            if (isAdrs == false && currentText.getValue().contains(",") && isValidAddress(currentText.getValue())){

                /*tempLineCount = currrentLineCount;
                while(tempLineCount < l.size()){
                    Text tempText = l.get(tempLineCount);
                    mPattern = Pattern.compile("(4)[0-9]{2}\\s[0-9]{3}");
                    matcher = mPattern.matcher(tempText.getValue());
                    if(matcher.find()){
                        addressCheckerValue = true;
                    }
                    mPattern = Pattern.compile("(4)[0-9]{2}-[0-9]{3}");
                    matcher = mPattern.matcher(tempText.getValue());
                    if(matcher.find()){
                        addressCheckerValue = true;
                    }
                    mPattern = Pattern.compile("(4)[0-9]{5}");
                    matcher = mPattern.matcher(tempText.getValue());
                    if(matcher.find()) {
                        addressCheckerValue = true;
                    }
                }*/
                addressCheckerValue = true;

                if(currentTextBlock.getComponents().size()>1){
                    while(!isAdrs) {
                        addressBuilder.append(currentText.getValue());
                        addressBuilder.append(" ");
                        mPattern = Pattern.compile("(4)[0-9]{2}\\s[0-9]{3}");
                        matcher = mPattern.matcher(addressBuilder);
                        if(matcher.find()){
                            mPattern = Pattern.compile("\\w+\\s*(\\s*,|.|:|-|;\\s*\\w*)*(4)[0-9]{2}\\s[0-9]{3}");
                            matcher = mPattern.matcher(addressBuilder);
                            if (matcher.find( )) {
                                address = matcher.group(0);
                                isAdrs = true;
                            }
                        }

                        mPattern = Pattern.compile("(4)[0-9]{2}-[0-9]{3}");
                        matcher = mPattern.matcher(addressBuilder);
                        if(matcher.find()){
                            mPattern = Pattern.compile("\\w+\\s*(\\s*,|.|:|-|;\\s*\\w*)*(4)[0-9]{2}-[0-9]{3}");
                            matcher = mPattern.matcher(addressBuilder);
                            if (matcher.find( )) {
                                address = matcher.group(0);
                                isAdrs = true;
                            }
                        }

                        mPattern = Pattern.compile("(4)[0-9]{5}");
                        matcher = mPattern.matcher(addressBuilder);
                        if(matcher.find()){
                            mPattern = Pattern.compile("\\w+\\s*(\\s*,|.|:|-|;\\s*\\w*)*(4)[0-9]{5}");
                            matcher = mPattern.matcher(addressBuilder);
                            if (matcher.find( )) {
                                address = matcher.group(0);
                                isAdrs = true;
                            }
                        }
                        if(currrentLineCount<currentTextBlock.getComponents().size()-1)
                        currentText = l.get(++currrentLineCount);
                        else break;
                    }
                }
            }
            ++currrentLineCount;
        }while(currrentLineCount < l.size());
    }

    public boolean isValidAddress(String s) {
        String n = ".*[0-9].*";
        String a = ".*[A-Z].*";
        return s.matches(n) && s.matches(a);
    }

    public String getAddress(){
        return  address;
    }
}



