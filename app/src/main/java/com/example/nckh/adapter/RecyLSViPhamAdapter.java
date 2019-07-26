package com.example.nckh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nckh.R;
import com.example.nckh.object.LSViPham.Doc;

import java.util.List;

public class RecyLSViPhamAdapter extends RecyclerView.Adapter<RecyLSViPhamAdapter.Holder> {

    private onClickListener onClickListener;
    private onScrollListener onScrollListener;

    private List<Doc> mViPhamObjectList;

    public RecyLSViPhamAdapter(List<Doc> mViPhamObjectList) {
        this.mViPhamObjectList = mViPhamObjectList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vi_pham, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.sttXe.setText("Xe số " + mViPhamObjectList.get(position).getMuontra().getXEID() + "");
        holder.thoiGianViPham.setText("Vi phạm vào lúc: " + mViPhamObjectList.get(position).getVPTHOIGIAN());
        holder.viTriViPham.setText("Vị trí: " + mViPhamObjectList.get(position).getVPLAT() + "-" + mViPhamObjectList.get(position).getVPLNG());
        holder.loiViPham.setText("Lỗi: " + mViPhamObjectList.get(position).getLoi().getLOITEN());
        holder.hinhThucXuLy.setText(mViPhamObjectList.get(position).getLoi().getLOIMOTA());
        onScrollListener.onScroll(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView sttXe, thoiGianViPham, viTriViPham, loiViPham, hinhThucXuLy;

        public Holder(@NonNull View itemView) {
            super(itemView);
            sttXe = (TextView) itemView.findViewById(R.id.tv_item_stt_xe);
            thoiGianViPham = (TextView) itemView.findViewById(R.id.tv_item_thoi_gian_vi_pham);
            viTriViPham = (TextView) itemView.findViewById(R.id.tv_item_vi_tri_vi_pham);
            loiViPham = (TextView) itemView.findViewById(R.id.tv_item_loi_vi_pham);
            hinhThucXuLy = (TextView) itemView.findViewById(R.id.tv_item_hinh_thuc_xu_ly);
            viTriViPham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onTextGPSClick(sttXe.getText().toString(), viTriViPham.getText().toString());

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
