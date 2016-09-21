package agency.tango.android.avatarview;

import android.support.annotation.NonNull;

import agency.tango.android.avatarview.views.AvatarView;

public interface IImageLoader {
    void loadImage(@NonNull AvatarView avatarView, @NonNull AvatarPlaceholder avatarPlaceholder, String avatarUrl);

    void loadImage(@NonNull AvatarView avatarView, String avatarUrl, String name);

    void loadImage(@NonNull AvatarView avatarView, String avatarUrl, String name, int textSizePercentage);
}
