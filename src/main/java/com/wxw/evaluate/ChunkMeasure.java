package com.wxw.evaluate;

public class ChunkMeasure {

	/**
     * |selected| = true positives + false positives <br>
     * Ԥ���������
     */
    private long selected;

    /**
     * |target| = true positives + false negatives <br>
     * �ο���������
     */
    private long target;
  
    
    /**
     * Ԥ����ȷ�ĸ���
     */
    private long truePositive;
    /**
	 * ���¼���ָ���һЩ���������Ա�ע��ָ�꡿
	 * @param wordsRef �ο��ķִʽ��
	 * @param tagsRef �ο��Ĵ��Ա��
	 * @param wordsPre Ԥ��ķִʽ��
	 * @param tagsPre Ԥ��Ĵ��Ա��
	 */
	public void update(String[] wordsRef, String[] tagsRef, String[] wordsPre, String[] tagsPre) {
		//���������¼��ǰɨ����ܳ���
    	int countRef = 0,countPre = 0;
    	//��¼��ǰ���ڵĴʵ�λ��
    	int i = 0,j = 0;
    	if(wordsRef.length > 0 && wordsPre.length > 0){
    		while(wordsRef[i] != null || !("".equals(wordsRef[i]))|| wordsPre[j] != null || !("".equals(wordsPre[j]))){
        		countRef += wordsRef[i].length();
        		countPre += wordsPre[j].length();
        		
        		//ƥ������
        		if((wordsRef[i] == wordsPre[j] || wordsRef[i].equals(wordsPre[j]))){

        			if((!tagsRef[i].equals("o") && !tagsPre[j].equals("o")) && (tagsRef[i] == tagsPre[j] || tagsRef[i].equals(tagsPre[j]))){
        					
        				truePositive++;        				
        			}       			
    				//�����ַ���ͬʱ���ɨ��
        			i++;j++;
        			//Ϊ�˷�ֹ���Ѿ�����߽��ˣ�����references[i]����predictions[i]���жϣ���ʱԽ����
    				if(i >= wordsRef.length || j >= wordsPre.length)
    					break;
   
        		}else{
        			//��ƥ������������Ҫ�Ƚϵ�ǰɨ�����ܳ���
        			//��1�������ȳ����Ǹ����������ȶ̵��Ǹ�Ҫ������ǰɨ��Ƚ�   			
        			if(countRef > countPre){
        				j++;
    					countRef -= wordsRef[i].length();
    					if(j >= wordsPre.length)
    						break;
    					//��2����������ȵ�ʱ�򣬶��߶���Ҫ��ǰɨ��
    				}else if(countRef == countPre){
    					i++;j++;
    					if(i >= wordsRef.length || j >= wordsPre.length)
    						break;
    					//��1�������ȳ����Ǹ����������ȶ̵��Ǹ�Ҫ������ǰɨ��Ƚ�
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
	 * ����ʵ��ʶ��׼ȷ��
	 * @return
	 */
	public double getPrecisionScore() {
		return selected > 0 ? (double) truePositive / (double) selected : 0;
	}

	/**
	 * ����ʵ��ʶ���ٻ���
	 * @return
	 */
	public double getRecallScore() { 
		return target > 0 ? (double) truePositive / (double) target : 0;
	}
	
	/**
	 * ����ʵ��ʶ��Fֵ
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
