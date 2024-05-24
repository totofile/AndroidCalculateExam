package fr.esgi.faugerascas.lculatricesimple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Déclaration des variables
    private TextView screen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    // Enumération des opérations possibles
    private enum Ops { PLUS, MOINS, FOIS, DIV }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupération de la référence à la TextView
        screen = findViewById(R.id.TV1);
    }

    // Méthode appelée lorsqu'un bouton numérique est cliqué
    public void onNumberClick(View v) {
        String number = ((Button) v).getText().toString();
        screen.append(number);
        if (isOp1) {
            op1 = op1 * 10 + Integer.parseInt(number);
        } else {
            op2 = op2 * 10 + Integer.parseInt(number);
        }
    }

    // Méthode appelée lorsqu'un bouton opérateur est cliqué
    public void onOperatorClick(View v) {
        if (!isOp1) {
            screen.setText("Erreur: Deux opérateurs consécutifs");
            return;
        }
        String operatorText = ((Button) v).getText().toString();
        screen.append(operatorText);
        isOp1 = false;
        switch (operatorText) {
            case "+":
                operator = Ops.PLUS;
                break;
            case "-":
                operator = Ops.MOINS;
                break;
            case "*":
                operator = Ops.FOIS;
                break;
            case "/":
                operator = Ops.DIV;
                break;
        }
    }

    // Méthode appelée lorsqu'on veut calculer le résultat
    public void computeResult(View v) {
        if (operator != null) {
            switch (operator) {
                case PLUS:
                    op1 += op2;
                    break;
                case MOINS:
                    op1 -= op2;
                    break;
                case FOIS:
                    op1 *= op2;
                    break;
                case DIV:
                    if (op2 == 0) {
                        screen.setText("Erreur: Division par zéro");
                        return;
                    }
                    op1 /= op2;
                    break;
            }
            op2 = 0;
            isOp1 = true;
            updateDisplay(op1);
        }
    }

    // Méthode appelée pour réinitialiser l'opération
    public void clear(View v) {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        screen.setText("");
    }

    // Méthode pour mettre à jour l'affichage
    private void updateDisplay(int number) {
        screen.setText(String.valueOf(number));
    }
}