package agency.tango.android.avatarview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Locale;

import agency.tango.android.avatarview.utils.StringUtils;

public class AvatarPlaceholder extends Drawable {
    private static final String DEFAULT_PLACEHOLDER_COLOR = "#3F51B5";
    private static final String COLOR_FORMAT = "#FF%06X";
  private static final String EMPTY_SPACE = " ";
    public static final int DEFAULT_TEXT_SIZE_PERCENTAGE = 33;
    public static final String DEFAULT_PLACEHOLDER_STRING = "-";
    public static final int BACKGROUND_COLOR_INACTIVE = -1;

    private Paint textPaint;
    private Paint backgroundPaint;
    private RectF placeholderBounds;

    private String avatarText;
    private int textSizePercentage;
    private String defaultString;
    private int backgroundColor;

    private float textStartXPoint;
    private float textStartYPoint;

    public AvatarPlaceholder(String name) {
        this(name, DEFAULT_TEXT_SIZE_PERCENTAGE, BACKGROUND_COLOR_INACTIVE, DEFAULT_PLACEHOLDER_STRING);
    }

    public AvatarPlaceholder(String name, @IntRange int textSizePercentage, @ColorInt int backgroundColor, @NonNull String defaultString) {
        this(name, false, textSizePercentage, backgroundColor, defaultString);
    }

    public AvatarPlaceholder(String name, boolean initialsVisible, @IntRange int textSizePercentage, @ColorInt int backgroundColor, @NonNull String defaultString) {
        this.defaultString = resolveStringWhenNoName(defaultString);
        this.avatarText = convertNameToAvatarText(name, initialsVisible);
        this.textSizePercentage = textSizePercentage;
        this.backgroundColor = backgroundColor;

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("white"));
        textPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);

      if (backgroundColor != BACKGROUND_COLOR_INACTIVE) {
        backgroundPaint.setColor(backgroundColor);
      } else {
        backgroundPaint.setColor(Color.parseColor(convertStringToColor(name)));
      }
    }

  @Override
    public void draw(@NonNull Canvas canvas) {
        if (placeholderBounds == null) {
            placeholderBounds = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            setAvatarTextValues();
        }

        canvas.drawRect(placeholderBounds, backgroundPaint);
        canvas.drawText(avatarText, textStartXPoint, textStartYPoint, textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
        backgroundPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        textPaint.setColorFilter(colorFilter);
        backgroundPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private void setAvatarTextValues() {
        textPaint.setTextSize(calculateTextSize());
        textStartXPoint = calculateTextStartXPoint();
        textStartYPoint = calculateTextStartYPoint();
    }

    private float calculateTextStartXPoint() {
        float stringWidth = textPaint.measureText(avatarText);
        return (getBounds().width() / 2f) - (stringWidth / 2f);
    }

    private float calculateTextStartYPoint() {
        return (getBounds().height() / 2f) - ((textPaint.ascent() + textPaint.descent()) / 2f);
    }

    private String resolveStringWhenNoName(String stringWhenNoName) {
        return StringUtils.isNotNullOrEmpty(stringWhenNoName) ? stringWhenNoName : DEFAULT_PLACEHOLDER_STRING;
    }

    private String convertNameToAvatarText(String name, boolean initialsVisible) {
        if (StringUtils.isNotNullOrEmpty(name)) {
            if (initialsVisible) {
                String[] nameWords = name.split(EMPTY_SPACE);
                StringBuilder stringBuilder = new StringBuilder();

                for (String namePart : nameWords) {
                    stringBuilder.append(namePart.substring(0, 1).toUpperCase(Locale.getDefault()));
                }
                return stringBuilder.toString();
            } else {
                return name.substring(0, 1).toUpperCase(Locale.getDefault());
            }
        } else {
            return defaultString;
        }
    }

    private String convertStringToColor(String text) {
        return StringUtils.isNullOrEmpty(text) ? DEFAULT_PLACEHOLDER_COLOR : String.format(COLOR_FORMAT, (0xFFFFFF & text.hashCode()));
    }

    private float calculateTextSize() {
        if (textSizePercentage < 0 || textSizePercentage > 100) {
            textSizePercentage = DEFAULT_TEXT_SIZE_PERCENTAGE;
        }
        return getBounds().height() * (float) textSizePercentage / 100;
    }
}