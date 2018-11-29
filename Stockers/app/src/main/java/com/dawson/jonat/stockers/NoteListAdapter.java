package com.dawson.jonat.stockers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import com.dawson.jonat.stockers.Entity.Note;

/**
 * @author Nicholas Apanian
 * Most of the code in this class is based off of:
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#10
 */
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Note> notes; // Cached copy of notes

    public NoteListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (notes != null) {
            Note current = notes.get(position);
            holder.noteItemView.setText(current.getNote());
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Word");
        }
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0;
    }
}