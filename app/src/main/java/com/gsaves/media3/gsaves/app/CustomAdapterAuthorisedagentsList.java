package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.RestrictionEntry;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Deleteagentresponse;
import com.gsaves.media3.gsaves.app.pojo.Updateagentresponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterAuthorisedagentsList extends RecyclerView.Adapter<CustomAdapterAuthorisedagentsList.MyViewHolder> {
    Context context;
    ArrayList<String> agent_name;
    ArrayList<String> agent_mobile;
    ArrayList<String> agent_loc;
    ArrayList<String> agent_email;
    ArrayList<String> agent_addr;
    ArrayList<String> agent_status;
    ArrayList agent_edit;
    ArrayList agent_delete;
    ArrayList<String> agent_id;

    CustomAdapterAuthorisedagentsList()
    {

    }

    public CustomAdapterAuthorisedagentsList(Context context, ArrayList<String> agent_name, ArrayList<String> agent_mobile, ArrayList<String> agent_loc, ArrayList<String> agent_email, ArrayList<String> agent_addr, ArrayList<String> agent_status, ArrayList agent_edit,ArrayList agent_delete,ArrayList<String> agent_id) {
        this.context = context;
        this.agent_name = agent_name;
        this.agent_mobile = agent_mobile;
        this.agent_loc = agent_loc;
        this.agent_email = agent_email;
        this.agent_addr = agent_addr;
        this.agent_status = agent_status;

        this.agent_edit=agent_edit;
        this.agent_delete=agent_delete;
        this.agent_id=agent_id;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authagents_list,parent, false);


        CustomAdapterAuthorisedagentsList.MyViewHolder vh = new CustomAdapterAuthorisedagentsList.MyViewHolder(v); // pass the view to View Holder
        return vh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
         holder.tv_aagents_namee.setText(agent_name.get(i));
         holder.tv_aagents_agencyy.setText(agent_addr.get(i));
         holder.ib_aagents_editt.setImageResource((Integer)agent_edit.get(i));
        holder.ib_aagents_deletee.setImageResource((Integer)agent_delete.get(i));
        final String agentid=agent_id.get(i);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.ib_aagents_editt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.authagents_edit_form);
                final EditText et_agent_name=(EditText)dialog.findViewById(R.id.et_authagent_name_edit);
                final EditText et_agent_email=(EditText)dialog.findViewById(R.id.et_authagent_id_edit);
                final EditText et_agent_ssn=(EditText)dialog.findViewById(R.id.et_authagent_ssn_edit);
                final EditText et_agent_mobile=(EditText)dialog.findViewById(R.id.et_authagent_mobile_edit);
                final EditText et_agent_addr=(EditText)dialog.findViewById(R.id.et_authagent_address_edit);
                et_agent_name.setText(agent_name.get(i));
                et_agent_email.setText(agent_email.get(i));
                et_agent_ssn.setText(agent_loc.get(i));
                et_agent_mobile.setText(agent_mobile.get(i));
                et_agent_addr.setText(agent_addr.get(i));

                Button bt_update_agent=(Button)dialog.findViewById(R.id.bt_authagent_update);
                bt_update_agent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try
                        {
                            (Api.getClient().updateagent(agentid,et_agent_name.getText().toString().trim(),et_agent_mobile.getText().toString().trim(),et_agent_ssn.getText().toString().trim(),et_agent_email.getText().toString().trim(),et_agent_addr.getText().toString().trim())).enqueue(new Callback<Updateagentresponse>() {
                                @Override
                                public void onResponse(Call<Updateagentresponse> call, Response<Updateagentresponse> response) {
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    updateagent(i,agentid,et_agent_name.getText().toString().trim(),et_agent_mobile.getText().toString().trim(),et_agent_ssn.getText().toString().trim(),et_agent_email.getText().toString().trim(),et_agent_addr.getText().toString().trim());

                                }

                                @Override
                                public void onFailure(Call<Updateagentresponse> call, Throwable t) {
                                    Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });

                            dialog.dismiss();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }
                });


                dialog.show();

            }
        });
        holder.ib_aagents_deletee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Confirm Delete..!!!");
                // Icon Of Alert Dialog
                //alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Are you sure,You want to delete");
                alertDialogBuilder.setCancelable(true);


                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            dialog.dismiss();
                            delete(holder.getAdapterPosition(),agentid);
                        }catch(Exception e)
                        {
                            Toast.makeText(alertDialogBuilder.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this,"You clicked over No",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });


    }

    public void delete(int position,String agent_id) { //removes the row
        agent_name.remove(position);
        notifyItemRemoved(position);
        try {
            (Api.getClient().deleteagent(agent_id)).enqueue(new Callback<Deleteagentresponse>() {
                @Override
                public void onResponse(Call<Deleteagentresponse> call, Response<Deleteagentresponse> response) {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Deleteagentresponse> call, Throwable t) {
                    Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        catch(Exception e)
        {
            Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }


    }

    public int getPosition()
    {
       System.out.println(agent_name.size());
        return (agent_name.size()-1);
    }

    public void insertItem(int position,String agent_nm,String agent_mob,String agent_ssn,String agent_mail,String agent_addrs)
    {
        position=getPosition();
        agent_name.add(position+1,agent_nm);
        agent_mobile.add(position+1,agent_mob);
        agent_loc.add(position+1,agent_ssn);
        agent_email.add(position+1,agent_mail);
        agent_addr.add(position+1,agent_addrs);
        notifyDataSetChanged();

    }
    public void updateagent(int position,String agent_id,String agent_nm,String agent_mob,String agent_ssn,String agent_mail,String agent_addrs)
    {
      agent_name.set(position,agent_nm);
        agent_mobile.set(position,agent_mob);
        agent_loc.set(position,agent_ssn);
        agent_email.set(position,agent_mail);
        agent_addr.set(position,agent_addrs);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return agent_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_aagents_namee,tv_aagents_agencyy;
        ImageButton ib_aagents_editt,ib_aagents_deletee;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_aagents_namee=(TextView)itemView.findViewById(R.id.tv_aagents_name);
            tv_aagents_agencyy=(TextView)itemView.findViewById(R.id.tv_aagents_agency);
            ib_aagents_editt=(ImageButton)itemView.findViewById(R.id.ib_aagents_edit);
            ib_aagents_deletee=(ImageButton)itemView.findViewById(R.id.ib_aagents_delete);
        }
    }
}
