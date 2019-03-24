package com.bb.ui;



import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.db.DbControl;
import com.bb.model.Info;
import com.bb.util.AsyncImageLoader;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.util.Constants;



public class FoodInfoActivity extends Activity {


	private Info food ;
	ImageButton add;
	ImageButton sub;
	EditText count;
	int num;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.food_info);
		
		 add = (ImageButton) this.findViewById(R.id.addbt );
		 sub = (ImageButton) this.findViewById(R.id.subbt );
		 count = (EditText) this.findViewById(R.id.count);
		
		add.setOnClickListener(new OnButtonClickListener());
		sub.setOnClickListener(new OnButtonClickListener());
		//中间显示文字的改变监听
		count.addTextChangedListener(new OnTextChangeListener());
		count.setText("1");
		num=1;
		
//获取上一个activity传过来的参数值
		food = (Info) getIntent().getSerializableExtra("food");
		
		TextView tv_name = (TextView) this.findViewById(R.id.name);
		tv_name.setText(   food.getFood_name()  ) ;

		TextView miaoshu = (TextView) this.findViewById(R.id.miaoshu );
		miaoshu.setText(  "简介：" +  food.getFood_description() ) ;

		TextView kouwei = (TextView) this.findViewById(R.id.kouwei );
		kouwei.setText(  "口味：" +  food.kouwei )  ;

		TextView jiage = (TextView) this.findViewById(R.id.jiage );
		jiage.setText( "价格：" +  food.food_discount_price )  ;
		
		TextView shangjia = (TextView) this.findViewById(R.id.shangjia );
		shangjia.setText( "商家：" +  food.renqun)  ;

		
		String flag = "" ;
		switch( food.food_flag ){
			case 0: flag = "地区特色" ;	break;
			case 1: flag = "精品推荐" ;	break;
			case 2: flag = "普通	" ;	break;
			case 3: flag = "饮品	" ;	break;
			case 4: flag = "素菜	" ;	break;
			case 5: flag = "汤菜	" ;	break;
		}
		
		TextView leibie = (TextView) this.findViewById(R.id.leibie );
		leibie.setText( "类别：" +  flag  )  ;
		
		 
		
//		从服务器上获取图片，并且显示
		ImageView iv = (ImageView) this.findViewById(R.id.food_img) ; 
        String picPath = Constants.WEB_APP_URL + "upload/" + food.getFood_pic()  ; 
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		
		Drawable cachedImage = asyncImageLoader.loadDrawable(
    			picPath , iv , new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, String imageUrl) {
						imageView.setImageDrawable(imageDrawable);
					}
				});

		if (cachedImage == null) {
			iv.setImageResource(R.drawable.pork);
		} else {
			iv.setImageDrawable(cachedImage);
		}
		
//		给按钮做响应事件
//		et_seat = (EditText) this.findViewById(R.id.seat);  
		
		Button btn = (Button) findViewById(R.id.button1) ;
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				String seat = et_seat.getText().toString().trim();
//				添加到购物车，购物车数据临时存放在手机自带sqlite的数据库
//				String seat = String.valueOf( food.getFood_id() ) ;
				
				String seat = food.renqun ;
				//String seat_id= food.yonghuming;
				 
				DbControl 	dbControl   = new DbControl( FoodInfoActivity.this ) ;
				if( dbControl. addGwc( FoodInfoActivity.this , food.getFood_name()  ,  seat , food.getFood_discount_price(),num,food.getShangjia() )  ) {
	        		Log.i(" add gwc "," add gwc success "); 
//	        	    Intent i = new Intent( FoodInfoActivity.this, GwcListActivity.class);
//			        startActivity(i);
	        		Toast.makeText(FoodInfoActivity.this, "加入购物车成功！", Toast.LENGTH_SHORT).show();
			        finish();
				} 
		       
			}
		}) ;
		
       Button dianhua = (Button) findViewById(R.id.sjdianhua) ;
		
		dianhua.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent call= new Intent();
				call.setAction(Intent.ACTION_VIEW);  //设置动作：显示数据
				call.setData(Uri.parse("tel:"+food.shangjia));
				startActivity(call);
		       
			}
		}) ;
		
	}
	
	 class OnButtonClickListener implements OnClickListener
	    {
	        @Override
	        public void onClick(View v)
	        {
	      //  得到输入框里的数字
	            String numString = count.getText().toString();
	      //      进行判断为空或是没文字设置为0
	            if (numString == null || numString.equals(""))
	            {
	                num = 1;
	                count.setText("1");
	            } 
	            switch (v.getId()) {
	    		case R.id.subbt:
	    			if (++num < 0)  //先加，再判断
                    {
                        num--;
       Toast.makeText(FoodInfoActivity.this, "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else
                    {
                        count.setText(String.valueOf(num));
                    }
	    			break;
	    			
	    		case R.id.addbt:
	    			 if (--num < 0)  //先减，再判断
	                    {
	                        num++;
	Toast.makeText(FoodInfoActivity.this, "不能小于0",0).show();
	                    } else
	                    {
	                        count.setText(String.valueOf(num));
	                    }
	    			 break;
	            }
	         
	        }
	    }
	
	 class OnTextChangeListener implements TextWatcher
	    {
	        @Override
	        public void afterTextChanged(Editable s)
	        {
	            String numString = s.toString();
	            if(numString == null || numString.equals(""))
	            {
	                num = 1;
	            }
	            else {
	                int numInt = Integer.parseInt(numString);
	                if (numInt < 0)
	                {
	                    Toast.makeText(FoodInfoActivity.this, "请输入一个大于0的数字",
	                            Toast.LENGTH_SHORT).show();
	                } else
	                {
	                    //设置EditText光标位置 为文本末端
	                    count.setSelection(count.getText().toString().length());
	                    num = numInt;

	                }
	            }
	        }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
	    }
	
	
}
