package com.ivoryworks.pgma;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String TAG = GalleryFragment.class.getSimpleName();
    private GridView mGridView;
    private GridAdapter mAdapter;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGridView = (GridView) layoutView.findViewById(R.id.gridView);

        // set Adapter
        mAdapter = new GridAdapter(getActivity(), null, true);
        mGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // init Loader
        getLoaderManager().initLoader(0, null, this);

        return layoutView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // destroy Loader
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // /sdcardの場合
        // MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        // 内部ストレージの場合
        // MediaStore.Images.Media.INTERNAL_CONTENT_URI
        return new CursorLoader(this.getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        // update
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public class GridAdapter extends CursorAdapter {

        private LayoutInflater mInflater;

        class ViewHolder {
            ImageView imageView;
        }

        public GridAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder holder = (ViewHolder) view.getTag();

            final long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            if (thumbnail != null) {
                holder.imageView.setImageBitmap(thumbnail);
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            final View view = mInflater.inflate(R.layout.thumbnail, null);

            ViewHolder holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.thumbnail_image);
            view.setTag(holder);

            return view;
        }
    }
}
