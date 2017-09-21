package agency.tango.android.avatarview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import agency.tango.android.avatarview.AvatarPlaceholder;
import agency.tango.android.avatarview.R;
import timber.log.Timber;

public class AvatarView extends ImageView {

    private int defaultBorderColor;
    private int defaultBorderWidth;
    private int defaultTextSizePercentage;

    private int borderColor;
    private int borderWidth;
    private int textSizePercentage;

    @ColorInt
    private int backgroundColor;

    private int viewSize;
    private Drawable drawable;

    int circleRadius;
    int circleCenterXValue;
    int circleCenterYValue;

    private Paint borderPaint = new Paint();
    private Paint mainPaint = new Paint();
    private Rect circleRect;

    public AvatarView(Context context) {
        super(context);
        init(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        saveBasicValues(canvas);

        if (viewSize == 0) {
            return;
        }

        Bitmap bitmap = cutIntoCircle(drawableToBitmap(drawable));

        if (bitmap == null) {
            return;
        }

        canvas.translate(circleCenterXValue, circleCenterYValue);

        //Draw Border
        canvas.drawCircle(circleRadius + borderWidth, circleRadius + borderWidth, circleRadius + borderWidth, borderPaint);

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    public int textSizePercentage() {
        return textSizePercentage;
    }

    public int backgroundColor() {
        return backgroundColor;
    }

    private void init(Context context, AttributeSet attrs) {
        setDefaultBorderValues();

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.AvatarView,
                    0, 0);
            try {
                configureBorderValues(typedArray);
            } finally {
                typedArray.recycle();
            }
        }

        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);

        mainPaint.setAntiAlias(true);
        mainPaint.setColor(getResources().getColor(R.color.av_bitmap_background_color));
        mainPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        try {
            Bitmap bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, viewSize, viewSize);
            drawable.draw(canvas);

            return bitmap;
        } catch (OutOfMemoryError error) {
            Timber.d(error, "OutOfMemoryError occurred while generating bitmap");
            return null;
        }
    }

    private Bitmap cutIntoCircle(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        try {
            Bitmap output = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(circleRadius + borderWidth, circleRadius + borderWidth, circleRadius, borderPaint);

            canvas.drawBitmap(bitmap, circleRect, circleRect, mainPaint);
            return output;
        } catch (OutOfMemoryError error) {
            Timber.d(error, "OutOfMemoryError occurred while generating bitmap");
            return null;
        }
    }

    private void setDefaultBorderValues() {
        defaultBorderColor = getResources().getColor(R.color.av_default_border);
        defaultBorderWidth = getResources().getDimensionPixelSize(R.dimen.av_default_border_width);
        defaultTextSizePercentage = AvatarPlaceholder.DEFAULT_TEXT_SIZE_PERCENTAGE;
    }

    private void configureBorderValues(TypedArray typedArray) {
        borderColor = typedArray.getColor(R.styleable.AvatarView_av_border_color, defaultBorderColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.AvatarView_av_border_width, defaultBorderWidth);
        textSizePercentage = typedArray.getInt(R.styleable.AvatarView_av_text_size_percentage, defaultTextSizePercentage);
        backgroundColor = typedArray.getInt(R.styleable.AvatarView_av_background_color, AvatarPlaceholder.BACKGROUND_COLOR_INACTIVE);
    }

    private void saveBasicValues(Canvas canvas) {
        int viewHeight = canvas.getHeight();
        int viewWidth = canvas.getWidth();

        viewSize = Math.min(viewWidth, viewHeight);

        circleCenterXValue = (viewWidth - viewSize) / 2;
        circleCenterYValue = (viewHeight - viewSize) / 2;
        circleRadius = (viewSize - (borderWidth * 2)) / 2;

        circleRect = new Rect(0, 0, viewSize, viewSize);

        maximizeAvailableBorderSize();

        if (viewSize != 0) {
            drawable = getDrawable();
        }
    }

    void maximizeAvailableBorderSize() {
        if (viewSize / 3 < borderWidth) {
            borderWidth = viewSize / 3;
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}