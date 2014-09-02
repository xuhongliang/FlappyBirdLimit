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
			fos = cont.openFileOutput("score.and1",Context.MODE_PRIVATE);//��ע2   
            dos = new DataOutputStream(fos);
            dos.writeInt(score);
            
        } catch (FileNotFoundException e) {     
            e.printStackTrace();   
        } catch (IOException e) {      
            e.printStackTrace();   
        } finally {   
            // ��finally�йر��� ������ʹtry�����쳣����Ҳ�ܶ�����йرղ��� ;   
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
            // �����Ҳ��������ļ��ͻᱨ�쳣,����finally��ر�����Ϊ��Ҫ!!!   
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
            // ��finally�йر���!��Ϊ����Ҳ������ݾͻ��쳣����Ҳ�ܶ�����йرղ��� ;   
            try {   
                if (cont.openFileInput("score.and1") != null) {   
                    // ����ҲҪ�жϣ���Ϊ�Ҳ���������£�������Ҳ����ʵ������   
                    // ��Ȼû��ʵ��������ȥ����close�ر���,�϶�"��ָ��"�쳣������   
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
