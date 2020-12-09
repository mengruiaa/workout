//package com.example.fitnessdemo.LZYZYH;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.fitnessdemo.LZYZYH.activity.DetailActivity;
//import com.example.fitnessdemo.LZYZYH.model.Product;
//import com.example.fitnessdemo.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.example.fitnessdemo.LZYZYH.TuiJian.updateUIThread;
//
//public class MenuFragment extends Fragment {
//    private LinearLayout search;
//    private CustomeAdapter customeAdapter;
//    private ListView userList;
//    private String name1;
//    private TextView tishi;
//    private Handler myhandler= new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String s = updateUIThread(msg);
//            if (s.equals("")||s==null||s.length()==0){
//                tishi.setText("本店暂无商品哦~~");
//            }else {
//                final String[] defaultProduct = s.split("/");
//                List<Product> products = new ArrayList<>();
//                for (int i = 0; i < defaultProduct.length; i++) {
//                    String[] items = defaultProduct[i].split(",");
//                    int mipmapId = getResources().getIdentifier(
//                            items[4],
//                            "mipmap",
//                            getActivity().getPackageName()
//                    );
//                    Log.e("ss", defaultProduct[i]);
//                    Product c = new Product(items[0], items[1], Float.parseFloat(items[2]),  ""+mipmapId);
//                    products.add(c);
//                }
//                customeAdapter = new CustomeAdapter(getActivity().getApplicationContext(),
//                        products, R.layout.mall_list_item);
//                userList.setAdapter(customeAdapter);
//                customeAdapter.notifyDataSetChanged();
//                Intent i2 = getActivity().getIntent();
//                name1 = i2.getStringExtra("name1");
//                userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        //设置目标CLass
//                        intent.setClass(getActivity(), DetailActivity.class);
//                        intent.putExtra("str", defaultProduct[position]);
//                        intent.putExtra("position", position);
//                        intent.putExtra("name2", name1);
//                        startActivity(intent);
//                    }
//                });
//            }
//        }
//    };
////    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.frgment_menu,null);
////        userList = view.findViewById(R.id.userList);
////        search = view.findViewById(R.id.search);
////        tishi = view.findViewById(R.id.tishi);
////        Intent i2 = getActivity().getIntent();
////        name1 = i2.getStringExtra("name1");
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                String C_URL = "http://192.168.137.1:8080/CakeWeb/showcakes";
////                String str = HttpConnect.ByPost("",C_URL);
////                Bundle bundle = new Bundle();
////                bundle.putString("cake",str);
////                Message msg = new Message();
////                msg.setData(bundle);
////                msg.what=0;
////                myhandler.sendMessage(msg);
////            }
////        }).start();
////        search.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent sch = new Intent();
////                sch.putExtra("name1",name1);
////                sch.setClass(getActivity().getApplicationContext(), SearchActivity.class);
////                startActivity(sch);
////            }
////        });
////
////        return view;
////    }
//}
