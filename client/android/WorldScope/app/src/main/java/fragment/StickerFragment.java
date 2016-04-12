package fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.litmus.worldscope.R;

import java.util.ArrayList;
import java.util.Random;

public class StickerFragment extends Fragment {

    final String TAG = "StickerFragment";
    final int CUT_OFF_RANGE = 500;
    final int STICKER_BOTTOM_MARGIN = 300;
    final int STICKER_TOP_MARGIN = 300;
    int heightLimit;
    int widthLimit;
    View view;
    RelativeLayout starContainer;
    AnimatorSet starAnimator;
    Random rand = new Random();

    public StickerFragment() {
        // Required empty public constructor
    }

    public static StickerFragment newInstance() {
        StickerFragment fragment = new StickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sticker, container, false);

        starContainer = (RelativeLayout) view.findViewById(R.id.starContainer);

        return view;
    }

    public void sendStickers() {
        Log.d(TAG, "Sending stickers");
        ImageView starView = createStarView();
        insertIntoStarContainer(starView);
        animateStarView(starView);
    }

    private ImageView createStarView() {
        ImageView starView = new ImageView(getContext());
        starView.setImageResource(R.drawable.ic_star);
        return starView;
    }

    private void insertIntoStarContainer(ImageView starView) {

        heightLimit = starContainer.getHeight();
        widthLimit = starContainer.getWidth();
        int randomX = rand.nextInt(widthLimit);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(75, 75);
        params.leftMargin = randomX;
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);

        starContainer.addView(starView, params);
    }

    private void animateStarView(ImageView starView) {

        Log.d(TAG, "Width: " + widthLimit);
        
        starAnimator = createWigglyAnimation(starView);

        starAnimator.start();
    }

    private AnimatorSet createWigglyAnimation(ImageView starView) {
        AnimatorSet animatorSet;

        int randomCutOff = rand.nextInt(CUT_OFF_RANGE);

        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(starView, "translationY", heightLimit - STICKER_BOTTOM_MARGIN, randomCutOff + STICKER_TOP_MARGIN);
        yAnimation.setDuration(700);

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(starView, "scaleX", 3f);
        scaleUpX.setDuration(700);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(starView, "scaleY", 3f);
        scaleUpY.setDuration(700);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(starView, "alpha", 0f);
        scaleUpY.setDuration(300);

        animatorSet = new AnimatorSet();
        animatorSet.play(yAnimation).with(scaleUpX).with(scaleUpY);
        animatorSet.play(fadeOut).after(yAnimation);

        animatorSet.setInterpolator(new LinearInterpolator());
        return animatorSet;
    }
}
