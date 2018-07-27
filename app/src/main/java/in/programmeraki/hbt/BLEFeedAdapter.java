package in.programmeraki.hbt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.programmeraki.hbt.model.BLEFeedData;

public class BLEFeedAdapter extends RecyclerView.Adapter<BLEFeedAdapter.MyViewHolder> {

    ArrayList<BLEFeedData> bleFeedDataArr = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_raw_feed, null, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.timestamp_tv.setText(bleFeedDataArr.get(position).getTimestamp());
        holder.pulse_tv.setText(bleFeedDataArr.get(position).getPulse());
        holder.temp_tv.setText(bleFeedDataArr.get(position).getTemp());
        holder.raw_feed_tv.setText("N/A");
    }

    @Override
    public int getItemCount() {
        return bleFeedDataArr.size();
    }

    public void appendNewFeed(BLEFeedData data){
        bleFeedDataArr.add(0, data);
        this.notifyItemInserted(0);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timestamp_tv;
        TextView pulse_tv;
        TextView temp_tv;
        TextView raw_feed_tv;

        MyViewHolder(View itemView) {
            super(itemView);
            timestamp_tv = itemView.findViewById(R.id.timestamp_tv);
            pulse_tv = itemView.findViewById(R.id.pulse_tv);
            temp_tv = itemView.findViewById(R.id.temp_tv);
            raw_feed_tv = itemView.findViewById(R.id.raw_feed_tv);
        }
    }
}
