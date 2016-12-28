package com.example.rahul.patient;

/**
 * Created by Rahul on 10/4/2016.
 */

 /*---------------------------------------------------------------------
        |  Class Configuration
        |
        |  Purpose:  Stores all of the links to our PHP scripts
        |
        *---------------------------------------------------------------*/
public class Configuration {

    // Address of our php scripts for CRUD

    public static final String URL_GET_PATIENTLIST = "http://singhrah.dev.fast.sheridanc.on.ca/CapstoneFolder/Select_Patient_List2.php";

    public static final String URL_GET_LOGIN = "http://singhrah.dev.fast.sheridanc.on.ca/CapstoneFolder/login.php";

    public static final String URL_GET_HOMEFragmentList = "http://singhrah.dev.fast.sheridanc.on.ca/CapstoneFolder/homeFragment.php";

    public static final String URL_GET_CHATLIST = "http://figuemat.dev.fast.sheridanc.on.ca/eMedicatorPlus/chat.php";

    public static final String URL_GET_PROFILEINFO = "http://sainiru.dev.fast.sheridanc.on.ca/eMedicatorPlus/profile_page.php";

    public static final String URL_GET_DETAILVIEW = "http://singhrah.dev.fast.sheridanc.on.ca/CapstoneFolder/DetailView.php";

    // Other required data used over many pages
    public static String caretakerID;
    public static String loginID;

    // Counter for avoiding reloading same data on page
    public static int count = 0;

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

}
