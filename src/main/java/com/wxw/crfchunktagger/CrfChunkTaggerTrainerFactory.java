package com.wxw.crfchunktagger;

import java.util.List;
import java.util.Set;

import com.asher_stern.crf.crf.run.CrfTrainer;
import com.asher_stern.crf.crf.run.CrfTrainerFactory;
import com.asher_stern.crf.utilities.TaggedToken;
import com.wxw.feature.StandardFeatureGenerator;
import com.wxw.feature.StandardFilterFactory;

/**
 * ����ѵ�����ϣ�����ѵ��ģ�͵Ķ���
 * @author ��ܰέ
 *
 */
public class CrfChunkTaggerTrainerFactory {

	public CrfChunkTaggerTrainer createTrainer(List<List<? extends TaggedToken<String, String>>> corpus){
		CrfTrainerFactory<String, String> factory = new CrfTrainerFactory<String, String>();
		CrfTrainer<String, String> crfTrainer = factory.createTrainer(corpus, 
				(Iterable<? extends List<? extends TaggedToken<String, String>>> theCorpus, Set<String> tags) -> new StandardFeatureGenerator(theCorpus, tags) , 
				new StandardFilterFactory());
		CrfChunkTaggerTrainer trainer = new CrfChunkTaggerTrainer(crfTrainer);

		return trainer;
	}
}
