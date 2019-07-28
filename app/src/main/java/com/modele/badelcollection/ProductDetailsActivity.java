package com.modele.badelcollection;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.modele.badelcollection.MainActivity.showCart;
import static com.modele.badelcollection.RegisterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewpager;
    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView codIndicator;
    private TextView tvCodIndicator;
    private TabLayout viewpagerIndicator;
    private Button coupenRedeenBtn;
    private LinearLayout coupenRedemptionLayout;

    private TextView rewardTitle;
    private TextView rewardBody;

    //// product Description

    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;
    private TextView productOnlyDescriptionBody;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private String productDescription;
    private String productOrtherDetails;
    //public static int tabPosition = -1;
    //// product Description
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishlistBtn;

    ////// rating layout
    private LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
    /////  rating layout

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;

    private FirebaseFirestore firebaseFirestore;

    ///// coupen Dialog
    public  static TextView coupenTitle;
    public  static TextView coupenExpiryDate;
    public  static TextView coupenBody;
    private static RecyclerView coupensRecyclerview;
    private static LinearLayout selectedCoupen;
    ///// coupen Dialog

    private Dialog signInDialog;
    private Dialog loadingDialog;
    private FirebaseUser currentUser;
    private String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        productImagesViewpager =findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicateur);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsTablayout = findViewById(R.id.product_details_tab_layout);
        productDetailsViewpager = findViewById(R.id.product_details_viewpager);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        coupenRedeenBtn= findViewById(R.id.coupen_redemption_btn);
        productTitle= findViewById(R.id.product_title);
        averageRatingMiniView= findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView= findViewById(R.id.total_ratings_miniview);
        productPrice= findViewById(R.id.product_price);
        cuttedPrice= findViewById(R.id.cutted_price);
        codIndicator= findViewById(R.id.cod_indicator_imageview);
        tvCodIndicator= findViewById(R.id.tv_cod_indicator);
        rewardTitle= findViewById(R.id.reward_title);
        rewardBody= findViewById(R.id.reward_body);
        productDetailsOnlyContainer= findViewById(R.id.product_details_container);
        productDetailsTabsContainer= findViewById(R.id.product_details_tabs_container);
        productDetailsTablayout= findViewById(R.id.product_details_tab_layout);
        productOnlyDescriptionBody= findViewById(R.id.product_details_body);
        totalRatings= findViewById(R.id.total_ratings);
        ratingsNoContainer= findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure= findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer= findViewById(R.id.ratings_progressbar_container);
        averageRating= findViewById(R.id.average_rating);
        addToCartBtn= findViewById(R.id.add_to_cart_btn);
        coupenRedemptionLayout = findViewById(R.id.coupen_redemption_layout);

        ///// loading Dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.slider_background);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background)); // valab aparti de api 21
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        ///// loading Dialog


        firebaseFirestore =FirebaseFirestore.getInstance();

        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)//"Mz8Hg9VntLYNfW9k6cBP")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long x = 1; x<(long)documentSnapshot.get("no_of_product_images")+ 1; x++){
                        productImages.add(documentSnapshot.get("product_image_"+x).toString());
                        }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewpager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+")ratings");
                    productPrice.setText("Gdes "+documentSnapshot.get("product_price").toString());
                    cuttedPrice.setText("Gdes "+documentSnapshot.get("cutted_price").toString());
                    productPrice.setText("Gdes "+documentSnapshot.get("product_price").toString());
                    if((boolean)documentSnapshot.get("COD")){
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCodIndicator.setVisibility(View.VISIBLE);
                    }else {
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCodIndicator.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long)documentSnapshot.get("free_coupens")+ documentSnapshot.get("free_coupens_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupens_body").toString());

                    if((boolean)documentSnapshot.get("use_tab_layout")){
                        productDetailsTabsContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                         productDescription = documentSnapshot.get("product_description").toString();
                        //ProductSpecificationFragment.productSpecificationModelList = new ArrayList<>();
                        productOrtherDetails = documentSnapshot.get("product_other_details").toString();
                        for(long x = 1; x<(long)documentSnapshot.get("total_spec_titles")+1;x++){
                            productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));
                            for (long y = 1; y < (long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1; y++){
                                productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));
                            }
                        }

                    }else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                    }
                    totalRatings.setText(documentSnapshot.get("total_ratings")+"ratings");
                    for(int x = 0; x < 5;x++){
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));

                    }
                    totalRatingsFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount(),productDescription,productOrtherDetails,productSpecificationModelList));

                    if(DBqueries.wishList.size() == 0){
                        DBqueries.loadWishList(ProductDetailsActivity.this,loadingDialog);
                    } else {
                        loadingDialog.dismiss();
                    }
                    if(DBqueries.wishList.contains(productID)){
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                    }else {
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }
                }else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.product_image);
        productImages.add(R.drawable.banner);
        productImages.add(R.drawable.banner_deux);
        productImages.add(R.drawable.banner_un);*/


        viewpagerIndicator.setupWithViewPager(productImagesViewpager,true);
        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser == null){
                    signInDialog.show();
                }else {
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        ALREADY_ADDED_TO_WISHLIST = false;
                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                    } else {

                        Map<String,Object> addProduct = new HashMap<>();
                        addProduct.put("product_ID_"+String.valueOf(DBqueries.wishList.size()),productID);

                        firebaseFirestore.collection("USERS")
                                .document(currentUser.getUid())
                                .collection("USER_DATA")
                                .document("MY_WISHLIST")
                                .set(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Map<String,Object> updateListSize = new HashMap<>();
                                    updateListSize.put("list_size", (long) (DBqueries.wishList.size()+1));

                                    firebaseFirestore.collection("USERS")
                                            .document(currentUser.getUid())
                                            .collection("USER_DATA")
                                            .document("MY_WISHLIST")
                                            .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                ALREADY_ADDED_TO_WISHLIST = true;
                                                addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                                                DBqueries.wishList.add(productID);
                                                String addToWishlist = getString(R.string.add_to_wishlist);
                                                Toast.makeText(ProductDetailsActivity.this, addToWishlist, Toast.LENGTH_SHORT).show();

                                            }else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }

            }
        });


    productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));

    productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //tabPosition = tab.getPosition();
            productDetailsViewpager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

        //// rating layout

        rateNowContainer = findViewById(R.id.rate_now_container);
        for(int x = 0; x < rateNowContainer.getChildCount();x++){
            final int startPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentUser == null){
                        signInDialog.show();
                    }else {
                        setRating(startPosition);
                    }

                }
            });
        }
        //// rating layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser == null){
                    signInDialog.show();
                }else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser == null){
                    signInDialog.show();
                }else {
                    ////todo: add to cart
                }
            }
        });

        ///// coupen Dialog

        final Dialog checkCoupenPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialog.setCancelable(true);
        checkCoupenPriceDialog.setTitle(R.string.check_coupen_dialog);
        checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerview = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerview);
        coupensRecyclerview = checkCoupenPriceDialog.findViewById(R.id.coupens_recyclerview);
        selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDialog.findViewById(R.id.discounted_price);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupensRecyclerview.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("buy 1 get 1","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("buy 1 get 1","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList,true);
        coupensRecyclerview.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        toggleRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerview();

            }
        });

        ////// coupen Dialog
        coupenRedeenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCoupenPriceDialog.show();
            }
        });

        //// sign dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);
        final Intent registerIntent = new Intent(ProductDetailsActivity.this,RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment =false;
                startActivity(registerIntent);

            }
        });

        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment =true;
                startActivity(registerIntent);

            }
        });
        /// sign Dialog

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            coupenRedemptionLayout.setVisibility(View.GONE);

        }else {
            coupenRedemptionLayout.setVisibility(View.VISIBLE);
        }
    }

    public static void showDialogRecyclerview(){
        if(coupensRecyclerview.getVisibility() == View.GONE){
            coupensRecyclerview.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupensRecyclerview.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);

        }
    }

    private void setRating(int startPosition) {
        for(int x =0; x<rateNowContainer.getChildCount(); x++){
            ImageView starBtn=(ImageView)rateNowContainer.getChildAt(x);
            // starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x<=startPosition){
               // starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==android.R.id.home) {
                finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            // todo: search
            return true;
        } else if (id == R.id.main_cart_icon) {
            if(currentUser == null){
                signInDialog.show();
            }else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
