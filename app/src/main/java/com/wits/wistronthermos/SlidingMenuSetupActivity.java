package com.wits.wistronthermos;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.animation.Interpolator;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class SlidingMenuSetupActivity extends SlidingFragmentActivity {
	private static final String TAG = "SlidingMenuSetupActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlidingActionBarEnabled(true); // 可移動/可滑動的actionbar
		initSlidUpCanvasTransformer();// 左邊menu縮放動畫
		// getSlidingMenu().setSlidingEnabled(false); //讓他無法滑動
		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidth(100);
//		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffset(100);// 離右邊多遠
		getSlidingMenu().setFadeDegree(0.8f);// 拉的時候有無變暗
		getSlidingMenu().setBehindCanvasTransformer(mTransformer);// 動畫

		// main
		setContentView(R.layout.activity_sliding_menu_setup);
//		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentHome(), "Home").commit();
		// right
//		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_right);
//		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		// left
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new FragmentLeftList()).commit();
	}
	private SlidingMenu.CanvasTransformer mTransformer;
	private void initSlidUpCanvasTransformer() {
		mTransformer = new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.translate(0, canvas.getHeight() * (1 - interp.getInterpolation(percentOpen)));
			}
		};
	}
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};
}
