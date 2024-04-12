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

public class ObservationListViewAdapter extends BaseAdapter {

  private final Context context;
  private final List<ObservationEntity> observations;

  public ObservationListViewAdapter(Context context, List<ObservationEntity> observations) {
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
    ObservationListViewHolder observationListViewHolder;

    if (Objects.isNull(view)) {
      view = LayoutInflater.from(context).inflate(R.layout.item_observation, parent, false);
      observationListViewHolder = new ObservationListViewHolder();
      observationListViewHolder.textViewId = view.findViewById(R.id.textViewId);
      observationListViewHolder.textViewDateTime = view.findViewById(R.id.textViewDateTime);
      observationListViewHolder.textViewLocation = view.findViewById(R.id.textViewLocation);
      observationListViewHolder.textViewSpecies = view.findViewById(R.id.textViewSpecies);
      view.setTag(observationListViewHolder);
    } else {
      observationListViewHolder = (ObservationListViewHolder) view.getTag();
    }

    ObservationEntity observation = observations.get(position);
    observationListViewHolder.textViewId.setText(String.valueOf(observation.getId()));
    observationListViewHolder.textViewDateTime.setText(String.valueOf(observation.getDateTime()));
    observationListViewHolder.textViewLocation.setText(observation.getLocation());
    observationListViewHolder.textViewSpecies.setText(observation.getSpecies());
    return view;
  }

  public void updateObservations(List<ObservationEntity> newObservations) {
    this.observations.clear();
    this.observations.addAll(newObservations);
    notifyDataSetChanged();
  }

  private static class ObservationListViewHolder {

    private TextView textViewId;
    private TextView textViewDateTime;
    private TextView textViewLocation;
    private TextView textViewSpecies;
  }
}