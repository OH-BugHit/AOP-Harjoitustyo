package com.oh.ready4play.adapterit_ja_itemit;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oh.ready4play.Pelaaja;
import com.oh.ready4play.R;
import com.oh.ready4play.UusiPeli;

import java.util.ArrayList;

/**
 * RecycleViewAdapteri Pelaajien esittämiseen Uusipeli näkymässä
 */
public class PelaajaAdapteri extends RecyclerView.Adapter<PelaajaAdapteri.ViewHolder> {
    private static ArrayList<Pelaaja> pelaajaSetti;

    public PelaajaAdapteri(ArrayList<Pelaaja> pelaajat) {pelaajaSetti = pelaajat;}

    View.OnClickListener poistaPelaajaListener = new View.OnClickListener() {

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            UusiPeli.nappulaKuva[pelaajaSetti.get(position).nappulaNumero] = true;
            UusiPeli.taynna = false;
            UusiPeli.INSTANCE.btLisaaPelaaja.setEnabled(true);
            UusiPeli.pelaajaMaara --;
            if (UusiPeli.pelaajaMaara < 2) {
                UusiPeli.INSTANCE.btAloitaPeli.setEnabled(false);
            } else {UusiPeli.INSTANCE.btAloitaPeli.setEnabled(true);}
            UusiPeli.INSTANCE.ivNappulanKuva.setImageDrawable(pelaajaSetti.get(position).pelaajakuva);
            UusiPeli.itemArrayList.remove(position);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public PelaajaAdapteri.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelaaja_item, parent, false);

        return new PelaajaAdapteri.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PelaajaAdapteri.ViewHolder holder, int position) {
        holder.pelaajanimi.setText(pelaajaSetti.get(position).pelaajanimi);

        holder.pelaajaKuva.setImageDrawable(pelaajaSetti.get(position).pelaajakuva);

        holder.poistaPelaaja.setTag(position);
        holder.poistaPelaaja.setOnClickListener(poistaPelaajaListener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter
     * @return Palauttaa pelaajasetin koon.
     */
    @Override
    public int getItemCount() {
        return pelaajaSetti.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView pelaajanimi;
        public final ImageView pelaajaKuva;
        public final Button poistaPelaaja;

        public ViewHolder(View itemView) {
            super(itemView);
            pelaajanimi = (TextView) itemView.findViewById(R.id.tvPelaajanimi_PelaajaItem);
            pelaajaKuva = (ImageView) itemView.findViewById(R.id.ivPelaajaKuva_Pelaaja_Item);
            poistaPelaaja = (Button) itemView.findViewById(R.id.btPoistaPelaaja_Pelaaja_Item);
        }
    }
}
