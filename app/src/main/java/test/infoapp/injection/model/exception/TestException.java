package test.infoapp.injection.model.exception;

public class TestException extends RuntimeException {
    public TestException(String message) {
        super(message);
        /*ignore print to stack for testing*/
        System.setErr(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int i) {
            }
        }));
    }
}
