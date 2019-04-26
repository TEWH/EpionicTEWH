package org.texasewh.epionic;

import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MethodCallsLogger;
import java.lang.Override;

public class AwesomeLibMain_LifecycleAdapter implements GeneratedAdapter {
  final AwesomeLibMain mReceiver;

  AwesomeLibMain_LifecycleAdapter(AwesomeLibMain receiver) {
    this.mReceiver = receiver;
  }

  @Override
  public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny,
      MethodCallsLogger logger) {
    boolean hasLogger = logger != null;
    if (onAny) {
      return;
    }
    if (event == Lifecycle.Event.ON_CREATE) {
      if (!hasLogger || logger.approveCall("init", 1)) {
        mReceiver.init();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_START) {
      if (!hasLogger || logger.approveCall("LibOnStart", 1)) {
        mReceiver.LibOnStart();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_STOP) {
      if (!hasLogger || logger.approveCall("LibOnStop", 1)) {
        mReceiver.LibOnStop();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_RESUME) {
      if (!hasLogger || logger.approveCall("LibOnResume", 1)) {
        mReceiver.LibOnResume();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_PAUSE) {
      if (!hasLogger || logger.approveCall("LibOnPause", 1)) {
        mReceiver.LibOnPause();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_DESTROY) {
      if (!hasLogger || logger.approveCall("cleanup", 1)) {
        mReceiver.cleanup();
      }
      return;
    }
  }
}
