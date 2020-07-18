package reciptizer.Local;

import android.animation.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.reciptizer.R;
import reciptizer.Activity_Main;
import reciptizer.Common.Helpers.ConstantHelper;
import reciptizer.Common.Recipe.RecipeFilter;
import reciptizer.Common.Recipe.Table1;

import static reciptizer.Common.Helpers.AnimHelper.*;

public class Activity_Filter extends Activity {

    protected String currentValue_RECIPE, currentValue_CATEGORY, currentValue_KITCHEN, currentValue_PREFERENCES;
    protected RecyclerView recyclerView;
    protected RecipeFilter recipeFilter;

    private String[] values_TABLE1_COLUMN_CATEGORY;
    private String[] values_TABLE1_COLUMN_KITCHEN;
    private String[] values_TABLE1_COLUMN_PREFERENCES;
    private Spinner spinner_TABLE1_COLUMN_CATEGORY, spinner_TABLE1_COLUMN_KITCHEN, spinner_TABLE1_COLUMN_PREFERENCES;
    private ImageView imageView_TABLE1_COLUMN_CATEGORY,imageView_TABLE1_COLUMN_KITCHEN,imageView_TABLE1_COLUMN_PREFERENCES;
    private boolean isSpinnersWereClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: onCreate");

        getResource ();

        setStartValues ();

        initLayout();

        setContentIntoRecyclerView();

        setStatusBarColor ();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: onResume");

