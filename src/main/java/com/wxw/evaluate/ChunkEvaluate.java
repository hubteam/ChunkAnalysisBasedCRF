package com.wxw.evaluate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.asher_stern.crf.postagging.postaggers.PosTagger;
import com.asher_stern.crf.utilities.CrfException;
import com.asher_stern.crf.utilities.TaggedToken;
import com.wxw.chunktagger.ChunkTagger;

/**
 * 
 * @author 王馨苇
 *
 */
public class ChunkEvaluate {

	private final Iterable<? extends List<? extends TaggedToken<String, String>>> corpus;
	private final ChunkTagger chunkTagger;
	private final PrintWriter taggedTestWriter;
	
	private List<String> wordsRef = new ArrayList<>();
	private List<String> tagsRef = new ArrayList<>();
	private List<String> wordsPre = new ArrayList<>();
	private List<String> tagsPre = new ArrayList<>();
	
	private long correct = 0;
	private long incorrect = 0;
	private double accuracy = 0.0;
	
	private static final Logger logger = Logger.getLogger(ChunkEvaluate.class);
	
	public ChunkEvaluate(Iterable<? extends List<? extends TaggedToken<String, String>>> corpus, ChunkTagger chunkTagger)
	{
		this(corpus,chunkTagger,null);
	}

	
	public ChunkEvaluate(Iterable<? extends List<? extends TaggedToken<String, String>>> corpus, ChunkTagger chunkTagger, PrintWriter taggedTestWriter)
	{
		super();
		this.corpus = corpus;
		this.chunkTagger = chunkTagger;
		this.taggedTestWriter = taggedTestWriter;
	}
	
	public void evaluate()
	{
		ChunkMeasure measure = new ChunkMeasure();
		
		Iterator<? extends List<? extends TaggedToken<String, String>>> reader = corpus.iterator();
		
		int debug_index=0;
		while (reader.hasNext())
		{
			List<? extends TaggedToken<String, String>> taggedSentence = reader.next();
			++debug_index;
			wordsRef.clear();
			tagsRef.clear();
			wordsPre.clear();
			tagsPre.clear();
			List<String> sentence = taggedSentenceToSentence(taggedSentence);
			//得到的结果
			List<TaggedToken<String,String>> taggedByPosTagger = chunkTagger.tagSentence(sentence);
			taggedSentenceToSentencePre(taggedByPosTagger);
			
//			evaluateSentence(taggedSentence,taggedByPosTagger);
			
//			if (taggedTestWriter!=null)
//			{
//				taggedTestWriter.println(printSentence(taggedSentence));
//				taggedTestWriter.println(printSentence(taggedByPosTagger));
//			}
			
			if (logger.isDebugEnabled())
			{
				if ((debug_index%100)==0){
					logger.debug("Evaluated: "+debug_index);
				}	
			}
		}
//		if (taggedTestWriter!=null) {taggedTestWriter.flush();}
		
//		accuracy = ((double)correct)/((double)(correct+incorrect));
	}

//	private void evaluateSentence(List<? extends TaggedToken<String,String>> taggedSentence, List<TaggedToken<String,String>> taggedByPosTagger)
//	{
//		Iterator<? extends TaggedToken<String,String>> iteratorTaggedOriginal = taggedSentence.iterator();
//		Iterator<? extends TaggedToken<String,String>> iteratorTaggedByPosTagger = taggedByPosTagger.iterator();
//		while (iteratorTaggedOriginal.hasNext() && iteratorTaggedByPosTagger.hasNext())
//		{
//			TaggedToken<String,String> original = iteratorTaggedOriginal.next();
//			TaggedToken<String,String> byPosTagger = iteratorTaggedByPosTagger.next();
//			if (!(equals(original.getToken(),byPosTagger.getToken()))) {throw new CrfException("Tokens not equal in evaluation.");}
//			if (equals(original.getTag(),byPosTagger.getTag()))
//			{
//				++correct;
//			}
//			else
//			{
//				++incorrect;
//			}
//		}
//		if (iteratorTaggedOriginal.hasNext() || iteratorTaggedByPosTagger.hasNext())
//		{
//			throw new CrfException("Sentences sizes are not equal in evaluation.");
//		}
//	}
	
	private String printSentence(List<? extends TaggedToken<String,String>> taggedSentence)
    {
		StringBuilder sb = new StringBuilder();
		for (TaggedToken<String,String> taggedToken : taggedSentence)
		{
			sb.append(taggedToken.getToken()).append("/").append( String.format("%-4s", taggedToken.getTag()) ).append(" ");
		}
		return sb.toString();
	}
	
	private List<String> taggedSentenceToSentence(List<? extends TaggedToken<String, String>> taggedSentence)
	{
		List<String> ret = new ArrayList<String>(taggedSentence.size());
		for (TaggedToken<String, String> token : taggedSentence)
		{
			ret.add(token.getToken().split("_")[0]);
			wordsRef.add(token.getToken().split("_")[0]);
			tagsRef.add(token.getTag());
		}
		return ret;
	}
	
	private void taggedSentenceToSentencePre(List<? extends TaggedToken<String, String>> taggedSentence)
	{
		for (TaggedToken<String, String> token : taggedSentence)
		{
			wordsPre.add(token.getToken().split("_")[0]);
			tagsPre.add(token.getTag());
		}
	}
	
	private static boolean equals(Object object1, Object object2)
	{
		if (object1==object2) return true;
		else if ( (object1==null) || (object2==null) ) return false;
		return object1.equals(object2);
	}
}
