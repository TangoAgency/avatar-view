package agency.tango.android.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Date;

import agency.tango.android.avatarview.AvatarPlaceholder;
import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class ExampleActivity extends AppCompatActivity {

    private final int layoutResId;

    private static final String OBAMA_IMAGE = "http://pixel.nymag.com/imgs/daily/vulture/2016/08/11/11-obama-sex-playlist.w529.h529.jpg";
    private static final String DUDA_IMAGE = "https://pbs.twimg.com/profile_images/556495456805453826/wKEOCDN0_400x400.png";
    private static final String STONOGA_IMAGE = "http://brzeg24.pl/wp-content/uploads/2015/06/Zbigniew-Stonoga-e1434539892479.jpg";
    private static final String LINDA_IMAGE = "http://www.cyfraplus.pl/ms_galeria/fotostar/3032_c.jpg";

    private AvatarView avatarWithNoImageSmall;
    private AvatarView avatarWithNoImage;
    private AvatarView avatarWithNoImageBiggest;
    private AvatarView avatarWithImageSmall;
    private AvatarView avatarWithImage;
    private AvatarView avatarWithImageBiggest;
    private AvatarView refreshableAvatar;

    private AvatarPlaceholder refreshableAvatarPlaceholder;
    private IImageLoader imageLoader;

    public ExampleActivity() {
        this.layoutResId = R.layout.example_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(layoutResId);
        super.onCreate(savedInstanceState);

        avatarWithNoImageSmall = (AvatarView) findViewById(R.id.avatar_no_image_small);
        avatarWithNoImage = (AvatarView) findViewById(R.id.avatar_no_image);
        avatarWithNoImageBiggest = (AvatarView) findViewById(R.id.avatar_no_image_biggest);
        avatarWithImageSmall = (AvatarView) findViewById(R.id.avatar_image_small);
        avatarWithImage = (AvatarView) findViewById(R.id.avatar_image);
        avatarWithImageBiggest = (AvatarView) findViewById(R.id.avatar_image_biggest);
        refreshableAvatar = (AvatarView) findViewById(R.id.refreshable_avatar);

        refreshableAvatarPlaceholder = new AvatarPlaceholder("Linda", 30);

        loadAvatarData();
    }

    private void loadAvatarData() {

        imageLoader = new PicassoLoader("?");

        imageLoader.loadImage(avatarWithNoImageSmall, (String) null, null);
        imageLoader.loadImage(avatarWithNoImage, null, "Jakub", 33);
        imageLoader.loadImage(avatarWithNoImageBiggest, null, "Lebron", 70);
        imageLoader.loadImage(avatarWithImageSmall, OBAMA_IMAGE, "Obama");
        imageLoader.loadImage(avatarWithImage, DUDA_IMAGE, "Duda");
        imageLoader.loadImage(avatarWithImageBiggest, STONOGA_IMAGE, "Stonoga");

        imageLoader.loadImage(refreshableAvatar, refreshableAvatarPlaceholder, LINDA_IMAGE);
    }

    public void reloadImage(View v) {
        imageLoader.loadImage(refreshableAvatar, refreshableAvatarPlaceholder, String.format("%s?%s", LINDA_IMAGE, new Date().getTime()));      //new Date() added in order to block Picasso caching
    }
}