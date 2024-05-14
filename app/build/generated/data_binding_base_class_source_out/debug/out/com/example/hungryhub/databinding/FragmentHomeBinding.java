// Generated by view binder compiler. Do not edit!
package com.example.hungryhub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.hungryhub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView PopularRecyclerView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageSlider imageSlider;

  @NonNull
  public final TextView textView18;

  @NonNull
  public final TextView viewAllMenu;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView PopularRecyclerView, @NonNull CardView cardView,
      @NonNull ImageSlider imageSlider, @NonNull TextView textView18,
      @NonNull TextView viewAllMenu) {
    this.rootView = rootView;
    this.PopularRecyclerView = PopularRecyclerView;
    this.cardView = cardView;
    this.imageSlider = imageSlider;
    this.textView18 = textView18;
    this.viewAllMenu = viewAllMenu;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.PopularRecyclerView;
      RecyclerView PopularRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (PopularRecyclerView == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.image_slider;
      ImageSlider imageSlider = ViewBindings.findChildViewById(rootView, id);
      if (imageSlider == null) {
        break missingId;
      }

      id = R.id.textView18;
      TextView textView18 = ViewBindings.findChildViewById(rootView, id);
      if (textView18 == null) {
        break missingId;
      }

      id = R.id.viewAllMenu;
      TextView viewAllMenu = ViewBindings.findChildViewById(rootView, id);
      if (viewAllMenu == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, PopularRecyclerView, cardView,
          imageSlider, textView18, viewAllMenu);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
