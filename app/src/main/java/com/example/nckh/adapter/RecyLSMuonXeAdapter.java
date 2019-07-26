package com.example.nckh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.object.LSMuonXe.Doc;

import java.util.List;

public class RecyLSMuonXeAdapter extends RecyclerView.Adapter<RecyLSMuonXeAdapter.Holder> {

    private onClickListener onClickListener;
    private onScrollListener onScrollListener;

    private List<Doc> mMuonXeObjectList;

    public RecyLSMuonXeAdapter(List<Doc> mMuonXeObjectList) {
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


        holder.sttXe.setText("Xe số " + mMuonXeObjectList.get(position).getXEID() + "");
        holder.thoiGianMuon.setText("Mượn lúc:" + mMuonXeObjectList.get(position).getMUONTHOIGIAN());
        holder.viTriMuon.setText(mMuonXeObjectList.get(position).getMUONVITRILAT() + "-" + mMuonXeObjectList.get(position).getMUONVITRILNG());
        if (mMuonXeObjectList.get(position).getTRATHOIGIAN() != null) {
            holder.thoiGianTra.setText(mMuonXeObjectList.get(position).getTRATHOIGIAN());
            holder.viTriTra.setText(mMuonXeObjectList.get(position).getTRAVITRILAT() + "-" + mMuonXeObjectList.get(position).getTRAVITRILNG());
        } else {
            holder.thoiGianTra.setText("Đang mượn xe");
            holder.viTriTra.setVisibility(View.GONE);
            holder.viTriTraName.setVisibility(View.GONE);
        }
        onScrollListener.onScroll(position);

    }

    @Override
    public int getItemCount() {
        return mMuonXeObjectList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView sttXe, thoiGianMuon, viTriMuon, thoiGianTra, viTriTra, viTriTraName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            sttXe = (TextView) itemView.findViewById(R.id.tv_item_stt_xe);
            thoiGianMuon = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_muon);
            viTriMuon = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_muon);
            thoiGianTra = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_tra);
            viTriTra = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_tra);
            viTriTraName = (TextView) itemView.findViewById(R.id.tv_item_name_vi_tri_tra);


            viTriMuon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onTextGPSClick(sttXe.getText().toString(), viTriMuon.getText().toString());

                }
            });

            viTriTra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onTextGPSClick(sttXe.getText().toString(), viTriTra.getText().toString());

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
