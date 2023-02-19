package com.example.webbrowser;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;



import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Controller extends Activity{
    Activity activity;
    Stack<String> history;
    String url;
    WebView myWebView;
    Menu menu;
    historyListCustomAdapter listAdapter;
    bookmarkListCustomAdapter listAdapterbm;
    List<websites>  bookmarkList = new ArrayList<websites>();
    String homepage;
    ProgressBar myProgressBar;


    public Controller(Activity activity){
        this.activity = activity;
        this.history = new Stack<>();
        setupHomeScreen();
    }

    //Adds bookmarks to list
    public void addBookmarks(){
        websites w = new websites(myWebView.getUrl());
        bookmarkList.add(w);
    }

//This sets up the bookmarks page depending on bookmarks saved
    public void setupBookmarks(){
        activity.setContentView(R.layout.activity_bookmark);
        ListView list = (ListView)activity.findViewById(R.id.listViewBookmark);
        listAdapterbm = new bookmarkListCustomAdapter(activity, bookmarkList);

        list.setAdapter(listAdapterbm);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String urlBM = bookmarkList.get(position).getUrl();

                activity.setContentView(R.layout.activity_main);

                WebViewClient myWebViewClient = new WebViewClient();
                myProgressBar = activity.findViewById(R.id.progressBar);

                myWebView = (WebView) activity.findViewById(R.id.webView);
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                myWebView.setWebViewClient(myWebViewClient);

                myWebView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        myProgressBar.setProgress(newProgress);
                    }
                });

                myWebView.loadUrl(urlBM);

                keepLoading();

            }
        });

    }

//Sets up the history page depending on websites visited
    public void setupHistoryScreen(){

        activity.setContentView(R.layout.activity_history);

        ListView list = (ListView)activity.findViewById(R.id.listViewHistory);
        List<websites> fullList = new ArrayList<websites>();

        WebBackForwardList weblist = myWebView.copyBackForwardList();

        for(int i = 0; i < weblist.getSize(); i++){
            WebHistoryItem item = weblist.getItemAtIndex(i);
            String url = item.getUrl();
            websites w = new websites(url);
            fullList.add(w);
        }

        listAdapter = new historyListCustomAdapter(activity, fullList);

        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                WebHistoryItem item = weblist.getItemAtIndex(position);
                String url = item.getUrl();

                activity.setContentView(R.layout.activity_main);

                WebViewClient myWebViewClient = new WebViewClient();
                myProgressBar = activity.findViewById(R.id.progressBar);

                myWebView = (WebView) activity.findViewById(R.id.webView);
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                myWebView.setWebViewClient(myWebViewClient);
                myWebView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        myProgressBar.setProgress(newProgress);
                    }
                });

                myWebView.loadUrl(url);

                keepLoading();

            }
        });

    }

//Back button
    public void onBackPress(){
        Log.d("ONBACK","Loading previous site if exists");
        if (myWebView.canGoBack()){
            myWebView.goBack();
        }
        else {
            Toast.makeText(activity, activity.getString(R.string.shut_down), Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


//Foward button
    public void onForwardPress(){
    Log.d("ONFORWARD","Loading next site if exist");
    if(myWebView.canGoForward()){
        myWebView.goForward();
    }
    else{
        Toast.makeText(activity, "Cannot go forward", Toast.LENGTH_SHORT).show();
        }
    }

//Goes to different settings/pages depending on what user selects
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.forward_button:
                onForwardPress();
                return true;

            case R.id.set_home_page:
                Toast.makeText(activity, "Setting as Homepage", Toast.LENGTH_SHORT).show();
                homepage = myWebView.getUrl();

                return true;

            case R.id.history_page:
                Toast.makeText(activity, "History selected", Toast.LENGTH_SHORT).show();
                 setupHistoryScreen();
                return true;

            case R.id.add_bookmark:
                Toast.makeText(activity, "Adding bookmark", Toast.LENGTH_SHORT).show();
                addBookmarks();
                return true;

            case R.id.saved_bookmarks:
                Toast.makeText(activity, "Bookmarks selected", Toast.LENGTH_SHORT).show();
                setupBookmarks();
                return true;

            case R.id.clear:
                Toast.makeText(activity, "Clear selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.clear_history:
                Toast.makeText(activity, "Clear History", Toast.LENGTH_SHORT).show();
                myWebView.clearHistory();

                return true;

            case R.id.clear_bookmarks:
                Toast.makeText(activity, "Clear Bookmarks", Toast.LENGTH_SHORT).show();
                bookmarkList.clear();
                return true;

            case R.id.refresh:
                Toast.makeText(activity, "Refresh", Toast.LENGTH_SHORT).show();
                myWebView.reload();
                return true;

            case R.id.home:

                activity.setContentView(R.layout.activity_main);
                WebViewClient myWebViewClient = new WebViewClient();
                myProgressBar = activity.findViewById(R.id.progressBar);
                myWebView = (WebView) activity.findViewById(R.id.webView);
                WebSettings webSettings = myWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                myWebView.setWebViewClient(myWebViewClient);

                myWebView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        myProgressBar.setProgress(newProgress);
                    }
                });


                if(homepage!= null){
                    myWebView.loadUrl((homepage));

                    keepLoading();
                    return true;

                }
                else
                    myWebView.loadUrl("https://www.google.com");

                    keepLoading();
                return true;

            default:
                return onOptionsItemSelected(item);
        }
    }


    public void createMenu(Menu menu){
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.web_menu, menu);
    }

    //This is the page the user will see when they open the app
    private void setupHomeScreen(){

        activity.setContentView(R.layout.activity_main);

        WebViewClient myWebViewClient = new WebViewClient();

        myWebView = (WebView) activity.findViewById(R.id.webView);
        //for progress bar
        myProgressBar = activity.findViewById(R.id.progressBar);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(myWebViewClient);

        //for the progress bar
        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                myProgressBar.setProgress(newProgress);
            }
        });


        //Home page atm
        myWebView.loadUrl("https://www.google.com");


        keepLoading();



    }


    public void keepLoading(){
        EditText input = (EditText) activity.findViewById(R.id.searchBar);

        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    Log.d("INPUT","url entered: "+input.getText());
                    // Perform action on key press
                    url = "http://"+input.getText();

                    history.push(url);
                    myWebView.loadUrl(url);
                    input.setText("");
                    return true;
                }
                return false;
            }
        });

    }



}
