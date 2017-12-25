package test.infoapp.ui.base;

public abstract class BaseLazyPresenter implements LazyLoadPresenter {
    protected int offset = 0;
    protected int limit = 20;
    private boolean isFirstLoad = true;
    private boolean isLoading = false;
    private boolean isEnd = false;

    @Override
    public void loadNext() {
        if (isLoading || isEnd) {
            return;
        }
        isLoading = true;
        isFirstLoad = false;
        startLoadNext();
    }

    protected void onLoading() {
        isLoading = true;
    }

    protected void onLoaded(int countOffset) {
        if (countOffset < limit) {
            isEnd = true;
        }
        offset = offset + countOffset;
        isLoading = false;
    }

    protected void onErrorLoaded() {
        isLoading = false;
    }

    protected void clearOffset() {
        offset = 0;
        isEnd = false;
        isFirstLoad = true;
    }

    protected boolean isFirstLoad() {
        return isFirstLoad;
    }

    protected abstract void startLoadNext();

}
