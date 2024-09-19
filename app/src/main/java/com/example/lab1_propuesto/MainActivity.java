package com.example.lab1_propuesto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_propuesto.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FILE_NAME = "user_data.txt";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText edtName = binding.edtName;
        EditText edtSurname = binding.edtSurname;
        EditText edtPhone = binding.edtPhone;
        EditText edtEmail = binding.edtEmail;
        EditText edtBloodGroup = binding.edtBloodGroup;
        Button btnRegister = binding.btnRegister;
        Button btnRead = binding.btnRead;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String surname = edtSurname.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtEmail.getText().toString();
                String bloodGroup = edtBloodGroup.getText().toString();

                if (name.isEmpty() || surname.isEmpty() || phone.isEmpty() || email.isEmpty() || bloodGroup.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Complete todos los campos");
                } else {
                    saveToFile(name, surname, phone, email, bloodGroup);
                    Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Datos guardados: Nombre - " + name + ", Apellido - " + surname);
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = loadFromFile();
                if (data == null) {
                    Toast.makeText(getApplicationContext(), "Error al leer los datos", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error al leer los datos");
                } else {
                    Toast.makeText(getApplicationContext(), "Datos cargados", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Datos cargados: " + data);
                }
            }
        });
    }

    private void saveToFile(String name, String surname, String phone, String email, String bloodGroup) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE)))) {
            writer.write("Name: " + name + "\n");
            writer.write("Surname: " + surname + "\n");
            writer.write("Phone: " + phone + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Blood Group: " + bloodGroup + "\n");
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar los datos", e);
        }
    }

    private String loadFromFile() {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al leer los datos", e);
            return null;
        }
        return data.toString();
    }
}
