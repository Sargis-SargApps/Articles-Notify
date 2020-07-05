package com.sargis.prueba1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sargis.prueba1.R;
import com.sargis.prueba1.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> mArticleList;

    public ArticleAdapter(List<Article> mArticleList){
        this.mArticleList=mArticleList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
         MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int p) {
     //   holder.image.setImageResource(mArticleList.get(p).getImage());
        holder.title.setText(mArticleList.get(p).getTitle());
        holder.description.setText(mArticleList.get(p).getDescription());
        holder.owner.setText(mArticleList.get(p).getOwnerName());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener == null) return;
                mOnItemClickListener.onItemClick(v, mArticleList.get(p), p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Article article, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
       // ImageView image;
        TextView title;
        TextView description;
        TextView owner;
        Button like;

        public MyViewHolder(View v){
            super(v);

            //image=(ImageView) v.findViewById(R.id.imageView);
            title=(TextView) v.findViewById(R.id.title);
            description=(TextView) v.findViewById(R.id.description);
            owner=(TextView) v.findViewById(R.id.owner);
            like=(Button)v.findViewById(R.id.btn_like);
        }

    }
}
