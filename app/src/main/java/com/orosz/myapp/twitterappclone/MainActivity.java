package com.orosz.myapp.twitterappclone;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
 

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //adapter class
    ArrayList<AdapterItems>    listnewsData = new ArrayList<AdapterItems>();
    int StartFrom=0;
    int UserOperation=SearchType.MyFollowing; // 0 my followers post 2- specifc user post 3- search post
    String Searchquery;
    int totalItemCountVisible=0; //totalItems visible
    LinearLayout ChannelInfo;
    TextView txtnamefollowers;
    int SelectedUserID=0;
    Button buFollow;
    MyCustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         ChannelInfo=(LinearLayout)findViewById(R.id.ChannelInfo) ;
        ChannelInfo.setVisibility(View.GONE);
        txtnamefollowers=(TextView)findViewById(R.id.txtnamefollowers) ;
          buFollow=(Button)findViewById(R.id.buFollow);
        //TODO: load user data setting
        SaveSettings saveSettings= new SaveSettings(getApplicationContext());
        saveSettings.LoadData();
        //TODO: set the adapter

        myadapter=new MyCustomAdapter(this,listnewsData);
        ListView lsNews=(ListView)findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);//intisal with data
        //LoadTweets(0,SearchType.MyFollowing);


    }

    public void buFollowers(View view) {
//TODO: add code s=for subscribe and un subscribe


    }


  SearchView searchView;
    Menu myMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        myMenu=menu;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.widget.SearchView) menu.findItem(R.id.searchbar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //final Context co=this;
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(co, query, Toast.LENGTH_LONG).show();
                Searchquery=null;
                try {
                    //for space with name
                    Searchquery = java.net.URLEncoder.encode(query , "UTF-8");
                 } catch (UnsupportedEncodingException e) {

                }
                //TODO: search in posts
                //LoadTweets(0,SearchType.SearchIn);// seearch
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //   searchView.setOnCloseListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                //TODO: main search
              //  LoadTweets(0,SearchType.MyFollowing);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater ;
        Context context;
        public MyCustomAdapter(Context context,ArrayList<AdapterItems>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
            this.context=context;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            final   AdapterItems s = listnewsDataAdpater.get(position);

            if(s.tweet_date.equals("add")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_add, null);

                final EditText etPost = (EditText) myView.findViewById(R.id.etPost);
                ImageView iv_post=(ImageView) myView.findViewById(R.id.iv_post) ;


                ImageView iv_attach=(ImageView) myView.findViewById(R.id.iv_attach) ;
                iv_attach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: Load image attachment
                       // LoadImage();
                    }
                });
                iv_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: Save pot
//                        String tweets=null;
//                        try {
//                            //for space with name
//                            tweets = java.net.URLEncoder.encode(  etPost.getText().toString() , "UTF-8");
//                            downloadUrl= java.net.URLEncoder.encode(downloadUrl , "UTF-8");
//                        } catch (UnsupportedEncodingException e) {
//                            tweets=".";
//                        }
//                        String url="http://10.0.2.2/~hussienalrubaye/twitterserver/tweetadd.php?user_id="+ SaveSettings.UserID +"&tweet_text="+ tweets +"&tweet_picture="+ downloadUrl;
//                        new  MyAsyncTaskgetNews().execute(url);
//                        etPost.setText("");

                    }
                });

                return myView;
            }
            else if(s.tweet_date.equals("loading")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_loading, null);
                return myView;
            }
            else if(s.tweet_date.equals("notweet")) {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_msg, null);
                return myView;
            }


            else {
                LayoutInflater mInflater = getLayoutInflater();
                View myView = mInflater.inflate(R.layout.tweet_item, null);

                TextView txtUserName = (TextView) myView.findViewById(R.id.txtUserName);
                txtUserName.setText(s.first_name);
                txtUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //TODO: Person Tweet
                        /*SelectedUserID=Integer.parseInt(s.user_id);
                        LoadTweets(0,SearchType.OnePerson);
                        txtnamefollowers.setText(s.first_name);

                        String url="http://10.0.2.2/~hussienalrubaye/twitterserver/isfollowing.php?user_id="+SaveSettings.UserID +"&following_user_id="+SelectedUserID;
                        new  MyAsyncTaskgetNews().execute(url);*/


                    }
                });
                TextView txt_tweet = (TextView) myView.findViewById(R.id.txt_tweet);
                txt_tweet.setText(s.tweet_text);

                TextView txt_tweet_date = (TextView) myView.findViewById(R.id.txt_tweet_date);
                txt_tweet_date.setText(s.tweet_date);

                //TODO: Picaso
                ImageView tweet_picture=(ImageView)myView.findViewById(R.id.tweet_picture);
                //Picasso.with(context).load(s.tweet_picture).into(tweet_picture);
                ImageView picture_path=(ImageView)myView.findViewById(R.id.picture_path);
                //Picasso.with(context).load(s.picture_path).into(picture_path);
                return myView;
            }
        }


        //load image
    }




}