        if (ConstantHelper.isNeedToRefreshFilterCreateRecyclerView)
        {
            setContentIntoRecyclerView();
            ConstantHelper.isNeedToRefreshFilterCreateRecyclerView = false;
        }
    }

    private void setStatusBarColor () {
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: setStatusBarColor");

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,getColorForStatusBar()));
        window.setNavigationBarColor(ContextCompat.getColor(this,getColorForNavigationBar()));
    }

    protected int getColorForStatusBar (){
        return R.color.FilterRecipeSetStatusBarColor;
    }

    protected int getColorForNavigationBar (){
        return R.color.RecipeSetStatusBarColor;
    }

    private void getResource () {
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: getResource");

        values_TABLE1_COLUMN_CATEGORY = getResources().getStringArray(R.array.TABLE1_COLUMN_CATEGORY);
        values_TABLE1_COLUMN_KITCHEN = getResources().getStringArray(R.array.TABLE1_COLUMN_KITCHEN);
        values_TABLE1_COLUMN_PREFERENCES = getResources().getStringArray(R.array.TABLE1_COLUMN_PREFERENCES);
    }

    private void setStartValues () {
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: setStartValues");

        currentValue_RECIPE = "";
        currentValue_CATEGORY = values_TABLE1_COLUMN_CATEGORY[0];
        currentValue_KITCHEN = values_TABLE1_COLUMN_KITCHEN[0];
        currentValue_PREFERENCES = values_TABLE1_COLUMN_PREFERENCES[0];
    }

    private void initLayout () {
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: initLayout");

        LinearLayout linearLayoutTitle = findViewById(R.id.Filter_linearLayout_Title);
        createLinearLayoutTitle(linearLayoutTitle);

        TextView textViewTitle = findViewById(R.id.Filter_textView_Title);
        createTextViewTitle(textViewTitle);

        EditText editTextRecipeName = findViewById(R.id.Filter_editText_recipeName);
        createEditTextRecipeName(editTextRecipeName);

        Button buttonNewRecipe = findViewById(R.id.Filter_button_newRecipe);
        createButtonNewRecipe(buttonNewRecipe);

        imageView_TABLE1_COLUMN_CATEGORY = findViewById(R.id.Filter_imageView_category);
        createImageViewCategory(imageView_TABLE1_COLUMN_CATEGORY);

        spinner_TABLE1_COLUMN_CATEGORY = findViewById(R.id.Filter_spinner_category);
        createSpinnerCategory(spinner_TABLE1_COLUMN_CATEGORY);

        imageView_TABLE1_COLUMN_KITCHEN = findViewById(R.id.Filter_imageView_kitchen);
        createImageViewKitchen(imageView_TABLE1_COLUMN_KITCHEN);

        spinner_TABLE1_COLUMN_KITCHEN = findViewById(R.id.Filter_spinner_kitchen);
        createSpinnerKitchen(spinner_TABLE1_COLUMN_KITCHEN);

        imageView_TABLE1_COLUMN_PREFERENCES = findViewById(R.id.Filter_imageView_preferences);
        createImageViewPreferences(imageView_TABLE1_COLUMN_PREFERENCES);

        spinner_TABLE1_COLUMN_PREFERENCES = findViewById(R.id.Filter_spinner_preferences);
        createSpinnerPreferences (spinner_TABLE1_COLUMN_PREFERENCES);

        recyclerView = findViewById(R.id.Filter_recyclerView);
        createRecyclerView(recyclerView);
    }

    protected void createTextViewTitle (TextView textViewTitle) {}

    protected void createEditTextRecipeName (final EditText editTextRecipeName) {
        editTextRecipeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((editTextRecipeName.getText()).toString().equals("Название рецепта"))
                    editTextRecipeName.setText("");
            }
        });
        editTextRecipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                currentValue_RECIPE = editTextRecipeName.getText().toString();
                setContentIntoRecyclerView();
            }
        });
    }

    protected void createButtonNewRecipe (final Button buttonNewRecipe) {
        buttonNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Intent intent = new Intent(Activity_Filter.this, Activity_New_Edit_Recipe.class);
                        startActivity(intent);
                    }
                };
                AnimMenuClick(buttonNewRecipe,adapter);
            }
        });
    }

    protected void createLinearLayoutTitle (LinearLayout linearLayoutTitle) {}

    protected void createImageViewCategory (final ImageView imageView_TABLE1_COLUMN_CATEGORY){
        imageView_TABLE1_COLUMN_CATEGORY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_TABLE1_COLUMN_CATEGORY.performClick ();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_CATEGORY);
            }
        });

    }

    protected void createSpinnerCategory (Spinner spinner_TABLE1_COLUMN_CATEGORY){
        ArrayAdapter<String> adapterSpinnerCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values_TABLE1_COLUMN_CATEGORY);
        adapterSpinnerCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_TABLE1_COLUMN_CATEGORY.setAdapter(adapterSpinnerCategory);

        spinner_TABLE1_COLUMN_CATEGORY.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnersWereClicked = true;
                v.performClick();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_CATEGORY);
                return false;
            }
        });

        spinner_TABLE1_COLUMN_CATEGORY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isSpinnersWereClicked) {
                    currentValue_CATEGORY = values_TABLE1_COLUMN_CATEGORY[position];
                    setContentIntoRecyclerView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void createImageViewKitchen (final ImageView imageView_TABLE1_COLUMN_KITCHEN) {
        imageView_TABLE1_COLUMN_KITCHEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_TABLE1_COLUMN_KITCHEN.performClick ();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_KITCHEN);
            }
        });
    }

    protected void createSpinnerKitchen (Spinner spinner_TABLE1_COLUMN_KITCHEN) {
        ArrayAdapter<String> adapterSpinnerKitchen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values_TABLE1_COLUMN_KITCHEN);
        adapterSpinnerKitchen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_TABLE1_COLUMN_KITCHEN.setAdapter(adapterSpinnerKitchen);

        spinner_TABLE1_COLUMN_KITCHEN.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnersWereClicked = true;
                v.performClick();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_KITCHEN);
                return false;
            }
        });

        spinner_TABLE1_COLUMN_KITCHEN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isSpinnersWereClicked) {
                    currentValue_KITCHEN = values_TABLE1_COLUMN_KITCHEN[position];
                    setContentIntoRecyclerView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void createImageViewPreferences (final ImageView imageView_TABLE1_COLUMN_PREFERENCES) {
        imageView_TABLE1_COLUMN_PREFERENCES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_TABLE1_COLUMN_PREFERENCES.performClick ();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_PREFERENCES);
            }
        });
    }

    protected void createSpinnerPreferences (Spinner spinner_TABLE1_COLUMN_PREFERENCES ) {
        ArrayAdapter<String> adapterSpinnerPreferences = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values_TABLE1_COLUMN_PREFERENCES);
        adapterSpinnerPreferences.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_TABLE1_COLUMN_PREFERENCES.setAdapter(adapterSpinnerPreferences);

        spinner_TABLE1_COLUMN_PREFERENCES.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnersWereClicked = true;
                v.performClick();
                AnimSpinnerOn(imageView_TABLE1_COLUMN_PREFERENCES);
                return false;
            }
        });

        spinner_TABLE1_COLUMN_PREFERENCES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isSpinnersWereClicked) {
                    currentValue_PREFERENCES = values_TABLE1_COLUMN_PREFERENCES[position];
                    setContentIntoRecyclerView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void createRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected void setContentIntoRecyclerView() {
        Log.d(Activity_Main.LOG_TAG, this.getClass() + "| method: setContentIntoRecyclerView");

        recipeFilter = DB.getRecipeFilterResult(currentValue_RECIPE, currentValue_CATEGORY, currentValue_KITCHEN, currentValue_PREFERENCES);

        FilterAdapter filterAdapter = new FilterAdapter(recipeFilter);
        recyclerView.setAdapter(filterAdapter);
    }

    protected class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {
        public final RecipeFilter recipeFilter;

        public FilterAdapter(RecipeFilter recipeFilter) {
            this.recipeFilter = recipeFilter;
        }

        @NonNull
        @Override
        public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            return new FilterViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilterViewHolder holder, int position) {
            Table1 item = recipeFilter.table1.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return recipeFilter.table1.size();
        }
    }

    protected class FilterViewHolder extends RecyclerView.ViewHolder {
        protected final TextView textViewRecipeName;
        protected final TextView textViewTime;
        protected final ImageView imageViewImgTitle;
        protected Context context;

        public FilterViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_filter, parent,false));
            textViewRecipeName = itemView.findViewById(R.id.Filter_result_item_textView_recipeName);
            textViewTime = itemView.findViewById(R.id.Filter_result_item_textView_time);
            imageViewImgTitle = itemView.findViewById(R.id.Filter_result_item_imageView_imgTitle);
            context = textViewRecipeName.getContext();
        }

        public void bind (Table1 table1) {
            bindTextViewRecipeName(table1);

            bindTextViewTime(table1);

            bindImageViewImgTitle(table1);

            bindItemView(table1);
        }

        protected void bindTextViewRecipeName(Table1 table1){
            textViewRecipeName.setText("" + table1.TABLE1_COLUMN_RECIPE);
        }

        protected void bindTextViewTime(Table1 table1){
            textViewTime.setText("" + table1.TABLE1_COLUMN_TIME);
        }

        protected void bindImageViewImgTitle(Table1 table1){
            if (table1.TABLE1_COLUMN_IMG_TITLE==null||table1.TABLE1_COLUMN_IMG_TITLE.equals("null")){
                imageViewImgTitle.setImageResource(R.drawable.no_img);
            }
            else {
                imageViewImgTitle.setImageURI(Uri.parse(table1.TABLE1_COLUMN_IMG_TITLE));
            }
        }

        protected void bindItemView (Table1 table1) {
            final int id = table1.TABLE1_COLUMN_ID;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            onClickItemView(id);
                        }
                    };
                    AnimClick (itemView, adapter);
                }
            });
        }

        protected void onClickItemView(int id) {
            Intent intent = new Intent(Activity_Filter.this, Activity_Recipe.class);
            intent.putExtra(DB.TABLE1_COLUMN_ID, String.valueOf(id));
            startActivity(intent);
        }
    }
}