package info.renamed.injection;

public class ComponentProviderNotInitializedException extends RuntimeException {
    private static final String ERROR = "ComponentProvider not initialized yet. " +
            "You must call ComponentProvider.getInstance().init(Application) before you can use it.";

    public ComponentProviderNotInitializedException() {
        super(ERROR);
    }
}
