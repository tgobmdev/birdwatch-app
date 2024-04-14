package br.edu.utfpr.birdwatchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.response.BirdResponse;
import java.util.List;
import java.util.Objects;

public class BirdListAdapter extends BaseAdapter {

  private final Context context;
  private final List<BirdResponse> birds;

  public BirdListAdapter(Context context, List<BirdResponse> birds) {
    this.context = context;
    this.birds = birds;
  }

  @Override
  public int getCount() {
    return birds.size();
  }

  @Override
  public Object getItem(int position) {
    return birds.get(position);
  }

  @Override
  public long getItemId(int position) {
    return birds.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    BirdListViewHolder birdListViewHolder;
    if (Objects.isNull(view)) {
      view = LayoutInflater.from(context).inflate(R.layout.item_bird, parent, false);
      birdListViewHolder = new BirdListViewHolder();
      birdListViewHolder.birdId = view.findViewById(R.id.item_bird_id);
      birdListViewHolder.specie = view.findViewById(R.id.item_specie);
      birdListViewHolder.color = view.findViewById(R.id.item_color);
      birdListViewHolder.commonName = view.findViewById(R.id.item_common_name);
      view.setTag(birdListViewHolder);
    } else {
      birdListViewHolder = (BirdListViewHolder) view.getTag();
    }

    BirdResponse response = birds.get(position);
    birdListViewHolder.birdId.setText(String.valueOf(response.getId()));
    birdListViewHolder.specie.setText(response.getSpecie());
    birdListViewHolder.color.setText(response.getColor());
    birdListViewHolder.commonName.setText(response.getCommonName());
    return view;
  }

  public void updateBirds(List<BirdResponse> newBirds) {
    this.birds.clear();
    this.birds.addAll(newBirds);
    notifyDataSetChanged();
  }

  private static class BirdListViewHolder {

    private TextView birdId;
    private TextView specie;
    private TextView color;
    private TextView commonName;
  }
}