package com.jcwl.jdshop.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcwl.jdshop.R;
import com.jcwl.jdshop.utils.StringHandle;

@SuppressLint("HandlerLeak")
public class PullUpDownListView extends ListView implements OnScrollListener {
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;

	private LayoutInflater inflater;

	private LinearLayout headView; // 头部
	private RelativeLayout footerView;// 底部

	private TextView tipsTextview;// 下拉刷新
	private TextView lastUpdatedTextView;// �?新更�?
	private TextView footerTextView;// 底部text
	private ImageView arrowImageView;// 箭头
	private ProgressBar progressBar;// 刷新进度�?
	private ProgressBar footerProgress;// 底部

	private RotateAnimation animation;// 旋转特效 刷新中箭头翻�? 向下变向�?
	private RotateAnimation reverseAnimation;

	// 用于保证startY的�?�在�?个完整的touch事件中只被记录一�?
	private boolean isRecored;

	// 是否正在上拉刷新
	private boolean isPullUping = false;
	// 是否还需要再次上拉刷�?
	private boolean isNeedPullUp = true;

	// private int headContentWidth;//头部宽度
	private int headContentHeight;// 头部高度

	private int startY;// 高度起始位置，用来记录与头部距离
	private int firstItemIndex;// 列表中首行索引，用来记录其与头部距离

	private int state = DONE;// 下拉刷新中�?�松�?刷新中�?�正在刷新中、完成刷�?

	private boolean isBack;

	public OnRefreshListener refreshListener;// 刷新监听
	// 上拉刷新接口
	private OnPullUpRefreshListener mOnPullUpRefreshListener;

	// private final static String TAG = "abc";

