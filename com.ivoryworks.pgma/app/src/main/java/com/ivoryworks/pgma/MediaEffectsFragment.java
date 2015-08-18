package com.ivoryworks.pgma;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

public class MediaEffectsFragment extends Fragment implements GLSurfaceView.Renderer {

    public static String TAG = MediaEffectsFragment.class.getSimpleName();
    private static final String STATE_CURRENT_EFFECT = "current_effect";
    private GLSurfaceView mEffectView;
    private int[] mTextures = new int[2];
    private EffectContext  mEffectContext;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;
    private Effect mEffect;
    private int mCurrentEffect;
    private boolean mInitialized = false;

    public static MediaEffectsFragment newInstance() {
        return new MediaEffectsFragment();
    }

    public MediaEffectsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_EFFECT, mCurrentEffect);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.effects_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mCurrentEffect = item.getItemId();
        mEffectView.requestRender();
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_effects, container, false);
        mEffectView = (GLSurfaceView) view.findViewById(R.id.effectsview);
        mEffectView.setEGLContextClientVersion(2);
        mEffectView.setRenderer(this);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mInitialized = false;
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_CURRENT_EFFECT)) {
            mCurrentEffect = savedInstanceState.getInt(STATE_CURRENT_EFFECT);
        } else {
            mCurrentEffect = R.id.none;
        }
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
        if (mInitialized == false) {
            mEffectContext = EffectContext.createWithCurrentGlContext();
            mTexRenderer.init();
            loadTextures();
            mInitialized = true;
        }
        if (mCurrentEffect != R.id.none) {
            initEffect(mCurrentEffect);
            applyEffect();
        }
        renderResult();
    }

    private void loadTextures() {
        // Generate textures
        GLES20.glGenTextures(2, mTextures, 0);
        AssetManager manager = getResources().getAssets();
        try {
            // Load input bitmap
            InputStream inputStreamBase = manager.open(Constants.BEETLE_FILENAME);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStreamBase);
            mImageWidth = bitmap.getWidth();
            mImageHeight = bitmap.getHeight();
            mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);

            // Upload to texture
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            // Set texture parameters
            GLToolbox.initTexParams();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderResult() {
        if (mCurrentEffect != R.id.none) {
            // if no effect is chosen, just render the original bitmap
            mTexRenderer.renderTexture(mTextures[1]);
        } else {
            // render the result of applyEffect()
            mTexRenderer.renderTexture(mTextures[0]);
        }
    }

    private void initEffect(int effectMenuId) {
        EffectFactory effectFactory = mEffectContext.getFactory();
        if (mEffect != null) {
            mEffect.release();
        }

        switch (effectMenuId){
            case R.id.menu_item_autofix:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_AUTOFIX);
                mEffect.setParameter("scale", 0.5f);
                break;
            case R.id.menu_item_blackwhite:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BLACKWHITE);
                mEffect.setParameter("black", .1f);
                mEffect.setParameter("white", .7f);
                break;
            case R.id.menu_item_brightness:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
                mEffect.setParameter("brightness", 2.0f);
                break;
            case R.id.menu_item_lomoish:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
                break;
            case R.id.menu_item_negative:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
                break;
            case R.id.menu_item_rotate90:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
                mEffect.setParameter("angle", 90);
                break;
            case R.id.menu_item_rotate180:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
                mEffect.setParameter("angle", 180);
                break;
            case R.id.menu_item_rotate270:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_ROTATE);
                mEffect.setParameter("angle", 270);
                break;
            default:
                break;
        }
    }

    private void applyEffect() {
        if (mCurrentEffect == R.id.menu_item_rotate90 || mCurrentEffect == R.id.menu_item_rotate270) {
            mTexRenderer.updateTextureSize(mImageHeight, mImageWidth);
            mEffect.apply(mTextures[0], mImageHeight, mImageWidth, mTextures[1]);
        } else {
            mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);
            mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
        }
    }
}
