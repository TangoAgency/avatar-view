# Avatar View
[![Download](https://api.bintray.com/packages/tangoagency/maven/avatar-view/images/download.svg) ](https://bintray.com/tangoagency/maven/avatar-view/_latestVersion)
[![Build Status](https://travis-ci.org/TangoAgency/avatar-view.svg?branch=master)](https://travis-ci.org/TangoAgency/avatar-view)
[![Android Arsenal Avatar View](https://img.shields.io/badge/Android%20Arsenal-avatar--view-green.svg?style=true)](https://android-arsenal.com/details/1/4427)

Avatar View library was implemented based on Matt Precious's [Don’t Fear the Canvas][Matt Precious's Lecture] lecture. I decided
to create this library in order to achieve an ImageView which can smoothly display user's profile image or his username/name initial
letter (in the case when the image was not provided).

Please take a look at the examples below:


| Simple library presentation | [ExampleActivity][ExampleActivityNoBindings] & [BindingsExample][ExampleOnBindings]
|:-:|:-:|
| ![FirstExample] | ![SecondExample] |

## Usage

This library can be used in two ways: using [standard Android methods][StandardMethodsPart] and using [Android Data Binding][DataBindingPart].

### Standard:

#### Step 1

Add gradle dependency:
```groovy
dependencies {
    compile 'agency.tango.android:avatar-view:{latest_release}'

    //if you want to use Picasso for loading images
    compile 'agency.tango.android:avatar-view-picasso:{latest_release}'

    //if you want to use Glide for loading images
    compile 'agency.tango.android:avatar-view-glide:{latest_release}'
}
```

#### Step 2

Add to your xml layout file:

```xml
<agency.tango.android.avatarview.views.AvatarView
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
        imageLoader.loadImage(avatarView, "http:/example.com/user/someUserAvatar.png", "User Name");
    }
```
If you want to use a different library than ```Picasso``` for loading images you have to create a loader which
extends [ImageLoaderBase][ImageLoaderBase] class. Basically, you have to override one method. Take a look how I have done
it in [PicassoLoader][PicassoLoader] available in the avatar-view-picasso module.

```ImageLoaderBase``` has two constructors: one with no parameters and second one where you can pass
```String``` placeholder in order to change the default "-". You can also set it directly in
```AvatarPlaceholder``` constructor. More info about ```AvatarPlaceholder``` [here][AvatarPlaceholderInfo].


### Data Binding:

#### Step 1

Add gradle dependency:
```groovy
dependencies {
    compile 'agency.tango.android:avatar-view:{latest_release}'
    compile 'agency.tango.android:avatar-view-bindings:{latest_release}'

    //if you want to use Picasso for loading images
    compile 'agency.tango.android:avatar-view-picasso:{latest_release}'

    //if you want to use Glide for loading images
    compile 'agency.tango.android:avatar-view-glide:{latest_release}'
}
```

#### Step 2

I will show how to edit your xml file based on ```User``` class. Let's see:

```xml
<data>
    <variable
        name="user"
        type="User" />
</data>

<agency.tango.android.avatarview.views.AvatarView
    android:layout_width="100dp"
    android:layout_height="100dp"
    bind:av_border_color="@android:color/white"
    bind:av_border_width="6dp"
    bind:av_text_size_percentage="40"
    bind:avatarUrl="@{user.avatarUrl}"
    bind:name="@{user.name}" />
```

#### Step 3

Add to your activity:

```java
private ExampleActivityBinding binding;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.example_activity, new ExampleDataComponent());
    binding.setUser(new User("User Name", "http:/example.com/user/someUserAvatar.png"));
}

private static class ExampleDataComponent implements DataBindingComponent {
    public AvatarViewBindings getAvatarViewBindings() {
        return new AvatarViewBindings(new PicassoLoader());
    }
}
```
Take a look at [AvatarViewBindings][AvatarViewBindings] class where ```BindingsAdapter``` is configured
(```bind:avatarUrl``` and ```bind:name``` for usage in XML). In order to correctly use ```AvatarViewBindings```
you have to create class extending ```DataBindingComponent``` and pass it as a third parameter in
```DataBindingUtil.setContentView()``` method. This is obligatory because ```AvatarViewBindings``` takes an
[IImageLoader][IImageLoader] parameter in it's constructor. You can find more information about
this topic in <a href="http://www.slideshare.net/radekpiekarz/deep-dive-into-android-data-binding">Deep dive
into Android Data Binding</a> talk.

I have explained [PicassoLoader][PicassoLoader] issue in [step 3][Step3A] in Standard Method part.

#### AvatarPlaceholder

[AvatarPlaceholder][AvatarPlaceholder] is a ```Drawable``` which is set as a ```AvatarView``` background when the image
hasn't been loaded yet. It is a letter on a one-color background (just like in ex. Google, Youtube avatars).
Default placeholder ```String``` (displayed when the username is ```null``` or empty) is "-". TextSizePercentage value
sets how big part of the view is taken by the text. Default textSizePercentage is ```33```. You can change
those values by passing another ones in ```AvatarPlaceholder``` constructor or not directly by using IImageLoader
class methods/constructors.

#### Additional information

- Avatar background color is calculated using ```hashCode()``` method called on a given name ```String```.
- Default border width is ```2dp``` and default border color is ```white```.
- Placeholder letters are currently always white (in future user will be able to choose a different color).
- It is recommended to set your default placeholder ```String``` as short as possible (the best would be one letter).

## Getting Help

To report a specific problem or feature request, [open a new issue on Github](https://github.com/TangoAgency/avatar-view/issues/new).

## Company

[![Facebook](https://github.com/TangoAgency/avatar-view/blob/master/images/facebook.png)](https://www.facebook.com/TangoDigitalAgency)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![Twitter](https://github.com/TangoAgency/avatar-view/blob/master/images/twitter.png)](https://twitter.com/Tango_Agency)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![LinkedIn](https://github.com/TangoAgency/avatar-view/blob/master/images/linkedin.png)](https://www.linkedin.com/company/tango-digital-agency)

[Here](https://github.com/TangoAgency/) you can see open source work developed by Tango Agency.

Whether you're searching for a new partner or trusted team for creating your new great product we are always ready to start work with you. 

You can contact us via contact@tango.agency.
Thanks in advance. 

 [Matt Precious's Lecture]: <https://www.youtube.com/watch?v=KH8Ldp39TUk>
 [FirstExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/example1.gif>
 [SecondExample]: <https://github.com/TangoAgency/avatar-view/blob/master/images/example2.gif>
 [PicassoLoader]: <https://github.com/TangoAgency/avatar-view/blob/master/avatar-view-picasso/src/main/java/agency/tango/android/avatarview/loader/PicassoLoader.java>
 [ImageLoaderBase]: <https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/ImageLoaderBase.java>
 [User]:<https://github.com/TangoAgency/avatar-view/blob/master/example-data-binding/src/main/java/agency/tango/android/avatarview/example/model/User.java>
 [AvatarViewBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/avatar-view-bindings/src/main/java/agency/tango/android/avatarviewbindings/bindings/AvatarViewBindings.java>
 [ExampleActivityNoBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/example/src/main/java/agency/tango/android/example/ExampleActivity.java>
 [ExampleOnBindings]:<https://github.com/TangoAgency/avatar-view/blob/master/example-data-binding/src/main/java/agency/tango/android/avatarview/example/viewmodel/ExampleViewModel.java>
 [Step3A]:<https://github.com/TangoAgency/avatar-view#step-3>
 [DataBindingPart]:<https://github.com/TangoAgency/avatar-view#data-binding>
 [StandardMethodsPart]:<https://github.com/TangoAgency/avatar-view#standard>
 [DataBindingPresentation]:<http://www.slideshare.net/radekpiekarz/deep-dive-into-android-data-binding>
 [IImageLoader]:<https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/IImageLoader.java>
 [AvatarPlaceholder]:<https://github.com/TangoAgency/avatar-view/blob/master/avatar-view/src/main/java/agency/tango/android/avatarview/AvatarPlaceholder.java>
 [AvatarPlaceholderInfo]:<https://github.com/TangoAgency/avatar-view#avatar-placeholder>
