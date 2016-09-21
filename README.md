(Readme in progress)

# Avatar View

Avatar View library was implemented based on Matt Precious's [Don’t Fear the Canvas][Matt Precious's Lecture] lecture. I decided
to create this library in order to achieve an ImageView which can smoothly display user's profile image or his username/name initial letter (in case
when image was not provided).

Please take a look at examples below:

![FirstExample]

## Usage

This library can be used in two ways: using standard Android methods and using Android Data Binding.

###Standard:

#### Step 1

Add gradle dependency:
```

dependencies {
    compile 'agency.tango.android:avatar-view:{latest_release}'
}
```

#### Step 2

Add to your xml layout file:
```

<agency.tango.android.avatarview.views.AvatarView
    android:id="@+id/avatar_view_example"
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:av_border_color="@android:color/white"
    app:av_border_width="4dp"
    app:av_text_size_percentage="35" />

```

#### Step 3

Add to your activity:
```

    AvatarView avatarView;
    IImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        avatarView = (AvatarView) findViewById(R.id.avatar_view_example);

        imageLoader = new PicassoLoader();
        imageLoader.loadImage(avatarView, "avatarUrl", "name");
    }
```
By default my library uses Picasso in order to correctly load image with placeholder. If you want to use
a different library you have to create a loader which extends ImageLoaderBase class. Basically you have to
override one method. Take a look how I have done it in [PicassoLoader][PicassoLoader].

###Data Binding:

#### Step 1

In progress


 [Matt Precious's Lecture]: <https://www.youtube.com/watch?v=KH8Ldp39TUk>
 [FirstExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/cena.gif>
 [SecondExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/example.gif>
 [PicassoLoader]: <https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/PicassoLoader.java>