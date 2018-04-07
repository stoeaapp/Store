package com.imagine.mohamedtaha.store.data;

import android.net.Uri;
import android.net.wifi.ScanResult;
import android.provider.BaseColumns;

/**
 * Created by MANASATT on 25/11/17.
 */

public class TaskContract {
    /*
      Clients need to know how to access the task data, and it's your job to provide
         these content URI's for the path to that data:
            1) Content authority,
            2) Base content URI,
            3) Path(s) to the tasks directory
            4) Content URI for data in the TaskEntry class

     */
    //The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.imagine.mohamedtaha.store";

    //The base content URI
    public static final Uri  BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //Define the possible paths for accessing data in this contract
    //This is the path for the "tasks" directory
    public static final String PATH_TASKS ="categories";
    public static final String PATH_TASKSSTORE ="store";


        /* TaskEntry is an inner class that defines the contents of the task table */
        public static final class TaskEntry implements BaseColumns{
            //TaskEntry content URI = base content URI + path
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
            public static final Uri CONTENT_URISTORE = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKSSTORE).build();

              //Table Names
            public static final String TABLE_CATEGORIES = "categories";
            public static final String TABLE_STORE = "store";
            public static final String TABLE_STOCKING_WAREHOUSE = "Stocking_marehouse";
            public static final String TABLE_PERMISSION = "permission";
            public static final String TABLE_DAILY_MOVEMENTS = "daily_movements";
            public static final String TABLE_USERS = "users";
            public static final String TABLE_CONVERT_STORE = "convert_store";

            // Since TaskEntry implements the interfaces "BaseColumns", it has an automatically produced
            // "_ID" column in addition to the two below

            //Common column names
            public static final String KEY_DATE = "created_at";
            public static final String KEY_TIME= "time";

            public static final String KEY_NOTES = "notes";
            public static final String KEY_CATEGORY_ID = "category_id";
            public static final String KEY_STORE_ID = "store_id";
            public static final String KEY_USER_ID = "user_id";


            //TABLE CATEGORIES  column names
            public static final String KEY_NAME_CATEGORY = "name_category";
            public static final String KEY_NATURAL_CATEGORY = "natural_category";

            //Table Stores column names
            public static final String KEY_TYPE_STORE = "type_store";
            public static final String KEY_TYPE_STORE_CONVERT = "type_store_convert";

            //Table permissions column names
            public static final String KEY_NAME_PERMISSION ="permission";

            //Table users column names
            public static final String KEY_NAME_USER = "name";
            public static final String KEY_PASSWORD_USERS = "password";
            public static final String KEY_RETRY_PASSWORD = "retry_password";
            public static final String KEY_MOBILE = "mobile";
            public static final String KEY_EMAIL = "email";

            //Table STOCKING_WAREHOUSE column names

            public static final String KEY_FIRST_BALANCE = "first_balanse";


            //Table DAILY_MOVEMENTS column names
            public static final String KEY_PERMISSION_ID = "permission_id";
            public static final String KEY_INCOMING = "incoming";
            public static final String KEY_ISSUED = "issued";
            public static final  String KEY_ACTUAL_BALANCE ="actual_balance";
            public static final  String KEY_CONVERT_FROM = "convert_from";
            public static final String KEY_CONVERT_TO = "convert_to";

            /*      categories
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    name category     |    natural category   | notes   | date add   | user Id |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -

                    */
               /*      Stores
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    type store   | notes   | date add   | user Id |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>   |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>> |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>  |
                                       - - - - - - - - - - - - - - - - - - - - - -

                    */

   /*      permission
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    name  permission  | notes   | date add   | user Id |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -

                    */
      /*      users
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    name user     |    password   | retry password   |  mobile | email| date add   |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -

                    */
   /*      STOCKING_WAREHOUSE
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    category id     |    store id   |first balance | notes   | date add   | user Id |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -

                    */
/*      DAILY_MOVEMENTS
         - - - - - - - - - - - - - - - - - - - - - -
                 | _id  |    category id     |    store id   |permission id | incoming | issued |acutral balance |
                  convert from | convert to | notes   | date add   | user Id |

                    - - - - - - - - - - - - - - - - - - - - - -
                    |  1   |  Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    |  2   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -
                    .
                    .
                    .
                    - - - - - - - - - - - - - - - - - - - - - -
                    | 43   |   Complete lesson   |       >>>>>      |   >>>>>      |   >>>>>      |   >>>>>      |
                    - - - - - - - - - - - - - - - - - - - - - -

                    */









        }




}
























