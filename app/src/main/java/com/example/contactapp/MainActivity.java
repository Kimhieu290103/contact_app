package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

import com.example.contactapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContacts;
    private ActivityMainBinding binding;
    private ArrayList<Contact> contactList;
    private contactsAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        SearchView searchView = (SearchView) findViewById(R.id.search_view) ;
        FloatingActionButton fab = findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khởi động màn hình thêm liên hệ
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new contactsAdapter(contactList);
        binding.rvContacts.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();


//        // Khi bạn muốn xóa tất cả dữ liệu từ cơ sở dữ liệu
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                appDatabase.contactDao().deleteAll();
//
//                // Cập nhật danh sách liên hệ trên giao diện (nếu cần)
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        contactList.clear();
//                        contactAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Khi người dùng thay đổi văn bản trong thanh tìm kiếm
                // Cập nhật danh sách liên hệ dựa trên kết quả tìm kiếm
                filterContacts(newText);
                return true;
            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDao = appDatabase.contactDao();
                List<Contact> newContacts = contactDao.getAll(); // Lấy danh sách liên hệ từ cơ sở dữ liệu

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear(); // Xóa danh sách cũ
                        contactList.addAll(newContacts); // Cập nhật danh sách mới
                        contactAdapter.notifyDataSetChanged(); // Thông báo cho adapter là dữ liệu đã thay đổi
                    }
                });
            }
        });




    }
    private void filterContacts(String query) {
        ArrayList<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredContacts.add(contact);
            }
        }
        contactAdapter.setContactList(filteredContacts);
    }
}

