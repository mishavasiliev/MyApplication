package com.mishavasiliev.vk;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKUsersArray;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[] {VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListView);

        VKSdk.login(this, scope);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                Toast.makeText(getApplicationContext(), "Пользователь успешно авторизовался", Toast.LENGTH_SHORT).show();
//                getMyFriends();

            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getApplicationContext(), "Произошла ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        }))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.steward:
//                steward;
                getMyFriends();
                return true;
            case R.id.mutual_friends:
                //mutual_friends;
                getMutalFriends(164253053, 164253053);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void getMyFriends(){

        String userID = "164253053";
//        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name"));
//        request.executeWithListener(new VKRequest.VKRequestListener() {
        VKParameters parameters = new VKParameters();
        parameters.put("user_ids", 164253053);
        VKRequest request = new VKRequest("friends.get", parameters);
            request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);



//                VKUsersArray friends = (VKUsersArray) response.parsedModel;
////                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (MainActivity.this,
////                        android.R.layout.simple_list_item_1, list);
//
////                listView.setAdapter(arrayAdapter);
//
////                try {
////                    VKApiUser user;
////                    JSONArray s = response.json.getJSONArray("response");
////                    user = new VKApiUser(s.getJSONObject(0));
////
////                    Log.e("erJson", "+ " + s.getJSONObject(0).toString() );
////
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                    Log.e("erJson", e.toString());
////                }
//
//

            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Toast.makeText(MainActivity.this, "attemptFailed", Toast.LENGTH_SHORT).show();
            }

             @Override
            public void onError(VKError error) {
                super.onError(error);
                 Log.e("erfriend", error.toString());
                Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

    });
    }


    void getMutalFriends(int id1, int id2){
        VKRequest request = VKApi.friends().getMutual(VKParameters.from("source_uid", id1, "target_uid", id2));
        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList list = (VKList) response.parsedModel;
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (MainActivity.this,
//                        android.R.layout.simple_list_item_1, list);
//                arrayAdapter.notifyDataSetChanged();
//                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Toast.makeText(MainActivity.this, "attemptFailed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

