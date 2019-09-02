package com.modele.badelcollection;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel>cartItemModelList;

    private int lastPosition = -1;
    private TextView cartTotalAmount;
    private boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
                default:
                    return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                return new CartItemViewholder(cartItemView);

                case CartItemModel.TOTAL_AMOUNT:
                    View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout,viewGroup,false);
                    return new CartTotalAmountViewholder(cartTotalView);
                    default:
                        return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                String productPrice = cartItemModelList.get(position).getProuductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                //int productQuantity =cartItemModelList.get(position).getProductQuantity();
                Long offersApplied = cartItemModelList.get(position).getOffersApplied();
                //int coupensApplied = cartItemModelList.get(position).getCoupensApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();

                ((CartItemViewholder)viewHolder).setItemDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied,position,inStock);

                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemPrice = 0;
                String deliveryPrice;
                int totalAmount;
                int saveAmount = 0;

                for(int x =0;x<cartItemModelList.size();x++){
                    if(cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){
                        totalItems++;
                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProuductPrice());

                    }
                }
                if(totalItemPrice>500){
                    deliveryPrice = "FREE";
                    totalAmount = totalItemPrice;
                }else {
                    deliveryPrice = "60";
                    totalAmount = totalItemPrice + 60;
                }


                //String totalItems = cartItemModelList.get(position).getTotalItem();
                //String totalItemPrice = cartItemModelList.get(position).getTotalItemPrice();
                //String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
                //String totalAmount = cartItemModelList.get(position).getTotalAmount();
                //String saveAmount = cartItemModelList.get(position).getSaveAmount();
                ((CartTotalAmountViewholder)viewHolder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,saveAmount);

                break;
                default:
                    return;
        }

        if(lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }
    class CartItemViewholder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView freeCoupensIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productprice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;
        private LinearLayout coupenRedemptionLayout;

        private LinearLayout deleteBtn;


        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image_cart);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupensIcon = itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productprice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            coupenRedemptionLayout = itemView.findViewById(R.id.coupen_redemption_layout);

            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productID, String resource, String title, Long freeCoupensNo, String productPriceText, String cuttedPriceText, Long offersAppliedNo, final int position,boolean inStock){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.icon_placeholder)).into(productImage);
            productTitle.setText(title);


            if (inStock) {

                if(freeCoupensNo>0){

                    freeCoupensIcon.setVisibility(View.VISIBLE);
                    freeCoupens.setVisibility(View.VISIBLE);

                    if(freeCoupensNo == 1){

                        freeCoupens.setText("free " + freeCoupensNo + " Coupen");

                    }else {

                        freeCoupens.setText("free " + freeCoupensNo + " Coupen");
                    }

                } else {
                    freeCoupensIcon.setVisibility(View.INVISIBLE);
                    freeCoupens.setVisibility(View.INVISIBLE);
                }

                productprice.setText("gdes "+productPriceText);
                productprice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setText("gdes "+cuttedPriceText);
                coupenRedemptionLayout.setVisibility(View.VISIBLE);

                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog quantityDialog = new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.quantity_dialog);
                        quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDialog.setCancelable(false);
                        quantityDialog.setTitle("Quantity");
                        final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_no);
                        Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                        Button okBtn = quantityDialog.findViewById(R.id.ok_btn);

                        // implementation cancel btn
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                quantityDialog.dismiss();
                            }
                        });

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                productQuantity.setText("Qty: " + quantityNo.getText());
                                quantityDialog.dismiss();
                            }
                        });
                        quantityDialog.show();

                    }
                });

                if(offersAppliedNo > 0){
                    offersApplied.setVisibility(View.VISIBLE);
                    offersApplied.setText(offersAppliedNo + " Offers Applied");
                }else {
                    offersApplied.setVisibility(View.INVISIBLE);
                }

            }else {
                productprice.setText("Out of Stock");
                productprice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorRouge));
                cuttedPrice.setText("");
                coupenRedemptionLayout.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.INVISIBLE);
                productQuantity.setVisibility(View.INVISIBLE);
                coupensApplied.setVisibility(View.GONE);
                offersApplied.setVisibility(View.GONE);
                freeCoupensIcon.setVisibility(View.INVISIBLE);
//                productQuantity.setText("Qty: " + 0);
//                productQuantity.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
//
//                productQuantity.setTextColor(Color.parseColor("#70000000"));
                //productQuantity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70000000"))); // use for Api 21
                // use for api 16 bienTot

            }


            if (showDeleteBtn){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);

            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!ProductDetailsActivity.running_cart_query){
                        ProductDetailsActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position,itemView.getContext(),cartTotalAmount);
                    }
                }
            });
        }
    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView saveAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);

            totalItems = itemView.findViewById(R.id.total_items);
            totalItemsPrice= itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            saveAmount = itemView.findViewById(R.id.saved_amount);
        }

        private void setTotalAmount (int totalItemText, int totalItemPriceText,String deliveryPriceText, int totalAmountText, int saveAmountText){
            totalItems.setText("price( "+totalItemText+" items)");
            totalItemsPrice.setText("gdes "+totalItemPriceText);
            if(deliveryPriceText.equals("FREE")){
                deliveryPrice.setText(deliveryPriceText);
            }else {
                deliveryPrice.setText("gdes "+deliveryPriceText);
            }

            totalAmount.setText("gdes "+totalAmountText);
            cartTotalAmount.setText("gdes "+totalAmountText);
            saveAmount.setText("You saved gdes "+saveAmountText+" on this order.");

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();

            if (totalItemPriceText == 0){
                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size() - 1);
                parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);
            }

        }
    }
}
