package cse.fet.gkv.recorder;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;

public class RecFrag extends Fragment {
    public RecFrag() {
    }

    MediaRecorder mr;
    ImageButton rec, stop;
    String fn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_rec, container, false);
        rec = v.findViewById(R.id.id_rec);
        stop = v.findViewById(R.id.id_stop);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mr = new MediaRecorder();
                mr.setAudioSource(MediaRecorder.AudioSource.MIC);
                mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                File fl = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Recorder");
                if (!fl.exists())
                    fl.mkdir();
                fn = "Rec" + ((System.currentTimeMillis() / 1000)) + ".mp3";
                mr.setOutputFile(fl.getAbsolutePath() + File.separator + fn);
                mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mr.prepare();
                    mr.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rec.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mr != null) {
                    mr.stop();
                    mr.release();
                    mr = null;
                    Adap.names.add(fn);
                }
                stop.setVisibility(View.GONE);
                rec.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mr != null) {
            mr.stop();
            mr.release();
            mr = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        stop.setVisibility(View.GONE);
        rec.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
