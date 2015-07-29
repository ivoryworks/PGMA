package com.ivoryworks.pgma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.EffectContext;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class MediaEffectsFragment extends Fragment implements GLSurfaceView.Renderer {

    public static String TAG = MediaEffectsFragment.class.getSimpleName();
    private GLSurfaceView mEffectView;
    private int[] mTextures = new int[2];
    private EffectContext  mEffectContext;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;

    public static MediaEffectsFragment newInstance() {
        return new MediaEffectsFragment();
    }

    public MediaEffectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_effects, container, false);
        mEffectView = (GLSurfaceView) view.findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(this);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        return view;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (mTexRenderer != null) {
            mTexRenderer.updateViewSize(width, height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mEffectContext == null) {
            mEffectContext = EffectContext.createWithCurrentGlContext();
            mTexRenderer.init();
            loadTextures();
        }
        renderResult();
    }

    private void loadTextures() {
        // Generate textures
        GLES20.glGenTextures(2, mTextures, 0);
        // Load input bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.octocat_l);
        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();
        mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);
        // Upload to texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        // Set texture parameters
        GLToolbox.initTexParams();
    }

    private void renderResult() {
        mTexRenderer.renderTexture(mTextures[0]);
    }
}
