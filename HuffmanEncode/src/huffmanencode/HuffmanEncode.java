/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanencode;

import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;
import java.io.*;
/**
 *
 * @author 敲可爱
 */
public class HuffmanEncode {
    huffmanTree root;
    HashMap<Byte,String> codeset = new HashMap<>();
    long length;
    
    //用霍夫曼树对编码器进行初始化
    public HuffmanEncode(huffmanTree root){
        this.root = root;
        StringBuilder str = new StringBuilder();
        generateCodeset(root, str);
        this.length = root.length;
    }
    
    //利用某棵霍夫曼树创建字典 str非空即可
    public void generateCodeset(huffmanTree root,StringBuilder str){
        if(root.left==root.right){
            codeset.put((byte)(root.ch.byteValue()+128), str.toString());
            return;
        }
        str.append("0");
        generateCodeset(root.left, str);
        str.deleteCharAt(str.length()-1);
        str.append("1");
        generateCodeset(root.right, str);
        str.deleteCharAt(str.length()-1);       
    }
    
    //测试代码 无用
    public static void geHuffmanEncode() throws IOException{
        InputStream is = new FileInputStream("huffin.txt");
        huffmanTree tree = huffmanTree.huffmanTreeFactory(is);
        OutputStream os = new FileOutputStream("huffmanTree.txt");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(tree);
        oos.writeLong(37845567L);               
        oos.flush();
    }
    
    //加密 用某棵霍夫曼树的编码 进行编码
    public void encode(huffmanTree root,InputStream is,OutputStream os)throws IOException{
        StringBuilder bui = new StringBuilder();
        generateCodeset(root, bui);
        int read = is.read();
        
        
        for(;read!=-1;){
            bui.append(codeset.get((byte)read));
            if(bui.length()>50000&&bui.length()%8==0){
                write(bui.toString(),os);
                bui = new StringBuilder();
            }
            read = is.read();
        }
        for(;bui.length()%8!=0;)
            bui.append("0");
        write(bui.toString(),os);
    }
    
    
    //将字符串以比特形式写出
    public static void write(String str,OutputStream os) throws IOException {
        for(int i=0;i<str.length();i++){
            int  sum = 0;
            for(int balance = 128;balance>0;balance>>=1){
                sum+=str.charAt(i)=='0'?0:balance;
            }
            os.write(sum);
        }
    }
    
    public static void main(String[] args) {
        try{
        geHuffmanEncode();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("huffmanTree.txt")));
        Object deSeri = ois.readObject();
        huffmanTree a = (huffmanTree)deSeri;
        for(;ois.available()!=0;){
            //System.out.println(ois.available());
            System.out.println(ois.readLong());
        }
        HuffmanEncode test = new HuffmanEncode(a);
        for(byte key:test.codeset.keySet())
                System.out.println(key+":"+test.codeset.get(key));
        //System.out.println(tree.equals(a));
        
        /*
        try{
        FileReader file = new FileReader("huffin.txt");
        Scanner sc = new Scanner(file);
        Byte bytes = sc.nextByte();
        
        
        }
        catch(Exception e){
            e.printStackTrace();
        }
        */
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



    
    
    