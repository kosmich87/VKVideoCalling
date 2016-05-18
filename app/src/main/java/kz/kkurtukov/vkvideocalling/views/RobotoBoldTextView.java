package kz.kkurtukov.vkvideocalling.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import kz.kkurtukov.vkvideocalling.utilities.font.TypefaceUtil;

public class RobotoBoldTextView extends TextView {

    public RobotoBoldTextView(Context context) {
        super(context);
        TypefaceUtil.initRobotoBold(this);
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypefaceUtil.initRobotoBold(this);
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypefaceUtil.initRobotoBold(this);
    }

}
