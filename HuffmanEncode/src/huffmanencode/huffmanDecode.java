/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanencode;

import java.util.*;
import java.io.*;

/**
 *
 * @author 敲可爱
 */
public class huffmanDecode {
    huffmanTree root;
    public huffmanDecode(huffmanTree root) {
        this.root = root;
    }
    
    public void decode(InputStream is,OutputStream os,Long length)throws IOException{
        Long len = 0L;
        int read = is.read();
        huffmanTree cur = root;
        while(read!=-1&&len<length){
            for(int bit = 128;bit>0;bit>>=1){
                if((bit&read)!=0)
                    cur = cur.right;
                else
                    cur = cur.left;
                if(cur.ch!=null){
                    int num = 0;
                    for(int balance = 128;balance>0;balance>>=1)
                        if((balance&cur.ch)!=0)
                            num+=balance;
                    os.write(num);
                    len++;
                    cur = root;
                }
            }
        }
    }
      
    
    public static void decode(huffmanTree root,InputStream is,OutputStream os,Long length)throws IOException{    
        Long len = 0L;
        int read = is.read();
        huffmanTree cur = root;
        while(read!=-1&&len<length){
            for(int bit = 128;bit>0;bit>>=1){
                if((bit&read)!=0)
                    cur = cur.right;
                else
                    cur = cur.left;
                if(cur.ch!=null){
                    int num = 0;
                    for(int balance = 128;balance>0;balance>>=1)
                        if((balance&cur.ch)!=0)
                            num+=balance;
                    os.write(num);
                    len++;
                    cur = root;
                }
            }
        }
        
    }
}
