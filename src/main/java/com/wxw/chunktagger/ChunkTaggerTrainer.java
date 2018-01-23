package com.wxw.chunktagger;

import java.io.File;
import java.util.List;

import com.asher_stern.crf.utilities.TaggedToken;

/**
 * 模型训练类
 * @author 王馨苇
 *
 */
public interface ChunkTaggerTrainer<C extends Iterable<? extends List<? extends TaggedToken<String, String>>>> {

	/**
	 * 训练模型
	 * @param corpus 语料
	 */
	public void train(C corpus);
	
	/**
	 * 获得训练好的模型
	 * @return
	 */
	public ChunkTagger getTrainedChunkTagger();
	
	/**
	 * 保存模型到指定的路径
	 * @param modelDirectory 保存模型的路径
	 */
	public void save(File modelDirectory);
}
