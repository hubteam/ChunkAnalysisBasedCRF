package com.wxw.crfchunktagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.asher_stern.crf.crf.CrfModel;
import com.asher_stern.crf.crf.run.CrfInferencePerformer;
import com.asher_stern.crf.utilities.CrfException;
import com.wxw.chunktagger.ChunkTagger;
import com.wxw.chunktagger.ChunkTaggerLoader;

/**
 * 根据路径加载模型
 * @author 王馨苇
 *
 */
public class CrfChunkTaggerLoader implements ChunkTaggerLoader{

	/**
	 * 根据路径得到模型
	 */
	public ChunkTagger load(File directory) {
		if (!directory.exists()) {throw new CrfException("Given directory: "+directory.getAbsolutePath()+" does not exist.");}
		if (!directory.isDirectory()) {throw new CrfException("The loader requires a directory, but was provided with a file: "+directory.getAbsolutePath()+".");}
		
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(directory, CrfChunkTaggerTrainer.SAVE_LOAD_FILE_NAME ))))
		{
			@SuppressWarnings("unchecked")
			CrfModel<String, String> model = (CrfModel<String, String>) inputStream.readObject();
			return new CrfChunkTagger(new CrfInferencePerformer<String, String>(model));
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new CrfException("Loading pos tagger failed.",e);
		}
	}

}
