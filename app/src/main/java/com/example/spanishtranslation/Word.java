package com.example.spanishtranslation;

public class Word {

    // for English into Spanish Language
    private String sDefaultTranslation;

    // for Spanish Language
    private String sSpanishTranslation;

    // for image Spanish Language
    private int sImageIdResource = HAS_IMAGE;

    private static final int HAS_IMAGE = -1;

    private int sMediaPlayer;

//    private int sImageSound;

    //عشان نعمل دالة فيها معرف string , drawable , int الي اخره نعمل دالتين 2 Constructor
    public Word(String sDefault , String sSpanish, int sMedia)
            /*, int sSound)*/
    {
        this.sDefaultTranslation = sDefault;
        this.sSpanishTranslation = sSpanish;
        this.sMediaPlayer = sMedia;
//        this.sImageSound = sSound;
    }

    // for set words.add(); here have strings this Words ------>...
    public Word(String sDefault , String sSpanish, int sImage, int sMedia)
/*, int sSound)*/
    {
        this.sDefaultTranslation = sDefault;
        this.sSpanishTranslation = sSpanish;
        this.sImageIdResource = sImage;
        this.sMediaPlayer = sMedia;
//        this.sImageSound = sSound;
    }

    public String getsDefaultTranslation()
    {
        return sDefaultTranslation;
    }

    public String getsSpanishTranslation()
    {
        return sSpanishTranslation;
    }

    public int getsImageIdResource()
    {
        return sImageIdResource;
    }

    public boolean hasImage()
    {
        return sImageIdResource != HAS_IMAGE;
    }

    public int getsMediaPlayer()
    {
        return sMediaPlayer;
    }

//    public int getsImageSound()
//    {
//        return sImageSound;
//    }
}
