package test.infoapp.util.connection;

public class ConnectionUtilTest extends ConnectionUtilAbs {
    private boolean isOnline = false;

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
