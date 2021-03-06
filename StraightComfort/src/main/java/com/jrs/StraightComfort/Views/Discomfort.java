package com.jrs.StraightComfort.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jrs.StraightComfort.R;
import com.jrs.StraightComfort.Utilities.Bodypart;
import com.jrs.StraightComfort.Utilities.Content;
import com.jrs.StraightComfort.Utilities.DiscomfortInfo;
import com.jrs.StraightComfort.Utilities.FilterActivity;
import com.jrs.StraightComfort.Utilities.SolutionInfo;

import java.util.ArrayList;

/**
 * Created by Steve Jung (jsh0211) on 2014-05-21.
 */
public class Discomfort extends FilterActivity {

    CustomAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discomfort);

     displayListView();
     checkButtonClick();

    }
    private void displayListView() {

        //create an ArrayAdaptar from the String Array
        dataAdapter = new CustomAdapter(this,
                R.layout.bodypart_check, filterscData().getDiscomfortcontents());
        ListView listView = (ListView) findViewById(R.id.lvAnalyze);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.cbBPname);
                cb.performClick();

            }
        });
    }


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private class CustomAdapter extends ArrayAdapter<DiscomfortInfo> {

        private ArrayList<DiscomfortInfo> bodypartList;

        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<DiscomfortInfo> bodypartList) {
            super(context, textViewResourceId, bodypartList);
            this.bodypartList = new ArrayList<DiscomfortInfo>();
            this.bodypartList.addAll(bodypartList);
        }

        private class ViewHolder {
            TextView BPname;
            CheckBox BPcheckBox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.bodypart_check, null);

                holder = new ViewHolder();
                holder.BPname = (TextView) convertView.findViewById(R.id.tvBPname);
                holder.BPcheckBox = (CheckBox) convertView.findViewById(R.id.cbBPname);
                convertView.setTag(holder);


                holder.BPcheckBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Bodypart bodypart = (Bodypart) cb.getTag();

                        bodypart.setSelected(cb.isChecked());
                    }

                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Bodypart bodyPart = bodypartList.get(position).getBodypart();

            holder.BPname.setText(bodyPart.getName());

            holder.BPcheckBox.setChecked(bodyPart.isSelected());

            holder.BPcheckBox.setTag(bodyPart);

            return convertView;

        }
   }

    private void checkButtonClick() {
        TextView myButton = (TextView) findViewById(R.id.tvAnalyze);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

        boolean turn = false;

                ArrayList<DiscomfortInfo> bodypartList = dataAdapter.bodypartList;
                for(DiscomfortInfo discomfort : bodypartList){
                    Bodypart bodypart = discomfort.getBodypart();
                    if (bodypart.isSelected())
                    {
                        turn = true;
                        break;
                    }
                }
                if (turn) {
                    filterscData().setDiscomfortcontents(dataAdapter.bodypartList);
                    Intent mIntent = new Intent(getApplicationContext(), Solutions.class);
                    startActivity(mIntent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please select a discomfort first!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
