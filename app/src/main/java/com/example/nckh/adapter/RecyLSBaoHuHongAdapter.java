package com.example.nckh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.object.HuHongObject;

import java.util.List;

public class RecyLSBaoHuHongAdapter extends RecyclerView.Adapter<RecyLSBaoHuHongAdapter.Holder> {

    private onClickListener onClickListener;
    private onScrollListener onScrollListener;
    private List<HuHongObject> mHuHongObjectList;


    public RecyLSBaoHuHongAdapter(List<HuHongObject> mHuHongObjectList) {
        this.mHuHongObjectList = mHuHongObjectList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hu_hong, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.sttXe.setText(mHuHongObjectList.get(position).getSttXe());
        holder.thoiGianHuHong.setText(mHuHongObjectList.get(position).getThoiGianHuhong());
        holder.viTriHuHong.setText(mHuHongObjectList.get(position).getViTriHuHong());
        holder.loaiHuHong.setText(mHuHongObjectList.get(position).getLoaiHuHong());
//        holder.sttXe.setText(mHuHongObjectList.get(position).getSttXe());
        onScrollListener.onScroll(position);
    }

    @Override
    public int getItemCount() {
        return mHuHongObjectList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView sttXe, thoiGianHuHong, viTriHuHong, loaiHuHong;

        public Holder(@NonNull View itemView) {
            super(itemView);
            sttXe = (TextView) itemView.findViewById(R.id.tv_item_stt_xe);
            thoiGianHuHong = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_hu_hong);
            viTriHuHong = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_hu_hong);
            loaiHuHong = (TextView) itemView.findViewById(R.id.tv_item_loai_hu_hong);

        }
    }


    //-----------EVENT Click------------

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onItemClick(int position, String idSanPham);
    }

    public interface onScrollListener {
        void onScroll(int position);
    }

    public void setOnScrollListener(onScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
