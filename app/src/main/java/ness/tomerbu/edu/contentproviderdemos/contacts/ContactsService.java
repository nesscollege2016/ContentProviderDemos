package ness.tomerbu.edu.contentproviderdemos.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

import ness.tomerbu.edu.contentproviderdemos.models.Contact;

public class ContactsService {


    //Method that requires Permission
    public static ArrayList<Contact> readContacts(Context context){
        ArrayList<Contact> contacts = new ArrayList<>();
        //get the Uri of the provider
        //getContentResovler().query(uri)///SQLIte

        Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(contentUri,
                null, null, null, null);

        if (cursor.moveToFirst()){
            do{
                //Read the data from the cursor
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));


                String name = cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));

                Contact c = new Contact(id ,name);
                contacts.add(c);

            }while (cursor.moveToNext());
        }
         return contacts;
    }
}
