package com.example.nckh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.object.LSBaoHuHong.Doc;

import java.util.List;

public class RecyLSBaoHuHongAdapter extends RecyclerView.Adapter<RecyLSBaoHuHongAdapter.Holder> {

    private onClickListener onClickListener;
    private onScrollListener onScrollListener;
    private List<Doc> mHuHongObjectList;


    public RecyLSBaoHuHongAdapter(List<Doc> mHuHongObjectList) {
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

        holder.sttXe.setText("Xe số " + mHuHongObjectList.get(position).getXEID() + "");
        holder.thoiGianHuHong.setText("Thời gian báo: " + mHuHongObjectList.get(position).getHHTHOIGIAN());
        holder.viTriHuHong.setText(mHuHongObjectList.get(position).getHUHONGLAT() + "-" + mHuHongObjectList.get(position).getHUHONGLNG());
        holder.loaiHuHong.setText(mHuHongObjectList.get(position).getHHMOTA());
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
            viTriHuHong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onTextGPSClick(sttXe.getText().toString(), viTriHuHong.getText().toString());

                }
            });

        }
    }


    //-----------EVENT Click------------

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onTextGPSClick(String sttXe, String toaDo);
    }

    public interface onScrollListener {
        void onScroll(int position);
    }

    public void setOnScrollListener(onScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
