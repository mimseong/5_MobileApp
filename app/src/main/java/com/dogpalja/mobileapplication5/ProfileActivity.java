package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dogpalja.mobileapplication5.models.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    TextView display_name_tv, description;
    CircleImageView other_user_profile_image;
    int other_user_id,user_id;
    GridView images_grid_layout;
    ArrayList<Image> arrayListImages;
    ImageArrayAdapter imageArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        display_name_tv =  (TextView) findViewById(R.id.display_name);
        description =  (TextView) findViewById(R.id.description);
        other_user_profile_image = (CircleImageView) findViewById(R.id.profile_image);
        images_grid_layout = (GridView) findViewById(R.id.images_grid_layout);


        other_user_id = getIntent().getIntExtra("user_id",0);
        User user = SharedPrefrenceManager.getInstance(getApplicationContext()).getUserData();
        user_id = user.getId();


        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String image = getIntent().getStringExtra("image");
        int posts =  getIntent().getIntExtra("posts",0);

        User userObject = new User(user_id,email,username,image,posts);

        arrayListImages = new ArrayList<Image>();


        getAllImages();
        imageArrayAdapter = new ImageArrayAdapter(ProfileActivity.this,0,arrayListImages);
        imageArrayAdapter.setUser(userObject);
        images_grid_layout.setAdapter(imageArrayAdapter);
    }

    private void getAllImages(){


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.get_all_images+other_user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){


                                JSONArray jsonObjectImages =  jsonObject.getJSONArray("images");

                                Log.i("arrayImages",jsonObjectImages.toString());

                                for(int i = 0 ; i<jsonObjectImages.length(); i++){
                                    JSONObject jsonObjectSingleImage = jsonObjectImages.getJSONObject(i);
                                    Log.i("jsonsingleImage",jsonObjectSingleImage.toString());

                                    Image image = new Image(jsonObjectSingleImage.getInt("id"),
                                            jsonObjectSingleImage.getString("image_url"),
                                            jsonObjectSingleImage.getString("image_name")
                                            ,jsonObjectSingleImage.getInt("user_id"));

                                    arrayListImages.add(image);


                                }


                                imageArrayAdapter.notifyDataSetChanged();

                            }else{

                                Toast.makeText(ProfileActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }


        );

        VolleyHandler.getInstance(getApplicationContext()).addRequestToQueue(stringRequest);




    }
}
