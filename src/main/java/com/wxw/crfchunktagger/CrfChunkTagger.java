package com.wxw.crfchunktagger;

import java.util.List;

import com.asher_stern.crf.crf.run.CrfInferencePerformer;
import com.asher_stern.crf.utilities.TaggedToken;
import com.wxw.chunktagger.Chunk;
import com.wxw.chunktagger.ChunkTagger;


public class CrfChunkTagger implements ChunkTagger{

	private final CrfInferencePerformer<String, String> inferencePerformer;
	
	public CrfChunkTagger(CrfInferencePerformer<String, String> inferencePerformer){
		this.inferencePerformer = inferencePerformer;
	}
	
	public Chunk[] parse(String sentence) {
		// TODO Auto-generated method stub
		return null;
	}

	public Chunk[][] parse(String sentence, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaggedToken<String, String>> tagSentence(List<String> sentence) {
		return inferencePerformer.tagSequence(sentence);
	}

}
