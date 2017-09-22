package agency.tango.android.avatarview.example.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.Date;

import agency.tango.android.avatarview.example.BR;
import agency.tango.android.avatarview.example.model.User;

public class ExampleViewModel extends BaseObservable {

    private static final String OBAMA_IMAGE = "http://pixel.nymag.com/imgs/daily/vulture/2016/08/11/11-obama-sex-playlist.w529.h529.jpg";
    private static final String DUDA_IMAGE = "https://pbs.twimg.com/profile_images/556495456805453826/wKEOCDN0_400x400.png";
    private static final String STONOGA_IMAGE = "http://brzeg24.pl/wp-content/uploads/2015/06/Zbigniew-Stonoga-e1434539892479.jpg";
    private static final String PICHAI_IMAGE = "https://business-reporter.co.uk/wp-content/uploads/2016/02/pichai-PA.jpg";

    public User userWithNoImageSmall;
    public User userWithNoImage;
    public User userWithNoImageBiggest;
    public User userWithImageSmall;
    public User userWithImage;
    public User userWithImageBiggest;
    public User refreshableUser;

    public ExampleViewModel() {
        userWithNoImageSmall = new User(null, null);
        userWithNoImage = new User("Marcin", null);
        userWithNoImageBiggest = new User("Lebron", null);
        userWithImageSmall = new User("Obama", OBAMA_IMAGE);
        userWithImage = new User("Duda", DUDA_IMAGE);
        userWithImageBiggest = new User("Stonoga", STONOGA_IMAGE);
        refreshableUser = new User("Pichai", PICHAI_IMAGE);
    }

    @Bindable
    public User getRefreshableUser() {
        return refreshableUser;
    }

    public void reloadImage() {
        refreshableUser.setAvatarUrl(String.format("%s?%s", PICHAI_IMAGE, new Date().getTime()));        //new Date() added in order to block Picasso caching
        notifyPropertyChanged(BR.refreshableUser);
    }
}
