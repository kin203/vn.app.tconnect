package vn.app.tconnect.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.app.tconnect.R;

public class FlashSaleModel {
    String name;
    String price;
    String img_url;

    public FlashSaleModel(String name, String price, String img_url, String type) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public FlashSaleModel(){

    }

    public static class TaiNgheAdapter extends RecyclerView.Adapter<TaiNgheAdapter.ViewHolder>{
        Context context;
        List<TaiNgheModel> list;

        public TaiNgheAdapter(Context context, List<TaiNgheModel> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tainghe_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Glide.with(context).load(list.get(position).getImg_url()).into(holder.popImg);
            holder.name.setText(list.get(position).getName());
            holder.price.setText(list.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView popImg;
            TextView name,price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                popImg=itemView.findViewById(R.id.MH_img);
                name= itemView.findViewById(R.id.MH_name);
                price= itemView.findViewById(R.id.MH_price);
            }
        }
    }
}
