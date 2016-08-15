package ness.tomerbu.edu.contentproviderdemos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ness.tomerbu.edu.contentproviderdemos.contacts.ContactsService;
import ness.tomerbu.edu.contentproviderdemos.models.Contact;

/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        View recyclerView = findViewById(R.id.contact_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.contact_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(
                new ContactsAdapter(ContactsService.readContacts(this))
        );
    }

    public class ContactsAdapter
            extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        private final List<Contact> contacts;

        public ContactsAdapter(List<Contact> items) {
            contacts = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.contact = contacts.get(position);

            holder.tvID.setText(holder.contact.getId());
            holder.tvName.setText(holder.contact.getName());

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        ContactDetailFragment fragment =
                                ContactDetailFragment.newInstance(holder.contact);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contact_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ContactDetailActivity.class);
                        intent.putExtra("Contact", holder.contact);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View layout;
            public final TextView tvID;
            public final TextView tvName;
            public Contact contact;

            public ViewHolder(View view) {
                super(view);
                layout = view;
                tvID = (TextView) view.findViewById(R.id.tvID);
                tvName = (TextView) view.findViewById(R.id.tvName);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvName.getText() + "'";
            }
        }
    }
}
