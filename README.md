# Avatar View
[![Build Status](https://travis-ci.org/TangoAgency/avatar-view.svg?branch=master)](https://travis-ci.org/TangoAgency/avatar-view)

Avatar View library was implemented based on Matt Precious's [Don’t Fear the Canvas][Matt Precious's Lecture] lecture. I decided
to create this library in order to achieve an ImageView which can smoothly display user's profile image or his username/name initial letter (in case
when image was not provided).

Please take a look at examples below:


| Simple library presentation | [ExampleActivity][ExampleActivityNoBindings] & [BindingsExample][ExampleOnBindings]
|:-:|:-:|
| ![FirstExample] | ![SecondExample] |

## Usage

This library can be used in two ways: using standard Android methods and using Android Data Binding.

###Standard:

#### Step 1

Add gradle dependency:
```
dependencies {
    compile 'agency.tango.android:avatar-view:{latest_release}'

    //if you want to use Picasso for loading images
    compile 'agency.tango.android:avatar-view-picasso:{latest_release}'
}
```

#### Step 2

Add to your xml layout file:

```xml
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
```java
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
If you want to use a different library than Picasso for loading images you have to create a loader which
extends ImageLoaderBase class. Basically you have to override one method. Take a look how I have done
it in [PicassoLoader][PicassoLoader] available in avatar-view-picasso module.

###Data Binding:

#### Step 1

Add gradle dependency:
```
dependencies {
    compile 'agency.tango.android:avatar-view:{latest_release}'
    compile 'agency.tango.android:avatar-view-bindings:{latest_release}'

    //if you want to use Picasso for loading images
    compile 'agency.tango.android:avatar-view-picasso:{latest_release}'
}
```

#### Step 2

I will show how to edit your xml file based on User class. Let's see:

```xml
<data>
    <variable
        name="viewModel"
        type="YourViewModelClass" />
</data>

<agency.tango.android.avatarview.views.AvatarView
    android:id="@+id/avatar_view_example"
    android:layout_width="100dp"
    android:layout_height="100dp"
    bind:av_border_color="@android:color/white"
    bind:av_border_width="6dp"
    bind:avatarUrl="@{viewModel.testUser.avatarUrl}"
    bind:name="@{viewModel.testUser.name}" />
```

#### Step 3

Your VieModel class has to contain [User][User] testUser field. Let's see:

```java
public class ExampleViewModel extends BaseObservable {

    public User testUser;

    public ExampleViewModel() {
        testUser = new User("username", "avatarUrl");
        notifyPropertyChanged(BR.testUser);
    }

    @Bindable
    public User getTestUser() {
        return testUser;
    }
}
```

#### Step 4

Add to your activity:

```java
private ExampleActivityBinding binding;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.example_activity, new ExampleDataComponent());
    binding.setViewModel(new ExampleViewModel());
}

private class ExampleDataComponent implements android.databinding.DataBindingComponent {
    public AvatarViewBindings getAvatarViewBindings() {
        return new AvatarViewBindings(new PicassoLoader());
    }
}
```
Take a look at [AvatarViewBindings][AvatarViewBindings] class where BindingsAdapter in configured. Because of that
you can use "bind:avatarUrl" and "bind:name" in xml file.

I have explained [PicassoLoader][PicassoLoader] issue in step 3 in Standard Method part

####Additional information

- Avatar background color is calculated using hashCode() method called on given name String.
- You can set "default placeholder String" by creating [ImageLoaderBase][ImageLoaderBase] instance (i.e. [PicassoLoader][PicassoLoader]) by calling constructor with String parameter.
By default "default placeholder String" is set to "?".
- Default border width is 2dp and default border color is white.
- Placeholder letters are currently always white (in future user will be able to choose a different color).

####Feel free to create issues and pull requests!


 [Matt Precious's Lecture]: <https://www.youtube.com/watch?v=KH8Ldp39TUk>
 [FirstExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/example1.gif>
 [SecondExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/example2.gif>
 [PicassoLoader]: <https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/PicassoLoader.java>
 [ImageLoaderBase]: <https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/ImageLoaderBase.java>
 [User]:<https://github.com/TangoAgency/avatar-view/blob/master/example-data-binding/src/main/java/agency/tango/android/avatarview/example/model/User.java>
 [AvatarViewBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/avatar-view-bindings/src/main/java/agency/tango/android/avatarviewbindings/bindings/AvatarViewBindings.java>
 [ExampleActivityNoBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/example/src/main/java/agency/tango/android/example/ExampleActivity.java>
 [ExampleOnBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/example-data-binding/src/main/java/agency/tango/android/avatarview/example/viewmodel/ExampleViewModel.java>