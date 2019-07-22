package com.example.nckh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.object.MuonXeObject;

import java.util.List;

public class RecyLSMuonXeAdapter extends RecyclerView.Adapter<RecyLSMuonXeAdapter.Holder> {

    private onClickListener onClickListener;
    private onScrollListener onScrollListener;

    private List<MuonXeObject> mMuonXeObjectList;

    public RecyLSMuonXeAdapter(List<MuonXeObject> mMuonXeObjectList) {
        this.mMuonXeObjectList = mMuonXeObjectList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_muon_xe, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        holder.sttXe.setText(mMuonXeObjectList.get(position).getSttXe());
        holder.thoiGianMuon.setText(mMuonXeObjectList.get(position).getThoiGianMuon());
        holder.viTriMuon.setText(mMuonXeObjectList.get(position).getViTriMuon());
        holder.thoiGianTra.setText(mMuonXeObjectList.get(position).getThoiGianTra());
        holder.viTriTra.setText(mMuonXeObjectList.get(position).getViTriTra());
        onScrollListener.onScroll(position);

    }

    @Override
    public int getItemCount() {
        return mMuonXeObjectList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView sttXe, thoiGianMuon, viTriMuon, thoiGianTra, viTriTra;

        public Holder(@NonNull View itemView) {
            super(itemView);
            sttXe = (TextView) itemView.findViewById(R.id.tv_item_stt_xe);
            thoiGianMuon = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_muon);
            viTriMuon = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_muon);
            thoiGianTra = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_tra);
            viTriTra = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_tra);
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
