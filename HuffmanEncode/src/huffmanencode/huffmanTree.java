/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanencode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

public class huffmanTree implements Serializable{
    Long length;
    Byte ch = null;
    double weight;
    huffmanTree left;
    huffmanTree right;
    
    //用字符和权重初始化一个霍夫曼树节点
    public huffmanTree(Byte ch,double weight) {
        this.ch = ch;
        this.weight = weight;
    }
    
    public huffmanTree(){}
    
    //工厂模式 用具体的某个刘内容产生其对应的霍夫曼树
    public static huffmanTree huffmanTreeFactory(InputStream is) throws IOException{
        
        Long[] times = new Long[256];
        Long sumLen = 0L;
        for(int i=0;i<times.length;i++)
            times[i] = 0L;
        int bit = is.read();
        while(bit!=-1){
            times[bit]++;
            bit = is.read();
        }
        for(Long num:times)
            sumLen += num;
        double[] weight = new double[256];
        for(int i=0;i<weight.length;i++)
            weight[i] = ((double)times[i])/sumLen;
        PriorityQueue<huffmanTree> heap = new PriorityQueue<>(new Comparator<huffmanTree>(){
            @Override
            public int compare(huffmanTree o1, huffmanTree o2) {
                return o1.weight-o2.weight>0?1:0;
            }
        });
        
        byte b = 0;
        for(int i=0;i<weight.length;i++,b++){
            heap.add(new huffmanTree(b, weight[i]));
        }
        for(;heap.size()>1;){
            huffmanTree t1 = heap.poll();
            huffmanTree t2 = heap.poll();
            huffmanTree fa = new huffmanTree(null,t1.weight+t2.weight);
            fa.left = t1;
            fa.right = t2;
            heap.add(fa);
        }
        huffmanTree ans = heap.poll();
        ans.length = sumLen;
        return ans;       
    }
   
   
    public boolean equals(huffmanTree root) {
        return same(this,root);
    }
    
    //判断两棵树是否一致
    public boolean same(huffmanTree r1,huffmanTree r2){
        if(r1==r2)
            return true;
        if(r1.ch==r2.ch);
        else if(r1.ch==null||r2.ch==null)
            return false;
        else if(r1.ch.byteValue()!=r2.ch.byteValue())
            return false;
        if(r1.weight!=r2.weight)
            return false;
        return same(r1.left,r2.left)&&same(r1.right,r2.right);
    }
    
    //缺少某序列化函数
}
   
