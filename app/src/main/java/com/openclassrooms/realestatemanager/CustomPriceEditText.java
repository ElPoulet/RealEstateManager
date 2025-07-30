package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CustomPriceEditText extends TextInputLayout {

    // Référence à l'EditText interne
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;

    // Constructeur principal pour l'instanciation depuis le code XML
    public CustomPriceEditText(Context context) {
        super(context);
        init(context, null);
    }

    // Constructeur pour l'instanciation depuis le code XML avec attributs
    public CustomPriceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    // Constructeur pour l'instanciation depuis le code XML avec attributs et style
    public CustomPriceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Gonfle le layout XML et l'attache à cette CustomView
        // 'this' fait référence à l'instance de CustomPriceEditText (qui est un TextInputLayout)
        LayoutInflater.from(context).inflate(R.layout.custom_text_input_view, this, true);

        // Récupère la référence à l'TextInputEditText à l'intérieur du layout gonflé
        textInputEditText = findViewById(R.id.text_edit);

        textInputLayout = findViewById(R.id.text_layout);

        // Vous pouvez ajouter ici une logique de personnalisation supplémentaire
        // par exemple, définir un type d'entrée, des listeners, etc.
        if (textInputEditText != null) {
            textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    /**
     * Permet d'obtenir le texte actuellement saisi dans le champ.
     * @return Le texte du champ sous forme de String.
     */
    public String getText() {
        return textInputEditText != null ? textInputEditText.getText().toString() : "";
    }

    public void setText(String address){
        textInputEditText.setText(address);
    }

    /**
     * Allows changing the hint text of the internal EditText.
     * @param hint The new hint text.
     */
    @Override
    public void setHint(CharSequence hint) {

        if (textInputEditText != null) {
            //textInputEditText.setHint(hint);
            textInputLayout.setHint(hint);
        }

        super.setHint(hint);
    }

    public void setTextInputEditText(int inputEditText){
        textInputEditText.setInputType(inputEditText);
    }

    public TextInputEditText getEditText() {
        return textInputEditText;
    }

}
