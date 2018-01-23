package com.wxw.chunktagger;

import java.util.List;

import com.asher_stern.crf.utilities.TaggedToken;

public interface ChunkTagger {

	/**
	 * ���ظ������ӵ������Ϣ
	 * @param sentence	�����������Ϣ�ľ���
	 * @return			���ӵ������Ϣ
	 */
	public Chunk[] parse(String sentence);
	
	/**
	 * ���ظ������ӵ����ŵ�k�������Ϣ
	 * @param sentence	�����������Ϣ�ľ���
	 * @param k			���������Ϣ�ĺ�ѡ����
	 * @return			���ŵ�k�������Ϣ
	 */
	public Chunk[][] parse(String sentence, int k);
	
	public List<TaggedToken<String,String>> tagSentence(List<String> sentence);
}
