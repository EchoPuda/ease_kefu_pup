package com.puppet.ease_kefu.handler;

import io.flutter.plugin.common.PluginRegistry;

/**
 * @author Puppet
 */
public class EaseKefuRequestHandler {

    private static PluginRegistry.Registrar registrar = null;

    public static void setRegistrar(PluginRegistry.Registrar reg) {
        EaseKefuRequestHandler.registrar = reg;
    }

    public PluginRegistry.Registrar getRegistrar() {
        return registrar;
    }
}