	public PullUpDownListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {

		inflater = LayoutInflater.from(context);

		headView = (LinearLayout) inflater.inflate(
				R.layout.refresh_list_header, this, false);// listview拼接headview

		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);// headview中各view
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);// headview中各view
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);// headview中各view
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);// headview中各view

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();// 头部高度
		// headContentWidth = headView.getMeasuredWidth();//头部宽度

		headView.setPadding(0, -1 * headContentHeight, 0, 0);// setPadding(int
																// left, int
																// top, int
																// right, int
																// bottom)
		headView.invalidate();// Invalidate the whole view

		// Log.v("size", "width:" + headContentWidth + " height:" +
		// headContentHeight);

		addHeaderView(headView);// 添加进headview
		setOnScrollListener(this);// 滚动监听

		// 添加底部view
		footerView = (RelativeLayout) inflater.inflate(
				R.layout.pull_to_refresh_footer, this, false);
		footerTextView = (TextView) footerView.findViewById(R.id.footer_text);
		footerProgress = (ProgressBar) footerView
				.findViewById(R.id.footer_progress);
		addFooterView(footerView);
		footerView.setVisibility(View.GONE);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);// 特效animation设置

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(250);
		reverseAnimation.setFillAfter(true);// 特效reverseAnimation设置
	}

	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,// 滚动事件
			int arg3) {
		firstItemIndex = firstVisiableItem;// 得到首item索引
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		if (isNeedPullUp && getLastVisiblePosition() == getCount() - 1
				&& !isPullUping) {
			isPullUping = true;
			prepareForUpRefresh();
			onPullUpRefresh();// 上拉刷新
			setSelection(getLastVisiblePosition());
		}
	}

	public boolean onTouchEvent(MotionEvent event) {// 触摸事件
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 手按�? 对应下拉刷新状�??
			if (firstItemIndex == 0 && !isRecored) {// 如果首item索引�?0，且尚未记录startY,则在下拉时记录之，并执行isRecored
													// = true;
				startY = (int) event.getY();
				isRecored = true;

				// Log.v(TAG, "在down时�?�记录当前位置�??");
			}
			break;

		case MotionEvent.ACTION_UP:// 手松�? 对应松开刷新状�??

			if (state != REFRESHING) {// 手松�?�?4个状态：下拉刷新、松�?刷新、正在刷新�?�完成刷新�?�如果当前不是正在刷�?
				if (state == DONE) {// 如果当前是完成刷新，�?么都不做
				}
				if (state == PULL_To_REFRESH) {// 如果当前是下拉刷新，状�?�设为完成刷新（意即下拉刷新中就松开了，实际未完成刷新），执行changeHeaderViewByState()
					state = DONE;
					changeHeaderViewByState();

					// Log.v(TAG, "由下拉刷新状态，到done状�??");
				}
				if (state == RELEASE_To_REFRESH) {// 如果当前是松�?刷新，状态设为正在刷新（意即松开刷新中松�?手，才是真正地刷新），执行changeHeaderViewByState()
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();// 真正刷新，所以执行onrefresh，执行后状�?�设为完成刷�?

					// Log.v(TAG, "由松�?刷新状�?�，到done状�??");
				}
			}

			isRecored = false;// 手松�?，则无论怎样，可以重新记录startY,因为只要手松�?就认为一次刷新已完成
			isBack = false;

			break;

		case MotionEvent.ACTION_MOVE:// 手拖动，拖动过程中不断地实时记录当前位置
			int tempY = (int) event.getY();
			if (!isRecored && firstItemIndex == 0) {// 如果首item索引�?0，且尚未记录startY,则在拖动时记录之，并执行isRecored
													// = true;
				// Log.v(TAG, "在move时�?�记录下位置");
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {// 如果状�?�不是正在刷新，且已记录startY：tempY为拖动过程中�?直在变的高度，startY为拖动起始高�?
				// 可以松手去刷新了
				if (state == RELEASE_To_REFRESH) {// 如果状�?�是松开刷新
					// �?上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if (((tempY - startY)/2 < headContentHeight)// 如果实时高度大于起始高度，且两�?�之差小于头部高度，则状态设为下拉刷�?
							&& (tempY - startY) > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();

						// Log.v(TAG, "由松�?刷新状�?�转变到下拉刷新状�??");
					}
					// �?下子推到顶了
					else if (tempY - startY <= 0) {// 如果实时高度小于等于起始高度了，则说明到顶了，状态设为完成刷�?
						state = DONE;
						changeHeaderViewByState();

						// Log.v(TAG, "由松�?刷新状�?�转变到done状�??");
					}
					// �?下拉了，或�?�还没有上推到屏幕顶部掩盖head的地�?
					else {// 如果当前拖动过程中既没有到下拉刷新的地步，也没有到完成刷新（到顶）的地步，则保持松开刷新状�??
							// 不用进行特别的操作，只用更新paddingTop的�?�就行了
					}
				}
				// 还没有到达显示松�?刷新的时�?,DONE或�?�是PULL_To_REFRESH状�??
				if (state == PULL_To_REFRESH) {// 如果状�?�是下拉刷新
					// 下拉到可以进入RELEASE_TO_REFRESH的状�?
					if ((tempY - startY)/2 >= headContentHeight) {// 如果实时高度与起始高度之差大于等于头部高度，则状态设为松�?刷新
						state = RELEASE_To_REFRESH;
						isBack = true;
						changeHeaderViewByState();

						// Log.v(TAG, "由done或�?�下拉刷新状态转变到松开刷新");
					}
					// 上推到顶�?
					else if (tempY - startY <= 0) {// 如果实时高度小于等于起始高度了，则说明到顶了，状态设为完成刷�?
						state = DONE;
						changeHeaderViewByState();

						// Log.v(TAG, "由DOne或�?�下拉刷新状态转变到done状�??");
					}
				}

				// done状�?�下
				if (state == DONE) {// 如果状�?�是完成刷新
					if (tempY - startY > 0) {// 如果实时高度大于起始高度了，则状态设为下拉刷�?
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
					}
				}

				// 更新headView的size
				if (state == PULL_To_REFRESH) {// 如果状�?�是下拉刷新，更新headview的size ?
					headView.setPadding(0, -1 * headContentHeight
							+ (tempY - startY)/2, 0, 0);
					headView.invalidate();
				}

				// 更新headView的paddingTop
				if (state == RELEASE_To_REFRESH) {// 如果状�?�是松开刷新，更�?
													// headview的paddingtop ?
					headView.setPadding(0, (tempY - startY)/2 - headContentHeight,
							0, 0);
					headView.invalidate();
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界�?
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("松开刷新");

			// Log.v(TAG, "当前状�?�，松开刷新");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状�?�转变来�?
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			// Log.v(TAG, "当前状�?�，下拉刷新");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);
			headView.invalidate();

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("正在刷新...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			// Log.v(TAG, "当前状�??,正在刷新...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			headView.invalidate();

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.goicon);
			tipsTextview.setText("下拉刷新");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			// Log.v(TAG, "当前状�?�，done");
			break;
		}
	}

	/**
	 * 模拟刷新状�??
	 */
	public void setRefreshing() {
		setSelection(0);
		state = REFRESHING;
		changeHeaderViewByState();
		onRefresh();
	}

	/**
	 * 设置刷新监听�?
	 */
	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	/**
	 * 设置上拉刷新监听�?
	 */
	public void setOnPullUpRefreshListener(
			OnPullUpRefreshListener onPullUpRefreshListener) {
		mOnPullUpRefreshListener = onPullUpRefreshListener;
	}

	private final Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==(-1* headContentHeight)){
				headView.setPadding(0, -1*headContentHeight, 0, 0);
				arrowImageView.clearAnimation();
				arrowImageView.setImageResource(R.drawable.goicon);
				tipsTextview.setText("下拉刷新");
			}else{
				headView.setPadding(0, msg.what, 0, 0);
				headView.invalidate();
			}
		};
	};

	/**
	 * 刷新成功
	 */
	public void onRefreshComplete() {
		isNeedPullUp=true;
		if(lastUpdatedTextView.getText().toString().equals("")){
			lastUpdatedTextView.setText("最后刷新时间:" + StringHandle.getCurrentTime());
			return;
		}
		state = DONE;
		tipsTextview.setText("刷新成功");
		lastUpdatedTextView.setText("最后刷新时间:" + StringHandle.getCurrentTime());// 刷新完成时，头部提醒的刷新日�?
		progressBar.setVisibility(View.GONE);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(400);
					for (int i = 1; i <= 40; i++) {
						float value=(i / 40.0f)*-1;
						int what=(int) (value * headContentHeight);
						handler.sendEmptyMessage(what);
						Thread.sleep(9);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 刷新成功
	 */
	public void onRefreshFail() {
		state = DONE;
		changeHeaderViewByState();
	}

	/**
	 * 刷新
	 */
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * 准备上拉刷新
	 */
	private void prepareForUpRefresh() {
		footerView.setVisibility(View.VISIBLE);
		footerProgress.setVisibility(VISIBLE);
		footerTextView.setText(R.string.toload20);
	}

	/**
	 * 上拉刷新
	 */
	public void onPullUpRefresh() {
		footerView.setVisibility(View.VISIBLE);
		if (mOnPullUpRefreshListener != null) {
			mOnPullUpRefreshListener.onPullUpRefresh();
		}
	}

	/**
	 * 上拉刷新完成复位
	 */
	public void onPullUpRefreshComplete() {
		isPullUping = false;
		footerView.setVisibility(View.GONE);
	}

	/**
	 * 上拉刷新失败:条件不够再次上拉刷新
	 */
	public void onPullUpRefreshFail() {
		isPullUping = false;
		isNeedPullUp = false;
		footerProgress.setVisibility(View.INVISIBLE);
		footerTextView.setText(getResources().getString(R.string.toloadnomore));
		footerView.setVisibility(View.GONE);
	}

	/**
	 * 刷新接口
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 上拉刷新方法接口
	 */
	public interface OnPullUpRefreshListener {
		public void onPullUpRefresh();
	}

	// 此处是�?�估计�?�headView的width以及height
	private void measureView(View child) {
		// 获取头部视图属�??
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;

		if (lpHeight > 0) { // 如果视图的高度大�?0
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

}
