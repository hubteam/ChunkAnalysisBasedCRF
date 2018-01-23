package com.wxw.evaluate;

public class ChunkMeasure {

	/**
     * |selected| = true positives + false positives <br>
     * 预测的样本数
     */
    private long selected;

    /**
     * |target| = true positives + false negatives <br>
     * 参考的样本数
     */
    private long target;
  
    
    /**
     * 预测正确的个数
     */
    private long truePositive;
    /**
	 * 更新计算指标的一些变量【词性标注的指标】
	 * @param wordsRef 参考的分词结果
	 * @param tagsRef 参考的词性标记
	 * @param wordsPre 预测的分词结果
	 * @param tagsPre 预测的词性标记
	 */
	public void update(String[] wordsRef, String[] tagsRef, String[] wordsPre, String[] tagsPre) {
		//定义变量记录当前扫描的总长度
    	int countRef = 0,countPre = 0;
    	//记录当前所在的词的位置
    	int i = 0,j = 0;
    	if(wordsRef.length > 0 && wordsPre.length > 0){
    		while(wordsRef[i] != null || !("".equals(wordsRef[i]))|| wordsPre[j] != null || !("".equals(wordsPre[j]))){
        		countRef += wordsRef[i].length();
        		countPre += wordsPre[j].length();
        		
        		//匹配的情况
        		if((wordsRef[i] == wordsPre[j] || wordsRef[i].equals(wordsPre[j]))){

        			if((!tagsRef[i].equals("o") && !tagsPre[j].equals("o")) && (tagsRef[i] == tagsPre[j] || tagsRef[i].equals(tagsPre[j]))){
        					
        				truePositive++;        				
        			}       			
    				//两个字符串同时向后扫描
        			i++;j++;
        			//为了防止：已经到达边界了，还用references[i]或者predictions[i]来判断，此时越界了
    				if(i >= wordsRef.length || j >= wordsPre.length)
    					break;
   
        		}else{
        			//不匹配的情况，则需要比较当前扫过的总长度
        			//（1）：长度长的那个不动，长度短的那个要继续向前扫描比较   			
        			if(countRef > countPre){
        				j++;
    					countRef -= wordsRef[i].length();
    					if(j >= wordsPre.length)
    						break;
    					//（2）：长度相等的时候，二者都需要向前扫描
    				}else if(countRef == countPre){
    					i++;j++;
    					if(i >= wordsRef.length || j >= wordsPre.length)
    						break;
    					//（1）：长度长的那个不动，长度短的那个要继续向前扫描比较
    				}else if(countRef < countPre){
    					i++;
    					countPre -= wordsPre[j].length();
    					if(i >= wordsRef.length)
    						break;
    				}
        		}
        	}
    	}
    	for (int k = 0; k < tagsPre.length; k++) {
    		if(!tagsPre[k].equals("o")){
    			selected++;
    		}   		   		
		}
    	for (int k = 0; k < tagsRef.length; k++) {
    		if(!tagsRef[k].equals("o")){
    			target++;
    		}
		}
	}
	
	/**
	 * 命名实体识别准确率
	 * @return
	 */
	public double getPrecisionScore() {
		return selected > 0 ? (double) truePositive / (double) selected : 0;
	}

	/**
	 * 命名实体识别召回率
	 * @return
	 */
	public double getRecallScore() { 
		return target > 0 ? (double) truePositive / (double) target : 0;
	}
	
	/**
	 * 命名实体识别F值
	 * @return
	 */
	public double getMeasure() {

        if (getPrecisionScore() + getRecallScore() > 0) {
            return 2 * (getPrecisionScore() * getRecallScore())
                    / (getPrecisionScore() + getRecallScore());
        } else {
            // cannot divide by zero, return error code
            return -1;
        }
    }
	
	@Override
	public String toString() {
		return "Precision: " + Double.toString(getPrecisionScore()) + "\n"
        + "Recall: " + Double.toString(getRecallScore()) + "\n" 
		+ "F-Measure: "+ Double.toString(getMeasure()) + "\n";
	}
}
