package com.anass.jplus.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.anass.jplus.Config.config.SETTINGS_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Config.getComicsnextback;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class SideEpeAdapter extends RecyclerView.Adapter<SideEpeAdapter.MyView> {

    Context context;
    private SharedPreferences sp;
    private String title;
    private int Current_List_Position;

    public SideEpeAdapter(Context context,String title,int Current_List_Position) {
        this.context = context;
        this.title = title;
        this.Current_List_Position = Current_List_Position;
        sp = context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);

    }

    @NonNull
    @Override
    public SideEpeAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(parent.getContext()).inflate(R.layout.epe_side,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SideEpeAdapter.MyView holder, int position) {

        EpesodModel episdoes = getComicsnextback.Epesodestv.get(position);

        int gg = position;
        String NameAndEpesoed = title + ":" + episdoes.getLebel( );
        int currentPage = sp.getInt("currentPage" + NameAndEpesoed, 1);
        int allduration = sp.getInt("allduration" + NameAndEpesoed, 0);


        if (allduration < 6000) {
            holder.showWhems.setMax(0);

        } else {
            holder.showWhems.setMax(allduration);
        }

        holder.showWhems.setProgress(currentPage);



        if (position == Current_List_Position){
            holder.selectedepe.setVisibility(View.VISIBLE);
        }

        holder.cast_name.setText(" الحلقة : " + episdoes.getLebel());

    }

    @Override
    public int getItemCount() {
        return getComicsnextback.Epesodestv.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        ImageView selectedepe;
        TextView cast_name;
        LinearProgressIndicator showWhems;
        public MyView(@NonNull View itemView) {
            super(itemView);

            selectedepe = itemView.findViewById(R.id.selectedepe);
            cast_name = itemView.findViewById(R.id.cast_name);
            showWhems = itemView.findViewById(R.id.showWhems);


        }
    }
}
