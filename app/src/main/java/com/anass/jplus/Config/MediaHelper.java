package com.anass.jplus.Config;

import android.content.Context;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;

public final class MediaHelper {
    public static RenderersFactory buildRenderersFactory(
            Context context, boolean preferExtensionRenderer, boolean softwareCodec) {

        if (preferExtensionRenderer){

            return new DefaultRenderersFactory(context.getApplicationContext())
                    .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER).setEnableDecoderFallback(softwareCodec);
        }else {

            return new DefaultRenderersFactory(context.getApplicationContext()).setEnableDecoderFallback(softwareCodec);
        }

    }
}
