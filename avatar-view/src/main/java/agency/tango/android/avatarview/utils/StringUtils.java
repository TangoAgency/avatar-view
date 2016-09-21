package agency.tango.android.avatarview.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static final String EMPTY = "";

    public static boolean isNotNullOrEmpty(String string) {
        return string != null && string.isEmpty() == false;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
