package agency.tango.android.avatarviewbindings.bindings;

import android.databinding.BindingAdapter;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class AvatarViewBindings {

    private IImageLoader imageLoader;

    public AvatarViewBindings(IImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @BindingAdapter({"bind:avatarUrl", "bind:name"})
    public void loadImage(AvatarView avatarView, String avatarUrl, String name) {
        if (avatarView != null) {

            imageLoader.loadImage(avatarView, avatarUrl, name);
        }
    }
}
