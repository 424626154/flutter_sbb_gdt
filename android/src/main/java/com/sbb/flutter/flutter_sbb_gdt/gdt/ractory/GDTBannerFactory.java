package com.sbb.flutter.flutter_sbb_gdt.gdt.ractory;

import android.content.Context;

import com.sbb.flutter.flutter_sbb_gdt.gdt.view.GDTBannerView;

import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class GDTBannerFactory extends PlatformViewFactory {
    PluginRegistry.Registrar mRegistrar;
    public GDTBannerFactory(PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        this.mRegistrar = registrar;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new GDTBannerView(context, mRegistrar, id, params);
    }
}
