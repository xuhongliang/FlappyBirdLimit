package com.xxx.tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class Score {
	static Context cont;

	public static int score = 0;

	public static void start(Context context){
		Log.i("aaa", "aaaaa");
		if(cont == null){
			cont = context;
			loadScore();
		}
	}
	public static void saveScore(int score) {
		if(cont == null){
			return;
		}
		FileOutputStream fos = null;		  
	    DataOutputStream dos = null;
		try {    
			fos = cont.openFileOutput("score.and1",Context.MODE_PRIVATE);//备注2   
            dos = new DataOutputStream(fos);
            dos.writeInt(score);
            
        } catch (FileNotFoundException e) {     
            e.printStackTrace();   
        } catch (IOException e) {      
            e.printStackTrace();   
        } finally {   
            // 在finally中关闭流 这样即使try中有异常我们也能对其进行关闭操作 ;   
            try {   
                dos.close();   
                fos.close();   
            } catch (IOException e) {     
                e.printStackTrace();   
            }
        }
	}

	public static void loadScore() {
	    FileInputStream fis = null;   
	    DataInputStream dis = null;
		try {    
            // 这里找不到数据文件就会报异常,所以finally里关闭流尤为重要!!!   
            if (cont.openFileInput("score.and1") != null) {     
                 
                fis = cont.openFileInput("score.and1");  
                dis = new DataInputStream(fis);
                score = dis.readInt();        
            }
        } catch (FileNotFoundException e) {    
            e.printStackTrace();   
        } catch (IOException e) {    
            e.printStackTrace();   
        } finally {   
            // 在finally中关闭流!因为如果找不到数据就会异常我们也能对其进行关闭操作 ;   
            try {   
                if (cont.openFileInput("score.and1") != null) {   
                    // 这里也要判断，因为找不到的情况下，两种流也不会实例化。   
                    // 既然没有实例化，还去调用close关闭它,肯定"空指针"异常！！！   
                    if(fis != null)fis.close();   
                }   
            } catch (FileNotFoundException e) {   
                e.printStackTrace();   
            } catch (IOException e) {     
                e.printStackTrace();   
            }   
        }   

	}
}
