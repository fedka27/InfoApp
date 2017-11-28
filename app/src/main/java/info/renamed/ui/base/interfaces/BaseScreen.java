package info.renamed.ui.base.interfaces;

public interface BaseScreen {
    interface View {

    }

    interface Presenter<T extends View> {

        void setView(T view);

        void onStart();

        void onStop();
    }

    interface AdapterPresenter {

    }
}
