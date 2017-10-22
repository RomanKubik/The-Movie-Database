package kubik.roman.moviesdb.models.movies_list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Endless scroll for RecyclerView mail list
 */
public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessOnScrollListener.class.getSimpleName();

    private int mPreviousTotal = 0; // The total number of items in the dataset after the last load
    private boolean mIsLoading = true; // True if we are still waiting for the last set of data to load.
    private int mVisibleThreshold = 3; // The minimum amount of items to have below your current scroll position before loading more.

    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;

    private int mCurrentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mIsLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                mIsLoading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }
        if (!mIsLoading && (mTotalItemCount - mVisibleItemCount)
                <= (mFirstVisibleItem + mVisibleThreshold) ) {

            mCurrentPage++;

            onLoadMore(mCurrentPage);

            mIsLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
