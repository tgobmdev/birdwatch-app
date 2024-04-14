package br.edu.utfpr.birdwatchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.edu.utfpr.birdwatchapp.R;
import br.edu.utfpr.birdwatchapp.response.ObservationResponse;
import java.util.List;
import java.util.Objects;

public class ObservationListAdapter extends BaseAdapter {

  private final Context context;
  private final List<ObservationResponse> observations;

  public ObservationListAdapter(Context context, List<ObservationResponse> observations) {
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
    return observations.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    ObservationListViewHolder observationListViewHolder;
    if (Objects.isNull(view)) {
      view = LayoutInflater.from(context).inflate(R.layout.item_observation, parent, false);
      observationListViewHolder = new ObservationListViewHolder();
      observationListViewHolder.observationId = view.findViewById(R.id.textViewId);
      observationListViewHolder.date = view.findViewById(R.id.textViewDateTime);
      observationListViewHolder.location = view.findViewById(R.id.textViewLocation);
      observationListViewHolder.specie = view.findViewById(R.id.textViewSpecies);
      view.setTag(observationListViewHolder);
    } else {
      observationListViewHolder = (ObservationListViewHolder) view.getTag();
    }

    ObservationResponse response = observations.get(position);
    observationListViewHolder.observationId.setText(String.valueOf(response.getId()));
    observationListViewHolder.date.setText(String.valueOf(response.getDateTime()));
    observationListViewHolder.location.setText(response.getLocation());
    observationListViewHolder.specie.setText(response.getSpecie());
    return view;
  }

  public void updateObservations(List<ObservationResponse> newObservations) {
    this.observations.clear();
    this.observations.addAll(newObservations);
    notifyDataSetChanged();
  }

  private static class ObservationListViewHolder {

    private TextView observationId;
    private TextView date;
    private TextView location;
    private TextView specie;
  }
}