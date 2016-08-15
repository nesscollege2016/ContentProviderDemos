package ness.tomerbu.edu.contentproviderdemos;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ness.tomerbu.edu.contentproviderdemos.models.Contact;


public class ContactDetailFragment extends Fragment {

    private static final String ARG_CONTACT = "Contact";
    private Contact contact;
    private TextView tvDetails;

    public static ContactDetailFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONTACT, contact);
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        contact = b.getParcelable(ARG_CONTACT);

        Activity activity = getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null && contact!=null) {
              appBarLayout.setTitle(contact.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_detail,
                container, false);

        tvDetails = (TextView) v.findViewById(R.id.contact_detail);

        if (contact != null) {
            tvDetails.setText(contact.getName());
        }

        return v;
    }
}
