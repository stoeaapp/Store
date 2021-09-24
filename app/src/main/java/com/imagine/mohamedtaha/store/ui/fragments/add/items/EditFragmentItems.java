package com.imagine.mohamedtaha.store.ui.fragments.add.items;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.imagine.mohamedtaha.store.R;
import com.imagine.mohamedtaha.store.databinding.FragmentEditCategoryBinding;
import com.imagine.mohamedtaha.store.room.data.Categories;
import com.imagine.mohamedtaha.store.util.DialogUtils;

import static com.imagine.mohamedtaha.store.Constant.ADD_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.DELETE_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.DIALOG_ITEMS;
import static com.imagine.mohamedtaha.store.Constant.ID_ITEM;
import static com.imagine.mohamedtaha.store.Constant.ITEMS;
import static com.imagine.mohamedtaha.store.Constant.NAME_ITEM;
import static com.imagine.mohamedtaha.store.Constant.NATURAL_ITEM;
import static com.imagine.mohamedtaha.store.Constant.NOTES;
import static com.imagine.mohamedtaha.store.Constant.UPDATE_ITEMS;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getDate;
import static com.imagine.mohamedtaha.store.data.TaskDbHelper.getTime;

public class EditFragmentItems extends BottomSheetDialogFragment {
    private FragmentEditCategoryBinding binding;
    private static final String EXTRA_ID = "id";
    long id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCategoryBinding.inflate(getLayoutInflater());
        if (getArguments() != null && getArguments().getLong(EXTRA_ID) != 0) {
            id = getArguments().getLong(EXTRA_ID);
            binding.BTAddCategory.setText(getString(R.string.action_edit));
            binding.BTDeleteCategory.setVisibility(View.VISIBLE);
            binding.TVTitleCategory.setText(getString(R.string.edit_category_titile));
            binding.ETNameItem.setText(getArguments().getString(NAME_ITEM));
            binding.ETNaturalCategory.setText(getArguments().getString(NATURAL_ITEM));
            binding.EtNotes.setText(getArguments().getString(NOTES));
        }
        binding.BTAddCategory.setOnClickListener(v -> saveCategory());
        binding.BTDeleteCategory.setOnClickListener(v -> DialogUtils.showMessageWithYesNoMaterialDesign(requireContext(),
                getString(R.string.title_delete_Item), getString(R.string.delete_dialog_msg), (dialog, which) -> {
                    if (id != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putLong(DELETE_ITEMS, requireArguments().getLong(ID_ITEM));
                        bundle.putString(ITEMS, DELETE_ITEMS);
                        getParentFragmentManager().setFragmentResult(DIALOG_ITEMS, bundle);
                        dismiss();
                    }
                }));
        return binding.getRoot();
    }

    private void saveCategory() {
        //Read from input field,use trim to eliminate leading or trailing wgite spase
        String nameCategoryString = binding.ETNameItem.getText().toString().trim();
        String naturalCategoryString = binding.ETNaturalCategory.getText().toString().trim();
        String notesString = binding.EtNotes.getText().toString().trim();
        //  boolean isExist = dbHelper.isExistCategoryName(nameCategoryString);

        //  String notesString = ETNotes.getText().toString().trim();
        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (getArguments() == null && TextUtils.isEmpty(nameCategoryString)) {
            binding.ETNameItem.setError(getString(R.string.error_empty_edit_text));
            binding.ETNameItem.requestFocus();
            return;
        }

      /*  if (isExist ==true){
            ETCategoryName.requestFocus();
            ETCategoryName.setError(getString(R.string.error_exist_category));
            return;
        }*/

//Dtermine if this is a new or existing Category bychecking if mCurrentCategoryURi is null or not
        if (getArguments() == null) {
            Categories addItem = new Categories(nameCategoryString, naturalCategoryString, notesString);
            addItem.setCreatedAt(getDate());
            addItem.setTime(getTime());
            Bundle bundle = new Bundle();
            bundle.putSerializable(ADD_ITEMS, addItem);
            bundle.putString(ITEMS, ADD_ITEMS);
            getParentFragmentManager().setFragmentResult(DIALOG_ITEMS, bundle);
            dismiss();

//            //This is a new Category , so insert a new Category into the provider,
//            //returning the content URI for the new category
//            Uri newUri = getContext().getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI,values);
//            //Show a Toast message depending on whether or not the inserting was successful.
//            if (newUri == null){
//                //If the new content URI is null,then there was an error with inserting
//                Toast.makeText(getContext(),getString(R.string.editor_insert_category_failed),Toast.LENGTH_LONG).show();
//            }else {
//                //Otherwise ,the inserting was successful and we can display a toast
//                Toast.makeText(getContext(), getString(R.string.editor_insert_category_successful), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
        } else {
            Categories updateItem = new Categories(nameCategoryString, naturalCategoryString, notesString);
            updateItem.setCreatedAt(getDate());
            Bundle bundle = new Bundle();
            bundle.putSerializable(UPDATE_ITEMS, updateItem);
            bundle.putString(ITEMS, UPDATE_ITEMS);
            getParentFragmentManager().setFragmentResult(DIALOG_ITEMS, bundle);
            dismiss();
            //Otherwise this is an Existing Category , soupdate the category with content URI: mCurrentCategoryUri
            //and pass in the new ContentValues. pasin null for the selection and selection args
            //becausa mCurrentCategoryUri will already identify the correct row in the database that we want to modify.

//            //Show a toast message depending on whether or not the update was successful.
//            if (rowsAffected == 0){
//                //If no rows were affected,then there was an error with the update
//                //   Toast.makeText(this, getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), getString(R.string.editor_update_category_failed), Toast.LENGTH_SHORT).show();
//
//            }else {
//                //Otherwise, the update was successful and we can display a toast.
//                Toast.makeText(getContext(), getString(R.string.editor_update_category_successful), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }


        }


    }
}