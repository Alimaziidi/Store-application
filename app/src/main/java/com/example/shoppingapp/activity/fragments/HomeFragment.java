package com.example.shoppingapp.activity.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.AllProductActivity;
import com.example.shoppingapp.activity.Global.Link;
import com.example.shoppingapp.activity.adapter.AmazingProductAdapter;
import com.example.shoppingapp.activity.adapter.BannerSecondAdapter;
import com.example.shoppingapp.activity.adapter.BrandAdapter;
import com.example.shoppingapp.activity.adapter.CategoryAdapter;
import com.example.shoppingapp.activity.adapter.NewProductAdapter;
import com.example.shoppingapp.activity.adapter.SliderAdapter;
import com.example.shoppingapp.activity.adapter.WatchProductAdapter;
import com.example.shoppingapp.activity.api.ApiClient;
import com.example.shoppingapp.activity.api.ApiInterface;
import com.example.shoppingapp.activity.model.Amazing;
import com.example.shoppingapp.activity.model.AmazingOfferProduct;
import com.example.shoppingapp.activity.model.Banner;
import com.example.shoppingapp.activity.model.Brand;
import com.example.shoppingapp.activity.model.Category;
import com.example.shoppingapp.activity.model.FirstAmazing;
import com.example.shoppingapp.activity.model.Product;
import com.example.shoppingapp.activity.model.TimerAmazing;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    View view;
    RequestQueue requestQueue;

    //Slider
    List<Banner> listBanner = new ArrayList<>();
    SliderAdapter sliderAdapter;
    ViewPager viewPager;

    TabLayout tabLayout ;

    //Category
    List<Category> listCategory = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerviewCategory;

    //Amazing Offer
    List<Amazing> listAmazing = new ArrayList<>();
    AmazingProductAdapter amazingProductAdapter;
    RecyclerView recyclerView_amazing;

    //Second Banner
    List<Banner> listBanner_second = new ArrayList<>();
    BannerSecondAdapter bannerSecondAdapter;
    RecyclerView recyclerViewSecondBanner;

    //New Product
    List<Product> listNewProduct = new ArrayList<>();
    NewProductAdapter newProductAdapter;
    RecyclerView recyclerViewNewProduct;

    //Brand
    List<Brand> listBrand = new ArrayList<>();
    BrandAdapter brandAdapter;
    RecyclerView recyclerView_brand;

    //Detail Category Product
    List<Amazing> listDetailCategoryProduct = new ArrayList<>();
    WatchProductAdapter watchProductAdapter;
    RecyclerView recyclerView_new_watch;

    TextView txt_new_product_more;


    //Timer
    Timer timer;
    TextView txt_second , txt_minute , txt_hour;
    ApiInterface request;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        request = ApiClient.getApiClient().create(ApiInterface.class);
        timer = new Timer();

        getBannerSlider();
        getCategory();
        getAmazing();
        getSecondBanner();
        getBrand();
        getNewProduct();
        getNewWatch();
        getTimer();

       txt_new_product_more = view.findViewById(R.id.txt_new_product_more);
        txt_new_product_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , AllProductActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }

    private void getTimer() {

        txt_minute = view.findViewById(R.id.txt_minute);
        txt_hour = view.findViewById(R.id.txt_hour);
        txt_second = view.findViewById(R.id.txt_second);


        Call<TimerAmazing> callTimerAmazing = request.getTimeAmazing();

        callTimerAmazing.enqueue(new Callback<TimerAmazing>() {
            @Override
            public void onResponse(Call<TimerAmazing> call, retrofit2.Response<TimerAmazing> response) {

                //Code For Your Timer
                /*
                String hour = response.body().getHour()+"";
                String minute = response.body().getMin()+"";
                String  second = response.body().getSec()+"";

                if (response.body().getHour()>23){
                    response.body().setHour(23);
                    txt_hour.setText(response.body().getHour()+"");
                }

                if (response.body().getHour()<10){
                    txt_hour.setText("0"+response.body().getHour());
                }else {
                    txt_hour.setText(response.body().getHour()+"");
                }

                if (response.body().getMin()<10){
                    txt_minute.setText("0"+response.body().getMin());
                }else {
                    txt_minute.setText(response.body().getMin()+"");
                }

                if (response.body().getSec()<10){
                    txt_second.setText("0"+response.body().getSec());
                }else {
                    txt_second.setText(response.body().getSec()+"");
                }

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        if (response.body().getSec()!=0){
                            response.body().setSec(response.body().getSec() -1);
                            if (response.body().getSec()<10){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt_second.setText("0"+response.body().getSec());
                                    }
                                });
                            }else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt_second.setText(response.body().getSec()+"");
                                    }
                                });
                            }
                        }else {
                            if (response.body().getMin()!=0){
                                response.body().setMin(response.body().getMin()-1);
                                response.body().setSec(59);
                                if (response.body().getMin()<10){
                                    //response.body().setMin(response.body().getMin()-1);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_minute.setText("0"+response.body().getMin());
                                            txt_second.setText(response.body().getSec()+"");
                                        }
                                    });
                                }else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_minute.setText(response.body().getMin()+"");
                                            txt_second.setText(response.body().getSec()+"");
                                        }
                                    });

                                }
                            }else {
                                response.body().setSec(59);
                                response.body().setMin(59);
                                response.body().setHour(response.body().getHour()-1);

                                if (response.body().getHour()<10){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_hour.setText("0"+response.body().getHour());
                                            txt_minute.setText(response.body().getMin()+"");
                                            txt_second.setText(response.body().getSec()+"");
                                        }
                                    });

                                }else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_hour.setText(response.body().getHour()+"");
                                            txt_minute.setText(response.body().getMin()+"");
                                            txt_second.setText(response.body().getSec()+"");
                                        }
                                    });
                                }
                            }
                        }
                    }
                },0,1000);

                Toast.makeText(getContext(), hour+" " + minute  + " " + second +" ", Toast.LENGTH_SHORT).show();
                 */

                String hour = response.body().getHour()+"";
                String minute = response.body().getMin()+"";
                String second = response.body().getSec()+"";

                if (response.body().getMin()<10){
                    txt_minute.setText("0"+response.body().getMin());
                }else {
                    txt_minute.setText(response.body().getMin()+"");
                }

                if (response.body().getSec()<10){
                    txt_second.setText("0"+response.body().getSec());
                }else {
                    txt_second.setText(response.body().getSec()+"");
                }

                if (response.body().getHour()<10){
                    txt_hour.setText("0"+response.body().getHour());
                }else {
                    txt_hour.setText(response.body().getHour()+"");
                }


                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        if (response.body().getSec() != 0){
                            response.body().setSec(response.body().getSec() - 1 );
                            if (response.body().getSec()<10){

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        txt_second.setText("0"+response.body().getSec());

                                    }
                                });

                            }else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        txt_second.setText(response.body().getSec()+"");

                                    }
                                });

                            }
                        }else {
                            response.body().setSec(59);
                            if (response.body().getMin() != 0){
                                response.body().setMin(response.body().getMin() - 1);
                                if (response.body().getMin() < 10){

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            txt_minute.setText("0"+response.body().getMin());
                                        }
                                    });

                                }else {

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            txt_minute.setText(response.body().getMin()+"");
                                            txt_second.setText(response.body().getSec()+"");

                                        }
                                    });
                                }
                            }
                        }


                    }
                },0 , 1000);



               // Toast.makeText(getContext(), hour + "  " + minute + "  " + second, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<TimerAmazing> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void getNewWatch() {

        recyclerView_new_watch = view.findViewById(R.id.recyclerView_new_watch);
        recyclerView_new_watch.setHasFixedSize(true);
        recyclerView_new_watch.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));

        FirstAmazing firstAmazing_Watch = new FirstAmazing("جدید ترین ساعت های هوشمند را مشاهده کنید"
                ,"https://www.digikala.com/statics/img/png/specialCarousel/Electronics.webp");

        listDetailCategoryProduct.add(new Amazing(1 , firstAmazing_Watch));

        watchProductAdapter = new WatchProductAdapter(getContext() , listDetailCategoryProduct);
        recyclerView_new_watch.setAdapter(watchProductAdapter);

        String url = Link.LINK_NEW_WATCH;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listDetailCategoryProduct.add(new Amazing(0 , products[i]));
                    watchProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);

    }

    private void getBrand() {

        recyclerView_brand = view.findViewById(R.id.recyclerView_brand);
        recyclerView_brand.setHasFixedSize(true);
        recyclerView_brand.setLayoutManager(new GridLayoutManager(getContext() , 3));
        brandAdapter = new BrandAdapter(getContext() , listBrand);
        recyclerView_brand.setAdapter(brandAdapter);

        String url = Link.LINK_BRAND;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Brand[] brands = gson.fromJson(response.toString() ,  Brand[].class);

                for (int i = 0 ; i<brands.length ; i++){

                    listBrand.add(brands[i]);
                    brandAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);


    }

    private void getNewProduct() {

        recyclerViewNewProduct = view.findViewById(R.id.recyclerView_new_product);
        recyclerViewNewProduct.setHasFixedSize(true);
        recyclerViewNewProduct.setLayoutManager(new GridLayoutManager(getContext() , 3));
        newProductAdapter = new NewProductAdapter(getContext() , listNewProduct);
        recyclerViewNewProduct.setAdapter(newProductAdapter);

        String url = Link.LINK_NEW_PRODUCT;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listNewProduct.add(products[i]);
                    newProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);

    }

    private void getSecondBanner() {
        recyclerViewSecondBanner = view.findViewById(R.id.recyclerView_banner_second);
        recyclerViewSecondBanner.setLayoutManager(new GridLayoutManager(getContext() , 2));
        recyclerViewSecondBanner.setHasFixedSize(true);
        bannerSecondAdapter = new BannerSecondAdapter(getContext() , listBanner_second);
        recyclerViewSecondBanner.setAdapter(bannerSecondAdapter);

        String url = Link.LINK_SECOND_BANNER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Banner[] banners = gson.fromJson(response.toString() ,  Banner[].class);

                for (int i = 0 ; i<banners.length ; i++){

                    listBanner_second.add(banners[i]);
                    bannerSecondAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);



    }

    private void getAmazing() {


        recyclerView_amazing = view.findViewById(R.id.recyclerView_amazing_offer);
        recyclerView_amazing.setHasFixedSize(true);
        recyclerView_amazing.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));

        FirstAmazing firstAmazing = new FirstAmazing("پیشنهاد های شگفت انگیز این هفته را از دست ندهید"
                ,"https://www.radokala.com/wp-content/uploads/2022/08/Amazing-offer-01.jpg");

        listAmazing.add(new Amazing(1 , firstAmazing));

        amazingProductAdapter = new AmazingProductAdapter(getContext() , listAmazing);
        recyclerView_amazing.setAdapter(amazingProductAdapter);

        String url = Link.LINK_AMAZING_OFFER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                AmazingOfferProduct[] amazingOfferProducts = gson.fromJson(response.toString() ,  AmazingOfferProduct[].class);

                for (int i = 0 ; i<amazingOfferProducts.length ; i++){

                    listAmazing.add(new Amazing(0 , amazingOfferProducts[i]));
                    amazingProductAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);



    }

    private void getCategory() {

        recyclerviewCategory = view.findViewById(R.id.recyclerView_Category);
        recyclerviewCategory.setHasFixedSize(true);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        categoryAdapter = new CategoryAdapter(getContext() , listCategory);
        recyclerviewCategory.setAdapter(categoryAdapter);

        String url = Link.LINK_CATEGORY_BY_LIMIT;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Category[] categories = gson.fromJson(response.toString() ,  Category[].class);

                for (int i = 0 ; i<categories.length ; i++){

                    listCategory.add(categories[i]);
                    categoryAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);


    }

    private void getBannerSlider() {

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabs);
        sliderAdapter = new SliderAdapter(getContext(),listBanner);
        viewPager.setAdapter(sliderAdapter);
        tabLayout.setupWithViewPager(viewPager,true);


        viewPager.setRotationY(180);

        final int paddingPx = 120;
        final float MIN_SCALE = 0.8f;
        final float MAX_SCALE = 1f;

        viewPager.setClipToPadding(false);
        viewPager.setPadding(paddingPx, 0, paddingPx, 0);

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                float pagerWidthPx = ((ViewPager) page.getParent()).getWidth();
                float pageWidthPx = pagerWidthPx - 2 * paddingPx;

                float maxVisiblePages = pagerWidthPx / pageWidthPx;
                float center = maxVisiblePages / 2f;

                float scale;
                if (position + 0.5f < center - 0.5f || position > center) {
                    scale = MIN_SCALE;
                } else {
                    float coef;
                    if (position + 0.5f < center) {
                        coef = (position + 1 - center) / 0.5f;
                    } else {
                        coef = (center - position) / 0.5f;
                    }
                    scale = coef * (MAX_SCALE - MIN_SCALE) + MIN_SCALE;
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        };
        viewPager.setPageTransformer(false, transformer);



        String url = Link.LINK_BANNER_SLIDER;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Banner[] banners = gson.fromJson(response.toString() ,  Banner[].class);

                for (int i = 0 ; i<banners.length ; i++){

                    listBanner.add(banners[i]);


                }

                sliderAdapter.notifyDataSetChanged();



            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);

        final boolean running_thread = true;

        Thread thread = new Thread(){

            @Override
            public void run() {


                while (running_thread){

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                    if (getActivity()==null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (viewPager.getCurrentItem() < listBanner.size()-1){
                                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                            }else {
                                viewPager.setCurrentItem(0);
                            }

                        }
                    });

                }

            }
        };
        
        thread.start();



    }

    @Override
    public void onDestroy() {

        if (timer != null){
            timer.cancel();
            timer.purge();
        }

        super.onDestroy();
    }
}