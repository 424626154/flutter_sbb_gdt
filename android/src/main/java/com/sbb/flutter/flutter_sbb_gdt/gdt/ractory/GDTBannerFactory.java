package com.sbb.flutter.flutter_sbb_gdt.gdt.ractory;

import android.content.Context;

import com.sbb.flutter.flutter_sbb_gdt.gdt.view.GDTBannerView;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class GDTBannerFactory extends PlatformViewFactory {
    BinaryMessenger messenger;
    public GDTBannerFactory(BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new GDTBannerView(context, messenger, id, params);
    }
}
