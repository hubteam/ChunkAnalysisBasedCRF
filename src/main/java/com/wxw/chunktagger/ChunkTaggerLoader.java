package com.wxw.chunktagger;

import java.io.File;

/**
 * 加载模型
 * @author 王馨苇
 *
 */
public interface ChunkTaggerLoader {

	/**
	 * 根据模型文件路径加载文件
	 * @param directory 模型文件路径
	 * @return
	 */
	public ChunkTagger load(File directory);
}
