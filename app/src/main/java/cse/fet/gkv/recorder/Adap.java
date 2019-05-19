package cse.fet.gkv.recorder;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static List<String> names = new ArrayList<>();
    private String prev;
    MediaPlayer mp;

    public Adap() {
        prev = " ";
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        vh = new ViewHolder(v);
        return vh;
    }

    ViewHolder vhp;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder vh = (ViewHolder) viewHolder;
            final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Recorder");
            final String filen = names.get(i);

            vh.name.setText(filen);


            vh.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vhp = vh;
                    try {
                        if (!prev.equals(filen)) {
                            if (mp != null && mp.isPlaying()) {
                                bhide();
                                mp.stop();
                                mp.release();
                                mp = null;
                            }
                            prev = filen;
                            mp = new MediaPlayer();
                            mp.setDataSource(file.getAbsolutePath() + File.separator + filen);
                            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mp.prepare();

                        }
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                bhide();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vh.play.setVisibility(View.GONE);
                    vh.pause.setVisibility(View.VISIBLE);
                }

            });

            vh.pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mp.isPlaying())
                        mp.pause();
                    vh.pause.setVisibility(View.GONE);
                    vh.play.setVisibility(View.VISIBLE);
                }
            });

        }

    }

    private void bhide() {
        vhp.pause.setVisibility(View.GONE);
        vhp.play.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageButton play, pause;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            play = itemView.findViewById(R.id.id_play);
            pause = itemView.findViewById(R.id.id_pause);
        }
    }
}
