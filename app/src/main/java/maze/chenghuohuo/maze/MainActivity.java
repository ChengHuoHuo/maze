package maze.chenghuohuo.maze;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;


import static java.lang.Math.random;


public class MainActivity extends Activity {

    private int[][] maze;
    private int[][] mazePath;
    private int c1[]={1,-1,0,0};
    private int c2[]={0,0,-1,1};
    GridView gridview;
    private int c=0;
    private int r=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得GridView对象
        gridview = (GridView) findViewById(R.id.gridview);
        //添加元素给gridview


        initmaze(51,51);
        createmaze(1,1);
        for(int i=0;i<51;i++)
            for(int j=0;j<51;j++)
            {
                if(maze[i][j]==5)
                {
                    maze[i][j]=1;
                }
            }
        final ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 51; i++)
            for (int j = 0; j < 51; j++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                if(i==1&&j==0)
                    map.put("ItemImg", R.drawable.green);
                else if(i==49&&j==50)
                    map.put("ItemImg", R.drawable.red);
                else if (maze[i][j] ==1)
                    map.put("ItemImg", R.drawable.road);
                else if(maze[i][j]==0)
                    map.put("ItemImg", R.drawable.timg);
                lst.add(map);
            }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, lst, R.layout.small_part, new String[]{"ItemImg"}, new int[]{R.id.imageView});
        gridview.setAdapter(simpleAdapter);

        final Button btn_reset=(Button)findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(null);
                Toast.makeText(MainActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
            }
        });

        final Button btn_find=(Button)findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initmazepath(51,51);
                findPath(r,c,maze,mazePath);
                for (int i1 = 0; i1 < 51; i1++)
                    for (int j1 = 0; j1 < 51; j1++) {
                        Log.d("msg","kkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                        if(mazePath[i1][j1]==2)
                        {
                            int position = i1*maze[0].length + j1;
                            updateSingle(position,R.drawable.blue);
                            Log.d("msg","sssssssssssssssssssssssssss");
                        }
                    }

            }
        });

    }
    public void initmaze(int i,int j) {
        maze = new int[i][j];

        for (int i1 = 0; i1 < i; i1++)
            for (int j1 = 0; j1 < j; j1++) {
            if(i1!=0&&j1!=0)
            {
                if(i1%2!=0&&j1%2==1)
                    maze[i1][j1]=1;
            }
            else
                maze[i1][j1] = 0;//迷宫矩阵
            }
    }

    private void initmazepath(int i,int j){
        mazePath= new int[i][j];
//        for (int i1 = 0; i1 < i; i1++)
//            for (int j1 = 0; j1 < j; j1++) {
//            mazePath[i1][j1]=maze[i1][j1];
//            }

    }

    public void findPath(int r,int c,int[][] maze,int[][] mazePath)
    {
        int rows=51;
        int cols=51;
        if(r==rows-2&&c==cols-2)
        {
            for(int i=0;i<rows;i++)
            {
                for(int j=0;j<cols;j++) {
                    mazePath[i][j] = maze[i][j];
                }
            }
        }

        for(int i=0;i<4;i++) {
            int newr = r + c1[i];
            int newc = c + c2[i];
            if(check(newr,newc,maze)) {
                maze[newr][newc] = 2;
                findPath(newr,newc,maze,mazePath);
                maze[newr][newc] = 1;
            }
        }
    }

    public boolean check(int r,int c,int[][] maze)
    {
        if(r<0||r>50||c<0||c>50||maze[r][c]==0||maze[r][c]==2)
            return false;
        return true;
    }


    public void createmaze(int X_index,int Y_index)
    {
        int rand_position,x,y,flag=0;
        Random rand=new Random();
        x=X_index;
        y=Y_index;
        while (true)
        {
            flag=0;
            flag=isHaveNeibohood(X_index,Y_index);
            if(flag==0)
            {
                return;
            }
            else
            {
                maze[X_index][Y_index]=5;
                x=X_index;
                y=Y_index;
            }
            while (true)
            {
                rand_position=rand.nextInt(4)%4;
                if(rand_position==0&&X_index>=3&&maze[X_index-2][Y_index]==1)//上
                {
                    X_index=X_index-2;
                }
                else if(rand_position==1&&X_index<(51-3)&&maze[X_index+2][Y_index]==1)//下
                {
                    X_index=X_index+2;
                }
                else if(rand_position==2&&Y_index>=3&&maze[X_index][Y_index-2]==1)
                {
                    Y_index=Y_index-2;
                }
                else if(rand_position==3&&Y_index<(51-3)&&maze[X_index][Y_index+2]==1)
                {
                    Y_index=Y_index+2;
                }
                maze[(x+X_index)/2][(y+Y_index)/2]=5;
                maze[X_index][Y_index]=5;
                createmaze(X_index,Y_index);
                break;
            }
        }
    }

    public int isHaveNeibohood(int X_index,int Y_index)
    {
        int i,j,flag=0;
        if((X_index>=3&&maze[X_index-2][Y_index]==1)||(X_index<(51-3)&&maze[X_index+2][Y_index]==1)||(Y_index>=3&&maze[X_index][Y_index-2]==1)||(Y_index<(51-3)&&maze[X_index][Y_index+2]==1))
        {
            return 1;
        }
        return 0;
    }

    private void updateSingle(int position,int img) {
        View view = gridview.getChildAt(position - 0);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(img);
    }

}


