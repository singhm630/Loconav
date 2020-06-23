package com.example.android.loconav;
public class userinformation {

    public String mName;
    public String mId;
    public String mImagename;
    public String mText1;
    public String mText2;
    public String mText4;
    public String mText5;
    public String mText6;

    public userinformation(){
        //needed for firebase

    }

    public userinformation(String imagename, String text1, String text2, String text4, String text5, String text6) {

        this.mImagename = imagename;
        this.mText1=text1;
        this.mText2=text2;
        this.mText4=text4;
        this.mText5=text5;
        this.mText6=text6;
    }


    public String getmImagename() {
        return mImagename;
    }

    public void setmImagename(String mImagename) {
        this.mImagename = mImagename;
    }
}
