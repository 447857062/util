package kelijun.com.utilprojection;


/**
 * Created by ${kelijun} on 2018/6/14.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TabWidget;

/**
 * A simple text label view that can be applied as a "badge" to any given {@link android.view.View}.
 * This class is intended to be instantiated at runtime rather than included in XML layouts.
 */
public class BadgeView extends android.support.v7.widget.AppCompatTextView {

    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;

    private static final int DEFAULT_MARGIN_DIP = 5;
    private static final int DEFAULT_LR_PADDING_DIP = 5;
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000"); //Color.RED;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;

    private int badgePosition;
    private int badgeMarginH;
    private int badgeMarginV;
    private int badgeColor;

    private boolean isShown;

    private ShapeDrawable badgeBg;

    private int targetTabIndex;

    public BadgeView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link android.view.View}.
     *
     * @param context context for this view.
     * @param target  the View to attach the badge to.
     */
    public BadgeView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    /**
     * Constructor -
     * <p>
     * create a new BadgeView instance attached to a target {@link android.widget.TabWidget}
     * tab at a given index.
     *
     * @param context context for this view.
     * @param target  the TabWidget to attach the badge to.
     * @param index   the position of the tab within the target.
     */
    public BadgeView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, null, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }

    private void init(Context context, View target, int tabIndex) {
        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;

        // apply defaults
        badgePosition = DEFAULT_POSITION;
        badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
        badgeMarginV = badgeMarginH;
        badgeColor = DEFAULT_BADGE_COLOR;

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);

        isShown = false;

        if (this.target != null) {
            applyTo(this.target);
        } else {
            show();
        }
    }

    /**
     * Make the badge visible in the UI.
     */
    public void show() {
        show(false, null);
    }

    private void show(boolean animate, Animation anim) {
        if (getBackground() == null) {
            if (badgeBg == null) {
                badgeBg = getDefaultBackground();
            }
            setBackgroundDrawable(badgeBg);
        }
        applyLayoutParams();

        if (animate) {
            this.startAnimation(anim);
        }
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }
    private void applyLayoutParams() {

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (badgePosition) {
            case POSITION_TOP_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.TOP;
                lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
                break;
            case POSITION_TOP_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
                break;
            case POSITION_BOTTOM_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
                break;
            case POSITION_BOTTOM_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
                break;
            case POSITION_CENTER:
                lp.gravity = Gravity.CENTER;
                lp.setMargins(0, 0, 0, 0);
                break;
            default:
                break;
        }

        setLayoutParams(lp);

    }
    private ShapeDrawable getDefaultBackground() {

        int r = dipToPixels(DEFAULT_CORNER_RADIUS_DIP);
        float[] outerR = new float[] {r, r, r, r, r, r, r, r};

        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(badgeColor);

        return drawable;

    }
    private void applyTo(View target) {

        ViewGroup.LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(context);

        if (target instanceof TabWidget) {

            // set target to the relevant tab child container
            target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
            this.target = target;

            ((ViewGroup) target).addView(container,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            this.setVisibility(View.GONE);
            container.addView(this);

        } else {

            // TODO verify that parent is indeed a ViewGroup
            ViewGroup group = (ViewGroup) parent;
            int index = group.indexOfChild(target);

            group.removeView(target);
            group.addView(container, index, lp);

            container.addView(target);

            this.setVisibility(View.GONE);
            container.addView(this);

            group.invalidate();

        }

    }

    /**
     * dp to px
     *
     * @param dip
     * @return
     */
    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }
}
