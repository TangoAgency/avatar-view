package agency.tango.android.avatarview.example.model;

public class User {
    private String name;
    private String avatarUrl;
    private boolean initialsVisible;

    public User(String name, String avatarUrl, boolean initialsVisible) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.initialsVisible = initialsVisible;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInitialsVisible() {
        return initialsVisible;
    }

    public void setInitialsVisible(boolean initialsVisible) {
        this.initialsVisible = initialsVisible;
    }
}