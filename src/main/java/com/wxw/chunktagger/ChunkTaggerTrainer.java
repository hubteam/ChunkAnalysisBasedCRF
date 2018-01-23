package com.wxw.chunktagger;

import java.io.File;
import java.util.List;

import com.asher_stern.crf.utilities.TaggedToken;

/**
 * ģ��ѵ����
 * @author ��ܰέ
 *
 */
public interface ChunkTaggerTrainer<C extends Iterable<? extends List<? extends TaggedToken<String, String>>>> {

	/**
	 * ѵ��ģ��
	 * @param corpus ����
	 */
	public void train(C corpus);
	
	/**
	 * ���ѵ���õ�ģ��
	 * @return
	 */
	public ChunkTagger getTrainedChunkTagger();
	
	/**
	 * ����ģ�͵�ָ����·��
	 * @param modelDirectory ����ģ�͵�·��
	 */
	public void save(File modelDirectory);
}
