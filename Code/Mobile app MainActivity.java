//Scott William Davidson, Matriculation Number: S1917367
package org.me.gcu.finalassessment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.me.gcu.equakestartercode.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ListView list;
    private String result = "";
    private String url1 = "";
    private TextView MoreInfo;
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private Button StartThreading;
    private Button StartDataButton;
    private TextView startTextView;
    private TextView endTextView;
    private Button EndDateButton;
    private DatePickerDialog.OnDateSetListener onStartDateSetListener;
    private DatePickerDialog.OnDateSetListener onEndDateSetListener;
    public static TextView MostNorthern;
    private ViewFlipper infoFlipper;
    public static TextView mostEastern;
    public static TextView mostSouthernly;
    public static TextView mostWesterly;
    private Button nextStats;
    private Button previousStats;
    private TextView listTitle;
    public static TextView largestMagnitude;
    public static TextView deepestQuake;
    public static TextView shallowestQuake;
    public static Directions direction = new Directions();
    public static ItemClass item = new ItemClass();
    public static InfoStorer infoStorer = new InfoStorer();
    private boolean rangeSelected;
    private Date startDateOfSearch;
    private Date endDateOfSearch;
    private Button searchTheDates;
    private Button cancelTheSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag", "in onCreate");
        // Set up the raw links to the graphical components
        Log.e("MyTag", "after startButton");
        nextStats = (Button) findViewById(R.id.NextStatsChange);
        nextStats.setOnClickListener(this);
        previousStats = (Button) findViewById(R.id.BckButton);
        previousStats.setOnClickListener(this);
        infoFlipper = (ViewFlipper) findViewById(R.id.InfoFlipper);
        list = (ListView) findViewById(R.id.List);
        Log.e("MyTag", "after startProgress");
        MoreInfo = (TextView) findViewById(R.id.MoreInfo);
        StartThreading = (Button) findViewById(R.id.StartThreading);
        StartThreading.setOnClickListener(this);
        EndDateButton = (Button) findViewById(R.id.EndDateButton);
        startTextView = (TextView) findViewById(R.id.textStartDateSelected);
        endTextView = (TextView) findViewById(R.id.textEndDateSelected);
        MostNorthern = (TextView) findViewById(R.id.MostNorthern);
        mostEastern = (TextView) findViewById(R.id.MostEasternly);
        mostSouthernly = (TextView) findViewById(R.id.MostSouthernly);
        mostWesterly = (TextView) findViewById(R.id.MostWesterly);
        StartDataButton = (Button) findViewById(R.id.StartDateButton);
        listTitle = (TextView) findViewById(R.id.ListTitle);
        largestMagnitude = (TextView) findViewById(R.id.LargestMagnitude);
        deepestQuake = (TextView) findViewById(R.id.DeepestQuake);
        shallowestQuake = (TextView) findViewById(R.id.ShallowestQuake);
        StartDataButton.setOnClickListener(this);
        EndDateButton.setOnClickListener(this);
        searchTheDates = (Button) findViewById(R.id.SearchTheDates);
        searchTheDates.setOnClickListener(this);
        searchTheDates.setEnabled(false);
        cancelTheSearch = (Button) findViewById(R.id.CancelSearch);
        cancelTheSearch.setEnabled(false);
        cancelTheSearch.setOnClickListener(this);

        onStartDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d("MyTag", dayOfMonth + "/" + month + "/" + year);

            String date = dayOfMonth + "/" + month + "/" + year;
            startTextView.setText(date + "\n");

        };

        onEndDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d("MyTag", dayOfMonth + "/" + month + "/" + year);
            String date = dayOfMonth + "/" + month + "/" + year;
            endTextView.setText(date);
        };


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MapOption:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View aview) {
        Log.e("MyTag", "in onClick");
        if(aview == StartThreading)
        {
            cancelTheSearch.setVisibility(View.VISIBLE);
            searchTheDates.setVisibility(View.VISIBLE);
            searchTheDates.setEnabled(true);
            StartThreading.setEnabled(false);
            StartDataButton.setVisibility(View.VISIBLE);
            EndDateButton.setVisibility(View.VISIBLE);
            nextStats.setVisibility(View.VISIBLE);
            previousStats.setVisibility(View.VISIBLE);
            listTitle.setVisibility(View.VISIBLE);
            //new DownloadFromURL().execute(url1);
            startProgress();


       }
        if(aview == nextStats)
        {
            infoFlipper.showNext();
        }
        if(aview == previousStats)
        {
            infoFlipper.showPrevious();
        }
        if(aview == StartDataButton)
        {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog message = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar, onStartDateSetListener, year, month, day );
            message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            message.show();

        }
        if(aview == EndDateButton)
        {
          Calendar calendar = Calendar.getInstance();
          int year = calendar.get(Calendar.YEAR);
          int month = calendar.get(Calendar.MONTH);
          int day = calendar.get(Calendar.DAY_OF_MONTH);
          DatePickerDialog message = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar, onEndDateSetListener, year, month, day );
          message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          message.show();
        }

       if(aview == cancelTheSearch)
       {
            cancelTheSearch.setEnabled(false);
            searchTheDates.setEnabled(true);
            direction.recalculateDirections();
            infoStorer.reset();
            rangeSelected = false;
            startProgress();
       }

        if(aview == searchTheDates)
        {
            searchTheDates.setEnabled(false);
            cancelTheSearch.setEnabled(true);
            Log.d("DATE SEARCH BUTTON", "WORKING, MATE");
            direction.recalculateDirections();
            Log.d("Most northern: ", direction.mostNorthern );
            Log.d("Most Southern: ", direction.mostSouthern );
            Log.d("Most western: ", direction.mostWestern );
            Log.d("Most Eastern : ", direction.mostEasternString );
            infoStorer.reset();
            Log.d("new largest depth: ", infoStorer.largestDepth);
            Log.d("new smallest depth: ", infoStorer.smallestDepth);
            Log.d("New largest magnitude: ", infoStorer.largestMagnitudeString);
            rangeSelected = true;
            String StartDateInfo = startTextView.getText().toString();
            String EndDateInfo = endTextView.getText().toString();
            Log.d("Dates: ", StartDateInfo + EndDateInfo);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                startDateOfSearch = dateFormat.parse(StartDateInfo);
                Log.d("Start date", startDateOfSearch.toString());
                endDateOfSearch = dateFormat.parse(EndDateInfo);
                Log.d("End date: ", endDateOfSearch.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startProgress();
        }
    }

    class DownloadFromURL extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... f_url)
        {
            InputStream urlData;
            try {
              Log.d("ASYNC TASK", "THIS IS FROM ASYNC TASK");
              URL dataUrl = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                urlData = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlData));
                String inputLine;

                while((inputLine = reader.readLine()) != null)
                {
                    result = result + inputLine;
                }

                urlData.close();
                reader.close();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

        }

    }
    public void startProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //


    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag", "after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    parseData(result);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, InfoStorer.strengthAndLocation) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            for (int g = 0; g < InfoStorer.strengthAndLocation.size(); g++) {
                                if (InfoStorer.itemMags.get(position) <= 1.0) {
                                    view.setBackgroundColor(getResources().getColor(R.color.green));
                                } else if (InfoStorer.itemMags.get(position) > 1.0 && InfoStorer.itemMags.get(position) <= 2.0) {
                                    view.setBackgroundColor(getResources().getColor(R.color.yellow));

                                } else if (InfoStorer.itemMags.get(position) > 2.0 && InfoStorer.itemMags.get(position) <= 3.0) {
                                    view.setBackgroundColor(getResources().getColor(R.color.purple_200));

                                } else if (InfoStorer.itemMags.get(position) > 3.0 && InfoStorer.itemMags.get(position) <= 4.0) {
                                    view.setBackgroundColor(getResources().getColor(R.color.red));

                                }

                            }

                            return view;
                        }
                    };

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MoreInfo.setText(infoStorer.itemDescription.get(position));
                        }
                    });
                }


                  private void parseData(String dataToParse) {

                    try {
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        factory.setNamespaceAware(true);
                        XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(new StringReader(dataToParse));
                        int eventType = xpp.getEventType();
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            // Found a start tag
                            if (eventType == XmlPullParser.START_TAG) {
                                if (xpp.getName().equalsIgnoreCase("item")) {
                                    item = new ItemClass();
                                }
                                // Check which Tag we have
                                if (xpp.getName().equalsIgnoreCase("title")) {
                                    String text = xpp.nextText();
                                    Log.d("MyTag", text);
                                    item.setTitle(text);


                                } else
                                    // Check which Tag we have
                                    if (xpp.getName().equalsIgnoreCase("description")) {
                                        String text = xpp.nextText();
                                        Log.d("MyTag", text);
                                        item.setDescription(text);


                                    } else
                                        // Check which Tag we have
                                        if (xpp.getName().equalsIgnoreCase("link")) {
                                            String text = xpp.nextText();
                                            Log.d("MyTag", text);
                                            item.setLink(text);

                                        } else
                                            // Check which Tag we have
                                            if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                                String text = xpp.nextText();
                                                Log.d("MyTag", text);
                                                item.setPubDate(text);

                                            } else if (xpp.getName().equalsIgnoreCase("category")) {
                                                String text = xpp.nextText();
                                                Log.d("MyTag", text);
                                                item.setCategory(text);

                                            } else if (xpp.getName().equalsIgnoreCase("lat")) {
                                                String text = xpp.nextText();
                                                Log.d("MyTag", "geoLat saved as: " + text);
                                                item.setLat(text);
                                            } else if (xpp.getName().equalsIgnoreCase("long")) {
                                                String text = xpp.nextText();
                                                Log.d("MyTag", "geoLat saved as: " + text);
                                                item.setLong(text);
                                            }
                            } else if (eventType == XmlPullParser.END_TAG) {
                               if(xpp.getName().equalsIgnoreCase("item") && !rangeSelected)
                                {

                                    infoStorer.AddToLists();
                                    direction.getMostNorthern();
                                    direction.getMostWestern();
                                    direction.getMostSouthern();
                                    direction.getMostEastern();
                                    item.getMagnitudes();
                                    item.getDepths();
                                    direction.AddToTextview();
                                }
                               if(xpp.getName().equalsIgnoreCase("item") && rangeSelected)
                               {
                                   Log.d("Y", "works");
                                   if(item.dateConverted().after(startDateOfSearch) && item.dateConverted().before(endDateOfSearch))
                                   {
                                       direction.getMostNorthern();
                                       direction.getMostWestern();
                                       direction.getMostSouthern();
                                       direction.getMostEastern();
                                       item.getMagnitudes();
                                       item.getDepths();
                                       direction.AddToTextview();
                                   }
                                   if(item.dateConverted().equals(startDateOfSearch) && item.dateConverted().before(endDateOfSearch))
                                   {
                                       direction.getMostNorthern();
                                       direction.getMostWestern();
                                       direction.getMostSouthern();
                                       direction.getMostEastern();
                                       item.getMagnitudes();
                                       item.getDepths();
                                       direction.AddToTextview();
                                   }
                                   if(item.dateConverted().after(startDateOfSearch) && item.dateConverted().equals(endDateOfSearch))
                                   {
                                       direction.getMostNorthern();
                                       direction.getMostWestern();
                                       direction.getMostSouthern();
                                       direction.getMostEastern();
                                       item.getMagnitudes();
                                       item.getDepths();
                                       direction.AddToTextview();
                                   }
                                   else
                                   if(item.dateConverted().equals(startDateOfSearch) && item.dateConverted().equals(endDateOfSearch))
                                   {
                                       direction.getMostNorthern();
                                       direction.getMostWestern();
                                       direction.getMostSouthern();
                                       direction.getMostEastern();
                                       item.getMagnitudes();
                                       item.getDepths();
                                       direction.AddToTextview();
                                   }

                               }
                            }
                            // Get the next event
                               eventType = xpp.next();

                        } // End of while
                    } catch (XmlPullParserException ae1) {
                        Log.e("MyTag", "Parsing error" + ae1.toString());
                    } catch (IOException ae1) {
                        Log.e("MyTag", "IO error during parsing");
                    }

                    System.out.println("End document");

                   infoStorer.magsAndLocationGetter();

                    

                }



            });
        }

    }

    private void UpdateList()
    {

    }
}
