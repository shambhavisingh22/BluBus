package com.example.shamb.busreservation;

/**
 * Created by shamb on 3/19/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterBindViewHolder>{
    List<Model> list;
    Context context;
    String date;
    public SearchAdapter(List<Model> res,Context c1, String date) {
        list = res;                // 1st
        context = c1;
         date = date;
    }

    @Override
    public SearchAdapterBindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)   // 3
                .inflate(R.layout.recycler_view_repeat,parent,false);
        return new SearchAdapterBindViewHolder(view);
    }



    @Override
    public void onBindViewHolder(SearchAdapterBindViewHolder holder, int position) {
       /* Picasso picasso = Picasso.with(context);
        if(list.get(position)!=null) {
            picasso.load(list.get(position).getImage_url())   // 5
                    .centerCrop()
                    .fit()
                    .into(holder.image);
        }*/
        if(list.get(position).getSource()!=null) {
            holder.t1.setText(list.get(position).getSource());
        }
        if(list.get(position).getDestination()!=null) {
            holder.t2.setText(list.get(position).getDestination());
        }
        if(list.get(position).getCost()!=0) {
            holder.t3.setText("" + list.get(position).getCost());
        }
        if(list.get(position).getStart_Time()!=null) {
            holder.t4.setText(list.get(position).getStart_Time());
        }
        if(list.get(position).getReached_Time()!=null) {
            holder.t5.setText(list.get(position).getReached_Time());
        }
        if(list.get(position).getName()!=null) {
            holder.t6.setText(list.get(position).getName());
        }
        if(list.get(position).getSeats_left()!=0) {
            holder.t3.setText("" + list.get(position).getSeats_left());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    } // 2

    public class SearchAdapterBindViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4,t5,t6;
        Button book;
        public SearchAdapterBindViewHolder(View TextView) {
            super(TextView);

            t1 = (TextView) itemView.findViewById(R.id.text1);
            t2 = (TextView) itemView.findViewById(R.id.text2);
            t3 = (TextView) itemView.findViewById(R.id.text3);
            t4 = (TextView) itemView.findViewById(R.id.text4);
            t5 = (TextView) itemView.findViewById(R.id.text5);
            t6 = (TextView) itemView.findViewById(R.id.text6);
            book=(Button) itemView.findViewById(R.id.book);

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle(); // to send ID aage.
                    bundle.putString("ID",list.get(getAdapterPosition()).getId());
                    bundle.putString("date",date);
                    Intent intent =new Intent("android.intent.action.BookForm");
             //       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

        }
    }
}
