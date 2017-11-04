package test.infoapp.ui.base.interfaces;

import com.tbruyelle.rxpermissions2.Permission;

import java.util.List;

public interface IPermissionRequestListener {
    interface View {
        void requestPermission(CallbackPermission callbackPermission, String... permissions);
    }

    interface CallbackPermission {
        void onResultPermission(List<Permission> permissions);
    }
}
