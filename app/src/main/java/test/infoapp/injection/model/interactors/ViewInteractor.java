package test.infoapp.injection.model.interactors;

import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import test.infoapp.ui.base.interfaces.IKeyboardManageListener;
import test.infoapp.ui.base.interfaces.IProgressListener;
import test.infoapp.ui.base.interfaces.IThrowableListener;
import test.infoapp.ui.base.interfaces.IViewEnabledListener;

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
