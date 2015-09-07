package com.ivoryworks.pgma;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Bitmap mBitmap;

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

        // add tool menu
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        MenuItem menuShare = toolbar.getMenu().add("share");
        menuShare.setIcon(R.drawable.ic_save_white_36dp);
        menuShare.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveBitmapToSd(mBitmap);
                return false;
            }
        });

        return view;
    }

    public void saveBitmapToSd(Bitmap mBitmap) {
        try {
            // sdcardフォルダを指定
            File root = Environment.getExternalStorageDirectory();

            // 日付でファイル名を作成　
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");

            // 保存処理開始
            FileOutputStream fos = null;
            fos = new FileOutputStream(new File(root, fileName.format(mDate) + ".jpg"));

            // jpegで保存
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // 保存処理終了
            fos.close();
        } catch (Exception e) {
            Log.e("Error", "" + e.toString());
        }
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (mTexRenderer != null) {
            mTexRenderer.updateViewSize(width, height);
            mBitmap = createBitmapFromGLSurface(0, 0, width, height, gl);
        }
    }

    private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl)
            throws OutOfMemoryError {
        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
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
            case R.id.menu_item_contrast:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CONTRAST);
                mEffect.setParameter("contrast", 1.4f);
                break;
            case R.id.menu_item_crossprocess:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
                break;
            case R.id.menu_item_documentary:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
                break;
            case R.id.menu_item_duotone:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_DUOTONE);
                mEffect.setParameter("first_color", Color.YELLOW);
                mEffect.setParameter("second_color", Color.DKGRAY);
                break;
            case R.id.menu_item_filllight:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FILLLIGHT);
                mEffect.setParameter("strength", .8f);
                break;
            case R.id.menu_item_fisheye:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FISHEYE);
                mEffect.setParameter("scale", .5f);
                break;
            case R.id.menu_item_flip_vertical:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("vertical", true);
                break;
            case R.id.menu_item_flip_horizontal:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_FLIP);
                mEffect.setParameter("horizontal", true);
                break;
            case R.id.menu_item_grain_1_0:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
                mEffect.setParameter("strength", 1.0f);
                break;
            case R.id.menu_item_grain_2_0:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAIN);
                mEffect.setParameter("strength", 2.0f);
                break;
            case R.id.menu_item_grayscale:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
                break;
            case R.id.menu_item_lomoish:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_LOMOISH);
                break;
            case R.id.menu_item_negative:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_NEGATIVE);
                break;
            case R.id.menu_item_posterize:
                mEffect = effectFactory.createEffect(EffectFactory.EFFECT_POSTERIZE);
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
