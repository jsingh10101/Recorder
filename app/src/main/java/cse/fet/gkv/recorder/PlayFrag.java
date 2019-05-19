package cse.fet.gkv.recorder;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.rtp.AudioStream;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayFrag extends Fragment {
    public PlayFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_files, container, false);
        RecyclerView r = (RecyclerView) v.findViewById(R.id.id_recyl);
        r.setLayoutManager(new LinearLayoutManager(getContext()));
        r.addItemDecoration(new LineItemDecoration(getContext(), LinearLayout.VERTICAL));
        r.setHasFixedSize(true);
        List<String> files = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Recorder");
        File dir[] = file.listFiles();
        if (dir != null) {
            for (File file1 : dir) {
                if (!Adap.names.contains(file1.getName()))
                    Adap.names.add(file1.getName());
            }
        }
        r.setAdapter(new Adap());
        return r;
    }


}
