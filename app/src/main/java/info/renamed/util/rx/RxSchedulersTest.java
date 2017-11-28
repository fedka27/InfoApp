package info.renamed.util.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersTest extends RxSchedulersAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

}
