package com.imagenproactiva.irplugin;

import org.apache.cordova.CordovaPlugin;

import android.hardware.ConsumerIrManager;

import org.apache.cordova.CallbackContext;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.lang.String;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Runnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ir extends CordovaPlugin {
    public static final String ACTION_TRANSMIT_IR_CODE = "transmit";
    Object irdaService;
    //Method irWrite;
    ConsumerIrManager irService;
    
    @Override
    public boolean execute(String action, JSONArray jsonArgs, final CallbackContext callbackContext) throws JSONException {
        try {
        	

        	
            if (ACTION_TRANSMIT_IR_CODE.equals(action)) {
            	
                JSONObject args = jsonArgs.getJSONObject(0);
                final Integer frequency = args.getInt("frequency");
                JSONArray signalJson = args.getJSONArray("signal");
                final int[] signal = new int[signalJson.length()];
                for (int i = 0; i < signalJson.length(); ++i) {
                    signal[i] = signalJson.optInt(i);
                }
                
            	
  
                
                final Context context = this.cordova.getActivity().getApplicationContext();
                this.cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                 
                    	
                       //ConsumerIrManager irService = (ConsumerIrManager)context.getSystemService(context.CONSUMER_IR_SERVICE);
                     // ConsumerIrManager irService = (ConsumerIrManager)context.getSystemService("irda");

                    irdaService = context.getSystemService("irda");
                     
                    Class c = irdaService.getClass();
             		/*Class p[] = { String.class };
             		irWrite = c.getMethod("write_irsend", p);
             		irWrite.invoke(irdaService, data);*/
                    
                    try{
                    
                    c.getMethod("write_irsend").invoke(irdaService, hex2dec("0000 006d 0022 0002 0152 00aa 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0696 0152 0055 0015 0e23"));
                    // irdaService.write_irsend(hex2dec("0000 006d 0022 0002 0152 00aa 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0696 0152 0055 0015 0e23"));
                    } catch (NoSuchMethodException e) {
            			e.printStackTrace();
                    } catch (IllegalArgumentException e) {
        				e.printStackTrace();
        			} catch (IllegalAccessException e) {
        				e.printStackTrace();
        			} catch (InvocationTargetException e) {
        				e.printStackTrace();
        }
                    
                     
                    ////  irdaService = getSystemService("irda");//
                      
                       
                      // irInit4JellyBean();
                       
                      	callbackContext.success("aja");

                    	
                        // transmit the pattern at 38.4KHz

                        //if (android.os.Build.VERSION.SDK_INT == 19) {
                          /*  int lastIdx = android.os.Build.VERSION.RELEASE.lastIndexOf(".");
                            int VERSION_MR = Integer.valueOf(android.os.Build.VERSION.RELEASE.substring(lastIdx + 1));
                            if (VERSION_MR < 3) {
                                int t = 1000000 / frequency;

                                for (int i = 0; i < signal.length; ++i) {
                                    signal[i] = signal[i] * t;
                                }
                                irService.transmit(38400, signal);
                            } else {*/
   //                             irService.transmit(38400, signal);
                          // }

                       // }
   //                     callbackContext.success("aja");
                       
                    }
                 });
                
            }
            return true;

        } catch ( Exception e  ){
            callbackContext.error("java ".concat(e.getMessage()));
            return false;
        }

    }
    
    
    public void irInit4JellyBean() {
    	 // irdaService = this.getSystemService("irda");
    	/*  Class c = irdaService.getClass();
    	  Class p[] = { String.class };
    	  try {
    	   irWrite = c.getMethod("write_irsend", p);
    	  } catch (NoSuchMethodException e) {
    	   e.printStackTrace();
    	  }*/
    }

    protected String hex2dec(String irData) {
    	  List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
    	  list.remove(0); // dummy
    	  int frequency = Integer.parseInt(list.remove(0), 16); // frequency
    	  list.remove(0); // seq1
    	  list.remove(0); // seq2

    	  for (int i = 0; i < list.size(); i++) {
    	   list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
    	  }

    	  frequency = (int) (1000000 / (frequency * 0.241246));
    	  list.add(0, Integer.toString(frequency));

    	  irData = "";
    	  for (String s : list) {
    	   irData += s + ",";
    	  }
    	  return irData;
    }
    
    
}


/*
 if (ACTION_TRANSMIT_IR_CODE.equals(action)) {

                JSONObject args = jsonArgs.getJSONObject(0);
                final Integer frequency = args.getInt("frequency");
                String strSignal = args.getString("signal");
                final int[] signal;

                String[] items = strSignal.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

                signal = new int[items.length];

                for (int i = 0; i < items.length; i++) {
                    try {
                        signal[i] = Integer.parseInt(items[i]);
                    } catch (NumberFormatException nfe) {
                    }
                    ;
                }


                final Context context = this.cordova.getActivity().getApplicationContext();
                this.cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        callbackContext.success("0");
                        ConsumerIrManager irService = (ConsumerIrManager) context.getSystemService(context.CONSUMER_IR_SERVICE);

                        Log.d("transmit", "lets go");

                        /*if (Build.VERSION.SDK_INT == ANDROID_KITKAT_SDK) {
                            intlastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
                            int VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1));
                            if (VERSION_MR < 3) {
                                // Before version of Android 4.4.2
                                irService.transmit(frequency, signal);
                            } else {
                                // Later version of Android 4.4.3

        irService.transmit(frequency, signal);
        //   }
        callbackContext.success("1");
        }

        });
 }else{
                callbackContext.error("ivalid action");
                return false;
            }
 */