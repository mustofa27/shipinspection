package com.example.mustofa.shipinspector;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mustofa on 3/3/2016.
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*30;
    public static final String SERVER_ADDRESS =  "http://newshipbuildinginspection.comxa.com/";
    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait");
    }
    public void storeUserDataInBackground(User user, GetUserCallback userCallback)
    {

        progressDialog.show();
        new StoreUserDataAsyncTask(user,userCallback).execute();
    }
    public void fetchUserDataInBackground(User user, GetUserCallback userCallback)
    {
        progressDialog.show();
        new FetchUserDataAsyncTask(user,userCallback).execute();
    }
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
            values.put("role", user.getRole());

            try {
                URL url = new URL(SERVER_ADDRESS+"register.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                outputStream.close();
                //conn.connect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getQuery(ContentValues values) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            result.append(URLEncoder.encode("username","UTF-8"));
            result.append(URLEncoder.encode(values.get("username").toString(),"UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("email","UTF-8"));
            result.append(URLEncoder.encode(values.get("email").toString(),"UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("password","UTF-8"));
            result.append(URLEncoder.encode(values.get("password").toString(),"UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("role","UTF-8"));
            result.append(URLEncoder.encode(values.get("role").toString(),"UTF-8"));
            return result.toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.Done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            ContentValues values = new ContentValues();
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
            User returnedUser = null;
            try {
                URL url = new URL(SERVER_ADDRESS+"login.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                //POST TO URL
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                //GET URL RESPONSE
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
                String response = stringBuilder.toString();
                JSONObject jsonResponse = new JSONObject(response);
                if(jsonResponse.length()!=0)
                {
                    int id = jsonResponse.getInt("id_user");
                    String username = jsonResponse.getString("username");
                    String role = jsonResponse.getString("role");
                    returnedUser = new User(id,username,user.getEmail(),user.getPassword(),role);
                }
                writer.close();
                outputStream.close();
                reader.close();
                inputStream.close();
                //conn.connect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        private String getQuery(ContentValues values) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            result.append(URLEncoder.encode("email","UTF-8"));
            result.append(URLEncoder.encode(values.get("email").toString(),"UTF-8"));
            result.append("&");
            result.append(URLEncoder.encode("password","UTF-8"));
            result.append(URLEncoder.encode(values.get("password").toString(),"UTF-8"));
            return result.toString();
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.Done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }
}
