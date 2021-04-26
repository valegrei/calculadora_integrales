package com.example.calculaintegrales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.calculaintegrales.databinding.ActivityMainBinding;
import com.itis.libs.parserng.android.expressParser.MathExpression;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCalcular.setOnClickListener(view -> {
            limpiar();
            String fxInput = binding.funcion.getText().toString();
            String a_s = binding.valA.getText().toString();
            String b_s = binding.valB.getText().toString();
            String n_s = binding.valN.getText().toString();

            try {
                double calc = integra_rect(fxInput, a_s, b_s, n_s);
                String resultado = String.valueOf(calc);
                binding.txtResultado.setText(resultado);
                Log.d(TAG,"result: " + resultado);
            }catch (Exception e){
                mostrarError(e.getMessage());
            }
        });
    }

    private void mostrarError(String msg){
        binding.txtError.setText(msg);
    }

    private void limpiar(){
        binding.txtError.setText(null);
        binding.txtResultado.setText(null);
    }

    private double integra_rect(String func, String ini, String fin, String iter){
        MathExpression expr = new MathExpression(getString(R.string.evalua,func));
        double integral = 0.0;
        double ini_d = Double.parseDouble(ini);
        double fin_d = Double.parseDouble(fin);
        double iter_d = Double.parseDouble(iter);//
        double step = (fin_d-ini_d)/iter_d;
        double e = 0.0;
        for (int i = 0; i<iter_d; i++){
            e = ini_d + step * (i+0.5);
            expr.setValue("x", String.valueOf(e));
            integral += step * Double.parseDouble(expr.solve());
        }
        return integral;
    }
}