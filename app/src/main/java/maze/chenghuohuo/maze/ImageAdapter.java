package maze.chenghuohuo.maze;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by 44223 on 2018/8/12.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] mImageIDs=
            {
                    R.drawable.green,
                    R.drawable.red,
                    R.drawable.road,
                    R.drawable.wall,
            };

    public ImageAdapter(Context c)
    {
        mContext=c;
    }

    //返回图片的个数
    @Override
    public int getCount() {
        return mImageIDs.length;
    }

    //获取图片的位置
    @Override
    public Object getItem(int position) {
        return position;
    }

    //获取图片的ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView==null)
        {
            //给图片设置资源
            imageView=new ImageView(mContext);
            //设置布局
            imageView.setLayoutParams(new GridView.LayoutParams(120,120));
            //设置显示比例类型
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else
        {
            imageView=(ImageView)convertView;
        }
        imageView.setImageResource(mImageIDs[position]);
        return imageView;
    }
}
