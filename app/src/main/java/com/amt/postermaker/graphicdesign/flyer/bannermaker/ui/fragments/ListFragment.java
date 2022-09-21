package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;
import com.msl.demo.view.ResizableStickerView;
import com.msl.textmodule.AutofitTextRel;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.DragListView.DragListListenerAdapter;
import java.util.ArrayList;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter.AdapterItem;

public class ListFragment extends Fragment {
    private boolean[] arrBoolean;
    private ArrayList<View> arrView = new ArrayList<>();
    public Button btn_layControls;
    String checkAll = "Lock_Mixed";
    ImageView img_All;
    RelativeLayout lay_Notext;
    public FrameLayout lay_container;
    RelativeLayout lay_selectAll;
    private DragListView mDragListView;
    public ArrayList<Pair<Long, View>> mItemArray = new ArrayList<>();
    public RelativeLayout txt_stkr_rel;

    private static class MyDragItem extends DragItem {
        MyDragItem(Context context, int i) {
            super(context, i);
        }

        public void onBindDragView(View view, View view2) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            ((ImageView) view2.findViewById(R.id.backimg)).setImageBitmap(createBitmap);
        }
    }

    public void setLayouts(RelativeLayout relativeLayout, FrameLayout frameLayout, Button button) {
        this.txt_stkr_rel = relativeLayout;
        this.lay_container = frameLayout;
        this.btn_layControls = button;
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.list_layout, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.swipe_refresh_layout);
        this.mDragListView = (DragListView) inflate.findViewById(R.id.drag_list_view);
        this.mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        this.mDragListView.setDragListListener(new DragListListenerAdapter() {
            public void onItemDragStarted(int i) {
            }

            public void onItemDragEnded(int i, int i2) {
                if (i != i2) {
                    for (int size = ListFragment.this.mItemArray.size() - 1; size >= 0; size--) {
                        ((View) ((Pair) ListFragment.this.mItemArray.get(size)).second).bringToFront();
                    }
                    ListFragment.this.txt_stkr_rel.requestLayout();
                    ListFragment.this.txt_stkr_rel.postInvalidate();
                }
            }
        });
        ((TextView) inflate.findViewById(R.id.txt_Nolayers)).setTypeface(Constants.getTextTypeface(getActivity()));
        this.lay_Notext = (RelativeLayout) inflate.findViewById(R.id.lay_text);
        this.img_All = (ImageView) inflate.findViewById(R.id.img_selectAll);
        ((TextView) inflate.findViewById(R.id.txt_all)).setTypeface(Constants.getTextTypeface(getActivity()));
        this.lay_selectAll = (RelativeLayout) inflate.findViewById(R.id.lay_selectAll);
        ((RelativeLayout) inflate.findViewById(R.id.lay_frame)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (ListFragment.this.lay_container.getVisibility() == View.VISIBLE) {
                        ListFragment.this.lay_container.animate().translationX((float) (-ListFragment.this.lay_container.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                ListFragment.this.lay_container.setVisibility(View.GONE);
                                ListFragment.this.btn_layControls.setVisibility(View.VISIBLE);
                            }
                        }, 200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.lay_selectAll.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.img_All.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ListFragment.this.txt_stkr_rel.getChildCount() != 0) {
                    if (ListFragment.this.checkAll.equals("Lock_All")) {
                        ListFragment.this.checkAll = "UnLock_All";
                        ListFragment.this.img_All.setImageResource(R.drawable.on_fp);
                    } else {
                        ListFragment.this.checkAll = "Lock_All";
                        ListFragment.this.img_All.setImageResource(R.drawable.off_fp);
                    }
                    ListFragment.this.lock_unLockAll(ListFragment.this.checkAll);
                }
            }
        });
        return inflate;
    }

    public void lock_unLockAll(String str) {
        for (int childCount = this.txt_stkr_rel.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.txt_stkr_rel.getChildAt(childCount);
            if (str.equals("Lock_All")) {
                if (childAt instanceof ResizableStickerView) {
                    ResizableStickerView resizableStickerView = (ResizableStickerView) childAt;
                    resizableStickerView.isMultiTouchEnabled = resizableStickerView.setDefaultTouchListener(false);
                }
                if (childAt instanceof AutofitTextRel) {
                    AutofitTextRel autofitTextRel = (AutofitTextRel) childAt;
                    autofitTextRel.isMultiTouchEnabled = autofitTextRel.setDefaultTouchListener(false);
                }
            } else {
                if (childAt instanceof ResizableStickerView) {
                    ResizableStickerView resizableStickerView2 = (ResizableStickerView) childAt;
                    resizableStickerView2.isMultiTouchEnabled = resizableStickerView2.setDefaultTouchListener(true);
                }
                if (childAt instanceof AutofitTextRel) {
                    AutofitTextRel autofitTextRel2 = (AutofitTextRel) childAt;
                    autofitTextRel2.isMultiTouchEnabled = autofitTextRel2.setDefaultTouchListener(true);
                }
            }
        }
        setupListRecyclerView();
    }

    public void getLayoutChild(boolean z) {
        if (z) {
            this.mItemArray.clear();
        }
        int i = 0;
        if (this.txt_stkr_rel.getChildCount() != 0) {
            if (z) {
                this.lay_Notext.setVisibility(View.GONE);
                this.lay_selectAll.setVisibility(View.VISIBLE);
            }
            this.arrBoolean = new boolean[this.txt_stkr_rel.getChildCount()];
            int childCount = this.txt_stkr_rel.getChildCount();
            int i2 = 0;
            for (int childCount2 = this.txt_stkr_rel.getChildCount() - 1; childCount2 >= 0; childCount2--) {
                if (z) {
                    this.mItemArray.add(new Pair(Long.valueOf((long) childCount2), this.txt_stkr_rel.getChildAt(childCount2)));
                }
                View childAt = this.txt_stkr_rel.getChildAt(childCount2);
                if (childAt instanceof AutofitTextRel) {
                    this.arrBoolean[childCount2] = ((AutofitTextRel) childAt).isMultiTouchEnabled;
                }
                if (childAt instanceof ResizableStickerView) {
                    this.arrBoolean[childCount2] = ((ResizableStickerView) childAt).isMultiTouchEnabled;
                }
                if (this.arrBoolean[childCount2]) {
                    i2++;
                } else {
                    i++;
                }
            }
            if (childCount == i) {
                this.checkAll = "Lock_All";
                this.img_All.setImageResource(R.drawable.off_fp);
            } else if (childCount == i2) {
                this.checkAll = "UnLock_All";
                this.img_All.setImageResource(R.drawable.on_fp);
            } else {
                this.checkAll = "Lock_Mixed";
                this.img_All.setImageResource(R.drawable.on_fp);
            }
        } else {
            this.lay_Notext.setVisibility(View.VISIBLE);
            this.lay_selectAll.setVisibility(View.INVISIBLE);
        }
        if (z) {
            setupListRecyclerView();
        }
    }

    private void setupListRecyclerView() {
        this.mDragListView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterItem adapterItem = new AdapterItem(getActivity(), this.mItemArray, R.layout.list_item, R.id.touch_rel, false);
        this.mDragListView.setAdapter(adapterItem, true);
        this.mDragListView.setCanDragHorizontally(false);
        this.mDragListView.setCustomDragItem(new MyDragItem(getContext(), R.layout.list_item));
    }
}
