package com.modele.badelcollection;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {

    private List<MyOrderItemModel>myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout,viewGroup,false);
       return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.Viewholder viewholder, int position) {
        int resource =myOrderItemModelList.get(position).getProductImage();
        int rating =myOrderItemModelList.get(position).getRating();
        String title =myOrderItemModelList.get(position).getProductTitle();
        String deliveredDate =myOrderItemModelList.get(position).getDeliveryStatus();
        viewholder.setData(resource,title,deliveredDate,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowContainer;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            productImage =itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            orderIndicator=itemView.findViewById(R.id.order_indicator);
            deliveryStatus=itemView.findViewById(R.id.order_delivered_date);
            rateNowContainer = itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });


        }

        private void setData(int resource,String title,String deliveredDate,int rating){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(deliveredDate.equals("Cancelled")) {
                ///utiliser a parti de api 21 (Video 31)
                //orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorRouge)));
            }else {
                //orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.greenSuccessful)));

            }
            deliveryStatus.setText(deliveredDate);

            //// rating layout
                    setRating(rating);
            for(int x =0;x<rateNowContainer.getChildCount();x++){
                final int startPosition = x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRating(startPosition);
                    }
                });
            }
            //// rating layout

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
    }
}
