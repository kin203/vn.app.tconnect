package vn.app.tconnect.adapter;

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
import vn.app.tconnect.models.FlashSaleModel;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {

    private Context context;
    private List<FlashSaleModel> flashSaleModelList;

    public SaleAdapter(Context context, List<FlashSaleModel> flashSaleModelList) {
        this.context = context;
        this.flashSaleModelList = flashSaleModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.flashsale_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(flashSaleModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(flashSaleModelList.get(position).getName());
        holder.price.setText(flashSaleModelList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return flashSaleModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg=itemView.findViewById(R.id.pop_img);
            name= itemView.findViewById(R.id.FS_name);
            price= itemView.findViewById(R.id.FS_price);
        }
    }
}
