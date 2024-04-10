package br.edu.utfpr.birdwatchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.entity.ObservationEntity;
import java.util.List;
import java.util.Objects;

public class ObservationAdapter extends BaseAdapter {

  private final Context context;
  private final List<ObservationEntity> observations;

  public ObservationAdapter(Context context, List<ObservationEntity> observations) {
    this.context = context;
    this.observations = observations;
  }

  @Override
  public int getCount() {
    return observations.size();
  }

  @Override
  public Object getItem(int position) {
    return observations.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    ViewHolder viewHolder;

    if (Objects.isNull(view)) {
      view = LayoutInflater.from(context).inflate(R.layout.item_observation, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.textViewId = view.findViewById(R.id.textViewId);
      viewHolder.textViewDateTime = view.findViewById(R.id.textViewDateTime);
      viewHolder.textViewLocation = view.findViewById(R.id.textViewLocation);
      viewHolder.textViewSpecies = view.findViewById(R.id.textViewSpecies);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    ObservationEntity observation = observations.get(position);
    viewHolder.textViewId.setText(String.valueOf(observation.getId()));
    viewHolder.textViewDateTime.setText(String.valueOf(observation.getDateTime()));
    viewHolder.textViewLocation.setText(observation.getLocation());
    viewHolder.textViewSpecies.setText(observation.getSpecies());
    return view;
  }

  private static class ViewHolder {

    private TextView textViewId;
    private TextView textViewDateTime;
    private TextView textViewLocation;
    private TextView textViewSpecies;
  }
}