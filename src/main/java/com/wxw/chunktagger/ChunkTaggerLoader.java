package com.wxw.chunktagger;

import java.io.File;

/**
 * ����ģ��
 * @author ��ܰέ
 *
 */
public interface ChunkTaggerLoader {

	/**
	 * ����ģ���ļ�·�������ļ�
	 * @param directory ģ���ļ�·��
	 * @return
	 */
	public ChunkTagger load(File directory);
}
