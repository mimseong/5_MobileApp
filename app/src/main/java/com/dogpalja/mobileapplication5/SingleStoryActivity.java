package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleStoryActivity extends AppCompatActivity {

    SquareImageView story_image;
    TextView image_title;
    ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_story);


        String image_url = getIntent().getStringExtra("image_url");
        String image_name = getIntent().getStringExtra("image_name");
        final int user_id = getIntent().getIntExtra("user_id",0);


        User user = SharedPrefrenceManager.getInstance(getApplicationContext()).getUserData();
        final int current_user_id = user.getId();

        story_image = (SquareImageView) findViewById(R.id.story_image);
        image_title = (TextView) findViewById(R.id.image_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);


        final String username = getIntent().getStringExtra("username");
        final String email = getIntent().getStringExtra("email");
        final String image = getIntent().getStringExtra("image");
        final int posts =  getIntent().getIntExtra("posts",0);

        if(!image_url.isEmpty()) {
            Picasso.get().load(image_url).error(R.drawable.user).into(story_image);
        }

        if(!image_name.isEmpty()){
            image_title.setText(image_name);
        }


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent;
                if(user_id == current_user_id){

                    profileIntent = new Intent(SingleStoryActivity.this,MainActivity.class);

                }else{

                    profileIntent = new Intent(SingleStoryActivity.this,ProfileActivity.class);
                    profileIntent.putExtra("user_id",user_id);
                    profileIntent.putExtra("username",  username);
                    profileIntent.putExtra("posts",  posts);
                    profileIntent.putExtra("email", email);
                    profileIntent.putExtra("image",  image);


                }

                startActivity(profileIntent);
            }
        });


    }
}
