package ab.cd.ef.postermaker.adapter;


import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import ab.cd.ef.postermaker.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}
