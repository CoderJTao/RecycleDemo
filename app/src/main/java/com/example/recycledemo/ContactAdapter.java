package com.example.recycledemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyRecyclerHolder> {

    private Context mContext;
    private List<Hero> heroList;

    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();


    public ContactAdapter(Context context) {
        mContext = context;

        heroList = new ArrayList<>();
    }

    public void setData(List<Hero> list) {
        if (heroList.size()> 0) {
            heroList.clear();
        }

        heroList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hero, viewGroup, false);
        return new MyRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerHolder myRecyclerHolder, int i) {
        if (heroList == null || heroList.size() == 0 || heroList.size() <= i) return;

        Hero hero = heroList.get(i);

        if (hero != null) {
            myRecyclerHolder.m_name.setText(hero.getName());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(hero.getName().charAt(0)), mColorGenerator.getColor(hero.getName()));
            myRecyclerHolder.m_image.setImageDrawable(drawable);
        }

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public static class MyRecyclerHolder extends RecyclerView.ViewHolder {
        public TextView m_name;
        public ImageView m_image;

        public MyRecyclerHolder(View itemView) {
            super(itemView);

            m_name = (TextView) itemView.findViewById(R.id.item_name);
            m_image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

}
