package info.renamed.injection.model.interactors;

import info.renamed.ui.base.interfaces.IKeyboardManageListener;
import info.renamed.ui.base.interfaces.IProgressListener;
import info.renamed.ui.base.interfaces.IThrowableListener;
import info.renamed.ui.base.interfaces.IViewEnabledListener;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

public class ViewInteractor {

    public <T> ObservableTransformer<T, T> manageView(IViewEnabledListener viewEnabled) {
        return objectObservable -> objectObservable
                .doOnSubscribe(disposable -> viewEnabled.enableView(false))
                .doFinally(() -> viewEnabled.enableView(true));
    }

    public <T> ObservableTransformer<T, T> manageKeyboardObservable(IKeyboardManageListener keyboardManageListener) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> keyboardManageListener.keyboardVisible(false));
    }

    public <T> SingleTransformer<T, T> manageKeyboardSingle(IKeyboardManageListener keyboardManageListener) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> keyboardManageListener.keyboardVisible(false));
    }

    public <T> ObservableTransformer<T, T> manageKeyboardAndProgress
            (IKeyboardManageListener keyboardManageListener,
             IProgressListener iProgressListener) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> {
                    keyboardManageListener.keyboardVisible(false);
                    iProgressListener.showProgress();
                })
                .doFinally(iProgressListener::hideProgress);
    }

    public <T> ObservableTransformer<T, T> manageProgressObservable(IProgressListener iProgressListener) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> iProgressListener.showProgress())
                .doFinally(iProgressListener::hideProgress);
    }

    public <T> SingleTransformer<T, T> manageProgressSingle(IProgressListener iProgressListener) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> iProgressListener.showProgress())
                .doFinally(iProgressListener::hideProgress);
    }

    public void manageError(IThrowableListener view, Throwable throwable) {
        throwable.printStackTrace();
//        if (throwable instanceof UnauthorizedException) {
//            view.authorizationError((UnauthorizedException) throwable);
//        } else {
        view.showError(throwable.getMessage());
//        }
    }
}
